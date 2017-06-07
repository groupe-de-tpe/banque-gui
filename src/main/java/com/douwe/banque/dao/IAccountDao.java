package com.douwe.banque.dao;

import com.douwe.banque.data.Account;
import com.douwe.banque.data.Customer;
import java.util.List;

/**
 * Cette interface définit les opérations nécessairespour la gestion des comptes bancaires
 * stockés dans un entrepot de données
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IAccountDao {

    /**
     * stocke le compte passé en paramètre dans l'entrepot de données
     * @param account le compte à stocker dans l'entrepot
     * @return le compte enregistré dans l'entrepot
     * @throws DataAccessException si un événement non prévu survient
     */
    public Account save(Account account) throws DataAccessException;
    
    /**
     * supprime le compte passé en paramètre dans l'entrepot
     * @param account le compte à supprimer
     * @throws DataAccessException si un événement non prévu survient
     */
    public void delete(Account account) throws DataAccessException;
    
    /**
     * met à jour le compte passé en paramètre. Le compte doit être préalabelement
     * stocké dans l'entrepot
     * @param account le compte à mettre à jour
     * @return le compte mis à jour
     * @throws DataAccessException si un événement non prévu survient
     */
    public Account update(Account account) throws DataAccessException;
    
    /**
     * renvoie la liste des comptes stockés dans l'entrepot.
     * @return la liste des comptes de l'entrepot
     * @throws DataAccessException si un événement non prévu survient
     */
    public List<Account> findAll() throws DataAccessException;
    
    /**
     * recherche un compte suivant la valeur de son identifiant
     * @param id l'identifiant du compte à rechercher
     * @return le compte dont l'identifiant est passé en paramètre
     * @throws DataAccessException si un événement non prévu survient ou si l'objet en question n'existe pas
     */
    public Account findById(Integer id) throws DataAccessException;

    /**
     * recherche un compte suivant la valeur du numéro de compte
     * @param accountNumber le numéro de compte à rechercher
     * @return le compte dont le numéro est passé en paramètre
     * @throws DataAccessException si un événement non prévu survient
     */
    public Account findByAccountNumber(String accountNumber) throws DataAccessException;

    /**
     * recherche les comptes appartement à un client
     * @param customer le client dont on recherche les comptes
     * @return la liste des comptes appartenant au client
     * @throws DataAccessException si un événement non prévu survient
     */
    public List<Account> findByCustomer(Customer customer) throws DataAccessException;
    
}
