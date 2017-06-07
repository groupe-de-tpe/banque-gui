package com.douwe.banque.dao;

import com.douwe.banque.data.Customer;
import com.douwe.banque.data.User;
import java.util.List;

/**
 * Cette interface définit les opérations nécessaires pour la gestion des clients
 * dans un entrepot de données.
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface ICustomerDao {
 
    /**
     * stocke un client dans un entrepot de données
     * @param customer le client à stocker dans l'entrepot
     * @return le client tel que stocké dans l'entrepot
     * @throws DataAccessException si un événement non prévu survient
     */
    public Customer save(Customer customer) throws DataAccessException;
    
    /**
     * supprimer un client de l'entrepot de données
     * @param customer le client à supprimer
     * @throws DataAccessException si un événement non prévu survient
     */
    public void delete(Customer customer) throws DataAccessException;
    
    /**
     * met à jour un client
     * @param customer le client à mettre à jour
     * @return le client tel que mis à jour dans l'entrepot des données
     * @throws DataAccessException si un événement non prévu survient
     */
    public Customer update(Customer customer) throws DataAccessException;
    
    /**
     * recherche un client suivant son identifiant
     * @param id l'identifiant du client à rechercher
     * @return le client dont l'identifiant est passé en paramètre
     * @throws DataAccessException si un événement non prévu survient
     */
    public Customer findById(Integer id) throws DataAccessException;
    
    /**
     * renvoie la liste des clients stockés dans l'entrepot
     * @return la list des clients de l'entrepot des données
     * @throws DataAccessException  si un événement non prévu survient
     */
    public List<Customer> findAll() throws DataAccessException;

    /**
     * recherche un client suivant son compte utilisateur
     * @param user le compte de l'utilisateur
     * @return le client dont le compte utilisateur est passé en paramètre
     * @throws DataAccessException si un événement non prévu survient
     */
    public Customer findByUser(User user) throws DataAccessException;

    /**
     * recherche un client suivant le nom
     * @param name le nom de l'utilisateur à rechercher
     * @return le client dont le nom est passé en paramètre
     * @throws DataAccessException si un événement non prévu survient
     */
    public Customer findByName(String name)  throws DataAccessException;
}
