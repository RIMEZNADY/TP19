package com.ma.service_voiture.repositories;

import com.ma.service_voiture.entities.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    List<Voiture> findByClientId(Long clientId);
}