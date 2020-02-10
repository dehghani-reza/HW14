package ir.maktab.repositories.customerside.card;


import ir.maktab.core.hibernate.HibernateUtil;
import ir.maktab.entities.customerside.card.CreditCard;
import ir.maktab.repositories.CrudRepository;
import org.hibernate.Session;

public class CreditCardDao extends CrudRepository<CreditCard,Long> {

    @Override
    protected Class<CreditCard> getEntityClass() {
        return CreditCard.class;
    }

    public Session updateTransferMoneySourceCard(CreditCard firstCreditCard){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(firstCreditCard);
        return session;
    }

    public void updateTransferMoneyDestinationCard(CreditCard secondCreditCard , Session session){
        session.beginTransaction();
        session.update(secondCreditCard);
        session.getTransaction().commit();
    }
}
