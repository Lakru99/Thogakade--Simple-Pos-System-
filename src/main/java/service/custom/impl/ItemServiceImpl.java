package service.custom.impl;

import controller.item.ItemService;
import dto.Item;
import dto.OrderDetail;
import javafx.collections.ObservableList;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    @Override
    public boolean addItem(Item item) {
        return false;
    }

    @Override
    public boolean deleteItem(String id) {
        return false;
    }

    @Override
    public ObservableList<Item> getAll() {
        return null;
    }

    @Override
    public boolean updateItem(Item item) {
        return false;
    }

    @Override
    public Item searchItem(String itemCode) {
        return null;
    }

    @Override
    public ObservableList<String> getItemCodes() {
        return null;
    }

    @Override
    public boolean updateStock(List<OrderDetail> orderDetails) {
        return false;
    }
}
