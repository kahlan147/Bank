package auction.web;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.RegistrationMgr;
import auction.service.SellerMgr;
import nl.fontys.util.DatabaseCleaner;
import nl.fontys.util.Money;

import javax.jws.WebService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.annotation.XmlAccessorType;
import java.sql.SQLException;
import java.util.List;

@WebService
public class Auction
{
    private AuctionMgr auctionMgr = new AuctionMgr();
    private SellerMgr sellerMgr = new SellerMgr();

    public Item getItem(Long id)
    {
        return auctionMgr.getItem(id);
    }

    public List<Item> findItemByDescription(String description)
    {
        return auctionMgr.findItemByDescription(description);
    }

    public Bid newBid(Item item, User buyer, Money amount)
    {
        return auctionMgr.newBid(item, buyer, amount);
    }

    public Item offerItem(User seller, Category cat, String description)
    {
        return sellerMgr.offerItem(seller, cat, description);
    }

    public boolean revokeItem(Item item)
    {
        return sellerMgr.revokeItem(item);
    }

    public void cleanDatabase()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("se42");
        try
        {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
