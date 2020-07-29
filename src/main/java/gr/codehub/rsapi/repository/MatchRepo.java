package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.Match;
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

@Query(value =
        "select count(id), { fn MONTHNAME(dof) }as MonthName\n" +
        "from Match \n" +
        "where isFinalized =1  \n" +
        "\n" +
        "group by { fn MONTHNAME(dof) }\n", nativeQuery = true)

    List<Match> getByMonth();
/*select count(id), { fn MONTHNAME(dof) }as MonthName
from Match
where isFinalized =1

group by { fn MONTHNAME(dof) }

SELECT  dof
FROM Match
where isFinalized =1
and (dof between '2020-03-27 16:16:36.0246299' and '2020-07-29 16:16:36.0246299')
ORDER BY dof DESC

*/

}
