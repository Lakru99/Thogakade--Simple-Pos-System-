package repository.custom.impl;

import db.DBConnection;
import entity.CustomerEntity;
import javafx.collections.ObservableList;
import dto.Customer;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import repository.custom.CustomerDao;
import util.HibernateUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public boolean save(CustomerEntity customer) {

        System.out.println("Repository : "+customer);

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(customer);
        session.getTransaction().commit();
        session.close();


//        String SQL = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";
//        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//            PreparedStatement pstm = connection.prepareStatement(SQL);
//            pstm.setObject(1, customer.getId());
//            pstm.setObject(2, customer.getTitle());
//            pstm.setObject(3, customer.getName());
//            pstm.setDate(4, Date.valueOf(customer.getDob()));
//            pstm.setDouble(5, customer.getSalary());
//            pstm.setObject(6, customer.getAddress());
//            pstm.setObject(7, customer.getCity());
//            pstm.setObject(8, customer.getProvince());
//            pstm.setObject(9, customer.getPostalCode());
//
//            return pstm.executeUpdate() > 0;
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR,"Error : "+e.getMessage()).show();
//        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ObservableList<CustomerEntity> getAll() {
        return null;
    }

    @Override
    public boolean update(CustomerEntity customer) {
        return false;
    }

    @Override
    public CustomerEntity search(String name) {
        return null;
    }
}
