package com.douwe.banque.data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class Account implements Serializable{
    
    private Integer id;
    
    private String  accountNumber;
    
    private double balance;
    
    private Date dateDeCreation;
    
    private AccountType type;
    
    private Customer customer;
    
    private int status;

    public Account(String accountNumber, double balance, Date dateDeCreation, AccountType type, Customer customer, int status) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.dateDeCreation = dateDeCreation;
        this.type = type;
        this.customer = customer;
        this.status = status;
    }
    
    
    public Account(){
        
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(Date dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
