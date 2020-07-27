package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.JobOfferSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferSkillRepo extends JpaRepository<JobOfferSkill, Long> {
}
