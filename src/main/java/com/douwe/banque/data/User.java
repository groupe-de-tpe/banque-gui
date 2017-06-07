package com.douwe.banque.data;

import java.io.Serializable;

/**
 * Cette classe représente un utilisateur du système.
 * Il est caractérisé par un login et un mot de passe.
 * Un utilisateur est d'un type bien connu.
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class User implements Serializable{
    /**
     * L'identifiant de l'utilisateur
     */
    private Integer id;
    
    /**
     * Le login utilisé par l'utilisateur
     */
    private String login;
    
    /**
     * Le mot de passe de l'utilisateur
     */
    private String password;
    
    /**
     * Le role de l'utilisateur.
     */
    private RoleType role;
    
    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
