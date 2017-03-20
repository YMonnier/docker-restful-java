package com.ymonnier.websocket.littleapp.utilities.database;

import com.ymonnier.websocket.littleapp.utilities.database.exceptions.InsertionException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Ysee on 14/12/2016 - 10:46.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public abstract class DAO<T> {
    protected Connection connection = null;

    public DAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Sauvegarde l objet dans la base de donnees
     * @param obj, objet a sauvegarder
     * @return true si l'enregistrement s'est bien passee, sinon false
     */
    public abstract boolean create(final T obj) throws InsertionException;

    /**
     * Recherche un objet selon son identifiant
     * @param id identifiant de l'objet
     * @return return l'objet trouve sinon false si pas trouve.
     */
    public abstract T read(final int id);

    /**
     * Met a jour l objet
     * @param obj objet a mettre a jour
     * @return true si la mise a jour s'est bien passee, sinon false.
     */
    public abstract boolean update(final T obj);

    /**
     * seppression de l objet
     * @param obj objet a supprimer
     * @return true si la suppression s'est bien passee, sinon false.
     */
    public abstract boolean delete(final T obj);
}
