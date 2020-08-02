package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.ApplicantSkill;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.ApplicantRepo;
import gr.codehub.rsapi.repository.ApplicantSkillRepo;
import gr.codehub.rsapi.repository.SkillRepo;
import gr.codehub.rsapi.repository.specs.ApplicantSpecification;
import gr.codehub.rsapi.repository.specs.SearchCriteria;
import gr.codehub.rsapi.repository.specs.SearchOperation;
import gr.codehub.rsapi.utility.FileReaderToList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApplicantServiceImpl implements ApplicantService {

    private ApplicantRepo applicantRepo;
    private ApplicantSkillRepo applicantSkillRepo;
    private SkillRepo skillRepo;

    @Autowired
    public ApplicantServiceImpl(ApplicantRepo applicantRepo, ApplicantSkillRepo applicantSkillRepo, SkillRepo skillRepo) {
        this.applicantRepo = applicantRepo;
        this.applicantSkillRepo = applicantSkillRepo;
        this.skillRepo = skillRepo;
    }

    @Override
    public List<Applicant> getApplicants(String firstName, String lastName, String address, String region, String email) {
        log.info("\nEnter getApplicants method");
        ApplicantSpecification msTitleRating = new ApplicantSpecification();
        if (firstName != null) msTitleRating.add(new SearchCriteria("firstName", firstName, SearchOperation.MATCH));
        if (lastName != null) msTitleRating.add(new SearchCriteria("lastName", lastName, SearchOperation.MATCH));
        if (address != null) msTitleRating.add(new SearchCriteria("address", address, SearchOperation.MATCH));
        if (region != null) msTitleRating.add(new SearchCriteria("region", region, SearchOperation.MATCH));
        if (email != null) msTitleRating.add(new SearchCriteria("email", email, SearchOperation.MATCH));
        List<Applicant> msTitleRatingList = applicantRepo.findAll(msTitleRating);
        log.info("\nExits getApplicants method and returns all applicants or by specific criteria");
        return msTitleRatingList;
    }

    @Override
    public Applicant addApplicant(Applicant applicant) throws ApplicantCreationException {
        log.info("\nEnter addApplicant method");
        if (applicant.getFirstName() == null || applicant.getLastName() == null || applicant.getRegion() == null
                || applicant.getAddress() == null || applicant.getDob() == null)
            throw new ApplicantCreationException("Please fill in all the fields");
        if (applicant.getEmail() == null || !applicant.getEmail().contains("@"))
            throw new ApplicantCreationException("Invalid applicant's Email ");
        log.info("\nExits addApplicant method after added applicant with name: " + applicant.getFirstName());
        return applicantRepo.save(applicant);
    }

    @Override
    public Applicant updateApplicant(Applicant applicant, long applicantId) throws ApplicantNotFoundException {
        log.info("\nEnter updateApplicant method");
        Applicant applicantInDb;
        Optional<Applicant> optionalApplicant = applicantRepo.findById(applicantId);
        if (optionalApplicant.isPresent()) {
            applicantInDb = optionalApplicant.get();
            if (applicant.getFirstName() != null)
                applicantInDb.setFirstName(applicant.getFirstName());
            if (applicant.getLastName() != null)
                applicantInDb.setLastName(applicant.getLastName());
            if (applicant.getAddress() != null)
                applicantInDb.setAddress(applicant.getAddress());
            if (applicant.getRegion() != null)
                applicantInDb.setRegion(applicant.getRegion());
            if (applicant.getEmail() != null)
                applicantInDb.setEmail(applicant.getEmail());
            if (applicant.getDob() != null)
                applicantInDb.setDob(applicant.getDob());
            applicantRepo.save(applicantInDb);
            log.info("\nExits updateApplicant, after update an applicant with ApplicantId : " + applicantId);
            return applicantInDb;
        } else throw new ApplicantNotFoundException("not such applicant exists");
    }

    @Override
    public Applicant deleteApplicant(long applicantId) throws ApplicantNotFoundException, ApplicantAlreadyClosed {
        log.info("\nEnter deleteApplicant");
        Applicant applicantInDb;
        Optional<Applicant> optionalApplicant = applicantRepo.findById(applicantId);
        if (optionalApplicant.isPresent()) {
            applicantInDb = optionalApplicant.get();
            if (applicantInDb.isInactive()) {
                throw new ApplicantAlreadyClosed("applicant already closed");
            }
            applicantInDb.setInactive(true);
            log.info("\nExits deleteApplicant,after changing an Applicant from being available with the index: " + applicantId);
            return applicantRepo.save(applicantInDb);
        } else throw new ApplicantNotFoundException("not such applicant exists");
    }

    @Override
    public Applicant getApplicant(long applicantId) throws ApplicantNotFoundException {
        log.info("\nEnter getApplicant method ");
        Optional<Applicant> optionalApplicant = applicantRepo.findById(applicantId);
        if (optionalApplicant.isPresent()) {
            log.info("\nExits getApplicant method, after get an Applicant with applicant Id: " + applicantId);
            return optionalApplicant.get();
        } else throw new ApplicantNotFoundException("not such applicant exists");
    }

    @Override
    public List<Applicant> readApplicants() throws IOException, InvalidFormatException {
        log.info("\nStart ReadApplicants From Excel File");
        log.info("\nExits ReadApplicants From Excel File after successfully read it");
        return FileReaderToList.readFromExcelApplicant("data.xlsx", applicantRepo, skillRepo, applicantSkillRepo);
    }

    @Override
    public ApplicantSkill addSkillToApplicant(long applicantId, long skillId)
            throws ApplicantNotFoundException, SkillNotFoundException {
        log.info("\nEnter addSkillToApplicant method");
        Applicant applicant = applicantRepo.findById(applicantId)
                .orElseThrow(() -> new
                        ApplicantNotFoundException("Cannot find applicant"));
        Skill skill = skillRepo.findById(skillId).orElseThrow(() -> new
                SkillNotFoundException("Cannot find Skill"));
        ApplicantSkill applicantSkill = new ApplicantSkill();
        applicantSkill.setApplicant(applicant);
        applicantSkill.setSkill(skill);
        applicantSkillRepo.save(applicantSkill);
        log.info("\nExits addSkillToApplicant method, after adding skills to applicant with applicantId: " +
                applicantId + " and skill id: " + skillId);
        return applicantSkill;
    }
}
