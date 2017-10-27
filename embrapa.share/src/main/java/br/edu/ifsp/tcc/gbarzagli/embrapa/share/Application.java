package br.edu.ifsp.tcc.gbarzagli.embrapa.share;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
	/** Main method - initiate our spring application */
    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }
    
    
    @Bean
    @ConfigurationProperties("app.datasource")
    public DataSource dataSource() {
    	return DataSourceBuilder.create()
    							.url("jdbc:postgresql://localhost:5432/digipathos")
    							.username("postgres")
    							.password("root")
    							.driverClassName("org.postgresql.Driver")
    							.build();
    }
    
}
