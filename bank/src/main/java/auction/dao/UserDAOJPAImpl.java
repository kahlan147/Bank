package auction.dao;

import auction.domain.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAOJPAImpl implements UserDAO
{
    private EntityManager em;

    private HashMap<String, User> users;

    public UserDAOJPAImpl()
    {
//        users = new HashMap<String, User>();
        em = Persistence.createEntityManagerFactory("se42").createEntityManager();
        em.persist(users);
    }

    @Override
    public int count()
    {
        return users.size();
    }

    @Override
    public void create(User user)
    {
        if (findByEmail(user.getEmail()) != null)
        {
           throw new EntityExistsException();
        }
        users.put(user.getEmail(), user);
    }

    @Override
    public void edit(User user)
    {
        if (findByEmail(user.getEmail()) == null)
        {
            throw new IllegalArgumentException();
        }
        users.put(user.getEmail(), user);
    }


    @Override
    public List<User> findAll()
    {
        return new ArrayList<User>(users.values());
    }

    @Override
    public User findByEmail(String email)
    {
        return users.get(email);
    }

    @Override
    public void remove(User user)
    {
        users.remove(user.getEmail());
    }
}
