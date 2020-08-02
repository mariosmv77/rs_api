package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.ApplicantSkill;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;

/**
 * Implements all the REST verbs for the entity applicant
 */
public interface ApplicantService {
    /**
     * Required request parameters for the Applicant entity
     * @param firstName
     * @param lastName
     * @param address
     * @param region
     * @param email
     * @return the aplicant(s) with the given field
     * and all aplicants if you don't give any of the parameters
     */
    List<Applicant> getApplicants(String firstName, String lastName, String address, String region,
                                  String email);

    /**
     * This method add a new applicant to applicant table on DB
     * @param applicant
     * @return the saved applicant
     * @throws ApplicantCreationException
     */
    Applicant addApplicant(Applicant applicant) throws ApplicantCreationException;

    /**
     * This method updates the fields of an applicant with a specific id
     * @param applicant
     * @param applicantId
     * @return applicantInDb
     * @throws ApplicantNotFoundException
     */
    Applicant updateApplicant(Applicant applicant, long applicantId) throws ApplicantNotFoundException;

    /**
     * This method change the value of field isClosed to true
     * @param applicantId
     * @return
     * @throws ApplicantNotFoundException
     * @throws ApplicantAlreadyClosed
     */
    Applicant deleteApplicant(long applicantId) throws ApplicantNotFoundException, ApplicantAlreadyClosed;

    /**
     * @param applicantId
     * @return the applicant with the specific id
     * @throws ApplicantNotFoundException
     */
    Applicant getApplicant(long applicantId) throws ApplicantNotFoundException;

    /**
     * adds a new skill to the applicantSkill<list> of a specific applicant
     * @param applicantId
     * @param skillId
     * @return
     * @throws ApplicantNotFoundException
     * @throws SkillNotFoundException
     */
    ApplicantSkill addSkillToApplicant(long applicantId, long skillId)
            throws ApplicantNotFoundException, SkillNotFoundException;

    /**
     * @return writes to the database table applicants from a xls file
     * @throws IOException
     * @throws InvalidFormatException
     */
    List<Applicant> readApplicants() throws IOException, InvalidFormatException;
}
