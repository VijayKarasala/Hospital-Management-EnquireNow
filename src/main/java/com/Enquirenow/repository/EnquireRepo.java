package com.Enquirenow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Enquirenow.entity.EnquireEntity;
@Repository
public interface EnquireRepo extends JpaRepository<EnquireEntity, Long> {

	public List<EnquireEntity> findByEmail(String email);

}
