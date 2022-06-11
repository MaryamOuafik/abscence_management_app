package com.gsnotes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;

public interface InscriptionDao extends JpaRepository<InscriptionAnnuelle, Long>  {
	InscriptionAnnuelle getInscriptionAnnuelleByEtudiant(Etudiant etu);

}
