package kr.human.ISP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class InterSectionProjectVer1Application {

	public static void main(String[] args) {
		SpringApplication.run(InterSectionProjectVer1Application.class, args);
	}

}
