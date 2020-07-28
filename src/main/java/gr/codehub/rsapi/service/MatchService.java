package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.MatchNotFoundException;
import gr.codehub.rsapi.model.Match;

import java.util.List;


public interface MatchService {
    List<Match> getMatches();
    List<Match> addPartiallyMatch(long jobOfferId) throws JobOfferNotFoundException;

    Match addManuallyMatch(long jobOfferId,long applicantId )
            throws ApplicantNotFoundException, JobOfferNotFoundException;
    Match addAutomaticMatch(long jobOfferId)
            throws JobOfferNotFoundException;
    Match updateMatch(Match match, long matchId)
            throws MatchNotFoundException;
    boolean deleteMatch(long matchIndex);
    Match getMatch(long matchId)
            throws MatchNotFoundException;
}
