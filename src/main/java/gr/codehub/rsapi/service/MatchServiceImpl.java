package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.ApplicantRepo;
import gr.codehub.rsapi.repository.JobOfferRepo;
import gr.codehub.rsapi.repository.MatchRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
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
        log.info("\nEnter getMatches method");
        log.info("\nExits getMatches method and returns all the matches");

        return matchRepo.findAll();
    }


    public List<Match> addPartiallyMatch(long jobOfferId) throws JobOfferNotFoundException, JobOfferAlreadyClosed {
        log.info("\nEnter addPartiallyMatch method ");

        JobOffer jobOfferInDb = jobOfferRepo.findById(jobOfferId)
                .orElseThrow(
                        () -> new JobOfferNotFoundException("not such JobOffer in DB"));
        if(jobOfferInDb.isClosed()){
            throw new JobOfferAlreadyClosed("Job offer is already Closed");
        }
        List<Skill> jobskills = new ArrayList<Skill>();
        List<Match> matchesTemp = new ArrayList<Match>();
        for (JobOfferSkill jobOfferSkill : jobOfferInDb.getJobOfferSkills()) {
            jobskills.add(jobOfferSkill.getSkill());
        }
        List<Applicant> applicants = applicantRepo.findAll();
       // List<Match> matches = matchRepo.findAll();
        for (Applicant applicant : applicants) {
            boolean alreadyMatched = false;
            Match newMatch = new Match();
            //List<ApplicantSkill> applicantSkillList = applicant.getApplicantSkills();
            List<Skill> appskills = new ArrayList<Skill>();
            List<Match> appmatches = applicant.getMatches();
            for (Match match : appmatches) {
                if ( match.getJobOffer().getId()== jobOfferId ){
                    alreadyMatched = true;
                }
            }
            for (ApplicantSkill applicantSkill : applicant.getApplicantSkills()) {
                appskills.add(applicantSkill.getSkill());
            }
            if (!applicant.isClosed()&& !alreadyMatched && !(appskills.containsAll(jobskills))) {
                for (Skill skill : appskills)
                {
                    if (jobskills.contains(skill)){
                        newMatch.setJobOffer(jobOfferInDb);
                        newMatch.setApplicant(applicant);
                        newMatch.setType(Match.type.PARTIAL);
                        matchesTemp.add(newMatch);
                        matchRepo.save(newMatch);
                        break;
                    }
                }
            }
        }
        log.info("\nExits addPartiallyMatch method and add a match for jobOfferId : " + jobOfferId);

        return matchesTemp;
    }

    @Override
    public List<Match> addAutomaticMatch(long jobOfferId) throws JobOfferNotFoundException,JobOfferAlreadyClosed {
        log.info("\nEnter addAutomaticMatch method ");

        JobOffer jobOfferInDb = jobOfferRepo.findById(jobOfferId)
                .orElseThrow(
                        () -> new JobOfferNotFoundException("not such JobOffer in DB"));
        if (jobOfferInDb.isClosed()) {
            throw new JobOfferAlreadyClosed("JobOffer is already closed");
        }
        List <Skill> jobskills = new ArrayList<Skill>();
        List <Match> matchesTemp = new ArrayList<Match>();
        //List <Match> matches = matchRepo.findAll();
        for(JobOfferSkill jobOfferSkill: jobOfferInDb.getJobOfferSkills()){
            jobskills.add(jobOfferSkill.getSkill());
        }
        List<Applicant> applicants = applicantRepo.findAll();

        for (Applicant applicant : applicants) {
            boolean alreadymatched = false;
            Match newMatch = new Match();
          //  List<ApplicantSkill> applicantSkillList= applicant.getApplicantSkills();
            List<Skill> appskills = new ArrayList<Skill>();

            for(ApplicantSkill applicantSkill: applicant.getApplicantSkills()){
                appskills.add(applicantSkill.getSkill());
            }
            List <Match> appmatches = applicant.getMatches();

            for(Match match : appmatches){
                if( match.getJobOffer().getId() == jobOfferId)
                    alreadymatched = true;
            }
            if (!applicant.isClosed() &&  appskills.containsAll(jobskills) && !alreadymatched) {
                newMatch.setJobOffer(jobOfferInDb);
                newMatch.setApplicant(applicant);
                newMatch.setType(Match.type.AUTO);
                matchesTemp.add(newMatch);
                matchRepo.save(newMatch);

            }
        }
        log.info("\nExits addAutomaticMatch method and add a match for jobOfferId : " + jobOfferId);

        return matchesTemp;
    }

    @Override
    public Match addManuallyMatch(long jobOfferId, long applicantId) throws ApplicantNotFoundException, JobOfferNotFoundException, JobOfferAlreadyClosed, ApplicantAlreadyClosed {
        log.info("\nEnter addManuallyMatch method ");

        JobOffer jobOfferInDb = jobOfferRepo.findById(jobOfferId)
                .orElseThrow(
                        () -> new JobOfferNotFoundException("not such JobOffer in DB"));

        Applicant applicantInDb = applicantRepo.findById(applicantId)
                .orElseThrow(
                        () -> new ApplicantNotFoundException("not such job Applicant in DB"));
        if(jobOfferInDb.isClosed()){
            throw new JobOfferAlreadyClosed("JobOffer is already closed");
        }
        if(applicantInDb.isClosed()){
            throw new ApplicantAlreadyClosed("Applicant is already closed");
        }
        Match newMatch = new Match();
        newMatch.setApplicant(applicantInDb);
        newMatch.setJobOffer(jobOfferInDb);
        newMatch.setType(Match.type.MANUAL);
        matchRepo.save(newMatch);
        log.info("\nExits addManuallyMatch method and add a match for jobOfferId : " + jobOfferId +
                " for the applicant with id: " + applicantId);
        return newMatch;
    }



    @Override
    public Match updateMatch(Match match, long matchId) throws MatchNotFoundException {
        return null;
    }

 /*   @Override
    public boolean deleteMatch(long matchIndex) {
        log.info("\nEnter deleteMatch method");
        log.info("\nExits deleteMatch method, after deleting a manual match");

        matchRepo.deleteById(matchIndex);
        return true;
    }*/

    @Override
    public Match getMatch(long matchId) throws MatchNotFoundException {
        log.info("\nEnter getMatch method ");

        Optional<Match> oMatch =
                matchRepo.findById(matchId);
        if (oMatch.isPresent()) {
            log.info("\nExits getMatch method, after get a match with match Id: " + matchId);
            return oMatch.get();
        } else throw new MatchNotFoundException("Match not found!");
    }

    @Override
    public boolean finalizeMatch(long matchId) throws MatchNotFoundException, MatchAlreadyFinalized, JobOfferAlreadyClosed, ApplicantAlreadyClosed {
        log.info("\nEnter finalizeMatch method ");

        Match match;
        Optional<Match> optionalMatch = matchRepo.findById(matchId);
        if(optionalMatch.isPresent()) {
            match = optionalMatch.get();
            if (match.isFinalized()==false) {
                if(match.getJobOffer().isClosed()){
                    throw new JobOfferAlreadyClosed("Job is already closed");
                }

                if(match.getApplicant().isClosed()){
                    throw new ApplicantAlreadyClosed("Applicant is already closed");
                }
                match.setFinalized(true);
                match.setDof(LocalDateTime.now());
                match.getApplicant().setClosed(true);
                match.getJobOffer().setClosed(true);
                matchRepo.save(match);
                log.info("\nExits finalizeMatch method, after finalizing a match with match Id: " + matchId);

                return true;
            }else
                throw new MatchAlreadyFinalized("Match is already finalized");
        }
        else throw new MatchNotFoundException("Match not found");
    }

    Match addManuallyMatchQuery(long jobOfferId, long applicantId)
            throws ApplicantNotFoundException, JobOfferNotFoundException, JobOfferAlreadyClosed, ApplicantAlreadyClosed {

        return null ;
    }
}
