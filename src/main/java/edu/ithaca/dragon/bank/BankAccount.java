package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (isEmailValid(email)) {
            this.email = email;
            this.balance = startingBalance;
        } else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw(double amount) throws InsufficientFundsException, IllegalArgumentException {
        if (amount > balance) {
            throw new InsufficientFundsException("Cant withdraw more than you have in your account");
        } else if (amount < 0) {
            throw new IllegalArgumentException("Can't withdraw a negative amount");
        } else {
            balance -= amount;
        }
    }


    public static boolean isEmailValid(String email) {
        String validDigits = "-_@.";

        //number of @ symbols
        int atCount = 0;

        //iterates each character of email
        for (int i = 0; i < email.length() - 1; i++) {
            Character c = email.charAt(i);

            //checks number of @s
            if (c == validDigits.charAt(2)) {
                atCount++;
            }
            //checks that each character is a valid digit
            if (!(Character.isLetter(c) || Character.isDigit(c) || c == validDigits.charAt(0) || c == validDigits.charAt(1) || c == validDigits.charAt(2) || c == validDigits.charAt(3))) {
                return false;

                //if current character is an allowed symbol, it checks that it is followed by a letter or number
            } else if (c == validDigits.charAt(0) || c == validDigits.charAt(1) || c == validDigits.charAt(2) || c == validDigits.charAt(3)) {
                Character nextChar = email.charAt(i + 1);
                if (!(Character.isLetter(nextChar) || Character.isDigit(nextChar))) {
                    return false;
                }
            }
        }
        //checks if email is empty
        if (email.isEmpty()) {
            return false;
        }

        //split the email at the @ so we can only look at domain
        String[] emailSplit = email.split("@");
        String domain = emailSplit[1];
        int periodIndex = 1000;
        String period = ".";

        //iterates domain to find where the period is
        for (int x = 0; x < domain.length() - 1; x++) {
            Character c2 = domain.charAt(x);
            if (c2 == period.charAt(0)) {
                periodIndex = x;
            }
        }
        //make sure domain contains a .
        if (!domain.contains(".")) {
            return false;

            //makes there is atleast two characters after period in domain
        } else if (domain.length() - 1 - periodIndex < 2) {
            return false;

            //checks that first character is a letter
        } else if (!Character.isLetter(email.charAt(0))) {
            return false;

            //checks if that there is exactly one @ symbol
        } else if (atCount != 1) {
            return false;
        } else {
            return true;
        }

        }
    }

