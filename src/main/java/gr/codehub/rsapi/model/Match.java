package gr.codehub.rsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime dof;

    public enum type {
        MANUAL,
        AUTO,
        PARTIAL
    }

    private type Type;
    private String matchPercentage;
    private boolean isFinalized;

    @ManyToOne
    @JsonIgnore
    private Applicant applicant;

    @ManyToOne
    @JsonIgnore
    private JobOffer jobOffer;
}
