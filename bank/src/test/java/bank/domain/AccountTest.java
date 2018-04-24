package bank.domain;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

/**
 * Created by Niels Verheijen on 24/04/2018.
 */
public class AccountTest {

    @Test
    public void getId() {

        EntityManager em = Persistence.createEntityManagerFactory("bankPU").createEntityManager();
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
//TODO: verklaar en pas eventueel aan
        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
//TODO: verklaar en pas eventueel aan
        assertTrue(account.getId() > 0L);

    }

    @Test
    public void setId() {
    }

    @Test
    public void getAccountNr() {
    }

    @Test
    public void setAccountNr() {
    }

    @Test
    public void getBalance() {
    }

    @Test
    public void setBalance() {
    }

    @Test
    public void getThreshold() {
    }

    @Test
    public void setThreshold() {
    }

    @Test
    public void add() {
    }
}