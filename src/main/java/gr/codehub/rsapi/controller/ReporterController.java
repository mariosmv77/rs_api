package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.utility.SurveyMonth;
import gr.codehub.rsapi.utility.SurveyNotMatchSkill;
import gr.codehub.rsapi.utility.SurveySkills;
import gr.codehub.rsapi.utility.SurveyWeek;
import gr.codehub.rsapi.service.MatchService;
import gr.codehub.rsapi.service.ReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReporterController {

    private ReporterService reporterService;

    @Autowired
    public ReporterController(ReporterService reporterService,MatchService matchService) {
        this.reporterService = reporterService;
    }

    @GetMapping("ProposalMatches")
    public List<Match> getProposalMatches() {

        return reporterService.getMatches();
    }

    @GetMapping("MostOfferedSkills")
    public List<SurveySkills> getMostOfferedSkills() {
        return reporterService.getMostPopularOfferedSkills();
    }

    @GetMapping("MostRequestedSkills")
    public List<SurveySkills> getMostRequestedSkills() {
        return reporterService.getMostPopularRequestedSkills();
    }

    @GetMapping("recentfinalized")
    private List<Match> getRecentFinalizedMatch(){
        return reporterService.getRecentFinalizedMatch();
    }

    @GetMapping("notmatched")
    public List<SurveyNotMatchSkill> getNotMatchedSkills(){
        return reporterService.getNotMatchedSkills();
    }

    @GetMapping("MatchByMonth")
    public List<SurveyMonth> getByMonth() {
        return reporterService.getByMonth();
    }

    @GetMapping("MatchByWeek")
    public List<SurveyWeek> getByWeek() {
        return reporterService.getByWeek();
    }
}
