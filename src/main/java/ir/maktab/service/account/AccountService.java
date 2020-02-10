package ir.maktab.service.account;

import ir.maktab.entities.bankside.BankBranch;
import ir.maktab.entities.customerside.Account;
import ir.maktab.entities.customerside.Customer;
import ir.maktab.entities.customerside.transaction.AccountTransaction;
import ir.maktab.repositories.customerside.AccountDao;

import java.util.List;

public class AccountService {

    AccountDao accountDao = new AccountDao();

    public Account createAccount(BankBranch bankBranch, Customer customer) {
        Account account = new Account();
        account.setBankBranch(bankBranch);
        account.setCustomer(customer);
        account.setAccountBalance(0L);
        return accountDao.save(account);
    }

    public Long accountBalance(Long accountId){
        Account account = accountDao.loadById(accountId);
        return account.getAccountBalance();
    }

    public Long addToAccountBalance(Long amount , Long accountId){
        Account account = accountDao.loadById(accountId);
        Long accountBalance = account.getAccountBalance();
        accountBalance+=amount;
        accountDao.update(account);
        return accountBalance;
    }

    public Long withdrawFromAccountBalance(Long amount , Long accountId) throws Exception {
        Account account = accountDao.loadById(accountId);
        Long accountBalance = account.getAccountBalance();
        if(accountBalance<amount){
            throw new Exception("you're account balance is low");
        }
        accountBalance-=amount;
        accountDao.update(account);
        return accountBalance;
    }

    public void removeAccount(Long accountId) throws Exception {
        Account account = accountDao.loadById(accountId);
        accountDao.deleteById(accountId);
    }

//    public List<AccountTransaction> transactions(Customer customer) todo all transaction

}
