package com.district12.backend.services;

import com.district12.backend.dtos.JwtTokenResponse;
import com.district12.backend.dtos.SecurityUser;
import com.district12.backend.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${auth.jwt.audiences}")
    private String[] audiences;

    @Value("${auth.jwt.timeout}")
    private int timeout;

    @Value("${auth.jwt.issuer}")
    private String issuer;

    private final JwtEncoder jwtEncoder;
    private final RefreshTokenService refreshTokenService;

    @Override
    public JwtTokenResponse generateToken(Authentication authentication) {
        var scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        var securityUser = (SecurityUser) authentication.getPrincipal();

        var claims = getClaimSet(securityUser.getId().toString(), scope);
        JwsHeader header = getHeader();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        String refreshToken = refreshTokenService.getRefreshTokenForUser(securityUser.getId());
        return new JwtTokenResponse(token, refreshToken, securityUser.getId());
    }

    @Override
    public JwtTokenResponse generateToken(String refreshToken) {
        User user = refreshTokenService.verifyToken(refreshToken);

        var claims = getClaimSet(user.getId().toString(), user.getRole().toString());
        JwsHeader header = getHeader();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        return new JwtTokenResponse(token, refreshToken, user.getId());
    }

    private JwtClaimsSet getClaimSet(String sub, String scope){
        return  JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(Instant.now())
                .audience(List.of(audiences))
                .expiresAt(Instant.now().plusSeconds(timeout))
                .subject(sub)
                .claim("scp", scope)
                .build();
    }

    private JwsHeader getHeader(){
        return JwsHeader.with(() -> "HS256").build();
    }
}
