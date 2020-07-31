package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferAlreadyClosed;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.JobOfferRepo;
import gr.codehub.rsapi.repository.JobOfferSkillRepo;
import gr.codehub.rsapi.repository.SkillRepo;
import gr.codehub.rsapi.utility.FileReaderToList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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

/**
 * The type Job offer service.
 */
@Service
@Slf4j
public class JobOfferServiceImpl implements JobOfferService {
    @Autowired
    private JobOfferRepo jobOfferRepo;
    private SkillRepo skillRepo;
    private JobOfferSkillRepo jobOfferSkillRepo;

    /**
     * Instantiates a new Job offer service.
     *
     * @param jobOfferRepo      the job offer repo
     * @param skillRepo         the skill repo
     * @param jobOfferSkillRepo the job offer skill repo
     */
    @Autowired
    public JobOfferServiceImpl(JobOfferRepo jobOfferRepo, SkillRepo skillRepo, JobOfferSkillRepo jobOfferSkillRepo) {
        this.jobOfferRepo = jobOfferRepo;
        this.skillRepo = skillRepo;
        this.jobOfferSkillRepo = jobOfferSkillRepo;
    }

    @Override
    public List<JobOffer> getJobOffers() {
        log.info("\nEnter getJobOffers method");
        log.info("\nExits getJobOffers method and returns all JobOffers");
        return jobOfferRepo.findAll();
    }

    @Override
    public JobOffer addJobOffer(JobOffer jobOffer) {
        log.info("\nEnter addJobOffer method") ;
        log.info("\nExits jobOffer method with Job Offer title : " + jobOffer.getTitle());

        return jobOfferRepo.save(jobOffer);
    }

    /**
     *
     * @param jobOffer
     * @param jobOfferId
     * @return
     * @throws JobOfferNotFoundException
     */
    @Override
    public JobOffer updateJobOffer(JobOffer jobOffer, long jobOfferId) throws JobOfferNotFoundException {
        log.info("\nEnter updateJObOffer method" );

        JobOffer jobOfferInDB;
        Optional<JobOffer> optionalJobOffer = jobOfferRepo.findById(jobOfferId);
        if(optionalJobOffer.isPresent()){
            jobOfferInDB = optionalJobOffer.get();
            if(jobOffer.getCompany()!=null)
                jobOfferInDB.setCompany(jobOffer.getCompany());
            if(jobOffer.getRegion()!=null)
                jobOfferInDB.setRegion(jobOffer.getRegion());
            if(jobOffer.getTitle()!=null)
                jobOfferInDB.setTitle(jobOffer.getTitle());
            if(jobOffer.getOfferDate()!=null)
                jobOfferInDB.setOfferDate(jobOffer.getOfferDate());
            jobOfferRepo.save(jobOfferInDB);
            log.info("\nExits updateJObOffer with JobOfferId : "+ jobOfferId  );
            return jobOfferInDB;
        }else throw new JobOfferNotFoundException("not such joboffer exists");
    }

    /**
     *
     * @param jobOfferIndex
     * @return
     * @throws JobOfferNotFoundException
     */
    @Override
    public JobOffer deleteJobOffer(long jobOfferIndex) throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        log.info("\nEnter deleteJobOffer");

        JobOffer jobOfferinDB= jobOfferRepo.findById(jobOfferIndex)
                .orElseThrow(() -> new JobOfferNotFoundException("No exist offer with this id"));

        if(jobOfferinDB.isClosed()){
            throw new JobOfferAlreadyClosed("JobOffer is already closed");
        }
        jobOfferinDB.setClosed(true);
        jobOfferRepo.save(jobOfferinDB);
        log.info("\nExits deleteJobOffer,after changing a Job from being available with the index: " + jobOfferIndex);
        return jobOfferinDB;
    }

    @Override
    public JobOffer getJobOffer(long jobOfferId) throws JobOfferNotFoundException {
        log.info("\nEnter getJobOffer method" );

        Optional<JobOffer> oJobOffer = jobOfferRepo.findById(jobOfferId);
        if (oJobOffer.isPresent()) {
            log.info("\nEnter getJobOffer method with jobOfferId: " + jobOfferId);
            return oJobOffer.get();
        }  else throw new JobOfferNotFoundException("No Joboffer with this id");
    }

    @Override
    public List<JobOffer> getSelectedJobOffers(String offerDate,
                                               String region,
                                               String name,
                                               Long jobOfferSkillId) throws JobOfferNotFoundException, ParseException {

        log.info("\nEnter getSelectedJobOffers method with arguments offerDate  or region or name or jobOfferSkillId" );

        if (offerDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            LocalDate date = formatter.parse(offerDate).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            log.info("\nExits getSelectedJobOffers method, after returning JobOffers by Offer Date: " + offerDate );
            return jobOfferRepo.findByOfferDate(date).orElseThrow(() -> new JobOfferNotFoundException("Job offer not found"));
        }
        if (region != null){
            log.info("\nExits getSelectedJobOffers method, after returning JobOffers by region: " + region );
            return jobOfferRepo.findByRegion(region).orElseThrow(() -> new JobOfferNotFoundException("Job offer not found"));}
        if (name != null){
            log.info("\nExits getSelectedJobOffers method, after returning JobOffers by name: " + name );

            return jobOfferRepo.findByTitle(name).orElseThrow(() -> new JobOfferNotFoundException("Job offer not found"));}
        if (jobOfferSkillId != null) {
            List<JobOffer> jobOffers = jobOfferRepo.findAll();
            List<JobOffer> tempJobOffers = new ArrayList<JobOffer>();
            for (JobOffer joboffer : jobOffers) {

                List<JobOfferSkill> jobOfferSkills = joboffer.getJobOfferSkills();

                for (JobOfferSkill jobOfferSkill : joboffer.getJobOfferSkills()) {
                    if (jobOfferSkill.getSkill().getId() == jobOfferSkillId) {
                        tempJobOffers.add(joboffer);
                    }

                }

            }
            log.info("\nExits getSelectedApplicants method, after returning jobOffers by jobOfferSkillId" );

            return tempJobOffers;

        }
        return jobOfferRepo.findAll();
    }


    @Override
    public JobOfferSkill addSkillToJobOffer(long jobOfferId, long skillID)
            throws JobOfferNotFoundException, SkillNotFoundException {
        log.info("\nEnter addSkillToJobOffer method");

        JobOffer jobOffer = jobOfferRepo.findById(jobOfferId)
                .orElseThrow(() -> new JobOfferNotFoundException("No Joboffer with this id"));
        Skill skill = skillRepo.findById(skillID)
                .orElseThrow(() -> new SkillNotFoundException("No skill with this id"));
        JobOfferSkill jobOfferSkill;
        jobOfferSkill = new JobOfferSkill();
        jobOfferSkill.setJobOffer(jobOffer);
        jobOfferSkill.setSkill(skill);
        jobOfferSkillRepo.save(jobOfferSkill);
        log.info("\nExits addSkillToJobOffer method, after adding skills to Job Offer with applicantId: "+ jobOfferId+ " and skill id: "+ skillID);

        return jobOfferSkill;
    }

    @Override
    public List<JobOffer> readJobOffers() throws IOException, InvalidFormatException {
        log.info("\nStart readJobOffers From Excel File");
        log.info("\nExits readJobOffers From Excel File after successfully read it");

        return FileReaderToList.readFromExcelJobOffers("data.xlsx", jobOfferRepo, skillRepo, jobOfferSkillRepo);
    }
}
