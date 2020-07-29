package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepo extends JpaRepository <Match, Long> {
    @Query(value =
            "select count(id), { fn MONTHNAME(dof) }as MonthName\n" +
                    "from Match \n" +
                    "where isFinalized =1  \n" +
                    "\n" +
                    "group by { fn MONTHNAME(dof) }\n", nativeQuery = true)
    List<SurveyStatistics> getByMonth();
}
