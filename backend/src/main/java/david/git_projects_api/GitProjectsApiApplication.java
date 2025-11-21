package david.git_projects_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GitProjectsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitProjectsApiApplication.class, args);
	}

}
