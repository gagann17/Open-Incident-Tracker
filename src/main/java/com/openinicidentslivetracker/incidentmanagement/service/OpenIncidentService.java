package com.openinicidentslivetracker.incidentmanagement.service;

import com.openinicidentslivetracker.incidentmanagement.model.OpenIncidents;
import com.openinicidentslivetracker.incidentmanagement.repository.OpenIncidentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenIncidentService {

    private final OpenIncidentsRepository openIncidentsRepository;

    private final SimpMessagingTemplate messagingTemplate;

    public void saveOpenIncidents(List<OpenIncidents> openIncidentsList){

        List<String> existingIncidentNumbers = openIncidentsRepository.findIncidentNumberByIncidentNumberIn
                (openIncidentsList
                .stream().map(OpenIncidents::getIncidentNumber).collect(Collectors.toList()));

        List<OpenIncidents> openIncidentsToSave = openIncidentsList.stream().filter(openIncidents -> !existingIncidentNumbers.contains(openIncidents.getIncidentNumber())).collect(Collectors.toList());

        openIncidentsRepository.saveAll(openIncidentsToSave);

        messagingTemplate.convertAndSend("/topic/incidents",openIncidentsToSave);
    }

    public List<String> fetchPreviouslyOpenIncidents(){
        return openIncidentsRepository.findAll().stream().map(OpenIncidents::getIncidentNumber).collect(Collectors.toList());
    }


    public void deleteIncidents(List<String> newlyClosedIncidents) {
        openIncidentsRepository.deleteByIncidentNumberIn(newlyClosedIncidents);
        messagingTemplate.convertAndSend("/topic/incidents/close",newlyClosedIncidents);
    }

    public List<OpenIncidents> getAllOpenIncidents() {
        return openIncidentsRepository.findAll();
    }
}
