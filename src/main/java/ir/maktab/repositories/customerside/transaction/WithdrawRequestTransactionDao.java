package ir.maktab.repositories.customerside.transaction;

import ir.maktab.entities.customerside.transaction.CreditTransferRequest;
import ir.maktab.entities.customerside.transaction.WithdrawRequestTransaction;
import ir.maktab.repositories.CrudRepository;

public class WithdrawRequestTransactionDao extends CrudRepository<WithdrawRequestTransaction,Long> {
    @Override
    protected Class<WithdrawRequestTransaction> getEntityClass() {
        return WithdrawRequestTransaction.class;
    }
}
