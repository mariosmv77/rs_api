package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.service.ApplicantService;
import gr.codehub.rsapi.service.JobOfferService;
import gr.codehub.rsapi.service.MatchService;
import gr.codehub.rsapi.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MatcherEngineController {

    private ApplicantService applicantService;
    private JobOfferService jobOfferService;
    private SkillService skillService;
    private MatchService matchService;


    @Autowired
    public MatcherEngineController(ApplicantService applicantService, JobOfferService jobOfferService,
                                 SkillService skillService,MatchService matchService) {
        this.applicantService = applicantService;
        this.jobOfferService = jobOfferService;
        this.skillService = skillService;
        this.matchService =matchService;
    }

    @PostMapping("match/{jobOfferId}/{applicantId}")
    public Match addManuallyMatch(@PathVariable long jobOfferId, @PathVariable long applicantId) throws ApplicantNotFoundException, JobOfferNotFoundException {
        return matchService.addManuallyMatch(jobOfferId,applicantId);
    }
    @PostMapping("match/{jobOfferId}")
    public Match addAutomaticMatch(@PathVariable long jobOfferId) throws  JobOfferNotFoundException {
        return matchService.addAutomaticMatch(jobOfferId);
    }

    @PostMapping("match/{id}")
    public List<Match> partialMatch(@PathVariable long id) throws JobOfferNotFoundException{
        return matchService.addPartiallyMatch(id);
    }

}
