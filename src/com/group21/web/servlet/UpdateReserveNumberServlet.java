package com.group21.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.group21.service.HistoryReserveSeatService;
import com.group21.service.HistorySeatsUsageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/updateReserveNumberServlet")
public class UpdateReserveNumberServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        System.out.println("ok0");
        boolean flag = true;
        String password = request.getParameter("password");
        String updateNumStr = request.getParameter("number");
        String opStr = request.getParameter("op");
        int updateNum = 0;
        int opNum = 0;
        Map<String, Object> map = new HashMap<String, Object>();
        //错误参数，不处理
        if (password == null || updateNumStr == null || opStr == null ||  password.length() == 0 || updateNumStr.length() ==  0 || opStr.length() == 0 || !"group21password".equals(password)) {
            flag = false;
            map.put("status","0");
        }
        if (flag) {
            System.out.println("ok1");
            try {
                updateNum = Integer.parseInt(updateNumStr);
                opNum = Integer.parseInt(opStr);
            }catch (Exception e){
                updateNum = 0;
                map.put("status","0");

            }
            System.out.println("ok2");
            if (updateNum <= 0) {
                map.put("status","0");
            }
            if (opNum < 0 || opNum > 1) {
                map.put("status","0");
            }
            else {
                System.out.println("ok3");
                boolean updateNumRes = new HistoryReserveSeatService().updateNum(opNum, updateNum);

                if (updateNumRes) {
                    //成功
                    map.put("status","1");
                }else {
                    //失败
                    map.put("status","0");
                }
            }
        }
        System.out.println("ok");
        JSONObject jsonObject = new JSONObject(map);
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}