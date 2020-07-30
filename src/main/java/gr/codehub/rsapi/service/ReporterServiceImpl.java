package gr.codehub.rsapi.service;

import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class ReporterServiceImpl implements ReporterService {

    private JobOfferRepo jobOfferRepo;
    private ApplicantRepo applicantRepo;
    private JobOfferSkillRepo jobOfferSkillRepo;
    private ApplicantSkillRepo applicantSkillRepo;
    private MatchRepo matchRepo;

    @Autowired
    public ReporterServiceImpl(JobOfferRepo jobOfferRepo, ApplicantRepo applicantRepo, ApplicantSkillRepo applicantSkillRepo, JobOfferSkillRepo jobOfferSkillRepo, MatchRepo matchRepo) {
        this.jobOfferRepo = jobOfferRepo;
        this.applicantRepo = applicantRepo;
        this.applicantSkillRepo = applicantSkillRepo;
        this.jobOfferSkillRepo = jobOfferSkillRepo;
        this.matchRepo = matchRepo;
    }

    @Override
    public List<SurveyStatistics> getMostPopularOfferedSkills() {
        log.info("\nEnter getMostPopularOfferedSkills method");
        log.info("\nExits getMostPopularOfferedSkills method after " +
                " returning the most popular Offered Skills by Applicants");

        return applicantSkillRepo.findMostOfferedSkill();

    }

    @Override
    public List<SurveyStatistics> getMostPopularRequestedSkills() {
        log.info("\nEnter getMostPopularRequestedSkills method");
        log.info("\nExits getMostPopularRequestedSkills method after " +
                " returning the most popular Requested Skills by JobOffers");
        return jobOfferSkillRepo.findMostRequestedSkills();
    }

    @Override
    public List<Match> getRecentFinalizedMatch() {
        log.info("\nEnter getRecentFinalizedMatch method");
        log.info("\nExits getRecentFinalizedMatch method after " +
                " returning most recent finalized matches");

        return matchRepo.getRecentFinalizedMatch();
    }

    @Override
    public List<Match> getMatches() {
        log.info("\nEnter getMatches method");
        log.info("\nExits getMatches and returns  the proposed matches applicants-offers");
        return matchRepo.findAll();
    }

    @Override
    public List<SurveyStatistics> getByMonth() {

        log.info("\nEnter getByMonth method");
        log.info("\nExits getByMonth method after " +
                "providing report about finalized matches by month");
        return matchRepo.getByMonth();
    }

    @Override
    public void getReports(String nameOfXlsFile) {

    }

    @Override
    public HashSet<Skill> getNotMatchSkills() {
        log.info("\nEnter getNotMatchSkills method");

        List<ApplicantSkill> applicantSkills = applicantSkillRepo.findAll();
        List<JobOfferSkill> jobOfferSkills = jobOfferSkillRepo.findAll();
        HashSet<Skill> notMatchedSkills = new HashSet<Skill>();

        for (JobOfferSkill jobofferSkill : jobOfferSkills) {
            boolean match = false;
            for (ApplicantSkill applicantSkill : applicantSkills) {
                if (jobofferSkill.getSkill().getId() == applicantSkill.getSkill().getId()) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                notMatchedSkills.add(jobofferSkill.getSkill());
            }
        }
        log.info("\nExits getNotMatchSkills method after " +
                "providing report about the skills that Applicants dont have, but Job offers require");

        return notMatchedSkills;
    }


}
