package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.ApplicantSkill;
import gr.codehub.rsapi.model.JobOffer;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    public List<Applicant> getApplicants() {
        return applicantRepo.findAll();
    }

    /**
     * This method search in Database the table of applicants
     *
     * @return a List with all applicants in Database
     */
    @Override
    public List<Applicant> getApplicantsByCriteria(String firstName, String lastName, String address, String region,
                                  String email, String dob, String isClosed) {

        log.info("\nEnter getApplicants method");
        log.info("\nExits getApplicants method and returns all applicants");

        ApplicantSpecification msTitleRating = new ApplicantSpecification();
        if(firstName!=null) msTitleRating.add(new SearchCriteria("firstName", firstName, SearchOperation.MATCH));
        if(lastName!=null) msTitleRating.add(new SearchCriteria("lastName", lastName, SearchOperation.MATCH));
        if(address!=null) msTitleRating.add(new SearchCriteria("address", address, SearchOperation.MATCH));
        if(region!=null) msTitleRating.add(new SearchCriteria("region", region, SearchOperation.MATCH));
        if(email!=null)msTitleRating.add(new SearchCriteria("email", email, SearchOperation.MATCH));
        if(isClosed!=null)msTitleRating.add(new SearchCriteria("isClosed", isClosed, SearchOperation.MATCH));
        if(dob!=null) msTitleRating.add(new SearchCriteria("dob", dob, SearchOperation.GREATER_THAN));
        List<Applicant> msTitleRatingList = applicantRepo.findAll(msTitleRating);
        return msTitleRatingList;
    }

    /**
     * This method add a new applicant to applicant table
     *
     * @param applicant
     * @return the saved applicant
     */

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

    /**
     * This is method updates the fields of an applicant with a specific id
     *
     * @param applicant
     * @param applicantId
     * @return applicantInDb
     * @throws ApplicantNotFoundException in case were applicant with specific id not exist
     */
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

    /**
     * This method change the value of field isClosed to true
     *
     * @param applicantIndex
     * @return the saved applicant with new values
     * @throws ApplicantNotFoundException in case were applicant with specific id not exist
     */
    @Override
    public Applicant deleteApplicant(long applicantIndex) throws ApplicantNotFoundException, ApplicantAlreadyClosed {
        log.info("\nEnter deleteApplicant");

        Applicant applicantInDb;
        Optional<Applicant> optionalApplicant = applicantRepo.findById(applicantIndex);
        if (optionalApplicant.isPresent()) {
            applicantInDb = optionalApplicant.get();
            if (applicantInDb.isInactive()) {
                throw new ApplicantAlreadyClosed("applicant already closed");
            }
            applicantInDb    .setInactive(true);
            log.info("\nExits deleteApplicant,after changing an Applicant from being available with the index: " + applicantIndex);
            return applicantRepo.save(applicantInDb);
        } else throw new ApplicantNotFoundException("not such applicant exists");

    }

    /**
     * This methhod find in Database an applicant with specific id
     *
     * @param applicantId
     * @return the applicant with specific id
     * @throws ApplicantNotFoundException in case were applicant with specific id not exist
     */
    @Override
    public Applicant getApplicant(long applicantId) throws ApplicantNotFoundException {
        log.info("\nEnter getApplicant method ");
        Optional<Applicant> optionalApplicant = applicantRepo.findById(applicantId);
        if (optionalApplicant.isPresent()) {
            log.info("\nExits getApplicant method, after get an Applicant with applicant Id: " + applicantId);
            return optionalApplicant.get();
        } else throw new ApplicantNotFoundException("not such applicant exists");
    }

    /**
     * This method search in Database for Applicants who satisfy specific criteria
     *
     * @param dob
     * @param region
     * @param name
     * @param applicantSkillId
     * @return a list with applicants who satisfy specific criteria
     * @throws ApplicantNotFoundException in case were applicant with specific id not exist
     * @throws ParseException             in case the localDate have problem
     */
    @Override
    public List<Applicant> getSelectedApplicants(String dob,
                                                 String region,
                                                 String name,
                                                 Long applicantSkillId) throws ApplicantNotFoundException, ParseException {
        log.info("\nEnter getSelectedApplicants method with arguments dob  or region or name or applicantSkillId");
        if (dob != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            LocalDate date = formatter.parse(dob).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            log.info("\nExits getSelectedApplicants method, after returning applicants by Date of birth : " + dob);
            return applicantRepo.findByDob(date).orElseThrow(() -> new ApplicantNotFoundException("Applicant not found"));
        }

        if (region != null) {
            log.info("\nExits getSelectedApplicants method, after returning applicants by region : " + region);
            return applicantRepo.findByRegion(region).orElseThrow(() -> new ApplicantNotFoundException("Applicant not found"));
        }

        if (name != null) {
            log.info("\nExits getSelectedApplicants method, after returning applicants by Firstname : " + name);

            return applicantRepo.findByFirstName(name).orElseThrow(() -> new ApplicantNotFoundException("Applicant not found"));
        }
        if (applicantSkillId != null) {
            List<Applicant> applicants = applicantRepo.findAll();
            List<Applicant> tempApplicants = new ArrayList<Applicant>();
            for (Applicant applicant : applicants) {

                List<ApplicantSkill> applicantSkills = applicant.getApplicantSkills();

                for (ApplicantSkill applicantSkill : applicant.getApplicantSkills()) {
                    if (applicantSkill.getSkill().getId() == applicantSkillId) {
                        tempApplicants.add(applicant);
                    }
                    break;
                }

            }
            log.info("\nExits getSelectedApplicants method, after returning applicants by applicantSkills");

            return tempApplicants;

        }
//            return applicantRepo.findByApplicantSkills(applicantSkillId).orElseThrow(() -> new ApplicantNotFoundException("Job offer not found"));

        return applicantRepo.findAll();
    }

    /**
     * This method read Applicants from excel
     *
     * @return saved applicants from excell
     * @throws IOException
     * @throws InvalidFormatException
     */
    @Override
    public List<Applicant> readApplicants() throws IOException, InvalidFormatException {
        log.info("\nStart ReadApplicants From Excel File");
        log.info("\nExits ReadApplicants From Excel File after successfully read it");

        return FileReaderToList.readFromExcelApplicant("data.xlsx", applicantRepo, skillRepo, applicantSkillRepo);
    }

    /**
     * This method add a skill with specific id to an applicant
     *
     * @param applicantId
     * @param skillId
     * @return saved applicantskill
     * @throws ApplicantNotFoundException in case where applicant with this id not exist
     * @throws SkillNotFoundException     in case were skill with specific id not exist
     */
    @Override
    public ApplicantSkill addSkillToApplicant(long applicantId, long skillId) throws ApplicantNotFoundException, SkillNotFoundException {
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

        log.info("\nExits addSkillToApplicant method, after adding skills to applicant with applicantId: " + applicantId + " and skill id: " + skillId);

        return applicantSkill;
    }
}
