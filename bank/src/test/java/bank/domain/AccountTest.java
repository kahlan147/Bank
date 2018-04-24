package bank.domain;

import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by Niels Verheijen on 24/04/2018.
 */
public class AccountTest {

    /*
        1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
        2.	Welke SQL statements worden gegenereerd?
        3.	Wat is het eindresultaat in de database?
        4.	Verklaring van bovenstaande drie observaties.
     */

    @Test
    public void Question1() {
        EntityManager em = Persistence.createEntityManagerFactory("bankPU").createEntityManager();
        try {
            new DatabaseCleaner(em).clean();
        }
        catch(SQLException e){

        }
        Account account = new Account(151L);
        em.getTransaction().begin();
        em.persist(account);
        //TODO: verklaar en pas eventueel aan
        //Account werd nog niet gezien als entity, omdat deze niet vermeld stond in de persistence.xml
        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        //TODO: verklaar en pas eventueel aan
        //Hij geeft de Id van het laatst opgeslagen account terug, er wordt een id meegegeven maar
        // omdat er een auto identity in staat wordt deze automatisch gegenereerd ipv de meegegeven waarde.
        assertTrue(account.getId() > 0L);

        /*
            1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
                - Het is een manier van controleren of de gewenste data teruggegeven wordt.
            2.	Welke SQL statements worden gegenereerd?
                - CREATE (als er nog geen table bestaat), INSERT, SELECT
            3.	Wat is het eindresultaat in de database?
                - een table met een row van opgegeven data.
            4.	Verklaring van bovenstaande drie observaties.
                - Er is een table aangemaakt in de database, hier wordt de account aangemaakt. De Id wordt niet
                 gegenereerd in de applicatie, maar in de database. Door de Id terug te vragen komen we erachter
                 dat het account inderdaad in de database geweest is.
         */

    }

    @Test
    public void Question2() {
    }

    @Test
    public void Question3() {
    }

    @Test
    public void Question4() {
    }

    @Test
    public void Question5() {
    }

    @Test
    public void Question6() {
    }

    @Test
    public void Question7() {
    }

    @Test
    public void Question8() {
    }

    @Test
    public void Question9() {
    }
}