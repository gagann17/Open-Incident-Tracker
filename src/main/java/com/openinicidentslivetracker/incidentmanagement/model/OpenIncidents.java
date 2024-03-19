package com.openinicidentslivetracker.incidentmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "opn_incdnts")
public class OpenIncidents {

    @Id
    @Column(nullable = false,updatable = false)
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "open_inc_sequence"),
                    @Parameter(name = "initial_value", value = "10000000"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long openIncId;

    @Column(nullable = false)
    private String incidentNumber;

    @Column(nullable = false)
    private String assignmentGroup;

    @Column(nullable = false)
    private Integer priority;
}
