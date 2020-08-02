package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobOfferRepo extends JpaRepository<JobOffer, Long> {
    Optional <List<JobOffer>> findByOfferDate(LocalDate dateOffer);
    Optional <List<JobOffer>> findByTitle(String name);
    Optional <List<JobOffer>> findByRegion(String region);

}
