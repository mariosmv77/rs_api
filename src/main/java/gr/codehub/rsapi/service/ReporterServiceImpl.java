package gr.codehub.rsapi.service;

import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.*;
import gr.codehub.rsapi.utility.SurveyMonth;
import gr.codehub.rsapi.utility.SurveyNotMatchSkill;
import gr.codehub.rsapi.utility.SurveySkills;
import gr.codehub.rsapi.utility.SurveyWeek;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<SurveySkills> getMostPopularOfferedSkills() {
        return applicantSkillRepo.findMostOfferedSkill();
    }

    @Override
    public List<SurveySkills> getMostPopularRequestedSkills() {
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
    public List<SurveyMonth> getByMonth() {
        return matchRepo.getByMonth();
    }

    @Override
    public List<SurveyWeek> getByWeek() {
        return matchRepo.getByWeek();
    }

    @Override
    public List<SurveyNotMatchSkill> getNotMatchedSkills() {
        return matchRepo.getNotMatchedSkills();
    }

}
