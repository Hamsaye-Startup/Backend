package org.hamsaye;

import org.hamsaye.storages.daos.StorageCategoryRepository;
import org.hamsaye.storages.daos.StorageRepository;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.storages.models.StorageFeatureEntity;
import org.hamsaye.storages.services.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;


@SpringBootApplication
@EnableTransactionManagement
public class HamsayeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HamsayeApplication.class, args);
	}

}
