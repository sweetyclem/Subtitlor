package com.subtitlor.beans;

public class Utilisateur {
	private String nom;
	private String prenom;
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) throws BeanException {
		if (nom.length() > 150)
			throw new BeanException("Le nom est trop long (150 caract�res maximum)");
		else
			this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}
