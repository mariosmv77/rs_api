package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.JobOfferAlreadyClosed;
import gr.codehub.rsapi.exception.JobOfferCreationException;
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
    /**
     * Finds all jobOffers from jobOffer table in DB
     * @return {@code List<JobOffer>}
     */
    List<JobOffer> getJobOffers();

    /**
     * saves a new jobOffer to the jobOffer table of the database
     * @param jobOffer
     * @return jobOffer object
     * @throws JobOfferCreationException
     */
    JobOffer addJobOffer(JobOffer jobOffer) throws JobOfferCreationException;

    /**
     * Updates the jobOffer with the specified jobOfferId with a new jobOffer
     * @param jobOffer
     * @param jobOfferId
     * @return jobOffer
     * @throws JobOfferNotFoundException
     */
    JobOffer updateJobOffer(JobOffer jobOffer, long jobOfferId) throws JobOfferNotFoundException;

    /**
     * Set isclosed field true on a jobOffer
     * @param jobOfferIndex
     * @return true on success
     * @throws JobOfferNotFoundException
     * @throws JobOfferAlreadyClosed
     */
    JobOffer deleteJobOffer(long jobOfferIndex) throws JobOfferNotFoundException, JobOfferAlreadyClosed;

    /**
     * @param jobOfferId
     * @return JobOffer
     * @throws JobOfferNotFoundException
     */
    JobOffer getJobOffer(long jobOfferId) throws JobOfferNotFoundException;

    /**
     * finds jobOffers with criteria that are passed as a parameter
     * @param offerDate
     * @param region
     * @param name
     * @param jobOfferSkillId
     * @return {@code List<JobOffer>}
     * @throws JobOfferNotFoundException
     * @throws ParseException
     */
    List<JobOffer> getSelectedJobOffers(String offerDate,
                                        String region,
                                        String name,
                                        Long jobOfferSkillId) throws JobOfferNotFoundException, ParseException;

    /**
     * @param jobOfferId
     * @param skillId
     * @return
     * @throws JobOfferNotFoundException
     * @throws SkillNotFoundException
     */
    JobOfferSkill addSkillToJobOffer(long jobOfferId, long skillId)
            throws JobOfferNotFoundException, SkillNotFoundException;

    /**
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    List<JobOffer> readJobOffers() throws IOException, InvalidFormatException;
}
