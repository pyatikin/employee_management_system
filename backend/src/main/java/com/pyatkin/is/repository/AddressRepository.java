package com.pyatkin.is.repository;

import com.pyatkin.is.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
