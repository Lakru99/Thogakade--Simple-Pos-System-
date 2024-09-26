package controller.item;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.Item;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQuantity;

    ItemService service = ItemController.getInstance();

    @FXML
    void addBtnOnAction(ActionEvent event) {
        Item item = new Item(
               txtItemCode.getText(),
               txtDescription.getText(),
               txtPackSize.getText(),
               Double.parseDouble(txtPrice.getText()),
               Integer.parseInt(txtQuantity.getText())
        );
        if (service.addItem(item)){
            new Alert(Alert.AlertType.INFORMATION, "Item Added..!").show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR, "Item Not Added").show();
        }

    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {
        service.deleteItem(txtItemCode.getText());
        loadTable();
    }

    @FXML
    void reloadBtnOnAction(ActionEvent event) {
        loadTable();
    }

    @FXML
    void searchBtnOnAction(ActionEvent event) {
        Item item = service.searchItem(txtItemCode.getText());
        setTextToValues(item);
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQuantity.getText())
        );
        service.updateItem((item));
        loadTable();
    }

    private void loadTable(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();

        itemTable.setItems(itemObservableList);

        try {
            String SQL = "SELECT * FROM item";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "12345");
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()){
               // System.out.println(resultSet.getString("CustTitle")+resultSet.getString("CustName"));
                Item customer =new Item(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                );
                itemObservableList.add(customer);
                System.out.println(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        loadTable();
        itemTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue!=null){
                setTextToValues(newValue);
            }
        }));
    }

    private void setTextToValues(Item newValue) {
        txtItemCode.setText(newValue.getItemCode());
        txtDescription.setText(newValue.getDescription());
        txtPackSize.setText(newValue.getPackSize());
        txtPrice.setText(newValue.getUnitPrice().toString());
        txtQuantity.setText(newValue.getQtyOnHand().toString());
    }
}
