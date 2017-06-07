package com.douwe.banque.dao;

import com.douwe.banque.data.User;
import java.util.List;

/**
 * Cette interface présente les différentes méthodes nécessaires pour réaliser les opérations sur les utilisateurs
 * du système stockées dans un endroit de manière persistence.
 * Le type exact de persistence n'est pas nécessaire. Il peut ainsi s'agir d'une base de données ou d'un simple fichier.
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IUserDao {
    
    /**
     * Cette méthode permet de stocker l'utilisateur en endroit de manière persistante.
     * @param user l'utilisateur à stocker
     * @return l'utilisateur avec un identifiant valide ou null en cas d'erreur.
     * @throws DataAccessException lorsque la sauvegarde ne se passe pas correctement. Il peut s'agir d'un problème 
     * lié à la base de données ou au fichier.
     */
    public User save(User user) throws DataAccessException;
    
    /**
     * Supprime de l'entrepot l'utilisateur passé en paramètre
     * @param user l'utilisateur à supprimer
     * @throws DataAccessException lorsqu'un événement non prévu apparait lors de la suppression
     */
    public void delete(User user) throws DataAccessException;
    
    /**
     * met à jour un utilisateur. Il est nécessaire que cet utilisateur soit préalablement dans la base de données
     * @param user l'utilisateur à mettre à jour
     * @return l'utilisateur tel que mis à jour dans l'entrepot
     * @throws DataAccessException si un événement non prévu survient
     */
    public User update(User user) throws DataAccessException;
    
    /**
     * renvoie l'utilisateur dont l'identifiant correspond à la valeur passée en paramètre
     * @param id l'identifiant de l'utilisateur que l'on recherche
     * @return l'utilisateur dont l'identifiant correspond à la valeur passée en paramètre
     * @throws DataAccessException si l'identifiant n'est pas un identifiant valide ou si un événement non prévu survient
     */
    public User findById(Integer id) throws DataAccessException;
    
    /**
     * renvoie tous les utilisateurs stockés dans l'entrepot.
     * @return la liste des utilisateurs stockés dans l'entrepot
     * @throws DataAccessException si un événement non prévu survient
     */
    public List<User> findAll() throws DataAccessException;

    /**
     * renvoie l'utilisateur dont le login est passé en paramètre
     * @param login le login de l'utilisateur à rechercher
     * @return l'utilisateur dont le login est passé en paramètre
     * @throws DataAccessException si aucun utilisateur ne correspond à ce login ou si un événement non prévu survient
     */
    public User findByLogin(String login) throws DataAccessException;

    /**
     * renvoie la liste des utilisateurs dont la valeur de l'attribut status correspond à la valeur passé en paramètre
     * @param status le status des utilisateurs que l'on recherche
     * @return la liste des utilisateurs qui ont le status passé en paramètre
     * @throws DataAccessException si un événement non prévu survient
     */
    public List<User> findByStatus(int status) throws DataAccessException;
    
}
