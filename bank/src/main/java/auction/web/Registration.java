package auction.web;

import auction.domain.User;
import auction.service.RegistrationMgr;

import javax.jws.WebService;

@WebService
public class Registration
{
    RegistrationMgr registrationMgr = new RegistrationMgr();

    public User registerUser(String email)
    {
        return registrationMgr.getUser(email);
    }

    public User getUser(String email)
    {
        return registrationMgr.getUser(email);
    }
}
