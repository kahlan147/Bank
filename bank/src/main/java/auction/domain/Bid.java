package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;

@Entity (name="Bid")
public class Bid {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private FontysTime time;
    @ManyToOne
    private User buyer;
    private Money amount;
    @OneToOne
    @JoinColumn(nullable = false)
    private Item madeFor;

    public Bid(){}

    public Bid(User buyer, Money amount, Item madeFor) {
        this.buyer = buyer;
        this.amount = amount;
        this.madeFor = madeFor;
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }
}
