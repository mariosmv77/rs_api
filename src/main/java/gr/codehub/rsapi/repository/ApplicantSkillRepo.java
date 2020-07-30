package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.ApplicantSkill;
import gr.codehub.rsapi.utility.SurveySkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantSkillRepo extends JpaRepository<ApplicantSkill, Long> {

   @Query(value = "SELECT TOP(20) COUNT(ApplicantSkill.id) frequency, ss.name skillName, ss.levels \n" +
           "FROM ApplicantSkill \n" +
           "INNER JOIN Skill ss ON ss.id=ApplicantSkill.skill_id \n" +
           "GROUP BY ss.name, ss.levels  \n" +
           "ORDER BY frequency DESC;", nativeQuery = true)
    List<SurveySkills> findMostOfferedSkill();


}

