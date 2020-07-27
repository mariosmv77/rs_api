package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepo extends JpaRepository <Match, Long> {
}
