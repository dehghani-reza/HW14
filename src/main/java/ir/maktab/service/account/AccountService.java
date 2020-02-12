package ir.maktab.service.account;

import ir.maktab.entities.bankside.BankBranch;
import ir.maktab.entities.customerside.Account;
import ir.maktab.entities.customerside.Customer;
import ir.maktab.entities.customerside.transaction.AccountTransaction;
import ir.maktab.entities.customerside.transaction.DepositRequestTransaction;
import ir.maktab.entities.customerside.transaction.WithdrawRequestTransaction;
import ir.maktab.repositories.customerside.AccountDao;
import ir.maktab.repositories.customerside.transaction.DepositRequestTransactionDao;
import ir.maktab.repositories.customerside.transaction.WithdrawRequestTransactionDao;

import java.time.LocalDate;
import java.util.List;

public class AccountService {



    AccountDao accountDao;
    WithdrawRequestTransactionDao withdrawRequestTransactionDao;
    DepositRequestTransactionDao depositRequestTransactionDao;

    public AccountService(AccountDao accountDao, WithdrawRequestTransactionDao withdrawRequestTransactionDao, DepositRequestTransactionDao depositRequestTransactionDao) {
        this.accountDao = accountDao;
        this.withdrawRequestTransactionDao = withdrawRequestTransactionDao;
        this.depositRequestTransactionDao = depositRequestTransactionDao;
    }

    public Account createAccount(BankBranch bankBranch, Customer customer) {
        Account account = new Account();
        account.setBankBranch(bankBranch);
        account.setCustomer(customer);
        account.setAccountBalance(0L);
        return accountDao.save(account);
    }

    public Long accountBalance(Long accountId) {
        Account account = accountDao.loadById(accountId);
        return account.getAccountBalance();
    }

    public Long addToAccountBalance(Long amount, Long accountId) {
        DepositRequestTransaction depositRequestTransaction = new DepositRequestTransaction();
        Account account = accountDao.loadById(accountId);
        Long accountBalance = account.getAccountBalance();
        transactionDeposit(account,amount,depositRequestTransaction);
        accountBalance += amount;
        account.setAccountBalance(accountBalance);
        try {
            accountDao.update(account);
        }catch (Exception e){
            saveTransactionDeposit(depositRequestTransaction);
        }
        depositRequestTransaction.setSuccessfullyDone(true);
        saveTransactionDeposit(depositRequestTransaction);
        return accountBalance;
    }

    public Long withdrawFromAccountBalance(Long amount, Long accountId,Long pass) throws Exception {
        WithdrawRequestTransaction withdrawRequestTransaction = new WithdrawRequestTransaction();
        Account account = accountDao.loadById(accountId);
        if(!account.getCreditCard().getFirstPassword().equals(pass)){
            throw new Exception("wrong pass");
        }
        Long accountBalance = account.getAccountBalance();
        transactionWithdraw(account,amount,withdrawRequestTransaction);
        if (accountBalance < amount) {
            withdrawRequestTransaction.setDescription("you're account balance is low");
            saveTransactionWithdraw(withdrawRequestTransaction);
            throw new Exception("you're account balance is low");
        }
        accountBalance -= amount;
        account.setAccountBalance(accountBalance);
        accountDao.update(account);
        withdrawRequestTransaction.setSuccessfullyDone(true);
        saveTransactionWithdraw(withdrawRequestTransaction);
        return accountBalance;
    }

    public void removeAccount(Long accountId) throws Exception {
        Account account = accountDao.loadById(accountId);
        accountDao.deleteById(accountId);
    }

    public void transactionDeposit(Account account,Long amount,DepositRequestTransaction depositRequestTransaction) {
        depositRequestTransaction.setDescription("deposit to account");
        depositRequestTransaction.setOriginAccount(account);
        depositRequestTransaction.setTransferAmount(amount);
        depositRequestTransaction.setTransactionDate(LocalDate.now());
        depositRequestTransaction.setSuccessfullyDone(false);
    }
    public void saveTransactionDeposit(DepositRequestTransaction depositRequestTransaction){
        depositRequestTransactionDao.save(depositRequestTransaction);
    }

    public void transactionWithdraw(Account account,Long amount, WithdrawRequestTransaction withdrawRequestTransaction) {
        withdrawRequestTransaction.setDescription("withdraw from account");
        withdrawRequestTransaction.setOriginAccount(account);
        withdrawRequestTransaction.setTransferAmount(amount);
        withdrawRequestTransaction.setTransactionDate(LocalDate.now());
        withdrawRequestTransaction.setSuccessfullyDone(false);
    }
    public void saveTransactionWithdraw( WithdrawRequestTransaction withdrawRequestTransaction){
        withdrawRequestTransactionDao.save(withdrawRequestTransaction);
    }

    public List<AccountTransaction> transactionList(LocalDate localDate, Long pass , Long accountId) throws Exception {
        Account account = accountDao.loadById(accountId);
        if(!account.getCreditCard().getFirstPassword().equals(pass)){
            throw  new Exception("wrong pass");
        }
        List<AccountTransaction> transactionByDate = accountDao.getTransactionByDate(localDate, account);
        return transactionByDate;
    }

}
