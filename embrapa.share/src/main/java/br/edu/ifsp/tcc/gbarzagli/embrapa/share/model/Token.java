package br.edu.ifsp.tcc.gbarzagli.embrapa.share.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;

public class Token extends Observable implements Runnable {

    private String key;
    private Long userId;
    private Date expirationDate;

    public Token(Long userId, String key) {
        this.key = key;
        this.userId = userId;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        this.expirationDate = calendar.getTime();
    }

    public String getKey() {
        return key;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public void run() {
        Date now = null;
        do {

            now = new Date();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
            }

        } while (now.before(expirationDate));

        setChanged();
        notifyObservers(userId);
    }

}
