package ir.maktab.repositories.bankside;

import ir.maktab.entities.bankside.BankBranch;
import ir.maktab.repositories.CrudRepository;

public class BankBranchDao extends CrudRepository<BankBranch,Long> {


    @Override
    protected Class<BankBranch> getEntityClass() {
        return BankBranch.class;
    }
}
