package controller.order;

import controller.item.ItemController;
import db.DBConnection;
import javafx.scene.control.Alert;
import dto.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public boolean placeOrder(Order order) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            String SQL="INSERT INTO orders VALUE(?,?,?)";
            connection.setAutoCommit(false);
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,order.getOrderId());
            psTm.setObject(2,order.getOrderDate());
            psTm.setObject(3,order.getCustomerId());
            boolean isOrderAdd = psTm.executeUpdate()>0;
            if (isOrderAdd){
                boolean isOrderDetailAdd = new OrderDetailController().addOrderDetail(order.getOrderDetails());
                if (isOrderDetailAdd){
                    boolean isUpdateStock = ItemController.getInstance().updateStock(order.getOrderDetails());
                    if (isUpdateStock){
                        connection.commit();
                        new Alert(Alert.AlertType.INFORMATION,"Order Placed !!").show();
                    }
                }
            }
            //need to clear buffer when all are added!
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
