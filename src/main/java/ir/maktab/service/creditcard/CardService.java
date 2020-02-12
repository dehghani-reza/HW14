package ir.maktab.service.creditcard;

import ir.maktab.entities.customerside.Account;
import ir.maktab.entities.customerside.card.CardPasswordInfo;
import ir.maktab.entities.customerside.card.CreditCard;
import ir.maktab.entities.customerside.transaction.CreditTransferRequest;
import ir.maktab.repositories.customerside.AccountDao;
import ir.maktab.repositories.customerside.card.CreditCardDao;
import ir.maktab.service.customer.transaction.CreditTransferRequestService;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.Random;

public class CardService {

    AccountDao accountDao;
    CreditCardDao creditCardDao;
    CreditTransferRequestService creditTransferRequestService;

    public CardService(AccountDao accountDao, CreditCardDao creditCardDao ,  CreditTransferRequestService creditTransferRequestService) {
        this.accountDao = accountDao;
        this.creditCardDao = creditCardDao;
        this.creditTransferRequestService=creditTransferRequestService;
    }

    public CreditCard createCard(Long accountId, Long password) {
        CardPasswordInfo cardPasswordInfo = new CardPasswordInfo();
        Random rand = new Random();
        int cvv2 = rand.nextInt(10000);
        cardPasswordInfo.setCvv2(cvv2);
        cardPasswordInfo.setExpirationDate(LocalDate.now().plusMonths(25));
        //**************************
        Account account = accountDao.loadById(accountId);
        //**************************
        CreditCard creditCard = new CreditCard();
        creditCard.setAccount(account);
        creditCard.setFirstPassword(password);
        creditCard.setCardPasswordInfo(cardPasswordInfo);
        creditCard.setSuspended(false);
        return creditCardDao.save(creditCard);
    }

    public CreditCard updateFirstPass(Long cardId, Long oldFirstPass, Long newFirstPass) throws Exception {
        CreditCard creditCard = creditCardDao.loadById(cardId);
        validateFirstPass(creditCard, oldFirstPass);
        creditCard.setFirstPassword(newFirstPass);
        return creditCardDao.update(creditCard);
    }

    public CreditCard updateSecondPass(Long cardId, Long firstPass, Long secondPass) throws Exception {
        CreditCard creditCard = creditCardDao.loadById(cardId);
        validateFirstPass(creditCard, firstPass);
        CardPasswordInfo cardPasswordInfo = creditCard.getCardPasswordInfo();
        cardPasswordInfo.setSecondPassword(secondPass);
        return creditCardDao.update(creditCard);
    }

    public Long cardBalance(Long cardId, Long firstPass) throws Exception {
        CreditCard creditCard = creditCardDao.loadById(cardId);
        validateFirstPass(creditCard, firstPass);
        return creditCard.getAccount().getAccountBalance();
    }

    public void transferMoneyByCard(Long sourceCardId, Long amount, CardPasswordInfo cardPasswordInfo, Long destinationCardId) throws Exception {
        CreditTransferRequest creditTransferRequest = new CreditTransferRequest();
        CreditCard sourceCreditCard = creditCardDao.loadById(sourceCardId);
        Long amountSource = sourceCreditCard.getAccount().getAccountBalance();
        transaction(amount,sourceCreditCard.getAccount(),null,"transferMoneyByCard",creditTransferRequest);

        if (sourceCreditCard.isSuspended()){
            creditTransferRequest.setDescription("you'r card not valid");
            transactionSave(creditTransferRequest,false);
            throw new Exception("you'r card not valid");
        }

        cardPasswordInfo.setCreditCard(sourceCreditCard);
        if (!sourceCreditCard.getCardPasswordInfo().equals(cardPasswordInfo)) {
            int wrongPassCounter = sourceCreditCard.getWrongPassCounter();
            wrongPassCounter += 1;
            sourceCreditCard.setWrongPassCounter(wrongPassCounter);
            creditCardDao.update(sourceCreditCard);
            creditTransferRequest.setDescription("wrong card password information");
            transactionSave(creditTransferRequest,false);
            suspendCard(sourceCreditCard);
            throw new Exception("wrong card password information");
        }

        if (amountSource < (amount + 500)) {
            creditTransferRequest.setDescription("you'r balance is low");
            transactionSave(creditTransferRequest,false);
            throw new Exception("you'r balance is low");
        }
        CreditCard destinationCreditCard = creditCardDao.loadById(destinationCardId);
        Long destinationAmount = destinationCreditCard.getAccount().getAccountBalance();
        transaction(amount,sourceCreditCard.getAccount(),destinationCreditCard,"transferMoneyByCard",creditTransferRequest);

        amountSource -= (amount + 500);
        sourceCreditCard.getAccount().setAccountBalance(amountSource);

        destinationAmount += amount;
        destinationCreditCard.getAccount().setAccountBalance(destinationAmount);

        try{
            Session session = creditCardDao.updateTransferMoneySourceCard(sourceCreditCard);
            creditCardDao.updateTransferMoneyDestinationCard(destinationCreditCard, session);
        }catch (Exception e){
            transactionSave(creditTransferRequest,false);
        }
        transactionSave(creditTransferRequest,true);
    }


    private void validateFirstPass(CreditCard creditCard, Long firstPass) throws Exception {
        int counter = creditCard.getWrongPassCounter();
        if (creditCard.isSuspended()) throw new Exception("you'r card is suspended please call bank");
        if (!creditCard.getFirstPassword().equals(firstPass)) {
            counter += 1;
            if (counter >= 3) {
                suspendCard(creditCard);
            }
            creditCard.setWrongPassCounter(counter);
            creditCardDao.update(creditCard);
            throw new Exception("password is wrong");
        }
    }
private void suspendCard(CreditCard creditCard) throws Exception {
        if(creditCard.getWrongPassCounter()>=3){
            creditCard.setSuspended(true);
            creditCardDao.save(creditCard);
            throw new Exception("your card has been suspended");
        }
}

    public void validateCardId(Long cardId) throws Exception {
        CreditCard creditCard = creditCardDao.loadById(cardId);
        if (creditCard == null) {
            throw new Exception("no card with this id registered");
        }
    }

    public void preValidateCardTransfer(Long sourceCardId, Long destinationCardId) throws Exception {
        validateCardId(sourceCardId);
        validateCardId(destinationCardId);
    }

    public void transaction(Long amount, Account account, CreditCard destinationCard, String description,CreditTransferRequest creditTransferRequest){
        creditTransferRequest.setTransferAmount(amount);
        creditTransferRequest.setOriginAccount(account);
        creditTransferRequest.setDestinationCardId(destinationCard);
        creditTransferRequest.setDescription(description);
        creditTransferRequest.setTransactionDate(LocalDate.now());
    }

    public  void  transactionSave(CreditTransferRequest creditTransferRequest , boolean isTransactionSuccessfullyDone){
        creditTransferRequest.setSuccessfullyDone(isTransactionSuccessfullyDone);
        creditTransferRequestService.saveCreditTransferRequestTransaction(creditTransferRequest);
    }

}
