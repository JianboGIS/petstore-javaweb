package com.XJB.dao;

import com.XJB.domain.*;
import com.XJB.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    private TransactionTemplate txTemplate = JDBCUtils.getTransactionTemplate();

    //检查是否可以创建订单：（1）账户余额是否大于订单金额？
    public Boolean checkDeposit(User user, ShoppingCart cart){
        return user.getDeposit() >= cart.getTotalMoney();
    }
    //检查是否可以创建订单：（2）宠物库存是否充足？
    public Boolean checkStock(ShoppingCart cart){
        Boolean result = true;
        PetDao petDao = new PetDao();
        //检查每一个购物项的宠物库存是否充足，有不足的则返回false，中断循环
        for (CartItem item : cart.getCartItemList()) {
            Pet pet =petDao.getById(item.getId());
            if (pet.getStock() < item.getQuantity()){
                result = false;
                break;
            }
        }
        return result;
    }

    //创建订单，返回字符串
    public String add(User user, ShoppingCart cart){
        //检查账户余额
        if (checkDeposit(user,cart) == false){
            return "账户余额不足";
        }
        //检查宠物库存
        if (checkStock(cart) == false){
            return "宠物库存不足";
        }
        int newOrderId = 0;
        try {
            //1.新增Order记录SQL语句，订单创建时间用MySQL内置函数 NOW()，订单状态state为'已付款'
            String sqlOrder = " INSERT INTO orders (createdate,state,user_id,name,phone,address,totalprice) " +
            " VALUES(NOW(),'已付款',?,?,?,?,?)";
            //执行 新增Order记录 SQL语句，返回新增Order记录的自增型id，因为OrderDetail表新增记录是需要使用order_id
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, user.getId());
                    ps.setString(2,user.getRealname());
                    ps.setString(3,user.getPhone());
                    ps.setString(4,user.getAddress());
                    ps.setDouble(5, cart.getTotalMoney());
                    return ps;
                }
            },keyHolder);
            newOrderId= keyHolder.getKey().intValue();//获取 新增Order记录主键id的值
            //2.新增 OrderDetail 记录SQL语句，
            for (CartItem item : cart.getCartItemList()) {
                String sqlOrderDetail="INSERT INTO orderdetail(order_id,pet_id,price,quantity,subtotal) VALUES(?,?,?,?,?)";
                template.update(sqlOrderDetail, newOrderId,item.getId(),item.getPrice(),
                item.getQuantity(),item.getPrice()*item.getQuantity());
            }
            //3.账户余额扣款SQL语句
            String sqlUser = "update users set deposit=deposit-? where id=?";
            template.update(sqlUser, cart.getTotalMoney(),user.getId());
            //4.宠物减库存SQL语句
            for (CartItem item : cart.getCartItemList()) {
                String sqlPet="update pets set stock=stock-? where id=?";
                template.update(sqlPet, item.getQuantity(),item.getId());
            }
            return "订单创建成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "订单创建失败";
        }
    }

    public List<Order> getListByUserId(int user_id){
        List<Order> orderList = null;
        try {
            String sql = "select * from orders where user_id = ? order by createdate desc";
            orderList = template.query(sql, new BeanPropertyRowMapper<>(Order.class),user_id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return orderList;
        }
    }

    //根据 订单状态 获取订单列表
    public List<Order> getListByState(String state){
        List<Order> orderList = null;
        try {
            String sql = "select * from orders where state = ? order by createdate desc";
            orderList = template.query(sql, new BeanPropertyRowMapper<>(Order.class),state);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return orderList;
        }
    }

    // 获取单个订单对象
    public Order getById(int id){
        Order order = null;
        try {
            String sql = "select * from orders where id = ?";
            order = template.queryForObject(sql,new BeanPropertyRowMapper<>(Order.class),id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return order;
        }
    }
    // 根据订单Id获取订单明细列表
    public List<OrderDetail> getOrderDetailListById(int id){
        List<OrderDetail> orderDetailList = null;
        try {
            String sql = "select * from orderdetail where order_id = ? ";
            orderDetailList = template.query(sql, new BeanPropertyRowMapper<>(OrderDetail.class),id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return orderDetailList;
        }
    }

    //设置订单状态
    public Boolean setState(int id,String state){
        int affectRows = 0;
        try {
            //1.编写sql
            String sql = "update orders set state=? where id=?";
            //2.调用update方法，写入数据库
            affectRows = template.update(sql,state,id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return affectRows > 0;
        }
    }

    //删除订单 （使用事务）
    public Boolean delete(int id){
        Order order = getById(id);
        List<OrderDetail> orderDetailList = getOrderDetailListById(id);

        //订单状态为：已付款 已发货 已收货 已取消。
        //只有已付款状态可以 删除订单，即设置为已取消,其他状态直接返回false
        if (!"已付款".equals(order.getState())){
            return false;
        }
        //使用txTemplate，开启事务，确保这些SQL语句要么全部执行完毕，要么全部不执行，不会出现部分执行情况。
        txTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult (TransactionStatus status) {
                try {
                    //1.给用户退款：账户余额增加  SQL语句
                    String sqlUser = "update users set deposit=deposit+? where id=?";
                    template.update(sqlUser,order.getTotalprice(),order.getUser_id());
                    //2.还原宠物库存：宠物库存增加
                    for (OrderDetail detail : orderDetailList) {
                        String sqlPet="update pets set stock=stock+? where id=?";
                        template.update(sqlPet, detail.getQuantity(),detail.getPet_id());
                    }
                    //3.删除Order:不是真正删除数据库中记录，而是设置订单状态为 已取消
                    String sqlOrder = " update orders set state='已取消' where id = ?";
                    template.update(sqlOrder,id);
                } catch (Exception e) {
                    status.setRollbackOnly();//sql执行过程中，出现异常时事务回滚，取消所有sql执行
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    //获取 全部订单状态字符串列表
    public List<String> getStateList(){
        List<String> stateList = new ArrayList<>();
        //订单状态为：已付款 已发货 已收货 已取消
        stateList.add("已付款");
        stateList.add("已发货");
        stateList.add("已收货");
        stateList.add("已取消");
        return stateList;
    }

    // 根据订单Id获取订单明细列表对应的宠物列表
    public List<Pet> getPetListById(int id){
        List<OrderDetail> orderDetailList = getOrderDetailListById(id);
        //按照 OrderDetail列表顺序获取对应的Pet，并组装成List
        List<Pet>  petlList = new ArrayList<>();
        PetDao petDao = new PetDao();
        for (int i=0; i <  orderDetailList.size(); i++){
            OrderDetail detail = orderDetailList.get(i);
            petlList.add(petDao.getById(detail.getPet_id()));
        }
        return petlList;
    }
}

