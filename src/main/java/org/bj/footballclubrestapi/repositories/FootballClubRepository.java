package org.bj.footballclubrestapi.repositories;

import org.bj.footballclubrestapi.models.FootballClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballClubRepository extends JpaRepository<FootballClub, Integer> {
}
