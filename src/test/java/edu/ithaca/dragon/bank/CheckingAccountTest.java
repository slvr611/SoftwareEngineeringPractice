package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckingAccountTest {

    @Test
    void getBalanceTest() {
        //Positive, negative, and 0 account value testing:
        //Valid case (0 - infinity)
        CheckingAccount checkingAccount;
        checkingAccount = new CheckingAccount("a@b.com", "pass", 200);
        assertEquals(200, checkingAccount.getBalance());  //Normal case, positive
        checkingAccount = new CheckingAccount("a@b.com","pass", 1);
        assertEquals(1, checkingAccount.getBalance());  //Edge case, positive
        checkingAccount = new CheckingAccount("a@b.com", "pass",0);
        assertEquals(0, checkingAccount.getBalance());  //Edge case, 0

        checkingAccount = new CheckingAccount("a@b.com", "pass",100);
        assertEquals(100, checkingAccount.getBalance());

    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        CheckingAccount checkingAccount;
        //Withdrawing only available funds tests:
        //Valid case (does not go over available funds)
        checkingAccount = new CheckingAccount("a@b.com", "pass",200);
        checkingAccount.withdraw(200);
        assertEquals(0, checkingAccount.getBalance());  //Edge case, withdrawing all available funds
        checkingAccount = new CheckingAccount("a@b.com", "pass",200);
        checkingAccount.withdraw(100);
        assertEquals(100, checkingAccount.getBalance());  //Normal case, withdrawing only half of available funds
        //Invalid case (withdraws more than available)
        CheckingAccount checkingAccount1 = new CheckingAccount("a@b.com", "pass",200);
        assertThrows(InsufficientFundsException.class, ()-> checkingAccount1.withdraw(201));  //Edge case, withdrawing just over available funds
        assertThrows(InsufficientFundsException.class, ()-> checkingAccount1.withdraw(400));  //Normal case, withdrawing double available funds

        //Passing only positive values tests:
        //Valid case (positive values including 0)
        checkingAccount = new CheckingAccount("a@b.com", "pass",200);
        checkingAccount.withdraw(0);  //Edge case, withdrawing 0
        checkingAccount.withdraw(100);  //Normal case, withdrawing positive number
        //Invalid case (negative values)
        CheckingAccount checkingAccount2 = new CheckingAccount("a@b.com", "pass",200);
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount2.withdraw(-1));  //Edge case, passing barely negative value
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount2.withdraw(-200));  //Normal case, passing very negative value

        //Significant decimals tests:
        //Valid case (0 - 2 significant decimals)
        checkingAccount = new CheckingAccount("a@b.com","pass", 200);
        checkingAccount.withdraw(50.3);  //Normal case, withdrawing 1 significant decimal
        checkingAccount.withdraw(50.35);  //Edge case, withdrawing 2 significant decimal
        //Invalid case (3 - infinity significant decimals)
        CheckingAccount checkingAccount3 = new CheckingAccount("a@b.com", "pass",200);
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount3.withdraw(50.352));  //Edge case, withdrawing 3 significant decimal
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount3.withdraw(50.35662));  //Normal case, withdrawing 5 significant decimal
    }

    @Test
    void depositTest() {
        CheckingAccount checkingAccount;

        //Passing only positive values tests:
        //Valid case (positive values including 0)
        checkingAccount = new CheckingAccount("a@b.com", "pass",200);
        checkingAccount.deposit(0);  //Edge case, depositing 0
        assertEquals(200, checkingAccount.getBalance(), 0.0001);
        checkingAccount.deposit(100);  //Normal case, depositing positive number
        assertEquals(300, checkingAccount.getBalance(), 0.0001);
        //Invalid case (negative values)
        CheckingAccount checkingAccount2 = new CheckingAccount("a@b.com","pass", 200);
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount2.deposit(-1));  //Edge case, passing barely negative value
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount2.deposit(-200));  //Normal case, passing very negative value

        //Significant decimals tests:
        //Valid case (0 - 2 significant decimals)
        checkingAccount = new CheckingAccount("a@b.com", "pass",200);
        checkingAccount.deposit(50.3);  //Normal case, depositing 1 significant decimal
        assertEquals(250.3, checkingAccount.getBalance(), 0.0001);
        checkingAccount.deposit(50.35);  //Edge case, depositing 2 significant decimal
        assertEquals(300.65, checkingAccount.getBalance(), 0.0001);
        //Invalid case (3 - infinity significant decimals)
        CheckingAccount checkingAccount3 = new CheckingAccount("a@b.com", "pass",200);
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount3.deposit(50.352));  //Edge case, depositing 3 significant decimal
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount3.deposit(50.35662));  //Normal case, depositing 5 significant decimal
    }

    @Test
    void transferTest() throws InsufficientFundsException{
        CheckingAccount checkingAccount;
        CheckingAccount checkingAccount1;

        //Passing only positive values tests:
        //Valid case (positive values including 0)
        checkingAccount = new CheckingAccount("a@b.com", "pass",200);
        checkingAccount1 = new CheckingAccount("a@b.com", "pass",200);
        checkingAccount.transfer(0, checkingAccount1);  //Edge case, transferring 0
        assertEquals(200, checkingAccount.getBalance(), 0.0001);
        assertEquals(200, checkingAccount1.getBalance(), 0.0001);
        checkingAccount.transfer(100, checkingAccount1);  //Normal case, transferring positive number
        assertEquals(100, checkingAccount.getBalance(), 0.0001);
        assertEquals(300, checkingAccount1.getBalance(), 0.0001);
        //Invalid case (negative values)
        CheckingAccount checkingAccount2 = new CheckingAccount("a@b.com", "pass",200);
        CheckingAccount checkingAccount3 = new CheckingAccount("a@b.com", "pass",200);
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount2.transfer(-1, checkingAccount3));  //Edge case, passing barely negative value
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount2.transfer(-200, checkingAccount3));  //Normal case, passing very negative value

        //Significant decimals tests:
        //Valid case (0 - 2 significant decimals)
        checkingAccount = new CheckingAccount("a@b.com", "pass",200);
        checkingAccount1 = new CheckingAccount("a@b.com", "pass",200);
        checkingAccount.transfer(50.3, checkingAccount1);  //Normal case, transferring 1 significant decimal
        assertEquals(149.7, checkingAccount.getBalance(), 0.0001);
        assertEquals(250.3, checkingAccount1.getBalance(), 0.0001);
        checkingAccount.transfer(50.35, checkingAccount1);  //Edge case, transferring 2 significant decimal
        assertEquals(99.35, checkingAccount.getBalance(), 0.0001);
        assertEquals(300.65, checkingAccount1.getBalance(), 0.0001);
        //Invalid case (3 - infinity significant decimals)
        CheckingAccount checkingAccount4 = new CheckingAccount("a@b.com", "pass",200);
        CheckingAccount checkingAccount5 = new CheckingAccount("a@b.com", "pass",200);
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount4.transfer(50.352, checkingAccount5));  //Edge case, transferring 3 significant decimal
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount4.transfer(50.35662, checkingAccount5));  //Normal case, transferring 5 significant decimal
    }

    @Test
    void isAmountValidTest(){
        //Non-negative test
        //Invalid case (-infinity - -0.01)
        assertFalse(CheckingAccount.isAmountValid(-100.00));  //Normal case, very negative value
        assertFalse(CheckingAccount.isAmountValid(-0.01));  //Edge case, barely negative value
        //Valid case (0.00 - infinity)
        assertTrue(CheckingAccount.isAmountValid(0.00));  //Edge case, barely non-negative
        assertTrue(CheckingAccount.isAmountValid(100.00));  //Normal case, postitive

        //Decimal place test
        //Valid case (0 - 2 decimal points)
        assertTrue(CheckingAccount.isAmountValid(10));  //Normal case, 0 decimal points
        assertTrue(CheckingAccount.isAmountValid(100.00));  //Edge case, 2 decimal points
        //Valid case (0 - 2 decimal points)
        assertFalse(CheckingAccount.isAmountValid(100.001));  //Edge case, 3 decimal points
        assertFalse(CheckingAccount.isAmountValid(100.00001));  //Normal case, 5 decimal points
    }

    @Test
    void isEmailValidTest(){
        //Prefix length tests
        //Invalid case (0)
        assertFalse(CheckingAccount.isEmailValid("@mail.com"));  //Edge case, size 0
        //Valid case (1-infinity)
        assertTrue(CheckingAccount.isEmailValid( "a@mail.com"));  //Edge case, size 1
        assertTrue(CheckingAccount.isEmailValid( "abcdef@mail.com"));  //Normal case, size 5

        //Invalid character tests:
        //Valid case (0)
        assertTrue(CheckingAccount.isEmailValid( "abcdef@mail.com"));  //Edge case, 0 invalid characters
        //Invalid case (1-infinity)
        assertFalse( CheckingAccount.isEmailValid("abc#def@mail.com"));  //Edge case, 1 invalid character
        assertFalse( CheckingAccount.isEmailValid("a^bc#de%f@mail.com"));  //Normal case, 3 invalid characters

        //Number of @ signs tests:
        //Invalid case (0)
        assertFalse(CheckingAccount.isEmailValid( "abcdef.mail.com"));  //Edge case, 0 @ signs
        //Valid case (1)
        assertTrue(CheckingAccount.isEmailValid( "abcdef@mail.com"));  //Edge case, 1 @ sign
        //Invalid case (2-infinity)
        assertFalse(CheckingAccount.isEmailValid( "abcdef@@mail.com"));  //Edge case, 2 @ signs
        assertFalse(CheckingAccount.isEmailValid( "abcdef@@@@@mail.com"));  //Normal case, 5 @ signs

        //.com length tests:
        //Invalid case (0-1)
        assertFalse(CheckingAccount.isEmailValid( "abcdef@mail"));  //Edge case, 0 .com length
        assertFalse(CheckingAccount.isEmailValid( "abcdef@mail."));  //Edge case, 0 .com length
        assertFalse(CheckingAccount.isEmailValid( "abcdef@mail.c"));  //Edge case, 1 .com length
        //Valid case (2-infinity)
        assertTrue(CheckingAccount.isEmailValid( "abcdef@mail.co"));  //Edge case, 2 .com length
        assertTrue(CheckingAccount.isEmailValid( "abcdef@mail.info"));  //Normal case, 4 .com length

        //Alphanumeric characters in a row tests:
        //Valid case (0-1)
        assertTrue(CheckingAccount.isEmailValid( "abcdef@mail.com"));  //Normal case, 0 non-alphanum character, 0 in a row
        assertTrue(CheckingAccount.isEmailValid( "abc.def@mail.com"));  //Normal case, 1 non-alphanum character, 1 in a row
        assertTrue(CheckingAccount.isEmailValid( "a.b.c.d.e.f@mail.com"));  //Edge case, 5 non-alphanum characters, 1 in a row
        //Invalid case (2-infinity)
        assertFalse(CheckingAccount.isEmailValid( "abc..def@mail.com"));  //Edge case, 2 non-alphanum characters, 2 in a row
        assertFalse(CheckingAccount.isEmailValid( "a.b.c..d.e.f@mail.com"));  //Normal case, 6 non-alphanum characters, 2 in a row
        assertFalse(CheckingAccount.isEmailValid( "abc.....def@mail.com"));  //Normal case, 5 non-alphanum characters, 5 in a row

        //Alphanumeric starting/ending characters test:
        //Valid case (Prefix, domain, and .com all start and end with alphanumeric characters):
        assertTrue(CheckingAccount.isEmailValid( "abcdef@mail.com"));  //Edge case, all valid
        //Invalid case (Prefix, domain, or .com don't start or end with alphanumeric characters):
        assertFalse(CheckingAccount.isEmailValid( "-abcdef@mail.com"));  //Edge case, prefix doesn't start with alphanumeric character
        assertFalse(CheckingAccount.isEmailValid( "abcdef-@mail.com"));  //Edge case, prefix doesn't end with alphanumeric character
        assertFalse(CheckingAccount.isEmailValid( "abcdef@-mail.com"));  //Edge case, suffix doesn't start with alphanumeric character
        assertFalse(CheckingAccount.isEmailValid( "abcdef@mail-.com"));  //Edge case, suffix doesn't end with alphanumeric character
        assertFalse(CheckingAccount.isEmailValid( "abcdef@mail.-com"));  //Edge case, domain doesn't start with alphanumeric character
        assertFalse(CheckingAccount.isEmailValid( "abcdef@mail.com-"));  //Edge case, domain doesn't end with alphanumeric character
    }

    @Test
    void constructorTest() {
        CheckingAccount checkingAccount = new CheckingAccount("a@b.com","pass",200);

        assertEquals("a@b.com", checkingAccount.getEmail());
        assertEquals(200, checkingAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("", "pass",100));
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("a@b.com", "pass",-100));
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("a@b.com", "pass",100.234));
    }

}