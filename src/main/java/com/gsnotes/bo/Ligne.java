package com.gsnotes.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ligne {

	@Id
	@GeneratedValue
	private int id;
	private String cne;
	private String nom ;
	private String prenom;
	private int id_niveau;
	private String type;

	
	public Ligne() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id ;
	}
	public String getNom() {
		return nom;
	}
	public void setnom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getCne() {
		return cne
				;
	}
	public void setCne(String cne) {
		this.cne = cne;
	}
	public int getNiveau() {
		return id_niveau;
	}
	public void setNiveau(int niveau) {
		this.id_niveau = niveau;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Ligne(int id,String Nom, String Prenom, String cne, int id_niveau, String type) {
		super();
		this.id = id ;
		this.nom = nom;
		this.prenom = prenom;
		this.cne = cne;
		this.id_niveau = id_niveau;
		this.type = type;
	}
	public String toString() {
		return ""
				+id+ " "
				+nom+ " "
				+prenom+ " "+cne+ " " +id_niveau+ " " +type+ " ";
		}
}
