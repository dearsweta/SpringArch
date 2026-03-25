package com.spring.internal_working.internal_work.repositories;

import com.spring.internal_working.internal_work.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {


}