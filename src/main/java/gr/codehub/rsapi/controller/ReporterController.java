package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.utility.SurveyMonth;
import gr.codehub.rsapi.utility.SurveyNotMatchSkill;
import gr.codehub.rsapi.utility.SurveySkills;
import gr.codehub.rsapi.utility.SurveyWeek;
import gr.codehub.rsapi.service.MatchService;
import gr.codehub.rsapi.service.ReporterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ReporterController {
    private ReporterService reporterService;

    @Autowired
    public ReporterController(ReporterService reporterService, MatchService matchService) {
        this.reporterService = reporterService;
    }

    @GetMapping("proposalMatches")
    public List<Match> getProposalMatches() {
        log.info("\nGET REQUEST: Calling getProposalMatches");
        return reporterService.getMatches();
    }

    @GetMapping("mostOfferedSkills")
    public List<SurveySkills> getMostOfferedSkills() {
        log.info("\nGET REQUEST: Calling getMostOfferedSkills");
        return reporterService.getMostPopularOfferedSkills();
    }

    @GetMapping("mostRequestedSkills")
    public List<SurveySkills> getMostRequestedSkills() {
        log.info("\nGET REQUEST: Calling getMostRequestedSkills");
        return reporterService.getMostPopularRequestedSkills();
    }

    @GetMapping("recentFinalized")
    private List<Match> getRecentFinalizedMatch() {
        log.info("\nGET REQUEST: Calling getRecentFinalizedMatch");
        return reporterService.getRecentFinalizedMatch();
    }

    @GetMapping("notMatched")
    public List<SurveyNotMatchSkill> getNotMatchedSkills() {
        log.info("\nGET REQUEST: Calling getNotMatchedSkills");
        return reporterService.getNotMatchedSkills();
    }

    @GetMapping("matchByMonth")
    public List<SurveyMonth> getByMonth() {
        log.info("\nGET REQUEST: Calling getByMonth");
        return reporterService.getByMonth();
    }

    @GetMapping("matchByWeek")
    public List<SurveyWeek> getByWeek() {
        log.info("\nGET REQUEST: Calling getByWeek");
        return reporterService.getByWeek();
    }
}
