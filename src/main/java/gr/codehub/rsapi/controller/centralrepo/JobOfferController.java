package gr.codehub.rsapi.controller.centralrepo;

import gr.codehub.rsapi.exception.JobOfferAlreadyClosed;
import gr.codehub.rsapi.exception.JobOfferCreationException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.JobOfferSkill;
import gr.codehub.rsapi.service.JobOfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
@RestController
@Slf4j
public class JobOfferController {
    private JobOfferService jobOfferService;
@Autowired
    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @PostMapping("jobOffer/{jobOfferId}/skill/{skillId}")
    public JobOfferSkill addSkillToJobOffer(@PathVariable long jobOfferId, @PathVariable long skillId) throws JobOfferNotFoundException, SkillNotFoundException {
        log.info("\n POST REQUEST: Calling addSkillToJobOffer method");
        return jobOfferService.addSkillToJobOffer(jobOfferId, skillId);
    }

    @PostMapping("jobOffer")
    public JobOffer addJobOffer(@RequestBody JobOffer jobOffer) throws JobOfferCreationException {
        log.info("\nPOST REQUEST: Calling enter addJobOffer method");
        return jobOfferService.addJobOffer(jobOffer);
    }

    @GetMapping("jobOffer")
    public List<JobOffer> getJobOffers() {
        log.info("\n GET REQUEST: Calling getJobOffers method");
        return jobOfferService.getJobOffers();
    }

    @GetMapping("jobOffer/{id}")
    public JobOffer getJobOffer(long id) throws JobOfferNotFoundException {
        log.info("\nGET REQUEST: Calling getJobOffer method(by Id) ");
        return jobOfferService.getJobOffer(id);
    }

    @GetMapping("selectedJobOffer")
    public List<JobOffer> getJobOfferByCriteria(
            @RequestParam(required = false) String offerDate,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long skill)
            throws JobOfferNotFoundException, ParseException {
        log.info("\nGET REQUEST: Calling getJobOfferByCriteria method ");
        return jobOfferService.getSelectedJobOffers(offerDate, region,
                name, skill);
    }

    @DeleteMapping("jobOffer/{id}")
    public JobOffer deleteJobOffer(long id) throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        log.info("\nDELETE REQUEST: Calling deleteJobOffer");
        return jobOfferService.deleteJobOffer(id);
    }

    @PutMapping("jobOffer/{id}")
    public JobOffer updateJobOffer(@RequestBody JobOffer jobOffer, long id) throws JobOfferNotFoundException {
        log.info("\nPUT REQUEST: Calling updateJobOffer method");
        return jobOfferService.updateJobOffer(jobOffer, id);
    }
}
