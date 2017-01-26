package com.subtitlor.dao;

import java.util.List;
import com.subtitlor.beans.Utilisateur;

public interface UtilisateurDao {
	void ajouter(Utilisateur utilisateur) throws DaoException;
	List<Utilisateur> lister() throws DaoException;
}
