package gr.codehub.rsapi.service;

import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.model.Skill;

import java.util.HashSet;
import java.util.List;

public interface ReporterService { // den eimai sigoyros oti xreiazetai ayto to service,mproyusan na ginoun sa methodoi apeutheias implementation
    //List<Skill> getMostPopular();
    HashSet<Skill> getNotMatchSkills();
    List<Match> getAllMatches(); // ayto idio me to getMatch tis class Match

    //void getReports(String nameOfXlsFile);// i xoris parametro kai apla dimiourgei ena neo excel arxeio

}
