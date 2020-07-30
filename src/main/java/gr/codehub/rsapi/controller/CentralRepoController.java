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

/**
 * The type Central repo controller.
 */
@RestController
public class CentralRepoController {

    private ApplicantService applicantService;
    private JobOfferService jobOfferService;
    private SkillService skillService;


    /**
     * Instantiates a new Central repo controller.
     *
     * @param applicantService the applicant service
     * @param jobOfferService  the job offer service
     * @param skillService     the skill service
     */
    @Autowired
    public CentralRepoController(ApplicantService applicantService, JobOfferService jobOfferService,
                                 SkillService skillService) {
        this.applicantService = applicantService;
        this.jobOfferService = jobOfferService;
        this.skillService = skillService;

    }

    /**
     * Gets applicants.
     *
     * @return the applicants
     */
//-----------------------------------------------------------------------------//
    //Applicant EndPoints
    @GetMapping("applicant")
    public List<Applicant> getApplicants() {

        return applicantService.getApplicants();
    }

    /**
     * Gets applicant.
     *
     * @param id the id
     * @return the applicant
     * @throws ApplicantNotFoundException the applicant not found exception
     */
    @GetMapping("applicant/{id}")
    public Applicant getApplicant(@PathVariable long id) throws ApplicantNotFoundException {
        return applicantService.getApplicant(id);
    }

    /**
     * Add applicant applicant.
     *
     * @param applicant the applicant
     * @return the applicant
     * @throws ApplicantCreationException the applicant creation exception
     */
    @PostMapping("applicant")
    public Applicant addApplicant(@RequestBody Applicant applicant) throws ApplicantCreationException {
        return applicantService.addApplicant(applicant);
    }

    /**
     * Update applicant applicant.
     *
     * @param applicant the applicant
     * @param id        the id
     * @return the applicant
     * @throws ApplicantNotFoundException the applicant not found exception
     */
    @PutMapping("applicant/{id}")
    public Applicant updateApplicant(@RequestBody Applicant applicant, @PathVariable long id) throws ApplicantNotFoundException {
        return applicantService.updateApplicant(applicant, id);
    }

    /**
     * Delete applicant applicant.
     *
     * @param id the id
     * @return the applicant
     * @throws ApplicantNotFoundException the applicant not found exception
     * @throws ApplicantAlreadyClosed     the applicant already closed
     */
    @DeleteMapping("applicant/{id}")
    public Applicant deleteApplicant(@PathVariable long id) throws ApplicantNotFoundException, ApplicantAlreadyClosed {
        return applicantService.deleteApplicant(id);
    }

    /**
     * Gets applicant by criteria.
     *
     * @param dob              the dob
     * @param region           the region
     * @param name             the name
     * @param applicantSkillId the applicant skill id
     * @return the applicant by criteria
     * @throws ApplicantNotFoundException the applicant not found exception
     * @throws ParseException             the parse exception
     */
    @GetMapping("selectedApplicant")
    public List<Applicant> getApplicantByCriteria(
            @RequestParam(required = false) String dob,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long applicantSkillId)
            throws ApplicantNotFoundException, ParseException {
        return applicantService.getSelectedApplicants(dob, region,
                name, applicantSkillId);
    }

    /**
     * Add applicant skill applicant skill.
     *
     * @param applicantId the applicant id
     * @param skillId     the skill id
     * @return the applicant skill
     * @throws ApplicantNotFoundException the applicant not found exception
     * @throws SkillNotFoundException     the skill not found exception
     */
    @PostMapping("applicant/{applicantId}/{skillId}")
    public ApplicantSkill addApplicantSkill(@PathVariable long applicantId, @PathVariable long skillId) throws ApplicantNotFoundException, SkillNotFoundException {
        return applicantService.addSkillToApplicant(applicantId, skillId);
    }

    /**
     * Add skill to job offer job offer skill.
     *
     * @param jobOfferId the job offer id
     * @param skillId    the skill id
     * @return the job offer skill
     * @throws JobOfferNotFoundException the job offer not found exception
     * @throws SkillNotFoundException    the skill not found exception
     */
//-----------------------------------------------------------------//
    //Job offer
    @PostMapping("jobOfferSkill/{jobOfferId}/{skillId}")
    public JobOfferSkill addSkillToJobOffer(@PathVariable long jobOfferId, @PathVariable long skillId) throws JobOfferNotFoundException, SkillNotFoundException {
        return jobOfferService.addSkillToJobOffer(jobOfferId, skillId);
    }

    /**
     * Add job offer job offer.
     *
     * @param jobOffer the job offer
     * @return the job offer
     * @throws JobOfferCreationException the job offer creation exception
     */
    @PostMapping("jobOffer")
    public JobOffer addJobOffer(@RequestBody JobOffer jobOffer) throws JobOfferCreationException {

        return jobOfferService.addJobOffer(jobOffer);
    }

    /**
     * Gets job offers.
     *
     * @return the job offers
     */
    @GetMapping("jobOffers")
    public List<JobOffer> getJobOffers() {
        return jobOfferService.getJobOffers();
    }

    /**
     * Gets job offer.
     *
     * @param id the id
     * @return the job offer
     * @throws JobOfferNotFoundException the job offer not found exception
     */
    @GetMapping("jobOffer")
    public JobOffer getJobOffer(
            @RequestParam(required = false) String id)
            throws JobOfferNotFoundException {
        return jobOfferService.getJobOffer(id);
    }

    /**
     * Gets job offer by criteria.
     *
     * @param offerDate     the offer date
     * @param region        the region
     * @param name          the name
     * @param jobOfferSkill the job offer skill
     * @return the job offer by criteria
     * @throws JobOfferNotFoundException the job offer not found exception
     * @throws ParseException            the parse exception
     */
    @GetMapping("selectedjobOffer")
    public List<JobOffer> getJobOfferByCriteria(
            @RequestParam(required = false) String offerDate,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long jobOfferSkill)
            throws JobOfferNotFoundException, ParseException {
        return jobOfferService.getSelectedJobOffers(offerDate, region,
                name, jobOfferSkill);
    }

    /**
     * Delete job offer job offer.
     *
     * @param id the id
     * @return the job offer
     * @throws JobOfferNotFoundException the job offer not found exception
     * @throws JobOfferAlreadyClosed     the job offer already closed
     */
    @DeleteMapping("joboffer/{id}")
    public JobOffer deleteJobOffer(long id) throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        return jobOfferService.deleteJobOffer(id);
    }

    /**
     * Update job offer job offer.
     *
     * @param jobOffer the job offer
     * @param id       the id
     * @return the job offer
     * @throws JobOfferNotFoundException the job offer not found exception
     */
    @PutMapping("joboffer/{id}")
    public JobOffer updateJobOffer(@RequestBody JobOffer jobOffer, long id) throws JobOfferNotFoundException {
        return jobOfferService.updateJobOffer(jobOffer, id);
    }

    //-------------------------------------------------------------------//
    //read from files

    /**
     * Read applicants list.
     *
     * @return the list
     * @throws IOException            the io exception
     * @throws InvalidFormatException the invalid format exception
     */
    @GetMapping("testApplicantRead")
    public List<Applicant> readApplicants() throws IOException, InvalidFormatException {
        return applicantService.readApplicants();
    }

    /**
     * Read job offers list.
     *
     * @return the list
     * @throws IOException            the io exception
     * @throws InvalidFormatException the invalid format exception
     */
    @GetMapping("testJobOfferRead")
    public List<JobOffer> readJobOffers() throws IOException, InvalidFormatException {
        return jobOfferService.readJobOffers();
    }

    /**
     * Read skills list.
     *
     * @return the list
     * @throws IOException            the io exception
     * @throws InvalidFormatException the invalid format exception
     */
    @GetMapping("testSkillRead")
    public List<Skill> readSkills() throws IOException, InvalidFormatException {
        return skillService.readSkills();
    }

    //skill controller

    /**
     * Gets skills.
     *
     * @return the skills
     */
    @GetMapping("skill")
    public List<Skill> getSkills() {
        return skillService.getSkills();
    }

    /**
     * Add skill skill.
     *
     * @param skill the skill
     * @return the skill
     */
    @PostMapping("skill")
    public Skill addSkill(@RequestBody Skill skill) {
        return skillService.addSkill(skill);
    }

    /**
     * Update skill skill.
     *
     * @param skill the skill
     * @param id    the id
     * @return the skill
     * @throws SkillNotFoundException the skill not found exception
     */
    @PutMapping("skill/{id}")
    public Skill updateSkill(@RequestBody Skill skill, @PathVariable long id) throws SkillNotFoundException {
        return skillService.updateSkill(skill, id);
    }

    /**
     * Delete skill boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws SkillNotFoundException the skill not found exception
     */
    @DeleteMapping("skill/{id}")
    public Boolean deleteSkill(@PathVariable long id) throws SkillNotFoundException {
        return skillService.deleteSkill(id);
    }

}
