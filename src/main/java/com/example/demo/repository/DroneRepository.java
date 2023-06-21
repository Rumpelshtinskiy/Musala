package com.example.demo.repository;

import com.example.demo.model.Drone;
import com.example.demo.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface DroneRepository extends JpaRepository<Drone, UUID> {

    Collection<Drone> findByStateIn(Collection<State> states);
}
