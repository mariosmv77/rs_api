package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.JobOfferSkill;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.utility.SurveySkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobOfferSkillRepo extends JpaRepository<JobOfferSkill, Long> {
    @Query(value = "SELECT TOP (20) COUNT(JobOfferSkill.id) frequency, ss.name skillName, ss.levels\n" +
            "FROM JobOfferSkill\n" +
            "INNER JOIN Skill ss ON ss.id=JobOfferSkill.skill_id\n" +
            "GROUP BY ss.name, ss.levels \n" +
            "ORDER BY frequency DESC;",nativeQuery = true)
    List<SurveySkills> findMostRequestedSkills();

    Optional<List<JobOfferSkill>> findBySkill(Skill skill);
}
