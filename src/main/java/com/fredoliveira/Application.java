package com.fredoliveira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.util.Base64Utils;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String... args) {
        String authorization = "Basic " + new String(Base64Utils.encode(("com.fredoliveira.springoauth" + ":" + "FO#xCg8Kh^UAGa3vC").getBytes()));

        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
