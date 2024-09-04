package controller.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Item;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXComboBox cmbTitle;
    public JFXTextField txtAddress;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    public JFXTextField txtSalary;
    public DatePicker dateDob;
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    CustomerService service =new CustomerController();

    @FXML
    void btnReloadAction(ActionEvent event) {
        loadTable();
    }

    private void loadTable(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        customerTable.setItems(customerObservableList);


        try {
            String SQL = "SELECT * FROM customer";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "12345");
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString("CustTitle")+resultSet.getString("CustName"));
                Customer customer =new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getString("CustAddress"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("postalCode")
                );
                customerObservableList.add(customer);
                System.out.println(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        titles.add("Ms");
        cmbTitle.setItems(titles);
        customerTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));
        loadTable();
    }

    private void setTextToValues(Customer newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
    }

    public void addBtnOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer(
                txtId.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                txtAddress.getText(),
                dateDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtCity.getText(), txtProvince.getText(),
                txtPostalCode.getText()
        );
        if (service.addCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Added").show();
        }
    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {
        service.deleteCustomer(txtId.getText());
    }

    public void searchBtnOnAction(ActionEvent actionEvent) {
        service.searchCustomer(txtId.getText());
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer(
                txtId.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                txtAddress.getText(),
                dateDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtCity.getText(), txtProvince.getText(),
                txtPostalCode.getText()
        );
        service.updateCustomer((customer));
        loadTable();
    }
}
