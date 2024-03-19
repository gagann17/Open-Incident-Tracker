package com.openinicidentslivetracker.incidentmanagement.repository;

import com.openinicidentslivetracker.incidentmanagement.model.IncidentDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface IncidentDetailsRepository extends JpaRepository<IncidentDetails,Long> {

//    boolean existsByIncidentNumberIn(List<String> incidentNumbers);

    @Query("SELECT i.incidentNumber FROM inc_dtls i WHERE i.incidentNumber IN :incidentNumbers")
    List<String> findIncidentNumberByIncidentNumberIn(List<String> incidentNumbers);

    @Modifying
    @Query("UPDATE inc_dtls i SET i.state = :state WHERE i.incidentNumber IN :newlyClosedIncidents")
    void updateStateForClosedIncidents(List<String> newlyClosedIncidents, String state);


    IncidentDetails getByIncidentNumber(String incidentNumber);
}
