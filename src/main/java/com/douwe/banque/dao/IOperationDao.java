package com.douwe.banque.dao;

import com.douwe.banque.data.Customer;
import com.douwe.banque.data.Operation;
import java.util.List;

/**
 * Cette interface présente l'ensemble des méthodes nécessaire pour interagir avec les objets Operation
 * stockés dans un entrepot de données.
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IOperationDao {
    /**
     *  stocke l'opération passée en paramètre dans l'entrepot des données
     * @param operation l'opération à stocker dans l'entrepot
     * @return renvoie opération telle que stockée dans l'entrepot
     * @throws DataAccessException si un événement non prevu survient
     */
    public Operation save(Operation operation) throws DataAccessException;
    
    /**
     * permet de supprimer l'opération passée en paramètre de l'entrepot des données
     * @param operation l'opération à supprimer
     * @throws DataAccessException  si un événement non prévu survient
     */
    public void delete(Operation operation) throws DataAccessException;
    
    /**
     * met à jour une opération dans l'entrepot des données. Il faut bien sûr que préalable que cette
     * opération soit déjà stockée dans l'entrepot.
     * @param operation l'opération dont on veut mettre à jour
     * @return l'opération telle que mise à jour
     * @throws DataAccessException si un événément non prévu survient
     */
    public Operation update(Operation operation) throws DataAccessException;
    
    /**
     * renvoie l'opération dont l'identifiant est passé en paramètre
     * @param id l'identifiant de l'opération à rechercher
     * @return l'opération qui correspond à l'identifiant passé en paramètre
     * @throws DataAccessException si aucune opération n'a cet identifiant ou si un événement non prévu survient
     */
    public Operation findById(Integer id) throws DataAccessException;
    
    /**
     * renvoie la liste des opérations stockées dans l'entrepot des données
     * @return la liste des opérations stockées dans l'entrepot des données
     * @throws DataAccessException  si un événement non prévu survient
     */
    public List<Operation> findAll() throws DataAccessException;

    /**
     * renvoie la liste des opérations qui ont traits à un compte détenu par un client
     * @param customer le client titulaire des comptes
     * @return la liste des opérations liées aux comptes détenus par le client dont l'identifiant est passé en paramètre
     * @throws DataAccessException si un événement non prévu survient
     */
    public List<Operation> findForCustomer(Customer customer) throws DataAccessException;
}
