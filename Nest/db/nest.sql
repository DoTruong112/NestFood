create database nest;
use nest;

-- Tạo bảng account
CREATE TABLE account (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  address VARCHAR(200) NOT NULL,
  avatar VARCHAR(200)
);
-- Tạo bảng product 
CREATE TABLE product (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  description VARCHAR(225) NOT NULL,
  image VARCHAR(100),
  category_id INT,
  mfg DATETIME NOT NULL,
  FOREIGN KEY (category_id) REFERENCES category(id)
);


-- Tạo bảng category
CREATE TABLE category (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL
);
-- Tạo bảng bill
	CREATE TABLE bill (
	  id INT PRIMARY KEY AUTO_INCREMENT,
	  account_id INT,
	  order_date DATETIME NOT NULL,
	  total_amount DECIMAL(10, 2) NOT NULL,
	  FOREIGN KEY (account_id) REFERENCES account(id)
	);
-- Tạo bảng orderdetail
CREATE TABLE orderdetail (
  id INT PRIMARY KEY AUTO_INCREMENT,
  order_id INT,
  product_id INT,
  quantity INT NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  FOREIGN KEY (order_id) REFERENCES bill(id),
  FOREIGN KEY (product_id) REFERENCES product(id)
);
-- Tạo bảng cart 
CREATE TABLE cart (
  id INT PRIMARY KEY AUTO_INCREMENT,
  account_id INT,
  product_id INT,
  quantity INT NOT NULL,
  FOREIGN KEY (account_id) REFERENCES account(id),
  FOREIGN KEY (product_id) REFERENCES product(id)
);
-- insert table account
INSERT INTO account (username, password, email, full_name, address, avatar)
VALUES ('p_truong', '123', 'dotruong0108t@gmail.com', 'Đỗ Nguyễn Phi Trường', 'Quận 12, TP HCM', '');

-- insert table category
INSERT INTO category (name)
VALUES ('Vegetables');
INSERT INTO category (name)
VALUES 
('Milk'),
('Coffes & Teas'),
('Fruits'),
('Meats'),
('Spices & Accessories'),
('Seafood')
;
INSERT INTO category (name)
VALUES 
('Fastfood')
;

-- Tạo trigger để mặc định mfg(ngày sản xuất)  trong table product là now()
DELIMITER ;;
CREATE TRIGGER `set_default_mfg_trigger`
BEFORE INSERT ON product
FOR EACH ROW
BEGIN
  IF NEW.mfg IS NULL THEN
    SET NEW.mfg = NOW();
  END IF;
END;;
DELIMITER ;

-- insert table product
INSERT INTO product (name, price, description, category_id, image)
VALUES ('Bắp cải trắng Nest Food 500G', 34500, 'Bắp cải trắng tươi giòn túi 500g đảm bảo độ tươi ngon không dư lượng thuốc trừ sâu 
an toàn cho người sử dụng, giá hợp lý tại Nest Food', 1, 'product-16-1.jpg');
INSERT INTO product (name, price, description, category_id, image)
VALUES 
('Xoài Thái', 35000, 'Quả xoài là một thứ quả ngon có giá trị lớn so với nhiều quả khác. 
Cách thưởng thức thú vị là thái xoài thành 
từng miếng mỏng ngâm trong rượu vang và đường, thêm ít quế cho thơm. 
Tuy nhiên, xoài không chỉ là nguồn cung cấp Vitamin C hiệu quả…', 4, 'product-1-1.jpg'),
('Cam Vàng Navel Nam Phi', 155000, 'Cam Vàng Navel Nam Phi – chính hiệu là một trong những loại trái cây nhập khẩu được bán tại VinFruits 
(100% nói không với chất bảo quản & trái cây Trung Quốc).', 4, 'product-3-1.jpg')
;
INSERT INTO product (name, price, description, category_id, image)
VALUES 
('Bắp ngọt hữu cơ 500g', 44000, 'Ngô ngọt là kết quả xuất hiện tự nhiên của đặc tính lặn của gen điều khiển việc chuyển
 đường thành tinh bột bên trong nội nhũ của hạt ngô.', 1, 'product-2-1.jpg');
 INSERT INTO product (name, price, description, category_id, image)
VALUES
('Ớt ngọt Baby 250g', 36255, 'Ớt trái cây ngọt baby là dòng ớt quả nhỏ, có vị ngọt, hương thơm, không cay, rất thích hợp cho ăn sống, món xào ngon, như là một 
sự bổ sung cho món salad và tất nhiên, hoàn hảo cho một bữa ăn nhẹ lành mạnh.', 4, 'product-4-1.jpg');
INSERT INTO product (name, price, description, category_id, image)
VALUES
('Chanh vàng nhập khẩu 500g', 37500, 'Chanh vàng (lemon) hay còn được gọi là chanh tây. Loại chanh này thường phân bố ở vùng khí hậu ấm.', 4, 'product-5-1.jpg');
INSERT INTO product (name, price, description, category_id, image)
VALUES
('Sữa tươi TH true Milk 1L', 37000, '﻿Sữa tươi tiệt trùng TH true Milk ít đường với hương vị thơm ngon thanh mát, giữ trọn vẹn dinh dưỡng từ sữa tươi sạch cho cả nhà vui khỏe mỗi ngày', 2, 'product-17-2.jpg');
INSERT INTO product (name, price, description, category_id, image)
VALUES
('Cà phê phô mai muối', 45000, '﻿Sự  hoàn hảo giữa hạt cà phê Robusta & Arabica thượng hạng được trồng trên những vùng cao nguyên Việt Nam màu mỡ, qua những bí quyết rang xay độc đáo.', 3, 'product-18-1.jpg');
INSERT INTO product (name, price, description, category_id, image)
VALUES
('Thịt thăn bò bít tết 500g', 80000, '﻿Thăn ngoại bò là phần thịt nằm ở tảng lưng, đã tách riêng phần thăn nội, toàn bộ xương và sụn. Tỷ lệ nạc cao xen kẽ với vân mỡ mịn đẹp tạo độ mềm mại và thơm ngon đặc biệt.', 5, 'product-8-1.jpg'),
('Cá ngừ đại dương phi lê 1kg ', 260000, '﻿Cá ngừ đại dương phi lê, bỏ xương và da. Cá tươi ngon, đẹp mắt, thích hợp ăn sống, steak...', 7, 'product-6-2.jpg'),
('Cá Basa phi lê 1kg', 84567, '﻿Cá Basa đông lạnh làm sạch.', 7, 'product-7-1.jpg'),
('Củ dền hữu cơ 500g', 67500, '﻿Củ dền cũng được sử dụng như các loại củ khác để xào hoặc nấu canh. Có thể xào với thịt, nấu canh thịt hoặc hầm với xương; cũng dùng luộc ăn chấm mắm, xì dầu. Ta thường dùng nấu chín ăn, nhưng có thể dùng củ nạo ra ăn trong những đĩa rau sống.', 1, 'product-10-1.jpg'),
('Bánh Hamburger bò nướng phô mai', 65000, '﻿Hamburger là một loại thức ăn bao gồm bánh mì kẹp thịt xay ở giữa. Miếng thịt có thể được nướng, chiên, hun khói hay nướng trên lửa.', 8, 'product-14-1.jpg'),
('Pizza Hawaii size M - 20cm', 150000, '﻿Pizza nướng sẵn  sẵn với các vị Xúc xích - Bò băm - Hải  sản - Gà BBQ - Hawaii ♨️🍕 Nguyên liệu tươi sạch, giá cả hợp lí, chất lượng tuyệt vời.', 8, 'product-15-1.jpg')
;

update product set name = 'Chanh vàng nhập khẩu' where id = '28';
update product set image = 'product-18-2.jpeg' where id = '38';
update product set name = 'Hamburger bò nướng' where id = '43';

-- Tạo trigger để mặc định order_date trong table bill là now()
DELIMITER ;;
CREATE TRIGGER `set_default_order_date_trigger`
BEFORE INSERT ON bill
FOR EACH ROW
BEGIN
  IF NEW.order_date IS NULL THEN
    SET NEW.order_date = NOW();
  END IF;
END;;
DELIMITER ;



-- insert table bill
INSERT INTO bill (account_id, total_amount)
VALUES (1, 199.99);

-- insert table order_detail
INSERT INTO orderdetail (order_id, product_id, quantity, price)
VALUES (1, 1, 2, 34500*2);

-- insert table cart
INSERT INTO cart (account_id, product_id, quantity)
VALUES (1, 1, 1);



