package com.gmind7.bakery.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.gmind7.bakery.config.AppConfig;

@Configuration
@EnableAutoConfiguration
@Import(AppConfig.class)
public class SpringBootRunner implements CommandLineRunner {

	@Autowired
	private BakeryAsyncFunction asyncFunction;
	
	@Autowired
	private BakeryFutureCallback futureCallbak;
	
	@Override
	public void run(String... arg0) throws Exception {
		futureCallbak.doAction();
		asyncFunction.doAction();
	}
	
	public static void main(String[] args) throws Exception {
		
//		Properties properties = new Properties();
//		
//		String springProfilesActvie = properties.getProperty("spring.profiles.active");
//		
//		SpringApplication app = new SpringApplication(SpringBootRunner.class);
		
//		if(springProfilesActvie==null) {
//			properties.setProperty("spring.profiles.active", "loc");
//			app.setDefaultProperties(properties);
//		}

        	SpringApplication.run(SpringBootRunner.class, args);
	}

}
