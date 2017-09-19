package br.edu.ifsp.tcc.gbarzagli.embrapa.share;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Tokens;

@Configuration
public class BeanConfiguration {

	@Bean
	public Tokens tokens() {
		return new Tokens();
    }

}
