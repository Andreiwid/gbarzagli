package br.edu.ifsp.tcc.gbarzagli.embrapa.share.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tokens implements Observer {
	
	private Map<Long, Token> storedTokens;
	private static ExecutorService executor;
	static {
		executor = Executors.newFixedThreadPool(1000);
	}

	public void addToken(Token token) {
		if (storedTokens == null) {
			storedTokens = new HashMap<>();
		}
		
		token.addObserver(this);
		executor.execute(token);
		storedTokens.put(token.getUserId(), token);
	}
	
	public boolean validateToken(Long userId) {
		return storedTokens.get(userId) != null; 
	}
	
	@Override
	public void update(Observable o, Object arg) {
		storedTokens.remove(arg);
	}
}
