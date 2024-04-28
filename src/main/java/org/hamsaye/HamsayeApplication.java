package org.hamsaye;

import org.hamsaye.storages.daos.StorageCategoryRepository;
import org.hamsaye.storages.daos.StorageRepository;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.hamsaye.storages.models.StorageEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class HamsayeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HamsayeApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

		return args -> {

			// generate repo
			StorageRepository storageRepository =
					ctx.getBean(StorageRepository.class);
			StorageCategoryRepository storageCategoryRepository =
					ctx.getBean(StorageCategoryRepository.class);

			// generate category
			StorageCategoryEntity storageCategoryEntity = StorageCategoryEntity.builder()
					.code("HDB")
					.name("HELLO DATABASE")
					.build();

			// add a repo
			storageCategoryRepository.saveAndFlush(storageCategoryEntity);

			// generate storage
			StorageEntity storage = StorageEntity.builder()
					.width(12)
					.height(15)
					.maxWeight(55)
					.amount(120000d)
					.discountAmount(0.6d)
					.status("1n4300012ma")
					.category(storageCategoryEntity)
					.build();

			// add a repo
			storageRepository.saveAndFlush(storage);
		};
	}
}
