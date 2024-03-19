package com.openinicidentslivetracker.incidentmanagement.service;

import com.openinicidentslivetracker.incidentmanagement.model.IncidentDetails;
import com.openinicidentslivetracker.incidentmanagement.model.OpenIncidents;
import com.openinicidentslivetracker.incidentmanagement.repository.IncidentDetailsRepository;
import com.openinicidentslivetracker.incidentmanagement.repository.OpenIncidentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClosedIncidentService {

    private final OpenIncidentsRepository openIncidentsRepository;

    private final IncidentDetailsRepository incidentDetailsRepository;

    private final OpenIncidentService openIncidentService;

    private final IncidentDetailsService incidentDetailsService;

    public void closeAndDeleteIncidents(List<IncidentDetails> incidentDetailsList, List<OpenIncidents> openIncidentsList){
        List<String> previouslyFetchedOpenIncidents = openIncidentService.fetchPreviouslyOpenIncidents();

        List<String> newlyClosedIncidents = previouslyFetchedOpenIncidents.stream()
                .filter(incidentNumber -> !(openIncidentsList.stream().map(OpenIncidents::getIncidentNumber).collect(Collectors.toList())).contains(incidentNumber)).collect(Collectors.toList());

        incidentDetailsService.closeIncidents(newlyClosedIncidents);
        openIncidentService.deleteIncidents(newlyClosedIncidents);

    }
}
