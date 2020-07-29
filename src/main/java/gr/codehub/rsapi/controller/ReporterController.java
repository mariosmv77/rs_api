package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.ReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
public class ReporterController {
    private ReporterService reporterService;

    @Autowired
    public ReporterController(ReporterService reporterService){
        this.reporterService = reporterService;
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
