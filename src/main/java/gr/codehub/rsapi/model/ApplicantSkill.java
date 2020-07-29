package gr.codehub.rsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ApplicantSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnore
    private Applicant applicant;

    public Applicant set_Applicant(Applicant applicant) {
        return this.applicant = applicant;
    }

    public Skill set_Skill(Skill skill) {
       return this.skill = skill;
    }

    @ManyToOne
    @JsonIgnore
    private Skill skill;


}
