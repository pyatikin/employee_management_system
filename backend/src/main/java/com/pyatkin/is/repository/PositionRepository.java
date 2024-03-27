package com.pyatkin.is.repository;

import com.pyatkin.is.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
