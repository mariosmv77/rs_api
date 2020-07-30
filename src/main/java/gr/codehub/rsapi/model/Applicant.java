package gr.codehub.rsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
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
    private Date dob;
    private boolean isClosed;



    public Applicant setfnamecustom(String fName) {
        this.firstName = fName;
        return  this;
    }

    public Applicant  setlNameCustom(String lName) {
        this.lastName = lName;
        return this;
    }

    public Applicant setAddressCust(String address) {
        this.address = address;
        return this;
    }

    public Applicant setRegionCust(String region) {
        this.region = region;
        return this;
    }

    public Applicant setEmailCust(String email) {
        this.email = email;
        return this;
    }
    public Applicant setDobCust(Date dob) {
        this.dob = dob;
        return this;
    }
    @OneToMany(mappedBy = "applicant")
    private List<ApplicantSkill> applicantSkills;

    @OneToMany(mappedBy = "applicant")
    private List<Match> matches;




}
