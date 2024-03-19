package com.openinicidentslivetracker.incidentmanagement.service.request;

import com.openinicidentslivetracker.incidentmanagement.model.OpenIncidents;

public class OpenIncidentsRequest {
    private String incidentNumber;
    private String assignmentGroup;
    private Integer priority;

    public OpenIncidents toOpenIncidents(){
        return OpenIncidents.builder().incidentNumber(this.incidentNumber).assignmentGroup(this.assignmentGroup).priority(this.priority).build();
    }
}
