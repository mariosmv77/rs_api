package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.service.ApplicantService;
import gr.codehub.rsapi.service.JobOfferService;
import gr.codehub.rsapi.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MatcherEngineController {

    private MatchService matchService;

    @Autowired
    public MatcherEngineController(MatchService matchService){
        this.matchService = matchService;
    }

    @GetMapping("match")
    public List<Match> getMatches() {
        return matchService.getMatches();
    }

    @PostMapping("automatic/{id}")
    public List<Match> automaticMatch(@PathVariable long id) throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        return matchService.addAutomaticMatch(id);
    }

    @PutMapping("finalize/{id}")
    public boolean finalizeMatch(@PathVariable long id)    throws MatchNotFoundException, MatchAlreadyFinalized {
        return matchService.finalizeMatch(id);
    }


    @PostMapping("match/{jobOfferId}/{applicantId}")
    public Match addManuallyMatch(@PathVariable long jobOfferId, @PathVariable long applicantId) throws ApplicantNotFoundException, JobOfferNotFoundException, ApplicantNotFoundException, ApplicantAlreadyClosed, JobOfferAlreadyClosed {
        return matchService.addManuallyMatch(jobOfferId,applicantId);
    }
    @PostMapping("partial/{id}")
    public List<Match> partiallyMatch(@PathVariable long id) throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        return matchService.addPartiallyMatch(id);
    }

}
