package com.openinicidentslivetracker.incidentmanagement.repository;

import com.openinicidentslivetracker.incidentmanagement.model.OpenIncidents;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface OpenIncidentsRepository extends JpaRepository<OpenIncidents,Long>{
    void deleteByIncidentNumberIn(List<String> newlyClosedIncidents);

    @Query("SELECT i.incidentNumber FROM opn_incdnts i WHERE i.incidentNumber IN :incidentNumbers")
    List<String> findIncidentNumberByIncidentNumberIn(List<String> incidentNumbers);
}
