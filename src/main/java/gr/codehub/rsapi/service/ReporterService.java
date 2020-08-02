package gr.codehub.rsapi.service;

import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.utility.SurveyMonth;
import gr.codehub.rsapi.utility.SurveyNotMatchSkill;
import gr.codehub.rsapi.utility.SurveySkills;
import gr.codehub.rsapi.utility.SurveyWeek;
import java.util.List;

public interface ReporterService {
    /**
     * Finds the top 20 skills that are most common along the applicants
     * Using Queries
     * @return { @code List<SurveyStatistics> }
     */
    List<SurveySkills> getMostPopularOfferedSkills();

    /**
     * Finds the top 20 skills that are most requested along the applicants
     * Using Queries
     * @return
     */
    List<SurveySkills> getMostPopularRequestedSkills();

    /**
     * Finds all matches that have been stored in the match table on DB
     *
     * @return List<Match>
     */
    List<Match> getMatches();

    /**
     * Finds all matches that have been finalized in the match table on DB
     * @return  {@code  List<Match>}
     */
    List<Match> getRecentFinalizedMatch();
    /**
     * Finds the skills that applicants didn't have but
     * where required by jobOffers
     * @return
     */
    List<SurveyNotMatchSkill> getNotMatchedSkills();

    /**
     * Finds all matches ordered by week
     * week = the n week of the year
     * @return {@code  List<SurveyStatistics>}
     */
    List<SurveyWeek> getByWeek();

    /**
     * Finds all matches ordered by month
     * @return {@code  List<SurveyStatistics>}
     */
    List<SurveyMonth> getByMonth();

}
