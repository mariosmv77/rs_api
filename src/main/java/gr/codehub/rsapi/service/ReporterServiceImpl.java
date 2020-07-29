package gr.codehub.rsapi.service;

import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.ApplicantSkill;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporterServiceImpl implements ReporterService{

    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private ApplicantRepo applicantRepo;
    @Autowired
    private ApplicantSkillRepo applicantSkillRepo;
    @Autowired
    private JobOfferSkillRepo jobOfferSkillRepo;

    @Override
    public List<SurveyStatistics> getMostPopularOfferedSkills() {
        return applicantSkillRepo.findMostOfferedSkill();

    }

    @Override
    public List<SurveyStatistics> getMostPopularRequestedSkills() {
        return jobOfferSkillRepo.findMostRequestedSkills();
    }

    @Override
    public List<Skill> getNotMatchSkill() {
        return null;
    }

    @Override
    public List<Match> getMatches() {
        return matchRepo.findAll();
    }

    @Override
    public List<Skill> getRecentFinalizedMatch() {
        return null;
    }

    @Override
    public List<SurveyStatistics> getByMonth() {
        return matchRepo.getByMonth();
    }

    @Override
    public void getReports(String nameOfXlsFile) {

    }

}
