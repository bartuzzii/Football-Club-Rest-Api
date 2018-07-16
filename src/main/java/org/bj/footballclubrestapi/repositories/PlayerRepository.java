package org.bj.footballclubrestapi.repositories;

import org.bj.footballclubrestapi.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Integer> {
}
