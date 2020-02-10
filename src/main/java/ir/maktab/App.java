package ir.maktab;

import ir.maktab.core.hibernate.HibernateUtil;
import ir.maktab.entities.Address;
import ir.maktab.entities.customerside.Customer;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();
        int rand_int2 = rand.nextInt(10000);
        Date date = new Date();
        long time = date.getTime();
        Calendar today = Calendar.getInstance();

        LocalDate datea = LocalDate.now().plusMonths(25);
        System.out.println( datea );
        today.set(Calendar.HOUR_OF_DAY, 0);

        for (int i = 0; i < 1; i++) {

            System.out.println(time);
        }
//        Session session = HibernateUtil.getSession();
//        session.beginTransaction();
//        Customer customer = new Customer();
//        customer.setName("reza");
//        customer.setLastName("dehghani");
//        Address address = new Address();
//        address.setAlley("1");
//        session.save(address);
//        session.getTransaction().commit();
//        session.close();
    }
}
