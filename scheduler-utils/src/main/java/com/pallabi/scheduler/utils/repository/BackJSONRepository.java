package com.pallabi.scheduler.utils.repository;


import com.pallabi.scheduler.utils.model.dto.BackJsonDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackJSONRepository extends CrudRepository<BackJsonDTO, Long> {

}
