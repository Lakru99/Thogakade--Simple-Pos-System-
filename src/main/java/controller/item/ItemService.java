package controller.item;

import javafx.collections.ObservableList;
import model.Item;
import model.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {
    boolean addItem(Item item);
    boolean deleteItem(String id);
    ObservableList<Item> getAll();
    boolean updateItem(Item item);

    Item searchItem(String itemCode);
    ObservableList<String> getItemCodes();
    boolean updateStock(List<OrderDetail> orderDetails);
}
