package repository.custom.impl;

import entity.ItemEntity;
import javafx.collections.ObservableList;
import dto.Item;
import repository.custom.ItemDao;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(ItemEntity item) {

        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ObservableList<ItemEntity> getAll() {
        return null;
    }

    @Override
    public boolean update(ItemEntity item) {
        return false;
    }

    @Override
    public ItemEntity search(String name) {
        return null;
    }
}
