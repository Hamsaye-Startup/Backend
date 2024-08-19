package com.hamsaye.chat;

import com.hamsaye.chat.users.requests.UserRequest;
import com.hamsaye.chat.users.responses.UserResponse;
import com.hamsaye.chat.users.services.UserServiceManagement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class ChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}

}
