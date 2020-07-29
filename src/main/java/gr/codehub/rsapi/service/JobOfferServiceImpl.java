package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.JobOfferRepo;
import gr.codehub.rsapi.repository.JobOfferSkillRepo;
import gr.codehub.rsapi.repository.SkillRepo;
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

@Service
public class JobOfferServiceImpl implements JobOfferService {
    @Autowired
    private JobOfferRepo jobOfferRepo;

    @Autowired
    private SkillRepo skillRepo;

    @Autowired
    private JobOfferSkillRepo jobOfferSkillRepo;

    @Override
    public List<JobOffer> getJobOffers() {
        return jobOfferRepo.findAll();
    }

    @Override
    public JobOffer addJobOffer(JobOffer jobOffer) throws JobOfferCreationException {
        if(jobOffer.getCompany()==null || jobOffer.getOfferDate()==null || jobOffer.getRegion()==null
        || jobOffer.getTitle()==null)
            throw new JobOfferCreationException("Missing fields");

        return jobOfferRepo.save(jobOffer);
    }

    @Override
    public JobOffer updateJobOffer(JobOffer jobOffer, long jobOfferId) throws JobOfferNotFoundException {
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
            return jobOfferInDB;
        }else throw new JobOfferNotFoundException("not such joboffer exists");
    }

    @Override
    public JobOffer deleteJobOffer(long jobOfferIndex) throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        JobOffer jobOfferinDB= jobOfferRepo.findById(jobOfferIndex)
                .orElseThrow(() -> new JobOfferNotFoundException("No exist offer with this id"));

        if(jobOfferinDB.isClosed()){
            throw new JobOfferAlreadyClosed("JobOffer is already closed");
        }
        jobOfferinDB.setClosed(true);
        jobOfferRepo.save(jobOfferinDB);
        return jobOfferinDB;
    }

    @Override
    public JobOffer getJobOffer(long jobOfferId) throws JobOfferNotFoundException {
        Optional<JobOffer> oJobOffer = jobOfferRepo.findById(jobOfferId);
        if (oJobOffer.isPresent())
            return oJobOffer.get();
        else throw new JobOfferNotFoundException("No Joboffer with this id");
    }

    @Override
    public List<JobOffer> getSelectedJobOffers(String offerDate,
                                               String region,
                                               String name,
                                               Long jobOfferSkillId) throws JobOfferNotFoundException, ParseException {

        if (offerDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            LocalDate date = formatter.parse(offerDate).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return jobOfferRepo.findByOfferDate(date).orElseThrow(() -> new JobOfferNotFoundException("Job offer not found"));
        }
        if (region != null)
            return jobOfferRepo.findByRegion(region).orElseThrow(() -> new JobOfferNotFoundException("Job offer not found"));
        if (name != null)
            return jobOfferRepo.findByTitle(name).orElseThrow(() -> new JobOfferNotFoundException("Job offer not found"));
        if (jobOfferSkillId != null) {
          List<JobOffer> jobOffers = jobOfferRepo.findAll();
          List<JobOffer> tempJobOffers= new ArrayList<JobOffer>();
          for (JobOffer joboffer: jobOffers) {

              List<JobOfferSkill> jobOfferSkills = joboffer.getJobOfferSkills();

              for (JobOfferSkill jobOfferSkill : joboffer.getJobOfferSkills()) {
                  if(jobOfferSkill.getSkill().getId() == jobOfferSkillId){
                      tempJobOffers.add(joboffer);
                  }

              }

          }
          return tempJobOffers;

        }
        return jobOfferRepo.findAll();
    }


    @Override
    public JobOfferSkill addSkillToJobOffer(long jobOfferId, long skillID)
            throws JobOfferNotFoundException, SkillNotFoundException {
        JobOffer jobOffer = jobOfferRepo.findById(jobOfferId)
                .orElseThrow(() -> new JobOfferNotFoundException("No Joboffer with this id"));
        Skill skill = skillRepo.findById(skillID)
                .orElseThrow(() -> new SkillNotFoundException("No skill with this id"));
        JobOfferSkill jobOfferSkill;
        jobOfferSkill = new JobOfferSkill();
        jobOfferSkill.setJobOffer(jobOffer);
        jobOfferSkill.setSkill(skill);
        jobOfferSkillRepo.save(jobOfferSkill);
        return jobOfferSkill;
    }

    @Override
    public List<JobOffer> readJobOffers() throws IOException, InvalidFormatException {
        return FileReaderToList.readFromExcelJobOffers("data.xlsx", jobOfferRepo, skillRepo,jobOfferSkillRepo);
    }
}
