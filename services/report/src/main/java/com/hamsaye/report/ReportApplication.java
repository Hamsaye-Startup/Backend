package com.hamsaye.report;

import com.hamsaye.report.violations.models.WarehouseViolationEntity;
import com.hamsaye.report.violations.responses.ViolationResponse;
import com.hamsaye.report.violations.services.ViolationService;
import com.hamsaye.report.violations.services.ViolationServiceManagement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class ReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner commandLineRunner(
			ViolationServiceManagement management,
			ViolationService service
	) {
		return args -> {

			UUID track = UUID.randomUUID();
			WarehouseViolationEntity warehouseViolation = WarehouseViolationEntity.violationBuilder()
					.warehouseId(1L)
					.reporterId(UUID.randomUUID())
					.trackingNum(track)
					.incidentDate(new Date(System.currentTimeMillis()))
					.incidentDesc("This is a test warehouse casting")
					.build();

			service.insertViolation(warehouseViolation);

			// find the violation
			ViolationResponse response = management.findViolationByTrack(track);
			System.out.println(response);
		};
	}*/
}
