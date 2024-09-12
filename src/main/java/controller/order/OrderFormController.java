package controller.order;

import controller.customer.CustomerController;
import controller.item.ItemController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.CartTM;
import model.Customer;
import model.Item;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    @FXML
    private Label lblOrderID;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private ComboBox<String> cmbCustomerID;

    @FXML
    private ComboBox<String> cmbItemCode;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtItemDescription;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtQTY;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colTotal;

    ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        String itemCode = cmbItemCode.getValue();
        String description = txtItemDescription.getText();
        Integer qty = Integer.parseInt(txtQTY.getText());
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        Double total = unitPrice*qty;
        cartTMS.add( new CartTM(itemCode,description,qty,unitPrice,total));

        tblCart.setItems(cartTMS);

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadCustomerIds();
        loadItemCodes();
        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newVal) -> {
            if (newVal!=null){
                searchCustomer(newVal);
            }
        });
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newVal) -> {
            if (newVal!=null){
                searchItem(newVal);
            }
        });
    }



    private void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow =f.format(date);

        lblDate.setText(dateNow);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e ->{
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void loadCustomerIds(){
        cmbCustomerID.setItems(CustomerController.getInstance().getCustomerIds());
    }
    private void searchCustomer(String customerID){
        Customer customer = CustomerController.getInstance().searchCustomer(customerID);
        txtCustomerName.setText(customer.getName());
        txtCustomerAddress.setText(customer.getAddress());
        System.out.println(customerID);
    }

    private void loadItemCodes(){
        cmbItemCode.setItems(ItemController.getInstance().getItemCodes());
    }
    private void searchItem(String newVal) {
        Item item = ItemController.getInstance().searchItem(newVal);
        txtItemDescription.setText(item.getDescription());
        txtStock.setText(item.getQtyOnHand().toString());
        txtUnitPrice.setText(item.getUnitPrice().toString());
    }

}
