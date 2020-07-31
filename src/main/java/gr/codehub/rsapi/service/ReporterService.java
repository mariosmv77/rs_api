package gr.codehub.rsapi.service;

import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.utility.SurveyMonth;
import gr.codehub.rsapi.utility.SurveyNotMatchSkill;
import gr.codehub.rsapi.utility.SurveySkills;
import gr.codehub.rsapi.utility.SurveyWeek;

import java.util.HashSet;
import java.util.List;

public interface ReporterService {
    List<SurveySkills> getMostPopularOfferedSkills();
    List<SurveySkills> getMostPopularRequestedSkills();
    List<Match> getMatches();
public interface ReporterService {
    /**
     * Finds the top 20 skills that are most common along the applicants
     * Using Queries
     * @return { @code List<SurveyStatistics> }
     */
    List<SurveyStatistics> getMostPopularOfferedSkills();

    /**
     * Finds the top 20 skills that are most requested along the applicants
     * Using Queries
     * @return
     */
    List<SurveyStatistics> getMostPopularRequestedSkills();

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
    List<SurveyMonth> getByMonth();
    List<SurveyNotMatchSkill> getNotMatchedSkills();
    List<SurveyWeek> getByWeek();

    /**
     *
     * Finds all matches ordered by month
     * @return {@code  List<SurveyStatistics>}
     */
    List<SurveyStatistics> getByMonth();


    void getReports(String nameOfXlsFile);

    /**
     * Finds the skills that applicants didn't have but
     * where required by jobOffers
     * @return
     */
    HashSet<Skill> getNotMatchSkills();

}
