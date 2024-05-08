package com.pallabi.scheduler.repository;


import com.pallabi.scheduler.model.dto.BackJsonDTO;
import com.pallabi.scheduler.model.dto.CustomerDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackJSONRepository extends CrudRepository<BackJsonDTO, Long> {

}
