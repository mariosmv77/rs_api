package gr.codehub.rsapi.controller.centralrepo;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.service.ApplicantService;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class    ApplicantController {
    private ApplicantService applicantService;

@Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

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

    @PostMapping("applicant/{applicantId}/skill/{skillId}")
    public ApplicantSkill addApplicantSkill(@PathVariable long applicantId, @PathVariable long skillId) throws ApplicantNotFoundException, SkillNotFoundException {
        log.info("\n POST REQUEST: Calling addSkillToApplicant method");
        return applicantService.addSkillToApplicant(applicantId, skillId);
    }
}
