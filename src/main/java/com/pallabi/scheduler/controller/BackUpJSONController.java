package com.pallabi.scheduler.controller;

import com.pallabi.scheduler.model.Customer;
import com.pallabi.scheduler.model.dto.BackJsonDTO;
import com.pallabi.scheduler.model.dto.CustomerDTO;
import com.pallabi.scheduler.service.BackUpJSONService;
import com.pallabi.scheduler.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController()
public class BackUpJSONController {
    @Autowired
    private BackUpJSONService backUpJSONService;

    @RequestMapping(value = "/backUPJSON/count", method = RequestMethod.GET)
    public ResponseEntity<Object> getBackupJSONCount(){
       long count = backUpJSONService.getBackupJSONCount();
        return new ResponseEntity<>("Count is - " + count, HttpStatus.OK);
    }

    @RequestMapping(value = "/backUPJSON/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getBackupJSONByDate(@PathVariable  @DateTimeFormat(pattern="yyyy-MM-dd") Date date){
        Optional<BackJsonDTO> backJsonDTOOptional = backUpJSONService.getBackupJSONBYTimeStamp(date);
        if(backJsonDTOOptional.isPresent()){
            String json = new String(backJsonDTOOptional.get().getBackUpJSON(), StandardCharsets.UTF_8);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Backup JSON not found for the date " + date, HttpStatus.NOT_FOUND);
        }
    }
}
