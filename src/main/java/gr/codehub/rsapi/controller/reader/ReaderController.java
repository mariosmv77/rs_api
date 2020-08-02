package gr.codehub.rsapi.controller.reader;

import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.ApplicantService;
import gr.codehub.rsapi.service.JobOfferService;
import gr.codehub.rsapi.service.SkillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class ReaderController {
    private ApplicantService applicantService;
    private JobOfferService jobOfferService;
    private SkillService skillService;

    @Autowired
    public ReaderController(ApplicantService applicantService, JobOfferService jobOfferService, SkillService skillService) {
        this.applicantService = applicantService;
        this.jobOfferService = jobOfferService;
        this.skillService = skillService;
    }

    @GetMapping("applicantRead")
    public List<Applicant> readApplicants() throws IOException, InvalidFormatException {
        log.info("\nGET REQUEST: Calling readApplicants From Excel File");
        return applicantService.readApplicants();
    }

    @GetMapping("jobOfferRead")
    public List<JobOffer> readJobOffers() throws IOException, InvalidFormatException {
        log.info("\nGET REQUEST: Calling readJobOffers From Excel File");
        return jobOfferService.readJobOffers();
    }

    @GetMapping("skillRead")
    public List<Skill> readSkills() throws IOException, InvalidFormatException {
        log.info("\nGET REQUEST: Calling readSkills From Excel File");
        return skillService.readSkills();
    }
}
