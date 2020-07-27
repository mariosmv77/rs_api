package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepo extends JpaRepository<JobOffer, Long> {


}
