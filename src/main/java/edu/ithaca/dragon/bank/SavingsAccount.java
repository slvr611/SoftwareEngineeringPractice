package edu.ithaca.dragon.bank;


public class SavingsAccount extends CheckingAccount{
    /**
     * @param email
     * @param password
     * @param startingBalance
     * @throws IllegalArgumentException if email, interestRate, dailyWithdrawMax or starting balance is invalid
     */
    double interestRate;
    double dailyWithdrawMax;
    double dailyWithdrawalTotal;

    public SavingsAccount(String email, String password, double startingBalance, double interestRate, double dailyWithdrawMax) {
        super(email, password, startingBalance);

        if (interestRate >= 0.0) this.interestRate = interestRate;
        else throw new IllegalArgumentException("Invalid interest rate.");
        if (dailyWithdrawMax > 0.0) this.dailyWithdrawMax = dailyWithdrawMax;
        else throw new IllegalArgumentException("Invalid daily withdrawal max.");
        this.dailyWithdrawalTotal = 0.0;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Can't withdraw a negative amount or one with more than 2 significant decimals");
        }
        if (amount > this.getBalance()) {
            throw new InsufficientFundsException("Cant withdraw more than you have in your account");
        } if(dailyWithdrawalTotal + amount > dailyWithdrawMax) {
            throw new IllegalArgumentException("over daily limit");
        }

        this.balance -= amount;
        dailyWithdrawalTotal += amount;
    }

    public void compoundInterest(){
        setBalance(getBalance() * (1.0 + interestRate));
    }

    public void resetWithdrawMax(){
        this.dailyWithdrawalTotal = 0.0;
    }
}
