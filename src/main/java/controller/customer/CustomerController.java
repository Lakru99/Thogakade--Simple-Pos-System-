package controller.customer;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import model.Item;
import util.CrudUtil;

import java.sql.*;

public class CustomerController implements CustomerService{

    private static CustomerController instance;

    private CustomerController() {
    }

    public static CustomerController getInstance() {
        return instance == null ? instance = new CustomerController() : instance;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        String SQL = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setObject(1, customer.getId());
            pstm.setObject(2, customer.getTitle());
            pstm.setObject(3, customer.getName());
            pstm.setDate(4, Date.valueOf(customer.getDob()));
            pstm.setDouble(5, customer.getSalary());
            pstm.setObject(6, customer.getAddress());
            pstm.setObject(7, customer.getCity());
            pstm.setObject(8, customer.getProvince());
            pstm.setObject(9, customer.getPostalCode());

            return pstm.executeUpdate() > 0;



        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Error : "+e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        String SQL = "DELETE FROM customer WHERE CustID='" + id + "'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            return connection.createStatement().executeUpdate(SQL) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Customer> getAll() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM customer";
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {
                //System.out.println(resultSet.getString("CustTitle") + resultSet.getString("CustName"));
                Customer customer = new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),

                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("postalCode")
                );
                customerObservableList.add(customer);
                System.out.println(customer);
            }
            return customerObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String SQL = "UPDATE customer SET CustTitle=?, CustName=?, CustAddress=?, DOB=?, salary=?, City=?, Province=?, postalCode=?  WHERE CustID=?";

        try {
            return CrudUtil.execute(
                    SQL,
                    customer.getTitle(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode(),
                    customer.getId()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String id) {
        String SQL = "SELECT * FROM customer WHERE CustID=?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, id);
            while (resultSet.next()) {
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)

                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ObservableList<String> getCustomerIds() {
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        ObservableList<Customer> customerObservableList = getAll();
        customerObservableList.forEach(customer -> {
            customerIds.add(customer.getId());
        });

        return customerIds;
    }

}
