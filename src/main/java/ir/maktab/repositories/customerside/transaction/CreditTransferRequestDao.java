package ir.maktab.repositories.customerside.transaction;


import ir.maktab.entities.customerside.transaction.CreditTransferRequest;
import ir.maktab.repositories.CrudRepository;

public class CreditTransferRequestDao extends CrudRepository<CreditTransferRequest,Long> {


    @Override
    protected Class<CreditTransferRequest> getEntityClass() {
        return CreditTransferRequest.class;
    }


}
