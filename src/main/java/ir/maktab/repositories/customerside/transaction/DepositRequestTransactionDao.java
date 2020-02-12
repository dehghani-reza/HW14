package ir.maktab.repositories.customerside.transaction;

import ir.maktab.entities.customerside.transaction.CreditTransferRequest;
import ir.maktab.entities.customerside.transaction.DepositRequestTransaction;
import ir.maktab.repositories.CrudRepository;

public class DepositRequestTransactionDao extends CrudRepository<DepositRequestTransaction,Long> {
    @Override
    protected Class<DepositRequestTransaction> getEntityClass() {
        return DepositRequestTransaction.class;
    }
}
