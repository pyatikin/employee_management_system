package com.test.react.reacttestproject.repository;

import com.test.react.reacttestproject.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * todo Document type ClientRepository
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}
