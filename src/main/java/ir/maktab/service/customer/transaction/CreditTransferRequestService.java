package ir.maktab.service.customer.transaction;

import ir.maktab.entities.customerside.Account;
import ir.maktab.entities.customerside.card.CreditCard;
import ir.maktab.entities.customerside.transaction.CreditTransferRequest;
import ir.maktab.repositories.customerside.transaction.CreditTransferRequestDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CreditTransferRequestService {


CreditTransferRequestDao creditTransferRequestDao;

    public CreditTransferRequestService(CreditTransferRequestDao creditTransferRequestDao) {
        this.creditTransferRequestDao = creditTransferRequestDao;
    }

    public void saveCreditTransferRequestTransaction(CreditTransferRequest creditTransferRequest){
        creditTransferRequestDao.save(creditTransferRequest);
    }


}
