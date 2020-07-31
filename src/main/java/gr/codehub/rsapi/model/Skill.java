package gr.codehub.rsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String levels;

    public Skill(long id) {
        this.id = id;
    }

    public Skill setName(String name) {
        this.name = name;
        return this;
    }

    public Skill setLevels(String levels) {
        this.levels = levels;
        return this;
    }

    @OneToMany(mappedBy = "skill")
    @JsonIgnore
    private List<ApplicantSkill> applicantSkills;

    @OneToMany(mappedBy = "skill")
    @JsonIgnore
    private List<JobOfferSkill> jobOfferSkills;

}
