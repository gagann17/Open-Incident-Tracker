package com.openinicidentslivetracker.incidentmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.Date;
import java.util.Objects;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "inc_dtls")
public class IncidentDetails {

    @Id
    @Column(nullable = false,updatable = false)
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "inc_dtls_sequence"),
                    @Parameter(name = "initial_value", value = "10000000"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long incId;

    @Column(nullable = false)
    private String incidentNumber;

    @Column(nullable = false)
    private String assignmentGroup;

    @Column(nullable = false)
    private String state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IncidentDetails that)) return false;
        return Objects.equals(getIncidentNumber(), that.getIncidentNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIncidentNumber());
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date openedAt;

    @Column(nullable = false)
    private Integer priority;

    @Column( nullable = false)
    private String shortDescription;


}
