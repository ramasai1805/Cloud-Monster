package com.nisumpractice.reportgeneration.data;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryError;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.InsertAllRequest;
import com.google.cloud.bigquery.InsertAllResponse;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.JobStatistics;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
// Sample to query in a table
public class App {
    @Autowired
    private BigQuery bigQuery;

    public static void insertSampleData() {
        // Step 1: Initialize BigQuery service
        BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId("sample-365809")
                .build().getService();

        // Step 2: Create insertAll (streaming) request
        InsertAllRequest insertAllRequest = getInsertRequest();

        // Step 3: Insert data into table
        InsertAllResponse response = bigquery.insertAll(insertAllRequest);

        // Step 4: Check for errors and print results
        if (response.hasErrors()) {
            for (Map.Entry<Long, List<BigQueryError>> entry : response.getInsertErrors()
                    .entrySet()) {
                System.out.printf("error in entry %d: %s", entry.getKey(),
                                  entry.getValue().toString());
            }
            return;
        }
        System.out.println("inserted successfully");
    }

    // To create a streaming insert request, we need to specify the table and dataset id
    // and create the rows we want to insert
    private static InsertAllRequest getInsertRequest() {
        String datasetId = "sample";
        String tableId = "users";
        return InsertAllRequest.newBuilder(datasetId, tableId).addRow(getRow(1, "carrot"))
                .addRow(getRow(2, "beans")).build();

    }

    // each row is a map with the row name as the key and row value as the value
    // since the value type is "Object" it can take any arbitrary type, based on
    // the datatype of the row defined on BigQuery
    private static Map<String, Object> getRow(int id, String vegetableName) {
        Map<String, Object> rowMap = new HashMap<String, Object>();
        rowMap.put("id", id);
        rowMap.put("name", vegetableName);
        return rowMap;
    }

    //    public static void main(String... args) throws Exception {
    //        insertSampleData();
    //    }

    private static void getFromExistingTable() throws Exception {

        // Step 1: Initialize BigQuery service
        // Here we set our project ID and get the `BigQuery` service object
        // this is the interface to our BigQuery instance that
        // we use to execute jobs on
        String jsonPath = "/Users/brellu/Downloads/sample-365809-28433da2c142.json";
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));
        BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId("sample-365809").setCredentials(credentials).build().getService();

        // Step 2: Prepare query job
        // A "QueryJob" is a type of job that executes SQL queries
        // we create a new job configuration from our SQL query and
        final String GET_WORD_COUNT ="SELECT * FROM `sample-365809.usersInfo.userservice` LIMIT 1000";
//                "SELECT word, word_count FROM `bigquery-public-data.samples.shakespeare` WHERE corpus='juliuscaesar' ORDER BY word_count DESC limit 10;";
        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(GET_WORD_COUNT).build();

        // Step 3: Run the job on BigQuery
        // create a `Job` instance from the job configuration using the BigQuery service
        // the job starts executing once the `create` method executes
        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).build());
        queryJob = queryJob.waitFor();
        // the waitFor method blocks until the job completes
        // and returns `null` if the job doesn't exist anymore
        if (queryJob == null) {
            throw new Exception("job no longer exists");
        }
        // once the job is done, check if any error occured
        if (queryJob.getStatus().getError() != null) {
            throw new Exception(queryJob.getStatus().getError().toString());
        }

        // Step 4: Display results
        // Print out a header line, and iterate through the
        // query results to print each result in a new line
        System.out.println("word\tword_count");
        TableResult result = queryJob.getQueryResults();
        for (FieldValueList row : result.iterateAll()) {
            // We can use the `get` method along with the column
            // name to get the corresponding row entry
            String word = row.get("cardholder_name").getStringValue();
            String wordCount = row.get("card_number").getStringValue();
            System.out.println(word+"|"+wordCount);
        }
    }

    private static void insertViaQuery() throws Exception {

        // Step 1: Initialize BigQuery service
        BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId("sample-365809")
                .build().getService();

        // Step 2: Prepare query job
        final String INSERT_VEGETABLES =
                "INSERT INTO `sample-365809.sample.users` (id, name) VALUES (1, 'carrot'), (2, 'beans');";
        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(INSERT_VEGETABLES).build();

        // Step 3: Run the job on BigQuery
        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).build());
        queryJob = queryJob.waitFor();
        if (queryJob == null) {
            throw new Exception("job no longer exists");
        }
        // once the job is done, check if any error occured
        if (queryJob.getStatus().getError() != null) {
            throw new Exception(queryJob.getStatus().getError().toString());
        }

        // Step 4: Display results
        // Here, we will print the total number of rows that were inserted
        JobStatistics.QueryStatistics stats = queryJob.getStatistics();
        Long rowsInserted = stats.getDmlStats().getInsertedRowCount();
        System.out.printf("%d rows inserted\n", rowsInserted);
    }

    public static void main(String args[]) throws Exception {
        String jsonPath = "/Users/brellu/Downloads/sample-365809-28433da2c142.json";
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));
        BigQuery bigQuery = BigQueryOptions.newBuilder().setProjectId("sample-365809").setCredentials(credentials).build().getService();
     getFromExistingTable();
    }
}