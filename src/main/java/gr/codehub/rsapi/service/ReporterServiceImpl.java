package gr.codehub.rsapi.service;

import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReporterServiceImpl implements ReporterService{

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
        return applicantSkillRepo.findMostOfferedSkill();

    }

    @Override
    public List<SurveyStatistics> getMostPopularRequestedSkills() {
        return jobOfferSkillRepo.findMostRequestedSkills();
    }

    @Override
    public List<Match> getRecentFinalizedMatch() {
        return matchRepo.getRecentFinalizedMatch();
    }
    @Override
    public List<Match> getMatches() {
        return matchRepo.findAll();
    }

    @Override
    public List<SurveyStatistics> getByMonth() {
        return matchRepo.getByMonth();
    }

    @Override
    public void getReports(String nameOfXlsFile) {

    }
    @Override
    public HashSet<Skill> getNotMatchSkills() {
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


        return notMatchedSkills;
    }


}
