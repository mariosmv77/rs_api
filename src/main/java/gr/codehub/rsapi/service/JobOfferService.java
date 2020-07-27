package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.JobOfferSkill;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;

public interface JobOfferService {
    List<JobOffer> getJobOffers();
    JobOffer addJobOffer(JobOffer jobOffer);
    JobOffer updateJobOffer(JobOffer jobOffer, long jobOfferId) throws JobOfferNotFoundException;
    boolean deleteJobOffer(long jobOfferIndex);
    JobOffer getJobOffer(long jobOfferId) throws JobOfferNotFoundException;
    List<JobOffer> getSelectedJobOffers(String criterion); // prepei na doume sti periptosi p einai to Date ti tha kanoume
    JobOfferSkill addSkillToJobOffer(long jobOfferId, long skillId)
            throws JobOfferNotFoundException, SkillNotFoundException;
    List<JobOffer> readJobOffers() throws IOException, InvalidFormatException;
}
