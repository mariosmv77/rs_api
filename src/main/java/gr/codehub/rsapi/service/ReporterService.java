package gr.codehub.rsapi.service;

import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.utility.SurveyMonth;
import gr.codehub.rsapi.utility.SurveyNotMatchSkill;
import gr.codehub.rsapi.utility.SurveySkills;
import gr.codehub.rsapi.utility.SurveyWeek;

import java.util.List;

public interface ReporterService {
    List<SurveySkills> getMostPopularOfferedSkills();
    List<SurveySkills> getMostPopularRequestedSkills();
    List<Match> getMatches();
    List<Match> getRecentFinalizedMatch();
    List<SurveyMonth> getByMonth();
    List<SurveyNotMatchSkill> getNotMatchedSkills();
    List<SurveyWeek> getByWeek();

}
