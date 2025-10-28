-- Initialize roles for the application
INSERT IGNORE INTO roles (id, role_name, created_at, updated_at, created_by, updated_by) VALUES 
(1, 'USER', NOW(), NOW(), 'system', 'system'),
(2, 'CARDEALER', NOW(), NOW(), 'system', 'system'),
(3, 'ADMIN', NOW(), NOW(), 'system', 'system');

-- Initialize addresses for common cities
INSERT IGNORE INTO address (pickup_address, drop_address, created_at, updated_at, created_by, updated_by) VALUES
('Chandigarh Railway Station, Chandigarh', 'Sector 17, Chandigarh', NOW(), NOW(), 'system', 'system'),
('Indira Gandhi International Airport, Delhi', 'Connaught Place, New Delhi', NOW(), NOW(), 'system', 'system'),
('Gorakhpur Railway Station, Gorakhpur', 'Gorakhpur City Center, Gorakhpur', NOW(), NOW(), 'system', 'system'),
('Sector 43, Chandigarh', 'Panjab University, Chandigarh', NOW(), NOW(), 'system', 'system'),
('Red Fort, Old Delhi', 'India Gate, New Delhi', NOW(), NOW(), 'system', 'system'),
('Gorakhpur Bus Stand, Gorakhpur', 'Tulsipur, Gorakhpur', NOW(), NOW(), 'system', 'system');

-- Note: This will only insert if roles/addresses don't exist
-- If running multiple times, you may need to clear tables first

