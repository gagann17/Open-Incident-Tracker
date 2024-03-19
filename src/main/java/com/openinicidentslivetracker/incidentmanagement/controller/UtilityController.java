package com.openinicidentslivetracker.incidentmanagement.controller;

import com.openinicidentslivetracker.incidentmanagement.model.IncidentDetails;
import com.openinicidentslivetracker.incidentmanagement.model.OpenIncidents;
import com.openinicidentslivetracker.incidentmanagement.service.IncidentDetailsService;
import com.openinicidentslivetracker.incidentmanagement.service.OpenIncidentService;
import com.openinicidentslivetracker.incidentmanagement.service.excel.ScheduledFileFetcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UtilityController {

    private final ScheduledFileFetcherService scheduledFileFetcherService;

    private final IncidentDetailsService incidentDetailsService;

    private final OpenIncidentService openIncidentService;

    @PostMapping("/upload")
    public ResponseEntity<?> saveIncidentDetails(@RequestParam("file")MultipartFile file){
        return new ResponseEntity<>(scheduledFileFetcherService.saveIncidentDetails(file), HttpStatus.CREATED);
    }

    @GetMapping("/get/incidentdetails/{incidentNumber}")
    public IncidentDetails getIncidentDetails(@PathVariable String incidentNumber){
        return incidentDetailsService.getIncidentDetails(incidentNumber);
    }

    @GetMapping("/get/open-incidents")
    public List<OpenIncidents> getAllOpenIncidents(){
        return openIncidentService.getAllOpenIncidents();
    }
}
