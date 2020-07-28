package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.JobOfferRepo;
import gr.codehub.rsapi.repository.JobOfferSkillRepo;
import gr.codehub.rsapi.repository.SkillRepo;
import gr.codehub.rsapi.utility.FileReaderToList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class JobOfferServiceImpl implements JobOfferService{
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
    public JobOffer addJobOffer(JobOffer jobOffer) {
        return jobOfferRepo.save(jobOffer);
    }

    @Override
    public JobOffer updateJobOffer(JobOffer jobOffer, long jobOfferId) throws JobOfferNotFoundException {
        JobOffer jobOfferinDB = jobOfferRepo.findById(jobOfferId)
                .orElseThrow(() -> new JobOfferNotFoundException("No exist offer with this id"));

        jobOfferinDB.setClosed(true);
        jobOfferRepo.save(jobOfferinDB);
        return jobOfferinDB;
    }

    @Override
    public boolean deleteJobOffer(long jobOfferIndex) {
        return true;
    }

    @Override
    public JobOffer getJobOffer(long jobOfferId) throws JobOfferNotFoundException {
        Optional<JobOffer> oJobOffer = jobOfferRepo.findById(jobOfferId);
        if (oJobOffer.isPresent())
            return oJobOffer.get();
        else throw new JobOfferNotFoundException("No Joboffer with this id");
    }

    @Override
    public List<JobOffer> getSelectedJobOffers(String criterion) {
        return null;
    }

    @Override
    public JobOfferSkill addSkillToJobOffer(long jobOfferId, long skillID)
            throws JobOfferNotFoundException, SkillNotFoundException {
        JobOffer jobOffer = jobOfferRepo.findById(jobOfferId)
                .orElseThrow(()-> new JobOfferNotFoundException("No Joboffer with this id"));
        Skill skill= skillRepo.findById(skillID)
                .orElseThrow(()-> new SkillNotFoundException("No skill with this id"));
        JobOfferSkill jobOfferSkill;
        jobOfferSkill= new JobOfferSkill();
        jobOfferSkill.setJobOffer(jobOffer);
        jobOfferSkill.setSkill(skill);
        jobOfferSkillRepo.save(jobOfferSkill);
//        jobOffer.getJobOfferSkills().add(jobOfferSkill);
//        jobOfferRepo.save(jobOffer);
        return jobOfferSkill;
    }

    @Override
    public List<JobOffer> readJobOffers() throws IOException, InvalidFormatException {
        return FileReaderToList.readFromExcelJobOffers("data.xlsx",jobOfferRepo);
    }
}
