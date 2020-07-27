package gr.codehub.rsapi.model;

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
    public enum levels {
        JUNIOR,
        MIDDLE,
        SENIOR
    }

    private levels lvl;

    public Skill setName(String name) {
        this.name = name;
        return this;
    }

    public Skill setLevels(String levels) {
        this.levels = levels;
        return this;
    }

    @OneToMany(mappedBy = "skill")
    private List<ApplicantSkill> applicantSkills;

    @OneToMany(mappedBy = "skill")
    private List<JobOfferSkill> jobOfferSkills;

}
