package com.gsnotes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Ligne;

public interface LigneDao extends JpaRepository<Ligne, Long> {

}