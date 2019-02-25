package cz.tslavik.fileload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FileloadApplication {

	@Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        //builder.additionalMessageConverters(new MappingJackson2HttpMessageConverter(),new FormHttpMessageConverter());
        return builder.build();
    }
	public static void main(String[] args) {
		SpringApplication.run(FileloadApplication.class, args);
	}

}
