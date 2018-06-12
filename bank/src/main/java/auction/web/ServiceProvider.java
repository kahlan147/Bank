package auction.web;

import javax.xml.ws.Endpoint;

public class ServiceProvider
{
    private static final String urlAuction = "http://localhost:8080/Auction";
    private static final String urlRegistration = "http://localhost:8080/Registration";

    public static void main(String[] args)
    {
        Endpoint.publish(urlAuction, new Auction());
        Endpoint.publish(urlRegistration, new Registration());
    }
}
