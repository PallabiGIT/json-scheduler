package com.pallabi.scheduler.service;

import com.pallabi.scheduler.model.dto.BackJsonDTO;
import com.pallabi.scheduler.model.dto.CustomerDTO;
import com.pallabi.scheduler.repository.BackJSONRepository;
import com.pallabi.scheduler.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import org.joda.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class BackUpJSONService {
    @Autowired
    private BackJSONRepository backJSONRepository;

    public void backUpJSON(BackJsonDTO backJsonDTO) {
        backJSONRepository.save(backJsonDTO);
        log.info("Complex json successfully saved in DB");
    }

    public long getBackupJSONCount() {
        long count = backJSONRepository.findAll().spliterator().getExactSizeIfKnown();
        log.info("Complex json count in DB  - {}", count);
        return count;
    }

    public Optional<BackJsonDTO> getBackupJSONBYTimeStamp(java.util.Date date) {
        return StreamSupport.stream(backJSONRepository.findAll().spliterator(), false)
                .filter(backJsonDTO -> compareDate(date, backJsonDTO.getInsertionTime()))
                .findFirst();
    }

    protected boolean compareDate(java.util.Date incomingDate, Timestamp timestamp) {
        DateTime dateTime = new DateTime(timestamp.getTime());
        int monthFromDB = dateTime.getMonthOfYear();
        int dayFromDB = dateTime.getDayOfMonth();
        int yearFromDB = dateTime.getYear();

        Calendar cal = Calendar.getInstance();
        cal.set( Calendar.YEAR, yearFromDB);
        cal.set( Calendar.MONTH, monthFromDB);
        cal.set( Calendar.DATE, dayFromDB);

        LocalDate localDate = new LocalDate(yearFromDB, monthFromDB, dayFromDB);
        Date fromDBDate = localDate.toDate();

        int status=fromDBDate.compareTo(incomingDate);

        return status == 0;
    }
}