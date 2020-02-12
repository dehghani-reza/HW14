package ir.maktab.service.creditcard;

import ir.maktab.entities.customerside.Account;
import ir.maktab.entities.customerside.card.CardPasswordInfo;
import ir.maktab.entities.customerside.card.CreditCard;
import ir.maktab.repositories.customerside.AccountDao;
import ir.maktab.repositories.customerside.card.CreditCardDao;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.Random;

public class CardService {

    AccountDao accountDao;
    CreditCardDao creditCardDao;

    public CardService(AccountDao accountDao, CreditCardDao creditCardDao) {
        this.accountDao = accountDao;
        this.creditCardDao = creditCardDao;
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
        CreditCard sourceCreditCard = creditCardDao.loadById(sourceCardId);
        Long amountSource = sourceCreditCard.getAccount().getAccountBalance();

        if (sourceCreditCard.isSuspended()) throw new Exception("you'r card not valid");
        cardPasswordInfo.setCreditCard(sourceCreditCard);
        if (!sourceCreditCard.getCardPasswordInfo().equals(cardPasswordInfo)) {
            int wrongPassCounter = sourceCreditCard.getWrongPassCounter();
            wrongPassCounter += 1;
            sourceCreditCard.setWrongPassCounter(wrongPassCounter);
            creditCardDao.update(sourceCreditCard);
            throw new Exception("wrong card password information");
        }

        if (amountSource < (amount + 500)) {
            throw new Exception("you'r balance is low");
        }
        CreditCard destinationCreditCard = creditCardDao.loadById(destinationCardId);
        Long destinationAmount = destinationCreditCard.getAccount().getAccountBalance();

        amountSource -= (amount + 500);
        sourceCreditCard.getAccount().setAccountBalance(amountSource);

        destinationAmount += amount;
        destinationCreditCard.getAccount().setAccountBalance(destinationAmount);

        Session session = creditCardDao.updateTransferMoneySourceCard(sourceCreditCard);
        creditCardDao.updateTransferMoneyDestinationCard(destinationCreditCard, session);

    }


    private void validateFirstPass(CreditCard creditCard, Long firstPass) throws Exception {
        int counter = creditCard.getWrongPassCounter();
        if (creditCard.isSuspended()) throw new Exception("you'r card is suspended please call bank");
        if (!creditCard.getFirstPassword().equals(firstPass)) {
            counter += 1;
            if (counter >= 3) {
                creditCard.setSuspended(true);
                creditCardDao.update(creditCard);
                throw new Exception("you'r card suspended");
            }
            creditCard.setWrongPassCounter(counter);
            creditCardDao.update(creditCard);
            throw new Exception("password is wrong");
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


}
