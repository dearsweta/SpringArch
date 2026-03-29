INSERT INTO categories (name)
VALUES ('Fruits'),
       ('Vegetables'),
       ('Dairy'),
       ('Beverages'),
       ('Snacks');

INSERT INTO products (name, price, category_id, description)
VALUES
-- Fruits
('Apple (1kg)', 120.00, 1, 'Fresh red apples from Himachal Pradesh'),
('Banana (1 dozen)', 60.00, 1, 'Naturally ripened bananas rich in potassium'),

-- Vegetables
('Tomato (1kg)', 40.00, 2, 'Farm fresh tomatoes for daily cooking'),
('Potato (1kg)', 30.00, 2, 'Staple vegetable for Indian households'),

-- Dairy
('Amul Milk (1L)', 65.00, 3, 'Full cream milk from Amul'),
('Paneer (200g)', 90.00, 3, 'Fresh cottage cheese for cooking'),

-- Beverages
('Coca Cola (750ml)', 50.00, 4, 'Chilled soft drink'),
('Tropicana Orange Juice (1L)', 110.00, 4, '100% orange juice'),

-- Snacks
('Lays Classic Chips', 20.00, 5, 'Lightly salted potato chips'),
('Parle-G Biscuits', 10.00, 5, 'Popular glucose biscuits');