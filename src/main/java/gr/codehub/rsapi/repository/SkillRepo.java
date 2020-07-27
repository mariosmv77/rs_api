package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Long> {
    Optional<Skill> findSkillByName (String name);
}
