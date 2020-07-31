package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicant, Long>, JpaSpecificationExecutor<Applicant> {
    /**
     * Takes a date of birth object and searches an applicant by this date
     * @param dob  {@code <List<Applicant>}
     * @return
     */
    Optional <List<Applicant>> findByDob(LocalDate dob);

    /**
     * Takes a name and searches an applicant
     * @param name  {@code <List<Applicant>}
     * @return
     */
    Optional <List<Applicant>> findByFirstName(String name);

    /**
     * Takes a region and searches an applicant
     * @param region  {@code <List<Applicant>}
     * @return
     */
    Optional <List<Applicant>> findByRegion(String region);
}
