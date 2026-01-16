package bankAccountApp;

import com.imt.mines.bankAccountApp.Bank;
import com.imt.mines.bankAccountApp.BankAccount;
import com.imt.mines.bankAccountApp.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class BankTest {
    String name = "John";
    char gender = 'm';
    int age = 22;
    int weight = 190;
    float height = 172;
    String hairColor = "brown";
    String eyeColor = "green";
    String email = "jufm@gmail.com";

    int ifloadaccManager = 0;
    int initMoneyAmount = 5000;
    int withdrawLimit = 760;
    String dateCreated = "05/21/2019";

    // Déclaration des objets
    Bank bank;
    Person accountHolder;

    @BeforeEach
    public void setup() {
        // Initialisation avant chaque test
        try {
            accountHolder = new Person(name, gender, age, weight, height, hairColor, eyeColor, email);
        } catch (Exception e) {
            System.out.print("Unexpected failure during test setup");
            e.printStackTrace();
        }
        bank = new Bank();
    }

    @Test
    public void testCreateAccount_DeleteAccount() {
        // Given
        BankAccount acc1 = new BankAccount(initMoneyAmount, withdrawLimit, dateCreated, accountHolder);
        bank.addAccount(acc1, ifloadaccManager);
        int accountNumber = acc1.getAccountNumber();

        // When
        bank.deleteAccount(acc1.getAccountNumber());

        // Then
        assertNull(bank.findAccount(accountNumber), "Account was not deleted");
    }

    @Test
    public void testFindAccount() {
        BankAccount acc1 = new BankAccount(initMoneyAmount, withdrawLimit, dateCreated, accountHolder);
        BankAccount acc2 = new BankAccount(initMoneyAmount, 400, dateCreated, accountHolder);

        bank.addAccount(acc1, ifloadaccManager);
        bank.addAccount(acc1, 2);
        bank.addAccount(acc1, 2);
        bank.addAccount(acc2, 2);

        BankAccount tmpacc = bank.findAccount(4);
        // Conversion JUnit 5
        assertEquals(400.0, tmpacc.getWithdrawLimit(), 0.001);
    }

    @Test
    public void testGetAccounts() {
        // Given
        BankAccount acc1 = new BankAccount(initMoneyAmount, withdrawLimit, dateCreated, accountHolder);
        BankAccount acc2 = new BankAccount(1000, withdrawLimit, dateCreated, accountHolder);
        bank.addAccount(acc2, ifloadaccManager);
        bank.addAccount(acc1, ifloadaccManager);

        // Then
        ArrayList<BankAccount> accounts = bank.getAccounts();
        assertEquals(2, accounts.size());
    }

    @Test
    public void testGetAccountsLoaded() {
        BankAccount acc1 = new BankAccount(initMoneyAmount, withdrawLimit, dateCreated, accountHolder);
        BankAccount acc2 = new BankAccount(initMoneyAmount, 400, dateCreated, accountHolder);

        bank.addAccount(acc1, ifloadaccManager);
        bank.addAccount(acc1, 2);
        bank.addAccount(acc1, 2);
        bank.addAccount(acc2, 2);

        assertEquals(4, bank.getAccountsLoaded());
    }

    // --- HAPPY PATH EXISTANT ---
    @Test
    public void testCreateAccount_GetMaximumBalance() {
        // Given (Soldes positifs : 5000 et 10000)
        int acct1Amount = 5000;
        int acct2Amount = 10000;
        BankAccount acc1 = new BankAccount(acct1Amount, withdrawLimit, dateCreated, accountHolder);
        BankAccount acc2 = new BankAccount(acct2Amount, withdrawLimit, dateCreated, accountHolder);
        bank.addAccount(acc2, ifloadaccManager);
        bank.addAccount(acc1, ifloadaccManager);

        // Then (Ça marche car 10000 > 0.0)
        assertEquals(10000, bank.getMaximumBalance(), 0f);
    }

    // --- NOUVEAU TEST EDGE CASE (Celui qui va échouer) ---
    @Test
    public void testGetMaximumBalance_WithAllNegativeAccounts() {
        // Scenario : Tous les comptes sont à découvert
        BankAccount acc1 = new BankAccount(-100, withdrawLimit, dateCreated, accountHolder);
        BankAccount acc2 = new BankAccount(-50, withdrawLimit, dateCreated, accountHolder); // Le max est -50

        bank.addAccount(acc1, 0);
        bank.addAccount(acc2, 0);

        // Act & Assert
        // Ce test va échouer si le code initialise max à 0.0
        assertEquals(-50.0, bank.getMaximumBalance(), 0.001, "Le solde maximum devrait être -50.0");
    }

    @Test
    public void testCreateAccount_GetMinimumBalance() {
        BankAccount acc1 = new BankAccount(initMoneyAmount, withdrawLimit, dateCreated, accountHolder);
        BankAccount acc2 = new BankAccount(1000, withdrawLimit, dateCreated, accountHolder);
        bank.addAccount(acc2, ifloadaccManager);
        bank.addAccount(acc1, ifloadaccManager);

        assertEquals(1000, bank.getMinimumBalance(), 0f);
    }

    @Test
    public void testGetAverageBalance() {
        // Scenario : On ajoute deux comptes (100 et 200). La moyenne doit être 150.
        BankAccount acc1 = new BankAccount(100.0, withdrawLimit, dateCreated, accountHolder);
        BankAccount acc2 = new BankAccount(200.0, withdrawLimit, dateCreated, accountHolder);

        bank.addAccount(acc1, 0);
        bank.addAccount(acc2, 0);

        // Vérification
        assertEquals(150.0, bank.getAverageBalance(), 0.001, "La moyenne des soldes est incorrecte");
    }
}