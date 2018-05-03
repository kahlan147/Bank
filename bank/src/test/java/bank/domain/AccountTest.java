package bank.domain;

import bank.dao.AccountDAOJPAImpl;
import org.junit.After;
import org.junit.Before;
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
    private EntityManager em;

    @Before
    public void SetUp() {
        em = Persistence.createEntityManagerFactory("bankPU").createEntityManager();

        try {
            new DatabaseCleaner(em).clean();
        }
        catch(SQLException e){

        }
        em = Persistence.createEntityManagerFactory("bankPU").createEntityManager();

    }


    /*
        1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
        2.	Welke SQL statements worden gegenereerd?
        3.	Wat is het eindresultaat in de database?
        4.	Verklaring van bovenstaande drie observaties.
     */

    @Test
    public void Question1() {

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
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        assertNull(account.getId());
        em.getTransaction().rollback();
// TODO code om te testen dat table account geen records bevat. Hint: bestudeer/gebruik AccountDAOJPAImpl
        AccountDAOJPAImpl accountDAOJPA = new AccountDAOJPAImpl(em);
        assertEquals(0, accountDAOJPA.count());

        /*
            1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
                - Er wordt gecontrolleerd of er daadwerkelijk 0 records van accounts in de database staan.

            2.	Welke SQL statements worden gegenereerd?
                - DELETE FROM ACCOUNT
                - SELECT COUNT(ID) FROM ACCOUNT

            3.	Wat is het eindresultaat in de database?
                - Het gemaakte account is weg gehaald doormiddel van de rollback.

            4.	Verklaring van bovenstaande drie observaties.
                -
        */

    }

    @Test
    public void Question3() {
        Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);
        em.getTransaction().begin();
        em.persist(account);
//TODO: verklaar en pas eventueel aan
        //assertNotEquals naar assertEquals - Het account is nog niet opgeslagen in de database, dus de waarde wordt nog niet aangepast.
        assertEquals(expected, account.getId());
        em.flush();
//TODO: verklaar en pas eventueel aan
        //assertEquals naar assertNotEquals. Bij het flushen wordt het account gesynchroniseerd met de database,
        //hierdoor wordt automatisch een ID gegenereerd en aan Account gegeven. Dit overschrijft het zelf opgegeven ID.
        //Het account wordt nog niet opgeslagen.
        assertNotEquals(expected, account.getId());
        em.getTransaction().commit();
//TODO: verklaar en pas eventueel aan
        //Account wordt nu wel opgeslagen in de database.
        /*
            1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
                Flush synchroniseerd het huidige object met de database, hierdoor wordt alvast een ID gegeneerd en aan het object
                meegegeven.

            2.	Welke SQL statements worden gegenereerd?
                IDENT_CURRENT, INSERT

            3.	Wat is het eindresultaat in de database?
                Het account is aangemaakt met een automatisch gegenereerd ID ipv het zelf opgegeven ID

            4.	Verklaring van bovenstaande drie observaties.
                Flush is een manier om een object te syncen met de database zonder het object op te slaan.

        */
    }

    @Test
    public void Question4() {
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
//TODO: verklaar de waarde van account.getBalance
        Long  cid = account.getId();
        account = null;
        EntityManager em2 = Persistence.createEntityManagerFactory("bankPU").createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class,  cid);
//TODO: verklaar de waarde van found.getBalance
        assertEquals(expectedBalance, found.getBalance());
        /*
            1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
                - 400

            2.	Welke SQL statements worden gegenereerd?
                - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
                - SELECT @@IDENTITY
                - SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?)

            3.	Wat is het eindresultaat in de database?
                - Een account met een balance van 400

            4.	Verklaring van bovenstaande drie observaties.
                - Er wordt een account gemaakt met een balance van 400, hiervan wordt het id opgevraagd.
                Vervolgens wordt er een tweede entity manager aangemaakt en het account met het vorige opgevraagde id opgehaald.
                Hiervan wordt dan gecontrolleerd of de balance nogsteeds op 400 staat.

        */
    }

    @Test
    public void Question5() {
        /*In de vorige opdracht verwijzen de objecten account en
         found naar dezelfde rij in de database. Pas een van de objecten aan,
          persisteer naar de database. Refresh vervolgens het andere
           object om de veranderde state uit de database te halen.
            Test met asserties dat dit gelukt is.*/

        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());

        Long  cid = account.getId();

        EntityManager em2 = Persistence.createEntityManagerFactory("bankPU").createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class,  cid);
        em2.persist(found);
        found.setBalance(300L);
        em2.getTransaction().commit();

        em.refresh(account);

        assertEquals(300L, account.getBalance().longValue());

        /*
            1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
                - Er wordt gecontrolleerd of de waarde correct op 400 is gezet, en vervolgens na de aanpassing wordt er opnieuw gecontrolleerd naar de nieuwe waarde.

            2.	Welke SQL statements worden gegenereerd?
                - INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
                - SELECT @@IDENTITY
                - SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?)
                - UPDATE ACCOUNT SET BALANCE = ? WHERE (ID = ?)
                - SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?)

            3.	Wat is het eindresultaat in de database?
                - account heeft een veranderde balance van 300

            4.	Verklaring van bovenstaande drie observaties.
                - 
        */
    }

    @Test
    public void Question6S1() {
        //Merge is een van de lastigere methoden uit JPA api. Het is belangrijk dat je deze opgave daarom zorgvuldig uitvoert.

        Account acc = new Account(1L);

// scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.getTransaction().commit();
//TODO: voeg asserties toe om je verwachte waarde van de attributen te verifieren.
//TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.

        /*
            1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
                -

            2.	Welke SQL statements worden gegenereerd?
                -

            3.	Wat is het eindresultaat in de database?
                -

            4.	Verklaring van bovenstaande drie observaties.
                -

        */
    }

    @Test
    public void Question6S2() {
        Account acc = new Account(1L);
        Account acc9 = new Account(9L);

// scenario 2
        Long balance2a = 211L;
        acc = new Account(2L);
        em.getTransaction().begin();
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        acc9.setBalance(balance2a + balance2a);
        em.getTransaction().commit();
//TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
//TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
// HINT: gebruik acccountDAO.findByAccountNr

        /*
            1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
                -

            2.	Welke SQL statements worden gegenereerd?
                -

            3.	Wat is het eindresultaat in de database?
                -

            4.	Verklaring van bovenstaande drie observaties.
                -

        */
    }

    @Test
    public void Question6S3() {
        Account acc = new Account(1L);
// scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        Account acc2 = em.merge(acc);
        assertTrue(em.contains(acc)); // verklaar
        assertTrue(em.contains(acc2)); // verklaar
        assertEquals(acc, acc2);  //verklaar
        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit();
//TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
//TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.

        /*
            1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
                -

            2.	Welke SQL statements worden gegenereerd?
                -

            3.	Wat is het eindresultaat in de database?
                -

            4.	Verklaring van bovenstaande drie observaties.
                -

        */
    }

    @Test
    public void Question6S4() {
// scenario 4
        Account account = new Account(114L) ;
        account.setBalance(450L) ;
        EntityManager em = Persistence.createEntityManagerFactory("bankPU").createEntityManager() ;
        em.getTransaction().begin() ;
        em.persist(account) ;
        em.getTransaction().commit() ;

        Account account2 = new Account(114L) ;
        Account tweedeAccountObject = account2 ;
        tweedeAccountObject.setBalance(650l) ;
        assertEquals((Long)650L,account2.getBalance()) ;  //verklaar
        account2.setId(account.getId()) ;
        em.getTransaction().begin() ;
        account2 = em.merge(account2) ;
        assertSame(account,account2) ;  //verklaar
        assertTrue(em.contains(account2)) ;  //verklaar
        assertFalse(em.contains(tweedeAccountObject)) ;  //verklaar
        tweedeAccountObject.setBalance(850l) ;
        assertEquals((Long)650L,account.getBalance()) ;  //verklaar
        assertEquals((Long)650L,account2.getBalance()) ;  //verklaar
        em.getTransaction().commit() ;
        em.close() ;

        /*
            1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
                -

            2.	Welke SQL statements worden gegenereerd?
                -

            3.	Wat is het eindresultaat in de database?
                -

            4.	Verklaring van bovenstaande drie observaties.
                -

        */
    }

    @Test
    public void Question7() {
        Account acc1 = new Account(77L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
//Database bevat nu een account.

// scenario 1
        Account accF1;
        Account accF2;
        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        assertSame(accF1, accF2);

// scenario 2
        accF1 = em.find(Account.class, acc1.getId());
        em.clear();
        accF2 = em.find(Account.class, acc1.getId());
        assertNotSame(accF1, accF2);
//TODO verklaar verschil tussen beide scenario’s
        //Scenario 2 - assertsame naar assertNotSame
        //Na de entitymanager te clearen vallen alle binden met de database weg,
        //hierdoor zal naderhand een nieuw object verkregen worden.
        /*
            1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
                - Scenario 2 - assertsame naar assertNotSame.

            2.	Welke SQL statements worden gegenereerd?
                - SELECT * FROM ACCOUNT WHERE <Class PRIMARY KEY>

            3.	Wat is het eindresultaat in de database?
                - een account is aangemaakt in de database.

            4.	Verklaring van bovenstaande drie observaties.
                - em.find(<Class>,Primary key) vind het object in de database van de opgegeven table met de opgegeven primary key
                  em.clear() laat alle verbindingen met de database wegvallen, waardoor de teruggekregen object niet hetzelfde zijn
                  als voorheen.

        */
    }

    @Test
    public void Question8() {
        Account acc1 = new Account(88L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        Long id = acc1.getId();
//Database bevat nu een account.

        em.remove(acc1);
        assertEquals(id, acc1.getId());
        Account accFound = em.find(Account.class, id);
        assertNull(accFound);
//TODO: verklaar bovenstaande asserts

        /*
            1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
                -

            2.	Welke SQL statements worden gegenereerd?
                -

            3.	Wat is het eindresultaat in de database?
                -

            4.	Verklaring van bovenstaande drie observaties.
                -

        */
    }

    @Test
    public void Question9() {
        /*Opgave 1 heb je uitgevoerd met @GeneratedValue(strategy = GenerationType.IDENTITY)
        Voer dezelfde opdracht nu uit met GenerationType SEQUENCE en TABLE.
        Verklaar zowel de verschillen in testresultaat als verschillen van de database structuur.*/

        //SEQUENCE: Geen verschil, werkt hetzelfde.
        //TABLE: Foutmelding op assertNull. De Id is 1. Hierna volgt een enorme error op de commit.
        /*
        IDENTITY
          Indicates that the persistence provider must assign primary keys for the entity using a database identity column.
        SEQUENCE
          Indicates that the persistence provider must assign primary keys for the entity using a database sequence.
        TABLE
          Indicates that the persistence provider must assign primary keys for the entity using an underlying database table to ensure uniqueness.

         */
    }
}