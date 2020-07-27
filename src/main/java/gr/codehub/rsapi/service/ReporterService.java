package gr.codehub.rsapi.service;

import gr.codehub.rsapi.model.Skill;

import java.util.List;

public interface ReporterService { // den eimai sigoyros oti xreiazetai ayto to service,mproyusan na ginoun sa methodoi apeutheias implementation
    List<Skill> getMostPopular();
    List<Skill> getNotMatchSkill();
    List<Skill> getMatches(); // ayto idio me to getMatch tis class Match
    List<Skill> getRecentFinalizedMatch();
    void getReports(String nameOfXlsFile);// i xoris parametro kai apla dimiourgei ena neo excel arxeio

}
