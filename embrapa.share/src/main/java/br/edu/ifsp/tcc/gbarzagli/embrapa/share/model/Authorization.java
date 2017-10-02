package br.edu.ifsp.tcc.gbarzagli.embrapa.share.model;

public class Authorization {

    private String token;

    private Long expiration;

    protected Authorization() {
    }

    public Authorization(String token, Long expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "Authorization [token=" + token + ", expiration=" + expiration + "]";
    }
}
