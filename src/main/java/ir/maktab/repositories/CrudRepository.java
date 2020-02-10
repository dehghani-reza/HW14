package ir.maktab.repositories;

import ir.maktab.core.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;

public abstract class CrudRepository<Entity, ID extends Serializable> {

    protected abstract Class<Entity> getEntityClass();

    Session session = HibernateUtil.getSession();

    public Entity save(Entity entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        return entity;
    }

    public Entity loadById(ID id) {
        session.beginTransaction();
        Entity entity = session.find(getEntityClass(), id);
        session.getTransaction().commit();
        return entity;
    }

    public List<Entity> loadAll() {
        session.beginTransaction();
        Query<Entity> query = session
                .createQuery("from " + getEntityClass().getName(), getEntityClass());
        List<Entity> entities = query.list();
        session.getTransaction().commit();
        return entities;
    }


    public Entity update(Entity entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        return entity;
    }

    public void delete(Entity entity) {
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
    }

    public void deleteById(ID id) throws Exception {
        Entity entity = loadById(id);
        if (entity == null) {
            throw new Exception("Not found to delete");
        }
        session.beginTransaction();
        session.remove(entity);
        session.getTransaction().commit();
    }
}
