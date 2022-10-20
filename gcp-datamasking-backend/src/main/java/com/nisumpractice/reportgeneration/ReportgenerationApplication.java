package com.nisumpractice.reportgeneration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import java.io.FileInputStream;
import java.io.IOException;
import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReportgenerationApplication {
	@Bean
	public Faker faker() {
		return new Faker();
	}
	public static void main(String[] args) {
		SpringApplication.run(ReportgenerationApplication.class, args);
	}
	@Bean
	public BigQuery bigQueryObject() throws IOException {
		String jsonPath = "/Users/brellu/Downloads/sample-365809-28433da2c142.json";
		GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));
		return BigQueryOptions.newBuilder().setProjectId("sample-365809").setCredentials(credentials).build().getService();
	}
}