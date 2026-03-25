
DELIMITER $$

CREATE PROCEDURE findProductsByPriceBetween(
    minPrice DECIMAL(10,2),
    maxPrice DECIMAL(10,2)
)
BEGIN
    SELECT id, name, price, description, category_id
    FROM products
    where price between minPrice and maxPrice
    ORDER BY name;


END $$

DELIMITER ;