package service.custom;

import dto.Item;
import dto.OrderDetail;
import javafx.collections.ObservableList;
import service.SuperService;

import java.util.List;

public interface ItemService extends SuperService {
    boolean addItem(Item item);
    boolean deleteItem(String id);
    ObservableList<Item> getAll();
    boolean updateItem(Item item);

    Item searchItem(String itemCode);
    ObservableList<String> getItemCodes();
    boolean updateStock(List<OrderDetail> orderDetails);
}
