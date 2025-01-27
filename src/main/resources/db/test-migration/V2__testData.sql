INSERT INTO users (email, full_name, password, role, phone_number)
VALUES
    ('john.doe@example.com', 'John Doe', '$2a$10$Ag/EVWeldbSzlxzMHWPdKezgYSL41TWXlcz7iNCiH5F9VCP/hRIcG', 'USER', '1234567890'),
    ('jane.smith@example.com', 'Jane Smith', '$2a$10$Ag/EVWeldbSzlxzMHWPdKezgYSL41TWXlcz7iNCiH5F9VCP/hRIcG', 'USER', '0987654321'),
    ('admin@example.com', 'Admin User', '$2a$10$Ag/EVWeldbSzlxzMHWPdKezgYSL41TWXlcz7iNCiH5F9VCP/hRIcG', 'ADMIN', NULL);


INSERT INTO categories (name, description)
VALUES
    ('Electronics', 'Devices and gadgets such as phones, laptops, and accessories'),
    ('Books', 'Fiction, non-fiction, and educational books'),
    ('Clothing', 'Men, women, and kids apparel');


INSERT INTO products (name, description, price, stock, category_id)
VALUES
    ('Smartphone', 'Latest model with advanced features', 699.99, 50, 1),
    ('Laptop', 'High-performance laptop for work and play', 1199.99, 30, 1),
    ('Science Fiction Book', 'A popular sci-fi novel', 14.99, 100, 2),
    ('T-Shirt', 'Comfortable cotton t-shirt', 9.99, 200, 3);


INSERT INTO addresses (name, address, city, zip_code, user_id)
VALUES
    ('Home', '123 Main St', 'Springfield', 12345, 1),
    ('Office', '456 Market Ave', 'Shelbyville', 54321, 2);

INSERT INTO orders (user_id, timestamp, status)
VALUES
    (1, NOW(), 'CHECKED_OUT'),
    (2, NOW(), 'CONFIRMED');

INSERT INTO cart_items (user_id, product_id, order_id, quantity)
VALUES
    (1, 1, 1, 2), -- User 1 ordered 2 smartphones in order 1
    (2, 3, 2, 1), -- User 2 ordered 1 science fiction book in order 2
    (1, 4, 1, 3); -- User 1 has 3 t-shirts in their cart (not checked out yet)

