package controller.order;

import db.DBConnection;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public boolean placeOrder(Order order){
        String SQL="INSERT INTO orders VALUE(?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,order.getOrderId());
            psTm.setObject(2,order.getOrderDate());
            psTm.setObject(3,order.getCustomerId());
            boolean isOrderAdd = psTm.executeUpdate()>0;
            if (isOrderAdd){
                boolean isOrderDetailAdd = new OrderDetailController().addOrderDetail(order.getOrderDetails());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
