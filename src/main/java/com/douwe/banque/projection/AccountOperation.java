package com.douwe.banque.projection;

import com.douwe.banque.data.Account;
import com.douwe.banque.data.Operation;
import com.douwe.banque.data.OperationType;
import com.douwe.banque.data.User;
import java.util.Date;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class AccountOperation {
    
    private Operation operation;
    
    private String accountNumber;
    
    private String username;
    
    public AccountOperation(String username, String accountNumber, Operation operation){
        this.username = username;
        this.accountNumber = accountNumber;
        this.operation = operation;
    }

    public Integer getId() {
        return operation.getId();
    }

    public void setId(Integer id) {
        operation.setId(id);
    }

    public OperationType getType() {
        return operation.getType();
    }

    public void setType(OperationType type) {
        operation.setType(type);
    }

    public Date getDateOperation() {
        return operation.getDateOperation();
    }

    public void setDateOperation(Date dateOperation) {
        operation.setDateOperation(dateOperation);
    }

    public String getDescription() {
        return operation.getDescription();
    }

    public void setDescription(String description) {
        operation.setDescription(description);
    }

    public Account getAccount() {
        return operation.getAccount();
    }

    public void setAccount(Account account) {
        operation.setAccount(account);
    }

    public User getUser() {
        return operation.getUser();
    }

    public void setUser(User user) {
        operation.setUser(user);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}