package com.spring.internal_working.internal_work.repositories;

import com.spring.internal_working.internal_work.entities.Addresses;
import org.springframework.data.repository.CrudRepository;

public interface AddressesRepository extends CrudRepository<Addresses, Long> {
}