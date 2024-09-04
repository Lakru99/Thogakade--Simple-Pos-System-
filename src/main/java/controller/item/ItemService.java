package controller.item;

import javafx.collections.ObservableList;
import model.Item;

import java.sql.SQLException;

public interface ItemService {
    boolean addItem(Item item);
    boolean deleteItem(String id);
    ObservableList<Item> getAll();
    boolean updateItem(Item item);

    Item searchItem(String itemCode);
}
