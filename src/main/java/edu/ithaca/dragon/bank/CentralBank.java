package edu.ithaca.dragon.bank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CentralBank implements AdvancedAPI, AdminAPI {

    protected Map<String, CheckingAccount> acctMap;

    CentralBank(){
        acctMap = new HashMap<String, CheckingAccount>();
    }

    //----------------- BasicAPI methods -------------------------//

    public boolean confirmCredentials(String email, String password) throws AccountNotFoundException {
        return getAcctByID(email).getPassword().equals(password);
    }

    public double checkBalance(String email) throws AccountNotFoundException {
        return getAcctByID(email).getBalance();
    }

    public void withdraw(String email, double amount) throws InsufficientFundsException, AccountNotFoundException{
        getAcctByID(email).withdraw(amount);
    }

    public void deposit(String email, double amount) throws AccountNotFoundException{
        getAcctByID(email).deposit(amount);
    }

    public void transfer(String emailToWithdrawFrom, String emailToDepositTo, double amount) throws InsufficientFundsException, AccountNotFoundException {
        getAcctByID(emailToWithdrawFrom).transfer(amount, getAcctByID(emailToDepositTo));
    }

    public String transactionHistory(String acctId) throws AccountNotFoundException {
        return getAcctByID(acctId).getHistory();
    }


    //----------------- AdvancedAPI methods -------------------------//



    public void createAccount(String email, String password, double startingBalance) throws AccountAlreadyExistsException, IllegalArgumentException {
        this.addAccount(new CheckingAccount(email, password, startingBalance), email);
    }

    public void closeAccount(String email) throws AccountNotFoundException, IllegalArgumentException{
        if(!acctMap.containsKey(email)){
            throw new AccountNotFoundException("Account with ID " + email + " not found!");
        }
        acctMap.remove(email);
    }


    //------------------ AdminAPI methods -------------------------//

    public double calcTotalAssets() {
        Collection<CheckingAccount> checkingAccountCollection = acctMap.values();
        double total = 0.0;
        if (checkingAccountCollection.isEmpty()) return 0.0;
        for (CheckingAccount acc : checkingAccountCollection) {
            if (acc == null) System.out.println("acc is null");
            if (checkingAccountCollection == null) System.out.println("collection is null");
            total += acc.getBalance();
        }
        return total;
    }

    public Collection<String> findAcctIdsWithSuspiciousActivity() {
        Collection<String> suspiciousAcctIds = new ArrayList<String>();
        acctMap.forEach((id, acct) -> {
            if(acct.checkSuspicious())
                suspiciousAcctIds.add(id);
        });
        return suspiciousAcctIds;
    }

    public void freezeAccount(String acctId) {

    }

    public void unfreezeAcct(String acctId) {

    }


    //------------------ Other useful methods -------------------------//

    /**
     * @param ba is the BankAccount to be added to the map
     * @param email is a string describing an email address unique to the account to be deleted
     * @post adds the given account to the list of accounts in the central bank
     * @throws AccountAlreadyExistsException if the given email is already associated with an account
     */
    protected void addAccount(CheckingAccount ba, String email) throws AccountAlreadyExistsException{
        if(acctMap.containsKey(email)){
            throw new AccountAlreadyExistsException("Account with ID " + email + " already exists!");
        }
        else acctMap.put(email, ba);
    }

    /**
     * @param email is a string describing an email address unique to the account to be found
     * @returns the account associated with the given email
     * @throws AccountNotFoundException if the given email is not associated with an account
     */
    protected CheckingAccount getAcctByID(String email) throws AccountNotFoundException{
        if(acctMap.containsKey(email)){
            return acctMap.get(email);
        }
        else throw new AccountNotFoundException("Account with ID " + email + " not found!");
    }

}
