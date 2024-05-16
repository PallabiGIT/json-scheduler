package com.pallabi.scheduler.utils.service;

import com.pallabi.scheduler.utils.model.dto.BackJsonDTO;
import com.pallabi.scheduler.utils.repository.BackJSONRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    public List<BackJsonDTO> getAllBackupJSON() {
       return StreamSupport
                .stream(backJSONRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
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