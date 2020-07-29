package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.SurveyStatistics;
import gr.codehub.rsapi.service.MatchService;
import gr.codehub.rsapi.service.ReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.HashSet;

@RestController
public class ReporterController {

    private ReporterService reporterService;
    private MatchService matchService;

    @Autowired
    public ReporterController(ReporterService reporterService,MatchService matchService) {
        this.reporterService = reporterService;
        this.matchService = matchService;
    }

    @GetMapping("ProposalMatches")
    public List<Match> getProposalMatches() {

        return reporterService.getMatches();
    }

    @GetMapping("MostOfferedSkills")
    public List<SurveyStatistics> getMostOfferedSkills() {
        return reporterService.getMostPopularOfferedSkills();
    }

    @GetMapping("MostRequestedSkills")
    public List<SurveyStatistics> getMostRequestedSkills() {
        return reporterService.getMostPopularRequestedSkills();
    }

    @GetMapping("MatchByMonth")
    public List<SurveyStatistics> getByMonth() {
        return reporterService.getByMonth();
    }


    @GetMapping("notmatched")
    public HashSet<Skill> getNotMatchedSkills(){
        return reporterService.getNotMatchSkills();
    }


    @GetMapping("matches")
    public List<Match> getAllMatches(){
        return reporterService.getAllMatches();
    }

}
