package auction.domain;

import javax.persistence.*;
import java.util.*;

@Entity (name="Users")
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "select u from Users as u"),
        @NamedQuery(name = "User.count", query = "select count(u) from Users as u"),
        @NamedQuery(name = "User.findByEmail", query = "select u from Users as u where u.email = :email")
})
public class User
{
    @Id
    @Column(unique = true)
    private String email;
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Item> offeredItems = new HashSet<>();

    public User()
    {

    }

    public User(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public Iterator getOfferedItems()
    {
        return offeredItems.iterator();
    }

    public int numberOfOfferedItems()
    {
        return offeredItems.size();
    }

    void addItem(Item item)
    {
        offeredItems.add(item);
    }

    @Override
    public boolean equals(Object other)
    {
        return other instanceof User && this.email.equals(((User) other).email);
    }
}
