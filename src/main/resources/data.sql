-- Mock data for testing

-- Insert festival
INSERT INTO festival (title, place, start_at, end_at) VALUES
('2025 Summer Music Festival', 'Seoul Olympic Park', '2025-07-01 10:00:00', '2025-07-03 22:00:00');

-- Insert booths
INSERT INTO booth (festival_id, title, place, type, start_at, end_at, total_review_count, avg_review_rating) VALUES
(1, 'BBQ Chicken Stand', 'Zone A-1', 'F&B', '2025-07-01 10:00:00', '2025-07-03 22:00:00', 0, 0.00),
(1, 'Burger House', 'Zone A-2', 'F&B', '2025-07-01 10:00:00', '2025-07-03 22:00:00', 0, 0.00),
(1, 'Official Merchandise', 'Zone B-1', 'MD', '2025-07-01 10:00:00', '2025-07-03 22:00:00', 0, 0.00),
(1, 'Artist Popup Store', 'Zone B-2', 'POPUP', '2025-07-01 10:00:00', '2025-07-03 22:00:00', 0, 0.00);

-- Insert menus
INSERT INTO menu (booth_id, name, price, image_url) VALUES
(1, 'BBQ Chicken Wings', 15000, 'https://example.com/chicken.jpg'),
(1, 'French Fries', 5000, 'https://example.com/fries.jpg'),
(2, 'Classic Burger', 12000, 'https://example.com/burger.jpg'),
(2, 'Cheese Burger', 14000, 'https://example.com/cheeseburger.jpg'),
(2, 'Soda', 3000, 'https://example.com/soda.jpg'),
(3, 'Festival T-Shirt', 35000, 'https://example.com/tshirt.jpg'),
(3, 'Official Cap', 25000, 'https://example.com/cap.jpg'),
(4, 'Artist Poster', 20000, 'https://example.com/poster.jpg');

-- Insert sample ticket
INSERT INTO ticket (user_id, ticket_number, is_used, verified_at) VALUES
(1, 'TCKT-2025-SAMPLE-0001', FALSE, NULL);
