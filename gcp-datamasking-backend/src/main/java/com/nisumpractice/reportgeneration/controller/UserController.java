package com.nisumpractice.reportgeneration.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.JobStatistics;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import com.lowagie.text.DocumentException;
import com.nisumpractice.reportgeneration.model.User;
import com.nisumpractice.reportgeneration.model.UserDto;
import com.nisumpractice.reportgeneration.repository.UserRepository;
import com.nisumpractice.reportgeneration.util.PDFExporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.extern.java.Log;

@RestController
@Log
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private BigQuery bigQuery;

    @PostMapping(value = "/createcard")
    public ResponseEntity<String> addCard(@RequestBody UserDto userDto) throws Exception {
        System.out.println(userDto);
        final String expiryDate = userDto.getExpiryDate().getMonth() + "/" + userDto.getExpiryDate().getYear();
        final String VALUES = "\'" + userDto.getCardHolderName() + "\',\'" + userDto.getCardNumber() + "\',\'" + userDto.getCardType() + "\',\'" + expiryDate + "\',\'" + userDto.getCvv() + "\',\'" + userDto.getEmailAddress() + "\',\'" + userDto.getPhoneNo() + "\'";
        final String INSERT_USERS = "INSERT INTO `sample-365809.usersInfo.userservice` (cardholder_name,card_number,card_type,expiry_date,cvv,email,phone) VALUES(" + VALUES + ");";
        System.out.println(INSERT_USERS);
        QueryJobConfiguration queryJobConfiguration = QueryJobConfiguration.newBuilder(INSERT_USERS).build();
        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryJobConfiguration).build());
        queryJob = queryJob.waitFor();
        if (queryJob == null) {
            throw new Exception("Job no longer exists");
        }
        if (queryJob.getStatus().getError() != null) {
            throw new Exception(queryJob.getStatus().getError().toString());
        }

        JobStatistics.QueryStatistics statistics = queryJob.getStatistics();
        Long rowsInserted = statistics.getDmlStats().getInsertedRowCount();
        System.out.println(rowsInserted);
        log.info(rowsInserted.intValue() + " rows insertd\n");
        return ResponseEntity.status(HttpStatus.OK).body("Dear user " + userDto.getCardHolderName() + " your card is successfully submitted");
    }

//    @GetMapping(value = "/users/{id}")
//    public List<User> findUser() {
//        return userRepository.findAll();
//    }

//    @GetMapping(value = "/users")
//    public List<User> findAll() {
//        return userRepository.findAll();
//    }

    @GetMapping("/pdf")
    public void exportToPDF(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userRepository.findAll();

        final String QUERY_OUTPUT ="SELECT * FROM `sample-365809.usersInfo.userservice` LIMIT 1000";
        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(QUERY_OUTPUT).build();

        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig).build());
        queryJob = queryJob.waitFor();

        if (queryJob == null) {
            throw new Exception("job no longer exists");
        }
        if (queryJob.getStatus().getError() != null) {
            throw new Exception(queryJob.getStatus().getError().toString());
        }
        TableResult result = queryJob.getQueryResults();

        PDFExporter exporter = new PDFExporter(result);
        exporter.export(response);
    }
}
