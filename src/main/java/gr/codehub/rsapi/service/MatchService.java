package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.MatchNotFoundException;
import gr.codehub.rsapi.model.Match;

import java.util.List;


public interface MatchService {
    List<Match> getMatches();
    List <Match> addPartiallyMatch(long jobOfferId) // edo esbisa to sketo addMatch kai ebala tis 3 periptoseis twn matches
            throws JobOfferNotFoundException;
    Match addManuallyMatch(long jobOfferId,long applicantId )
            throws ApplicantNotFoundException, JobOfferNotFoundException;
    List <Match> addAutomaticMatch(long jobOfferId)
            throws JobOfferNotFoundException;
    Match updateMatch(Match match, long matchId)
            throws MatchNotFoundException;
    boolean deleteMatch(long matchIndex);
    Match getMatch(long matchId)
            throws MatchNotFoundException;
    boolean finalizeMatch(long matchId) throws MatchNotFoundException;
}
