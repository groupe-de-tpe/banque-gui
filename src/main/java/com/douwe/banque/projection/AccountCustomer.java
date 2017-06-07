package com.douwe.banque.projection;

import com.douwe.banque.data.Account;
import com.douwe.banque.data.AccountType;
import com.douwe.banque.data.Customer;
import java.util.Date;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class AccountCustomer {
    
    private String customerName;
    
    private Account account;
    
    public AccountCustomer(String name, Account account){
        this.customerName = name;
        this.account = account;
    }
    
    public AccountCustomer(){
        account = new Account();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getId() {
        return account.getId();
    }

    public void setId(Integer id) {
        account.setId(id);
    }

    public String getAccountNumber() {
        return account.getAccountNumber();
    }

    public void setAccountNumber(String accountNumber) {
        account.setAccountNumber(accountNumber);
    }

    public double getBalance() {
        return account.getBalance();
    }

    public void setBalance(double balance) {
        account.setBalance(balance);
    }

    public Date getDateDeCreation() {
        return account.getDateDeCreation();
    }

    public void setDateDeCreation(Date dateDeCreation) {
        account.setDateDeCreation(dateDeCreation);
    }

    public AccountType getType() {
        return account.getType();
    }

    public void setType(AccountType type) {
        account.setType(type);
    }

    public Customer getCustomer() {
        return account.getCustomer();
    }

    public void setCustomer(Customer customer) {
        account.setCustomer(customer);
    }

    public int getStatus() {
        return account.getStatus();
    }

    public void setStatus(int status) {
        account.setStatus(status);
    }
    
    
}
