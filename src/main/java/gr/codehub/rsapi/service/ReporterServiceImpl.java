package gr.codehub.rsapi.service;

import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReporterServiceImpl implements  ReporterService {
    @Autowired
    private MatchRepo matchRepo;

    @Override
    public List<Skill> getMostPopular() {
        return null;
    }

    @Override
    public List<Skill> getNotMatchSkill() {
        return null;
    }

    @Override
    public List<Skill> getMatches() {
        return null;
    }

    @Override
    public List<Match> getRecentFinalizedMatch() {
        return matchRepo.getRecentFinalizedMatch();
    }

    @Override
    public void getReports(String nameOfXlsFile) {

    }

    @Override
    public List<Match> getByMonth() {
        return matchRepo.getByMonth();
    }
}
