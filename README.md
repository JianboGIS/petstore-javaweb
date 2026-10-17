# 宠物商城 PetStore

**PetStore 网上宠物商城**：一个完整的 Java Web 电商示例项目，覆盖商品浏览、分类查询、购物车、下单结算、个人中心与后台管理全流程。

项目采用经典 **Java Web MVC** 三层结构：JSP + Servlet + JSTL/EL 作为视图与控制器，Spring `JdbcTemplate` + Druid 连接池访问 MySQL 数据库。

---

## 一、技术栈

| 层次 | 技术 | 版本 |
|---|---|---|
| 视图 V | JSP + JSTL/EL + Bootstrap 4.6.1 + Font Awesome 3.2.1 + jQuery 3.6.0 | — |
| 控制 C | Servlet（`@WebServlet` 注解注册） | javax.servlet-api 4.0.1 |
| 模型 M | Spring `JdbcTemplate` | 5.3.17 |
| 数据库 | MySQL | 8.0（驱动 mysql-connector-java 8.0.28） |
| 连接池 | Alibaba Druid | 1.2.8 |
| 文件上传 | Apache Commons FileUpload / IO | 1.4 / 2.2 |
| JSON | Alibaba fastjson2 | 2.0.18 |
| 单元测试 | JUnit 5 | 5.10.2 |
| 构建 | Maven（war 包） | maven-war-plugin 3.3.2 |
| 运行 | Tomcat 9.x（Servlet 4.0，**不支持 Tomcat 10+**） | 9.0.x |
| JDK | Java 8 及以上（源码兼容 8） | — |

---

## 二、目录结构

```
PetStore/
├── pom.xml                          # Maven 构建配置
├── sql/petstore.sql                 # 建库建表 + 初始化测试数据
├── lib/                             # 本地离线依赖 jar（已 .gitignore，不纳入版本控制）
└── src/
    ├── main/
    │   ├── java/com/XJB/
    │   │   ├── domain/              # 实体类：Pet / Category / User / Order / OrderDetail / CartItem / ShoppingCart
    │   │   ├── dao/                 # 数据访问：PetDao / CategoryDao / UserDao / OrderDao
    │   │   ├── servlet/             # 控制器：24 个 Servlet
    │   │   └── utils/JDBCUtils.java # Druid 数据源 + TransactionTemplate
    │   ├── resources/druid.properties  # 数据库连接配置
    │   └── webapp/
    │       ├── index.jsp detail.jsp cart.jsp reg.jsp login.jsp
    │       ├── header.jsp footer.jsp sidebar.jsp
    │       ├── userinfo.jsp userpwd.jsp userdeposit.jsp userorder.jsp order.jsp
    │       ├── admin/               # 后台管理：分类 / 宠物 / 订单
    │       ├── css/ js/ font/ img/ peting/   # 静态资源与宠物图片
    │       └── WEB-INF/web.xml
    └── test/java/com/XJB/
        ├── dao/                     # PetDaoTest / UserDaoTest / CategoryDaoTest / OrderDaoTest
        └── domain/ShoppingCartTest.java
```

---

## 三、快速开始

### 1. 初始化数据库

```bash
mysql -uroot -p < sql/petstore.sql
```

脚本会创建数据库 `petstore`、5 张表（`users` / `category` / `pets` / `orders` / `orderdetail`）并插入
4 个分类、13 只宠物、3 个用户（`Admin` / `Tommy` / `Jack`，密码明文均为 `1234@qwer`，MD5 存储）的测试数据。

### 2. 修改数据库连接

编辑 `src/main/resources/druid.properties`：

```properties
driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/petstore?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
username=root
password=your_password  # ← 改成你本机的数据库密码
```

### 3. 打包部署

```bash
mvn clean package                 # 生成 target/PetStore.war
```

把 `PetStore.war` 复制到 Tomcat 的 `webapps/` 下启动即可；或在 IDEA 中
**Edit Configurations → Tomcat 9 → Deployment** 添加 `PetStore:war exploded`，
Application context 设为 `/PetStore`，启动后访问：

```
http://localhost:8080/PetStore/IndexServlet
```

> 后台管理入口：`http://localhost:8080/PetStore/admin/categorylist.jsp`

### 4. 运行单元测试

`src/test/java` 下的测试类会**真实读写数据库**，请先执行第 1、2 步：

```bash
mvn test
```

---

## 四、功能模块

**前台购物（顾客）**
- 首页展示：最新上架 12 只宠物、分类导航栏、关键字查询
- 分类浏览宠物 / 查询宠物
- 宠物详情、加入购物车
- 购物车：查看、增减数量、删除、金额合计
- 用户注册 / 登录 / 注销
- 个人中心：个人信息修改、密码修改、账户充值、我的订单及订单详情（Ajax）
- 订单确认与下达（事务：写订单 + 扣余额 + 减库存）

**后台管理（管理员）**
- 分类管理：浏览、添加、修改
- 宠物管理：按分类浏览、新增 / 编辑宠物、图片上传（Ajax）
- 订单管理：按状态浏览、发货、取消订单

---

## 五、实现说明与一致性处理

项目在整理为可独立构建、可运行的 Maven 工程过程中，完成并记录了以下一致性处理与补充实现，
均在代码注释或本说明中标注：

| # | 问题 | 处理 |
|---|---|---|
| 1 | `Pet.java` 的包名为 `com.XJB.domin`（拼写错误），其余 35 处均为 `com.XJB.domain` | 统一为 `com.XJB.domain` |
| 2 | 图片目录名混用 `peting` 与 `petimg`（`FileUploadServlet` 用 `petimg`，其余页面用 `peting`） | 统一为 `peting` |
| 3 | `JDBCUtils.java` 基础版本已含 `getTransactionTemplate()`，另一处描述重复给出 | 不重复追加 |
| 4 | `OrderDao.java` 基础版本已声明 `template` / `txTemplate` 字段，另一处描述重复给出 | 不重复追加 |
| 5 | Servlet 文件名（`UserEditServlet`）与类名/注解（`EditUserServlet` / `@WebServlet("/EditUserServlet")`）不一致，页面表单提交到 `/EditUserServlet` | 文件命名为 `EditUserServlet.java` |
| 6 | `OrderDaoTest` / `UserDaoTest` 等测试类缺少实体类 `import` | 补充 `import com.XJB.domain.*;` |
| 7 | 后台页面位于 `/admin/` 子目录，但页头用相对路径引用 `css/`、`js/`、`img/`，会导致样式 404 | 后台页头改为 `${ctx}/css/...` 等绝对路径 |
| 8 | 用户密码在数据库中以 MD5 保存，未单独说明加密工具 | `UserDao` 使用 Spring `DigestUtils.md5DigestAsHex` 对密码加密，脚本中的密码串 `9bdc59cf…` 即 `1234@qwer` 的 MD5 |

其它：`pom.xml` 为完整的 war 工程配置，包含全部 `<dependencies>`、`maven-war-plugin 3.3.2`、
`finalName` 与编译版本（Java 8 / UTF-8）设置。

---

## 六、交付校验

- `javac` 全量编译通过：主源码 36 个文件、测试源码 5 个文件，零错误。
- 部署到 Apache Tomcat 9.0.123 运行验证：全部 20 个 JSP 页面与静态资源均返回 `HTTP 200`
  （JSP 编译、JSTL 标签、Bootstrap/jQuery/FontAwesome 资源加载均正常）。
- 连接数据库后完成全流程实测：首页展示 12 只宠物、分类浏览、关键字查询、登录、加入购物车、
  确认订单、下单（订单/明细/余额/库存四处数据一致变更）、后台分类新增与订单发货，均正常。
- 数据库实测环境使用 MariaDB 11.4.5（MySQL 协议兼容），`sql/petstore.sql` 一次导入成功；
  正式环境仍建议使用 MySQL 8.0。
