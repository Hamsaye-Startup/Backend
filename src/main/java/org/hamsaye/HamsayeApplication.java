package org.hamsaye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class HamsayeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HamsayeApplication.class, args);
	}

}
