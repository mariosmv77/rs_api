package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.MatchNotFoundException;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.ApplicantRepo;
import gr.codehub.rsapi.repository.JobOfferRepo;
import gr.codehub.rsapi.repository.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    private MatchRepo matchRepo;
    private JobOfferRepo jobOfferRepo;
    private ApplicantRepo applicantRepo;

    @Autowired
    public MatchServiceImpl(MatchRepo matchRepo, JobOfferRepo jobOfferRepo, ApplicantRepo applicantRepo) {
        this.matchRepo = matchRepo;
        this.jobOfferRepo = jobOfferRepo;
        this.applicantRepo = applicantRepo;
    }

    @Override
    public List<Match> getMatches() {
        return matchRepo.findAll();
    }

    @Override
    public List<Match> addPartiallyMatch(long jobOfferId) throws JobOfferNotFoundException {
        JobOffer jobOfferInDb = jobOfferRepo.findById(jobOfferId)
                .orElseThrow(
                        () -> new JobOfferNotFoundException("not such JobOffer in DB"));
        List<Skill> jobskills = new ArrayList<Skill>();
        List<Match> matchesTemp = new ArrayList<Match>();
        for (JobOfferSkill jobOfferSkill : jobOfferInDb.getJobOfferSkills()) {
            jobskills.add(jobOfferSkill.getSkill());
        }
        List<Applicant> applicants = applicantRepo.findAll();
        for (Applicant applicant : applicants) {
            Match newMatch = new Match();
            List<ApplicantSkill> applicantSkillList = applicant.getApplicantSkills();
            List<Skill> appskills = new ArrayList<Skill>();
            for (ApplicantSkill applicantSkill : applicant.getApplicantSkills()) {
                appskills.add(applicantSkill.getSkill());
            }
            if (!applicant.isClosed() && Collections.indexOfSubList(jobskills, appskills) != -1) {
                newMatch.setJobOffer(jobOfferInDb);
                newMatch.setApplicant(applicant);
                newMatch.setT(Match.type.PARTIAL);
                matchesTemp.add(newMatch);
                matchRepo.save(newMatch);
            }
        }
        return matchesTemp;
    }

    @Override
    public Match addManuallyMatch(long jobOfferId, long applicantId) throws ApplicantNotFoundException, JobOfferNotFoundException {

        JobOffer jobOfferInDb = jobOfferRepo.findById(jobOfferId)
                .orElseThrow(
                        () -> new JobOfferNotFoundException("not such JobOffer in DB"));

        Applicant applicantInDb = applicantRepo.findById(applicantId)
                .orElseThrow(
                        () -> new ApplicantNotFoundException("not such job Applicant in DB"));
        Match newMatch = new Match();
        newMatch.setApplicant(applicantInDb);
        newMatch.setJobOffer(jobOfferInDb);
        newMatch.setT(Match.type.MANUAL);
        matchRepo.save(newMatch);
        return newMatch;
    }

    @Override
    public Match addAutomaticMatch(long jobOfferId) throws JobOfferNotFoundException {
        JobOffer jobOfferInDb = jobOfferRepo.findById(jobOfferId)
                .orElseThrow(
                        () -> new JobOfferNotFoundException("not such JobOffer in DB"));

        List<Applicant> applicants = applicantRepo.findAll();
        Match newMatch = new Match();
        for (Applicant applicant : applicants) {
            if (!applicant.isClosed() && applicant.getApplicantSkills().containsAll(jobOfferInDb.getJobOfferSkills())) {
                newMatch.setJobOffer(jobOfferInDb);
                newMatch.setApplicant(applicant);
                newMatch.setT(Match.type.AUTO);
                matchRepo.save(newMatch);
                return newMatch;
            } else
                return null;
        }
        return null;
    }

    @Override
    public Match updateMatch(Match match, long matchId) throws MatchNotFoundException {
        return null;
    }

    @Override
    public boolean deleteMatch(long matchIndex) {
        matchRepo.deleteById(matchIndex);
        return true;
    }

    @Override
    public Match getMatch(long matchId) throws MatchNotFoundException {
        Optional<Match> oMatch =
                matchRepo.findById(matchId);
        if (oMatch.isPresent()) return oMatch.get();
        else throw new MatchNotFoundException("Match not found!");
    }
}
