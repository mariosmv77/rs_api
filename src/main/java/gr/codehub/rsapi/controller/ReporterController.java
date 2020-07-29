package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.service.ReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReporterController {
    private ReporterService reporterService;

    @Autowired
    public ReporterController(ReporterService reporterService) {
        this.reporterService = reporterService;
    }

    @GetMapping("recentfinalized")
    private List<Match> getRecentFinalizedMatch(){
        return reporterService.getRecentFinalizedMatch();
    }

    @GetMapping("byMonth")
    private List<Match> getByMonth(){
        return reporterService.getByMonth();
    }
}
