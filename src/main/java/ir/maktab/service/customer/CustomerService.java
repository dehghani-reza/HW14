package ir.maktab.service.customer;

import ir.maktab.entities.customerside.Account;
import ir.maktab.entities.customerside.Customer;
import ir.maktab.repositories.customerside.CustomerDao;

import java.util.List;

public class CustomerService {

    CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer loadCustomerById(Long customerId){
        return customerDao.loadById(customerId);
    }

    public List<Account> loadCustomerAccounts(Long customerId){
        Customer customer = loadCustomerById(customerId);
        return  customer.getAccountList();
    }
}
