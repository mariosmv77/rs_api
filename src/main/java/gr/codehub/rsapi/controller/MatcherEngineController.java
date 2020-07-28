package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.MatchNotFoundException;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.service.ApplicantService;
import gr.codehub.rsapi.service.JobOfferService;
import gr.codehub.rsapi.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MatcherEngineController {

    private MatchService matchService;

    @Autowired
    public MatcherEngineController(MatchService matchService){
        this.matchService = matchService;
    }

    @PostMapping("automatic/{id}")
    public List<Match> automaticMatch(@PathVariable long id) throws JobOfferNotFoundException{
        return matchService.addAutomaticMatch(id);
    }

    @PutMapping("finalize/{id}")
    public boolean finalizeMatch(@PathVariable long id)    throws MatchNotFoundException{
        return matchService.finalizeMatch(id);
    }


    @PostMapping("match/{jobOfferId}/{applicantId}")
    public Match addManuallyMatch(@PathVariable long jobOfferId, @PathVariable long applicantId) throws ApplicantNotFoundException, JobOfferNotFoundException, ApplicantNotFoundException {
        return matchService.addManuallyMatch(jobOfferId,applicantId);
    }
    @PostMapping("partial/{id}")
    public List<Match> partiallyMatch(@PathVariable long id) throws JobOfferNotFoundException{
        return matchService.addPartiallyMatch(id);
    }

}
