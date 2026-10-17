package com.XJB.servlet;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.XJB.dao.OrderDao;
import com.XJB.domain.OrderDetail;
import com.XJB.domain.Pet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/OrderDetailServlet")
public class OrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数  订单编号 id
        int id =Integer.parseInt(request.getParameter("id"));
        //2.使用模型（M）中的对象执行业务方法，获取业务数据
        OrderDao orderDao = new OrderDao();
        List<OrderDetail> orderDetailList = orderDao.getOrderDetailListById(id);
        List<Pet> petList = orderDao.getPetListById(id);
        //3.将数据转化为JSon格式，响应给客户端
        //3.1 Java对象列表 转化 JSON对象列表
        JSONArray detailJSONArray =JSONArray.parseArray(JSON.toJSONString(orderDetailList));
        //3.2 给每一个OrderDetail对象增加一个title属性 （因为OrderDetail中未包含宠物名称属性）
        for (int i=0; i < detailJSONArray.size(); i++){
            JSONObject detailObj = (JSONObject)detailJSONArray.get(i);
            detailObj.put("title",petList.get(i).getTitle());
        }
        //3.3 将JSON对象列表转化为字符串响应给客户端
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(detailJSONArray.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
