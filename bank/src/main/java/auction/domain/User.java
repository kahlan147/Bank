package auction.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
    @OneToMany
    private List<Item> auctionItems = new ArrayList<>();

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

    @Override
    public boolean equals(Object other)
    {
        return other instanceof User && this.email.equals(((User) other).email);
    }
}
