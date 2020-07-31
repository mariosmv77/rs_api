package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.Match;

import java.util.List;


public interface MatchService {
    List<Match> getMatches();
    List <Match> addPartiallyMatch(long jobOfferId) // edo esbisa to sketo addMatch kai ebala tis 3 periptoseis twn matches
            throws JobOfferNotFoundException, JobOfferAlreadyClosed;
    Match addManuallyMatch(long jobOfferId,long applicantId )
            throws ApplicantNotFoundException, JobOfferNotFoundException, JobOfferAlreadyClosed, ApplicantAlreadyClosed;
    List <Match> addAutomaticMatch(long jobOfferId)
            throws JobOfferNotFoundException, JobOfferAlreadyClosed;
    Match updateMatch(Match match, long matchId)
            throws MatchNotFoundException;
    Match getMatch(long matchId)
            throws MatchNotFoundException;
    boolean finalizeMatch(long matchId) throws MatchNotFoundException, MatchAlreadyFinalized, JobOfferAlreadyClosed, ApplicantAlreadyClosed;
}
