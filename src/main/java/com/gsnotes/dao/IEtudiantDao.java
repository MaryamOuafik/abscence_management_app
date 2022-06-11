package com.gsnotes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.Utilisateur;

public interface IEtudiantDao extends JpaRepository<Etudiant, Long>{

}
