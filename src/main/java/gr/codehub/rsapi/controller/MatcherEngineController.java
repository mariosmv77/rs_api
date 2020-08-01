package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class MatcherEngineController {

    private MatchService matchService;

    @Autowired
    public MatcherEngineController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("match")
    public List<Match> getMatches() {
        log.info("\nGET REQUEST: Calling getMatches method");

        return matchService.getMatches();
    }

    @PostMapping("automatic/{id}")
    public List<Match> automaticMatch(@PathVariable long id) throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        log.info("\nPOST REQUEST: Calling addAutomaticMatch method ");

        return matchService.addAutomaticMatch(id);
    }

    @PutMapping("finalize/{id}")
    public boolean finalizeMatch(@PathVariable long id) throws MatchNotFoundException, MatchAlreadyFinalized, JobOfferAlreadyClosed, ApplicantAlreadyClosed {
        log.info("\nPUT REQUEST: Calling finalizeMatch method ");

        return matchService.finalizeMatch(id);
    }


    @PostMapping("match/{jobOfferId}/{applicantId}")
    public Match addManuallyMatch(@PathVariable long jobOfferId, @PathVariable long applicantId) throws ApplicantNotFoundException, JobOfferNotFoundException, ApplicantNotFoundException, ApplicantAlreadyClosed, JobOfferAlreadyClosed {
        log.info("\nPOST REQUEST: Calling addManuallyMatch method ");

        return matchService.addManuallyMatch(jobOfferId, applicantId);
    }

    @PostMapping("partial/{id}")
    public List<Match> partiallyMatch(@PathVariable long id) throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        log.info("\nPOST REQUEST: Calling addPartiallyMatch method ");

        return matchService.addPartiallyMatch(id);
    }

    @DeleteMapping("match/{matchId}")
    public boolean deleteMatch(@PathVariable long matchId) {
        log.info("\nDELETE REQUEST: Calling deleteMatch method ");

        return matchService.deleteMatch(matchId);
    }

    @GetMapping("match/{matchId}")
    public Match getMatch(@PathVariable long matchId) throws MatchNotFoundException {
        log.info("\nGET REQUEST: Calling getMatch method");

        return matchService.getMatch(matchId);
    }

}
