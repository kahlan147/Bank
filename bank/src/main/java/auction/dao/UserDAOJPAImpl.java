package auction.dao;

import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDAOJPAImpl implements UserDAO
{
    private EntityManager em;

    public UserDAOJPAImpl()
    {
        this.em = Persistence.createEntityManagerFactory("se42").createEntityManager();
    }

    @Override
    public int count()
    {
        Query q = em.createNamedQuery("User.count", User.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(User user)
    {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void edit(User user)
    {
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    @Override
    public List<User> findAll()
    {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList(); //If database is empty, this returns an empty list by itself.
                                                   //There is no exception handling needed for catching an empty list.
    }

    @Override
    public User findByEmail(String email)
    {
        Query q = em.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);
        try
        {
            return (User) q.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public void remove(User user)
    {
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }
}