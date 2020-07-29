package gr.codehub.rsapi.service;

import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.SurveyStatistics;

import java.util.List;

public interface ReporterService { // den eimai sigoyros oti xreiazetai ayto to service,mproyusan na ginoun sa methodoi apeutheias implementation
    List<SurveyStatistics> getMostPopularOfferedSkills();
    List<SurveyStatistics> getMostPopularRequestedSkills();
    List<Skill> getNotMatchSkill();
    List<Match> getMatches(); // ayto idio me to getMatch tis class Match
    List<Skill> getRecentFinalizedMatch();
    List<SurveyStatistics> getByMonth();
    void getReports(String nameOfXlsFile);// i xoris parametro kai apla dimiourgei ena neo excel arxeio

}
