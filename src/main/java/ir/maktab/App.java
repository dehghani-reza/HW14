package ir.maktab;


import com.fasterxml.jackson.databind.ObjectMapper;
import ir.maktab.core.hibernate.HibernateUtil;
import ir.maktab.entities.Address;
import ir.maktab.entities.bankside.BankBranch;
import ir.maktab.entities.bankside.BankBranchManager;
import ir.maktab.entities.bankside.BankEmployee;
import ir.maktab.entities.customerside.Account;
import ir.maktab.entities.customerside.Customer;
import ir.maktab.entities.customerside.card.CardPasswordInfo;
import ir.maktab.service.account.AccountService;
import ir.maktab.service.creditcard.CardService;
import ir.maktab.service.customer.CustomerService;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InterruptedException {
        try {
            starter();
            System.out.println("welcome");
        } catch (PersistenceException e) {
            System.out.println("welcome back");
        }
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

        Scanner scanner = new Scanner(System.in);
        Scanner scannerLong = new Scanner(System.in);
        AccountService accountService = applicationContext.getBean("accountService", AccountService.class);
        CardService cardService = applicationContext.getBean("cardService", CardService.class);
        CustomerService customerService = applicationContext.getBean("customerService", CustomerService.class);

        String command = "enter";
        while (!command.equalsIgnoreCase("Exit")) {
            System.out.println("here is your services: \n1) withdraw \t2) Deposit \t3) transfer with card \t4) balance check " +
                    "\n5) create new account \t6) add card to account \t7) change first pass \t8) change second pass \t9) load all account");
            command = scanner.nextLine();
            command = command.toLowerCase();
            command = command.replaceAll("\\s+", "");
            switch (command) {
                case "withdraw":
                    System.out.println("enter amount and accountId");
                    Long amount = scannerLong.nextLong();
                    Long accountId = scannerLong.nextLong();
                    try {
                        System.out.println(accountService.withdrawFromAccountBalance(amount, accountId));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "deposit":
                    System.out.println("enter amount and accountId");
                    amount = scannerLong.nextLong();
                    accountId = scannerLong.nextLong();
                    System.out.println(accountService.addToAccountBalance(amount, accountId));
                    break;
                case "transferwithcard":
                    System.out.println("your card id:");
                    Long sourceCardId = scannerLong.nextLong();
                    System.out.println("destination card id:");
                    Long destinationCardId = scannerLong.nextLong();
                    try {
                        cardService.preValidateCardTransfer(sourceCardId, destinationCardId);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println("how much money you want to transfer?");
                    Long moneyToTransfer = scannerLong.nextLong();
                    System.out.println("write your cvv2 expirationDate(yyyy/mm/dd) secondPassword");
                    int cvv2 = scannerLong.nextInt();
                    LocalDate exDate = null;
                    String expirationDate = scanner.nextLine();
                    try {
                        exDate = stringToDate(expirationDate);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    Long secondPassword = scannerLong.nextLong();
                    CardPasswordInfo cardPasswordInfo = new CardPasswordInfo();
                    cardPasswordInfo.setCvv2(cvv2);
                    cardPasswordInfo.setSecondPassword(secondPassword);
                    cardPasswordInfo.setExpirationDate(exDate);
                    try {
                        cardService.transferMoneyByCard(sourceCardId, moneyToTransfer, cardPasswordInfo, destinationCardId);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case "addcardtoaccount":
                    System.out.println("enter you'r account id and your firstpass for you'r new card:");
                    accountId = scannerLong.nextLong();
                    Long firstPass = scannerLong.nextLong();
                    try {
                        System.out.println(cardService.createCard(accountId, firstPass));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "balancecheck":
                    System.out.println("enter you'r card id and your first pass :");
                    Long cardId = scannerLong.nextLong();
                    firstPass = scannerLong.nextLong();
                    try {
                        System.out.println(cardService.cardBalance(cardId, firstPass));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "changefirstpass":
                    System.out.println("write your card id and old pass and new first pass:");
                    cardId = scanner.nextLong();
                    Long oldPass = scanner.nextLong();
                    Long newPass = scanner.nextLong();
                    try {
                        System.out.println(cardService.updateFirstPass(cardId, oldPass, newPass));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "changesecondpass":
                    System.out.println("write your card id and first pass and new second pass:");
                    cardId = scanner.nextLong();
                    firstPass = scanner.nextLong();
                    secondPassword = scanner.nextLong();
                    try {
                        System.out.println(cardService.updateSecondPass(cardId, firstPass, secondPassword));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "loadallaccount":
                    System.out.println("please write your Id:");
                    Long customerId = scannerLong.nextLong();
                    List<Account> accountList = customerService.loadCustomerAccounts(customerId);
                    accountList.forEach(System.out::println);
                    break;
            }
        }


    }//end of method main

    static void starter() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        session.save(applicationContext.getBean("address1", Address.class));
        session.save(applicationContext.getBean("address2", Address.class));
        session.save(applicationContext.getBean("address3", Address.class));
        session.save(applicationContext.getBean("address4", Address.class));
        session.save(applicationContext.getBean("manager1", BankBranchManager.class));
        session.save(applicationContext.getBean("branch_pirozi", BankBranch.class));
        session.save(applicationContext.getBean("employee1", BankEmployee.class));
        session.save(applicationContext.getBean("employee2", BankEmployee.class));
        session.save(applicationContext.getBean("customer1", Customer.class));
        session.save(applicationContext.getBean("customer2", Customer.class));
        session.save(applicationContext.getBean("customer3", Customer.class));
        session.save(applicationContext.getBean("account1", Account.class));
        session.save(applicationContext.getBean("account2", Account.class));
        session.save(applicationContext.getBean("account3", Account.class));
        session.save(applicationContext.getBean("account4", Account.class));
        session.save(applicationContext.getBean("account5", Account.class));
        session.getTransaction().commit();

    }

    static LocalDate stringToDate(String date) throws Exception {
        String[] parts = date.split("/");
        if (parts.length != 3) {
            throw new Exception("wrong date format");
        }
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        return LocalDate.of(year, month, day);
    }
}
