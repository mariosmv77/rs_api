package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.service.ApplicantService;
import gr.codehub.rsapi.service.JobOfferService;
import gr.codehub.rsapi.service.MatchService;
import gr.codehub.rsapi.service.SkillService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
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

    @GetMapping("applicant")
    public List<Applicant> getApplicants() {

        return applicantService.getApplicants();
    }

    @GetMapping("applicant/{id}")
    public Applicant getApplicant(@PathVariable long id) throws ApplicantNotFoundException {
        return applicantService.getApplicant(id);
    }

    @PostMapping("applicant")
    public Applicant addApplicant(@RequestBody Applicant applicant) {
        return applicantService.addApplicant(applicant);
    }

    @PutMapping("applicant/{id}")
    public Applicant updateApplicant(@RequestBody Applicant applicant, @PathVariable long id) throws ApplicantNotFoundException {
        return applicantService.updateApplicant(applicant,id);
    }
    @DeleteMapping("applicant/{id}")
    public Applicant deleteApplicant(@PathVariable long id) throws ApplicantNotFoundException {
        return applicantService.deleteApplicant(id);
    }

    @GetMapping("testSkillRead")
    public List<Skill> readSkills() throws IOException, InvalidFormatException {
        return skillService.readSkills();
    }

    @PostMapping("jobOfferSkill/{jobOfferId}/{skillId}")
    public JobOfferSkill addSkillToJobOffer(@PathVariable long jobOfferId, @PathVariable long skillId) throws JobOfferNotFoundException, SkillNotFoundException {
        return jobOfferService.addSkillToJobOffer(jobOfferId,skillId);
    }

    @PostMapping("jobOffer")
    public JobOffer addJobOffer(@RequestBody JobOffer jobOffer) {
        return jobOfferService.addJobOffer(jobOffer);
    }

    @GetMapping("joboffer")
    public List<JobOffer> getJobOffer() {
        return jobOfferService.getJobOffers();
    }

    @GetMapping("testApplicantRead")
    public List<Applicant> readApplicants() throws IOException, InvalidFormatException {
        return applicantService.readApplicants();
    }

    @GetMapping("testJobOfferRead")
    public List<JobOffer> readJobOffers() throws IOException, InvalidFormatException {
        return jobOfferService.readJobOffers();
    }

    //skill controller

    @GetMapping("skill")
    public List<Skill> getSkills(){
        return skillService.getSkills();
    }

    @PostMapping("skill")
    public Skill addSkill(@RequestBody Skill skill){
        return skillService.addSkill(skill);
    }

    @PutMapping("skill/{id}")
    public Skill updateSkill(@RequestBody Skill skill , @PathVariable long id) throws SkillNotFoundException{
        return skillService.updateSkill(skill,id);
    }

    @DeleteMapping("skill/{id}")
    public Boolean deleteSkill(@PathVariable long id) throws SkillNotFoundException {
        return skillService.deleteSkill(id);
    }

    //ApplicantSkill Controller
    @PostMapping("applicant/{applicantId}/{skillId}")
    public ApplicantSkill addApplicantSkill(@PathVariable long applicantId, @PathVariable long skillId) throws ApplicantNotFoundException, SkillNotFoundException {
        return applicantService.addSkillToApplicant(applicantId,skillId);
    }
}
