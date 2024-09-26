package controller.customer;

import javafx.collections.ObservableList;
import dto.Customer;

public interface CustomerService1 {
    boolean addCustomer(Customer customer);
    boolean deleteCustomer(String id);
    ObservableList<Customer> getAll();
    boolean updateCustomer(Customer customer);

    Customer searchCustomer(String name);

    ObservableList<String> getCustomerIds();

}
