package ir.maktab.repositories.customerside.card;


import ir.maktab.entities.customerside.card.CardPasswordInfo;
import ir.maktab.repositories.CrudRepository;

public class CardPasswordInfoDao extends CrudRepository<CardPasswordInfo,Long> {


    @Override
    protected Class<CardPasswordInfo> getEntityClass() {
        return CardPasswordInfo.class;
    }
}
