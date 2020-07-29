package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicant, Long> {
    Optional <List<Applicant>> findByDob(LocalDate dob);
    Optional <List<Applicant>> findByFirstName(String name);
    //Optional <List<Applicant>> findByApplicantSkills(Long applicantSkills);
    Optional <List<Applicant>> findByRegion(String region);
}
