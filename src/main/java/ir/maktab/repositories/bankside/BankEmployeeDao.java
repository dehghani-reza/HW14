package ir.maktab.repositories.bankside;

import ir.maktab.entities.bankside.BankEmployee;
import ir.maktab.repositories.CrudRepository;


public class BankEmployeeDao extends CrudRepository<BankEmployee, Long> {

    @Override
    protected Class<BankEmployee> getEntityClass() {
        return BankEmployee.class;
    }
}
