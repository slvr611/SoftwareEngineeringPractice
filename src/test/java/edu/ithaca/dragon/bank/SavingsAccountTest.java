package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {

    @Test
    void compoundInterestTest(){
        SavingsAccount savingsAccount;
        savingsAccount = new SavingsAccount("a@b.com", "pass", 200, .5, 100);
        assertEquals(200.0, savingsAccount.getBalance());
        savingsAccount.compoundInterest();
        assertEquals(300.0, savingsAccount.getBalance());
        savingsAccount.compoundInterest();
        assertEquals(450.0, savingsAccount.getBalance());

        SavingsAccount savingsAccount2;
        savingsAccount2 = new SavingsAccount("a@c.com", "pass", 100, 0, 100);
        assertEquals(100.0, savingsAccount2.getBalance());
        savingsAccount2.compoundInterest();
        assertEquals(100.0, savingsAccount2.getBalance());
        savingsAccount2.compoundInterest();
        assertEquals(100.0, savingsAccount2.getBalance());

        SavingsAccount sa3;
        assertThrows(IllegalArgumentException.class, ()-> new SavingsAccount("a@d.com", "pass", 100, -0.1, 100));
    }

    @Test
    void withdrawMaxTest() throws InsufficientFundsException {
        SavingsAccount savingsAccount;
        savingsAccount = new SavingsAccount("a@b.com", "pass", 200.0, .5, 100);

        savingsAccount.withdraw(100.0);

        assertEquals(100.0, savingsAccount.getBalance());

        assertThrows(IllegalArgumentException.class, ()-> savingsAccount.withdraw(1.0));

        savingsAccount.resetWithdrawMax();
        savingsAccount.withdraw(50.0);
        savingsAccount.withdraw(50.0);


    }

}
