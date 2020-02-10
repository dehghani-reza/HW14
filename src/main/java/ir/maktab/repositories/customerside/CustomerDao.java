package ir.maktab.repositories.customerside;


import ir.maktab.entities.customerside.Customer;
import ir.maktab.repositories.CrudRepository;

public class CustomerDao extends CrudRepository<Customer,Long> {


    @Override
    protected Class<Customer> getEntityClass() {
        return Customer.class;
    }
}
