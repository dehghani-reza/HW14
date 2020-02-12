package ir.maktab.repositories.customerside;


import ir.maktab.core.hibernate.HibernateUtil;
import ir.maktab.entities.customerside.Account;
import ir.maktab.entities.customerside.Customer;
import ir.maktab.entities.customerside.transaction.AccountTransaction;
import ir.maktab.entities.customerside.transaction.CreditTransferRequest;
import ir.maktab.entities.customerside.transaction.DepositRequestTransaction;
import ir.maktab.entities.customerside.transaction.WithdrawRequestTransaction;
import ir.maktab.repositories.CrudRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class AccountDao extends CrudRepository<Account,Long> {
    Session session = HibernateUtil.getSession();
    @Override
    protected Class<Account> getEntityClass() {
        return Account.class;
    }

    public List<AccountTransaction> getTransactionByDate(LocalDate localDate, Account account){
        session.getTransaction();
        Query<CreditTransferRequest> query = session.createQuery("from CreditTransferRequest where originAccount=:a and transactionDate>=:b and transactionDate<=:c");
        query.setParameter("a",account);
        query.setParameter("b",localDate);
        query.setParameter("c",LocalDate.now());
        List<CreditTransferRequest> creditTransferRequests = query.list();
        Query<WithdrawRequestTransaction> query1 = session.createQuery("from WithdrawRequestTransaction where originAccount= :a and transactionDate>= :b and transactionDate<= :c");
        query1.setParameter("a",account);
        query1.setParameter("b",localDate);
        query1.setParameter("c",LocalDate.now());
        List<WithdrawRequestTransaction> withdrawRequestTransactions = query1.list();
        Query<DepositRequestTransaction> query2 = session.createQuery("from DepositRequestTransaction where originAccount= :a and transactionDate>= :b and transactionDate<= :c");
        query2.setParameter("a",account);
        query2.setParameter("b",localDate);
        query2.setParameter("c",LocalDate.now());
        List<DepositRequestTransaction> depositRequestTransactions = query2.list();
        List<AccountTransaction> accountTransactions = new LinkedList<>();
        accountTransactions.addAll(depositRequestTransactions);
        accountTransactions.addAll(withdrawRequestTransactions);
        accountTransactions.addAll(creditTransferRequests);
        return accountTransactions;

    }
}
