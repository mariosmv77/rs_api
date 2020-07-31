package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.ApplicantSkill;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface ApplicantService {
    List<Applicant> getApplicants();
    List<Applicant> getApplicantsByCriteria(String firstName, String lastName, String address, String region,
                                  String email, String dob, String isClosed);

    Applicant addApplicant(Applicant applicant) throws ApplicantCreationException;
    Applicant updateApplicant(Applicant applicant, long applicantId) throws ApplicantNotFoundException;
    Applicant deleteApplicant(long applicantIndex) throws ApplicantNotFoundException, ApplicantAlreadyClosed;
    Applicant getApplicant(long applicantId) throws ApplicantNotFoundException;
    List<Applicant> getSelectedApplicants(String dob,
                                          String region,
                                          String name,
                                          Long ApplicantSkillId) throws ApplicantNotFoundException, ParseException;
    ApplicantSkill addSkillToApplicant(long applicantId, long skillId)
            throws ApplicantNotFoundException, SkillNotFoundException;
    List<Applicant> readApplicants() throws IOException, InvalidFormatException;
}
