package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.Match;

import java.util.List;


public interface MatchService {
    /**
     * finds all mathes tha are stored in match table on DB
     * @return {@code List<Match>}
     */
    List<Match> getMatches();

    /**
     * Matches the jobOffer with the specified id
     * with applicants that have at least one common skill
     * @param jobOfferId
     * @return {@code List <Match>}
     * @throws JobOfferNotFoundException
     * @throws JobOfferAlreadyClosed
     */
    List <Match> addPartiallyMatch(long jobOfferId)
            throws JobOfferNotFoundException, JobOfferAlreadyClosed;

    /**
     * Makes a new match to the specified jobOffer ID with the specified applicant
     * with no check on common skills
     * @param jobOfferId
     * @param applicantId
     * @return {@code Match}
     * @throws ApplicantNotFoundException
     * @throws JobOfferNotFoundException
     * @throws JobOfferAlreadyClosed
     * @throws ApplicantAlreadyClosed
     */
    Match addManuallyMatch(long jobOfferId,long applicantId )
            throws ApplicantNotFoundException, JobOfferNotFoundException, JobOfferAlreadyClosed, ApplicantAlreadyClosed;

    /**
     * Matches all the applicants that have at least all jobOffer skills
     * that are required
     * @param jobOfferId
     * @return {@code List <Match>}
     * @throws JobOfferNotFoundException
     * @throws JobOfferAlreadyClosed
     */
    List <Match> addAutomaticMatch(long jobOfferId)
            throws JobOfferNotFoundException, JobOfferAlreadyClosed;

    /**
     * @param match
     * @param matchId
     * @return
     * @throws MatchNotFoundException
     */
    Match updateMatch(Match match, long matchId)
            throws MatchNotFoundException;

    /**
     * @param matchId
     * @return
     * @throws MatchNotFoundException
     */
    Match getMatch(long matchId) throws MatchNotFoundException;

    /**
     * Set isFinalized field to true when a match is finalized
     * @param matchId
     * @return
     * @throws MatchNotFoundException
     * @throws MatchAlreadyFinalized
     * @throws JobOfferAlreadyClosed
     * @throws ApplicantAlreadyClosed
     */
    boolean finalizeMatch(long matchId) throws MatchNotFoundException, MatchAlreadyFinalized, JobOfferAlreadyClosed, ApplicantAlreadyClosed;
}
