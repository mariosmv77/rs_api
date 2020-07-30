package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.JobOfferSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferSkillRepo extends JpaRepository<JobOfferSkill, Long> {
    @Query(value = "SELECT TOP (20) COUNT(JobOfferSkill.id) frequency, ss.name skillName\n" +
            "FROM JobOfferSkill\n" +
            "INNER JOIN Skill ss ON ss.id=JobOfferSkill.skill_id\n" +
            "GROUP BY ss.name \n" +
            "ORDER BY frequency DESC;",nativeQuery = true)
    List<SurveyStatistics> findMostRequestedSkills();


//    @Query(value = "select  "


//   )


}
