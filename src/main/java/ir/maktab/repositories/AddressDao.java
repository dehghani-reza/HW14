package ir.maktab.repositories;


import ir.maktab.entities.Address;

public class AddressDao extends CrudRepository<Address,Long> {


    @Override
    protected Class<Address> getEntityClass() {
        return Address.class;
    }
}
