package auction.dao;

import auction.domain.Item;
import auction.domain.User;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by Niels Verheijen on 15/05/2018.
 */
public class ItemDAOJPAImpl implements ItemDAO
{

    private EntityManagerFactory emf;

    public ItemDAOJPAImpl()
    {
        this.emf = Persistence.createEntityManagerFactory("se42");
    }

    @Override
    public int count()
    {
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("Item.count", Item.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(Item item)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
    }

    @Override
    public void edit(Item item)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(item);
        em.getTransaction().commit();
    }

    @Override
    public Item find(Long id)
    {
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("Item.findById", Item.class);
        q.setParameter("id", id);
        try
        {
            return (Item) q.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public List<Item> findAll()
    {
        EntityManager em = emf.createEntityManager();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Item.class));
        return em.createQuery(cq).getResultList(); //If database is empty, this returns an empty list by itself.
        //There is no exception handling needed for catching an empty list.

    }

    @Override
    public List<Item> findByDescription(String description)
    {
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("Item.findByDescription", Item.class);
        q.setParameter("description", description);
        try
        {
            return (List<Item>) q.getResultList();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public void remove(Item item)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(item));
        em.getTransaction().commit();
    }
}
