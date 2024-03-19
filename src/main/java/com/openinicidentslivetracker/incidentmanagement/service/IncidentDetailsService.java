package com.openinicidentslivetracker.incidentmanagement.service;

import com.openinicidentslivetracker.incidentmanagement.model.IncidentDetails;
import com.openinicidentslivetracker.incidentmanagement.repository.IncidentDetailsRepository;
import com.openinicidentslivetracker.incidentmanagement.repository.OpenIncidentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncidentDetailsService {

    private final IncidentDetailsRepository incidentDetailsRepository;

    public void saveIncidentDetails(List<IncidentDetails> incidentDetailsList){
        //To check if the incidents given in the sheet already exist in the database
        List<String> existingIncidentNumbers = incidentDetailsRepository.findIncidentNumberByIncidentNumberIn(incidentDetailsList.stream().map(IncidentDetails::getIncidentNumber).collect(Collectors.toList()));

        List<IncidentDetails> incidentDetailsToSave = incidentDetailsList.stream().filter(incidentDetails -> !existingIncidentNumbers.contains(incidentDetails.getIncidentNumber())).collect(Collectors.toList());

        incidentDetailsRepository.saveAll(incidentDetailsToSave);

    }

    public void closeIncidents(List<String> newlyClosedIncidents) {
        if (!newlyClosedIncidents.isEmpty()) {
            incidentDetailsRepository.updateStateForClosedIncidents(newlyClosedIncidents, "Closed");
        }
    }

    public IncidentDetails getIncidentDetails(String incidentNumber) {
        return incidentDetailsRepository.getByIncidentNumber(incidentNumber)
;    }
}
