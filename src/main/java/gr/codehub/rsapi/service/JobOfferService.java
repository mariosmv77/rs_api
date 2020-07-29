package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.JobOfferSkill;
import gr.codehub.rsapi.model.Skill;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface JobOfferService {
    List<JobOffer> getJobOffers();

    JobOffer addJobOffer(JobOffer jobOffer);

    JobOffer updateJobOffer(JobOffer jobOffer, long jobOfferId) throws JobOfferNotFoundException;

    JobOffer deleteJobOffer(long jobOfferIndex) throws JobOfferNotFoundException;

    JobOffer getJobOffer(long jobOfferId) throws JobOfferNotFoundException;

    List<JobOffer> getSelectedJobOffers(String offerDate,
                                        String region,
                                        String name,
                                        Long jobOfferSkillId) throws JobOfferNotFoundException, ParseException;

    JobOfferSkill addSkillToJobOffer(long jobOfferId, long skillId)
            throws JobOfferNotFoundException, SkillNotFoundException;

    List<JobOffer> readJobOffers() throws IOException, InvalidFormatException;
}
