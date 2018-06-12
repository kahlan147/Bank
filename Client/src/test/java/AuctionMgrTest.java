import AuctionWS.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AuctionMgrTest
{
    Registration registrationService = new RegistrationService().getPort(Registration.class);
    Auction auctionService = new AuctionService().getPort(Auction.class);

    @Before
    public void setUp() throws Exception
    {
        auctionService.cleanDatabase();
    }

    @Test
    public void getItem()
    {

        String email = "xx2@nl";
        String omsch = "omsch";

        User seller1 = registrationService.registerUser(email);
        Category cat = new Category();
        cat.setDescription("cat2");
        Item item1 = auctionService.offerItem(seller1, cat, omsch);
        Item item2 = auctionService.getItem(item1.getId());
        assertEquals(omsch, item2.getDescription());
        assertEquals(email, item2.getSeller().getEmail());
    }

    @Test
    public void findItemByDescription()
    {
        String email3 = "xx3@nl";
        String omsch = "omsch";
        String email4 = "xx4@nl";
        String omsch2 = "omsch2";

        User seller3 = registrationService.registerUser(email3);
        User seller4 = registrationService.registerUser(email4);
        Category cat = new Category();
        cat.setDescription("cat3");
        Item item1 = auctionService.offerItem(seller3, cat, omsch);
        Item item2 = auctionService.offerItem(seller4, cat, omsch);

        List<Item> res = auctionService.findItemByDescription(omsch2);
        assertEquals(0, res.size());

        res = auctionService.findItemByDescription(omsch);
        assertEquals(2, res.size());

    }

    @Test
    public void newBid()
    {

        String email = "ss2@nl";
        String emailb = "bb@nl";
        String emailb2 = "bb2@nl";
        String omsch = "omsch_bb";

        User seller = registrationService.registerUser(email);
        User buyer = registrationService.registerUser(emailb);
        User buyer2 = registrationService.registerUser(emailb2);
        // eerste bod
        Category cat = new Category();
        cat.setDescription("cat9");
        Item item1 = auctionService.offerItem(seller, cat, omsch);
        Money money1 = new Money();
        money1.setCents(10);
        money1.setCurrency("eur");
        Bid new1 = auctionService.newBid(item1, buyer, money1);
        assertEquals(emailb, new1.getBuyer().getEmail());

        // lager bod
        Money money2 = new Money();
        money2.setCents(9);
        money2.setCurrency("eur");
        Bid new2 = auctionService.newBid(item1, buyer2, money2);
        assertNull(new2);

        // hoger bod
        Money money3 = new Money();
        money3.setCents(11);
        money3.setCurrency("eur");
        Bid new3 = auctionService.newBid(item1, buyer2, money3);
        assertEquals(emailb2, new3.getBuyer().getEmail());


        //FIXME: is this it? opdracht 4.2
        Money money4 = new Money();
        money4.setCents(12);
        money4.setCurrency("eur");
        Bid new4 = auctionService.newBid(null, buyer2, money4);
        assertNull(new4);
    }
}
