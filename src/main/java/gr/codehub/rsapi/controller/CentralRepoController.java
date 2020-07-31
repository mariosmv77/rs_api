package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.service.ApplicantService;
import gr.codehub.rsapi.service.JobOfferService;
import gr.codehub.rsapi.service.MatchService;
import gr.codehub.rsapi.service.SkillService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CentralRepoController {

    private ApplicantService applicantService;
    private JobOfferService jobOfferService;
    private SkillService skillService;


    @Autowired
    public CentralRepoController(ApplicantService applicantService, JobOfferService jobOfferService,
                                 SkillService skillService) {
        this.applicantService = applicantService;
        this.jobOfferService = jobOfferService;
        this.skillService = skillService;

    }

    //-----------------------------------------------------------------------------//
    //Applicant EndPoints
    @GetMapping("applicant")
    public List<Applicant> getApplicants(@RequestParam(required = false) String firstName,
                                         @RequestParam(required = false) String lastName,
                                         @RequestParam(required = false) String address,
                                         @RequestParam(required = false) String region,
                                         @RequestParam(required = false) String email) {
        log.info("\n GET REQUEST: Calling getApplicants method");

        return applicantService.getApplicants(firstName, lastName, address, region, email);
    }

    @GetMapping("applicant/{id}")
    public Applicant getApplicant(@PathVariable long id) throws ApplicantNotFoundException {
        log.info("\nGET REQUEST: Calling getApplicant method(by Id) ");

        return applicantService.getApplicant(id);
    }

    @PostMapping("applicant")
    public Applicant addApplicant(@RequestBody Applicant applicant) throws ApplicantCreationException {
        log.info("\nPOST REQUEST: Calling enter addApplicant method");
        return applicantService.addApplicant(applicant);
    }

    @PutMapping("applicant/{id}")
    public Applicant updateApplicant(@RequestBody Applicant applicant, @PathVariable long id) throws ApplicantNotFoundException {
        log.info("\nPUT REQUEST: Calling updateApplicant method");
        return applicantService.updateApplicant(applicant, id);
    }

    @DeleteMapping("applicant/{id}")
    public Applicant deleteApplicant(@PathVariable long id) throws ApplicantNotFoundException, ApplicantAlreadyClosed {
        log.info("\nDELETE REQUEST: Calling deleteApplicant");

        return applicantService.deleteApplicant(id);
    }

    @GetMapping("selectedApplicant")
    public List<Applicant> getApplicantByCriteria(
            @RequestParam(required = false) String dob,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long applicantSkillId)
            throws ApplicantNotFoundException, ParseException {
        log.info("\nGET REQUEST: Calling getSelectedApplicants method ");

        return applicantService.getSelectedApplicants(dob, region,
                name, applicantSkillId);
    }

    @PostMapping("applicant/{applicantId}/{skillId}")
    public ApplicantSkill addApplicantSkill(@PathVariable long applicantId, @PathVariable long skillId) throws ApplicantNotFoundException, SkillNotFoundException {
        log.info("\n POST REQUEST: Calling addSkillToApplicant method");

        return applicantService.addSkillToApplicant(applicantId, skillId);
    }

    //-----------------------------------------------------------------//
    //Job offer
    @PostMapping("jobOfferSkill/{jobOfferId}/{skillId}")
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

    @GetMapping("selectedjobOffer")
    public List<JobOffer> getJobOfferByCriteria(
            @RequestParam(required = false) String offerDate,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long jobOfferSkill)
            throws JobOfferNotFoundException, ParseException {
        log.info("\nGET REQUEST: Calling getJobOfferByCriteria method ");

        return jobOfferService.getSelectedJobOffers(offerDate, region,
                name, jobOfferSkill);
    }

    @DeleteMapping("joboffer/{id}")
    public JobOffer deleteJobOffer(long id) throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        log.info("\nDELETE REQUEST: Calling deleteJobOffer");

        return jobOfferService.deleteJobOffer(id);
    }

    @PutMapping("joboffer/{id}")
    public JobOffer updateJobOffer(@RequestBody JobOffer jobOffer, long id) throws JobOfferNotFoundException {
        log.info("\nPUT REQUEST: Calling updateJobOffer method");

        return jobOfferService.updateJobOffer(jobOffer, id);
    }

    //-------------------------------------------------------------------//
    //read from files

    @GetMapping("testApplicantRead")
    public List<Applicant> readApplicants() throws IOException, InvalidFormatException {
        log.info("\nGET REQUEST: Calling readApplicants From Excel File");
        return applicantService.readApplicants();
    }

    @GetMapping("testJobOfferRead")
    public List<JobOffer> readJobOffers() throws IOException, InvalidFormatException {
        log.info("\nGET REQUEST: Calling readJobOffers From Excel File");

        return jobOfferService.readJobOffers();
    }

    @GetMapping("testSkillRead")
    public List<Skill> readSkills() throws IOException, InvalidFormatException {
        log.info("\nGET REQUEST: Calling readSkills From Excel File");

        return skillService.readSkills();
    }

    //skill controller

    @GetMapping("skill")
    public List<Skill> getSkills() {
        log.info("\nGET REQUEST: Calling getSkills method");

        return skillService.getSkills();
    }

    @PostMapping("skill")
    public Skill addSkill(@RequestBody Skill skill) {
        log.info("\nPOST REQUEST: Calling addSkill method");

        return skillService.addSkill(skill);
    }

    @PutMapping("skill/{id}")
    public Skill updateSkill(@RequestBody Skill skill, @PathVariable long id) throws SkillNotFoundException {
        log.info("\nPUT REQUEST:Calling updateSkill method");

        return skillService.updateSkill(skill, id);
    }

    @DeleteMapping("skill/{id}")
    public Boolean deleteSkill(@PathVariable long id) throws SkillNotFoundException {
        log.info("\nDELETE REQUEST: Clling deleteSkill method");

        return skillService.deleteSkill(id);
    }

}
