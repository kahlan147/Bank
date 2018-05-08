package auction.dao;

import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDAOJPAImpl implements UserDAO
{
    private EntityManager em;

    public UserDAOJPAImpl(EntityManager em)
    {
        this.em = em;
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
        em.persist(user);

    }

    @Override
    public void edit(User user)
    {
        em.merge(user);
    }

    @Override
    public List<User> findAll()
    {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList();
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
        em.remove(user);
    }
}