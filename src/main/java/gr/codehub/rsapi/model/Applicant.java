package gr.codehub.rsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String address;
    private String region;
    private String email;
    private LocalDate dob;
    private boolean isClosed;

    public Applicant setId(long id) {
        this.id = id;
        return this;
    }

    public Applicant setfName(String fName) {
        this.firstName = fName;
        return  this;
    }

    public Applicant  setlName(String lName) {
        this.lastName = lName;
        return this;
    }

    public Applicant setAddress(String address) {
        this.address = address;
        return this;
    }

    public Applicant setRegion(String region) {
        this.region = region;
        return this;
    }

    public Applicant setEmail(String email) {
        this.email = email;
        return this;
    }

    @OneToMany(mappedBy = "applicant")
    @JsonIgnore
    private List<ApplicantSkill> applicantSkills;

    @OneToMany(mappedBy = "applicant")
    @JsonIgnore
    private List<Match> matches;




}
