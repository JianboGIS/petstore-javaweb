-- ============================================================
--  宠物商城 PetStore 数据库初始化脚本
--  数据库：MySQL 8.x    字符集：utf8mb4
--  用法：mysql -uroot -p < petstore.sql
-- ============================================================

DROP DATABASE IF EXISTS petstore;
CREATE DATABASE petstore DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE petstore;

-- ------------------------------------------------------------
-- 1. 用户表 users
-- ------------------------------------------------------------
CREATE TABLE users (
    id       INT            NOT NULL AUTO_INCREMENT COMMENT '用户编号',
    username VARCHAR(50)    NOT NULL                COMMENT '登录用户名',
    pwd      VARCHAR(50)    NOT NULL                COMMENT '登录密码，MD5 加密存储',
    realname VARCHAR(50)    DEFAULT NULL            COMMENT '真实姓名',
    email    VARCHAR(50)    DEFAULT NULL            COMMENT '电子邮件',
    phone    VARCHAR(50)    DEFAULT NULL            COMMENT '手机号码',
    address  VARCHAR(100)   DEFAULT NULL            COMMENT '联系地址',
    deposit  DECIMAL(10, 2) NOT NULL DEFAULT 0.00   COMMENT '当前账户余额',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '用户表';

-- ------------------------------------------------------------
-- 2. 宠物分类表 category
-- ------------------------------------------------------------
CREATE TABLE category (
    id   INT         NOT NULL AUTO_INCREMENT COMMENT '宠物分类编号',
    name VARCHAR(50) NOT NULL                COMMENT '宠物分类名称',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '宠物分类表';

-- ------------------------------------------------------------
-- 3. 宠物表 pets
-- ------------------------------------------------------------
CREATE TABLE pets (
    id          INT            NOT NULL AUTO_INCREMENT COMMENT '宠物编号',
    category_id INT            NOT NULL                COMMENT '外键，宠物所属的分类编号',
    title       VARCHAR(50)    NOT NULL                COMMENT '宠物标题（宠物名称）',
    tag         VARCHAR(50)    DEFAULT NULL            COMMENT '宠物特性标签',
    photo       VARCHAR(50)    NOT NULL                COMMENT '照片的图片文件名',
    price       DECIMAL(10, 2) NOT NULL                COMMENT '价格',
    stock       INT            NOT NULL DEFAULT 0      COMMENT '库存数量',
    ondate      DATETIME       NOT NULL                COMMENT '上架日期',
    descs       VARCHAR(1000)  NOT NULL                COMMENT '详细描述',
    PRIMARY KEY (id),
    KEY idx_pets_category (category_id),
    CONSTRAINT fk_pets_category FOREIGN KEY (category_id) REFERENCES category (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '宠物表';

-- ------------------------------------------------------------
-- 4. 订单表 orders
-- ------------------------------------------------------------
CREATE TABLE orders (
    id         INT            NOT NULL AUTO_INCREMENT COMMENT '订单编号',
    user_id    INT            NOT NULL                COMMENT '外键，下达订单的用户编号',
    createdate DATETIME       NOT NULL                COMMENT '创建订单日期时间',
    state      VARCHAR(50)    NOT NULL                COMMENT '订单状态：已付款/已发货/已收货/已取消',
    name       VARCHAR(50)    NOT NULL                COMMENT '收货人姓名',
    phone      VARCHAR(50)    NOT NULL                COMMENT '收货人手机号码',
    address    VARCHAR(200)   NOT NULL                COMMENT '收货人地址',
    totalprice DECIMAL(10, 2) NOT NULL                COMMENT '订单总金额',
    PRIMARY KEY (id),
    KEY idx_orders_user (user_id),
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '订单表';

-- ------------------------------------------------------------
-- 5. 订单明细表 orderdetail
-- ------------------------------------------------------------
CREATE TABLE orderdetail (
    id       INT            NOT NULL AUTO_INCREMENT COMMENT '订单明细编号',
    order_id INT            NOT NULL                COMMENT '外键，订单明细所属的订单编号',
    pet_id   INT            NOT NULL                COMMENT '外键，订单明细对应的宠物编号',
    price    DECIMAL(10, 2) NOT NULL                COMMENT '成交价格',
    quantity INT            NOT NULL                COMMENT '购买数量',
    subtotal DECIMAL(10, 2) NOT NULL                COMMENT '明细项小计金额',
    PRIMARY KEY (id),
    KEY idx_detail_order (order_id),
    KEY idx_detail_pet (pet_id),
    CONSTRAINT fk_detail_order FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_detail_pet FOREIGN KEY (pet_id) REFERENCES pets (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '订单明细表';

-- ============================================================
--  初始化测试数据
-- ============================================================

-- 插入宠物分类测试数据
INSERT INTO category (id, name) VALUES (1, '猫');
INSERT INTO category (id, name) VALUES (2, '狗');
INSERT INTO category (id, name) VALUES (3, '鸟');
INSERT INTO category (id, name) VALUES (4, '鱼');

-- 插入宠物测试数据
INSERT INTO pets (id, category_id, title, tag, photo, price, stock, ondate, descs) VALUES
(1,  3, '金丝雀',     '艳丽,活泼',       'bird1.jpg', 70.00,  5, '2022-09-09', '金丝雀又名芙蓉鸟，羽色艳丽、鸣声婉转动听，容易饲养，是非常受欢迎的观赏鸟。'),
(2,  3, '八哥',       '善鸣叫,习人语',   'bird2.jpg', 50.00,  5, '2022-09-09', '八哥通体乌黑，额前有羽冠，经驯养后能模仿人语和其他声音，是传统的笼养观赏鸟。'),
(3,  3, '画眉鸟',     '极善鸣啭,歌声动听', 'bird3.jpg', 60.00,  5, '2022-09-09', '画眉鸟眼圈白色向后延伸呈蛾眉状，鸣声洪亮婉转，被称为林中歌手。'),
(4,  3, '百灵鸟',     '能歌善舞,委婉动听', 'bird4.jpg', 80.00,  5, '2022-09-09', '百灵鸟善于鸣叫，且能边飞边唱，姿态优美，是著名的鸣禽。'),
(5,  1, '英国短毛猫', '圆脸,温顺',       'cat1.jpg',  1200.00, 8, '2022-09-20', '英国短毛猫体型圆润，被毛短而浓密，性格温和安静，是非常适合家庭饲养的伴侣猫。'),
(6,  1, '布偶猫',     '长毛,亲人',       'cat2.jpg',  3000.00, 6, '2022-09-25', '布偶猫全身特别松弛柔软，像软绵绵的布偶一样，性格温顺，对人非常友善。'),
(7,  1, '中华田园猫', '机敏,易养',       'cat3.jpg',  200.00, 12, '2022-10-01', '中华田园猫适应性强，聪明机敏，身体健壮，是十分理想的家养伴侣动物。'),
(8,  2, '金毛寻回犬', '温顺,聪明',       'dog1.jpg',  2500.00, 5, '2022-10-05', '金毛寻回犬性格温和、友善、聪明，对小孩尤其有耐心，是优秀的家庭犬。'),
(9,  2, '柯基犬',     '短腿,活泼',       'dog2.jpg',  3500.00, 4, '2022-10-12', '威尔士柯基犬腿短身长，性格开朗活泼，精力充沛，深受人们喜爱。'),
(10, 2, '哈士奇',     '雪橇犬,调皮',     'dog3.jpg',  2000.00, 7, '2022-10-18', '西伯利亚雪橇犬外观冷峻，精力旺盛，性格活泼调皮，需要较大的运动量。'),
(11, 4, '孔雀鱼',     '色彩斑斓,好养',   'fish1.jpg', 15.00,  50, '2022-11-01', '孔雀鱼体色艳丽多彩，繁殖能力强，对水质要求不高，是入门级观赏鱼的首选。'),
(12, 4, '锦鲤',       '寓意吉祥,观赏',   'fish2.jpg', 300.00, 20, '2022-11-06', '锦鲤体格健美、色彩艳丽、花纹多变，寓意吉祥，是著名的大型观赏鱼。'),
(13, 4, '神仙鱼',     '体态优雅,静逸',   'fish3.jpg', 45.00,  30, '2022-11-12', '神仙鱼体态高雅、游姿优美，被称为热带鱼之王，是水族箱中的经典品种。');

-- 插入用户测试数据  密码的明文是 1234@qwer（MD5 加密）
INSERT INTO users (id, username, pwd, realname, email, phone, address) VALUES
(1, 'Admin',  '9bdc59cfe4b46f08d182c0fc440c86e8', '管理员', 'admin@qq.com', '13505751111', '管理员的联系地址');
INSERT INTO users (id, username, pwd, realname, email, phone, address) VALUES
(2, 'Tommy',  '9bdc59cfe4b46f08d182c0fc440c86e8', '童米米', 'tommy@qq.com', '13505752222', '童米米的联系地址');
INSERT INTO users (id, username, pwd, realname, email, phone, address) VALUES
(3, 'Jack',   '9bdc59cfe4b46f08d182c0fc440c86e8', '杰克',   'jack@qq.com',  '13505753333', '杰克的联系地址');

-- 插入订单测试数据
INSERT INTO orders (id, user_id, createdate, state, name, phone, address, totalprice) VALUES
(1, 2, '2022-11-20 10:30:00', '已付款', '童米米', '13505752222', '童米米的联系地址', 130.00);
INSERT INTO orderdetail (order_id, pet_id, price, quantity, subtotal) VALUES
(1, 1, 70.00, 1, 70.00), (1, 3, 60.00, 1, 60.00);
