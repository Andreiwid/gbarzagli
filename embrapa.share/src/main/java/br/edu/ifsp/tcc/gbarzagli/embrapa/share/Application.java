package br.edu.ifsp.tcc.gbarzagli.embrapa.share;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	
	/** Main method - initiate our spring application */
    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }
    
    
    @Bean
    @ConfigurationProperties("app.datasource")
    public DataSource dataSource() {
    	return DataSourceBuilder.create()
    							.url("jdbc:mysql://localhost:3306/embrapa")
    							.username("root")
    							.password("")
    							.driverClassName("com.mysql.jdbc.Driver")
    							.build();
    }
}
