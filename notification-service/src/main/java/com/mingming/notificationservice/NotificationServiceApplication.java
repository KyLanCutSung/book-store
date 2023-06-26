package com.mingming.notificationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableBinding(Sink.class)
public class NotificationServiceApplication {

	private Logger logger = LoggerFactory.getLogger(NotificationServiceApplication.class);
	@StreamListener(Sink.INPUT)
	public void consumeMessage(Message message){
		logger.info("Consume Payload: "+message.getEmployeeId()+" "+message.getMessage());
	}
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
