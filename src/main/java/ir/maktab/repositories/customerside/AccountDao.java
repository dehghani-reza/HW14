package ir.maktab.repositories.customerside;


import ir.maktab.entities.customerside.Account;
import ir.maktab.entities.customerside.Customer;
import ir.maktab.repositories.CrudRepository;

public class AccountDao extends CrudRepository<Account,Long> {

    @Override
    protected Class<Account> getEntityClass() {
        return Account.class;
    }

}
