package com.imt.mines.bank.bdd;

import static org.junit.Assert.assertEquals;
import bankAccountApp.BankAccount; // Import corrigé selon ton projet
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class BankAccountBasicSteps {
    private BankAccount account;
    private double observedBalance; // Attention: getBalance() renvoie un double

    @Given("I have a new bank account")
    public void i_have_a_new_bank_account() {
        // TODO: create a new bank account with initial balance 0
        // On utilise le constructeur par défaut qui initialise tout à 0/null
        account = new BankAccount();
    }

    @When("I check its balance")
    public void i_check_its_balance() {
        // TODO: read the balance from the account and store it in observedBalance
        observedBalance = account.getBalance();
    }

    @Then("the balance should be {int}")
    public void the_balance_should_be(Integer expected) {
        // TODO: assert that observedBalance equals expected
        // On compare le double observé avec l'entier attendu (avec une tolérance delta de 0.001)
        assertEquals(expected.doubleValue(), observedBalance, 0.001);
    }
}