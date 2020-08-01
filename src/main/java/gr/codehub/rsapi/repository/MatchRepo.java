package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.utility.SurveyMonth;
import gr.codehub.rsapi.utility.SurveyNotMatchSkill;
import gr.codehub.rsapi.utility.SurveyWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepo extends JpaRepository <Match, Long> {

    @Query(value = "select TOP(20) *\n" +
        "from Match  \n" +
        "where isFinalized =1  \n" +
        "order by dof desc ", nativeQuery = true)
    List<Match> getRecentFinalizedMatch();

    @Query(value = "select count(id) as Frequency, { fn MONTHNAME(dof) }as MonthName\n" +
            "from Match\n" +
            "where isFinalized =1\n" +
            "\n" +
            "group by { fn MONTHNAME(dof) }", nativeQuery = true)
    List<SurveyMonth> getByMonth();

    @Query(value = "SELECT count(id)as NumberOfMatches, {fn WEEK( dof) } as NumberOfWeek\n" +
            "FROM Match\n" +
            "where isFinalized =1\n" +
            "and (dof between '2020-03-27 16:16:36.0246299' and '2020-08-04 16:16:36.0246299')\n" +
            "group by  {fn WEEK( dof) }", nativeQuery = true)
    List<SurveyWeek> getByWeek();

    @Query(value = "select distinct\n" +
            "JobOfferSkill.skill_id as Skill_id, name, levels\n" +
            "from Skill, JobOfferSkill\n" +
            "left outer join ApplicantSkill on\n" +
            "ApplicantSkill.skill_id = JobOfferSkill.skill_id\n" +
            "where ApplicantSkill.skill_id IS NULL and\n" +
            "JobOfferSkill.skill_id = Skill.id",nativeQuery = true)
    List<SurveyNotMatchSkill> getNotMatchedSkills();

}
