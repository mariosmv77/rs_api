package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.ApplicantSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantSkillRepo extends JpaRepository<ApplicantSkill, Long> {
}
