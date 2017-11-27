package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller.form;

import java.util.List;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Post;

public class HomeForm {
    
    private UserPartForm user;
    private List<Post> posts;
    
    public UserPartForm getUser() {
        return user;
    }
    public void setUser(UserPartForm user) {
        this.user = user;
    }
    public List<Post> getPosts() {
        return posts;
    }
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    @Override
    public String toString() {
        return "HomeForm [user=" + user + ", posts=" + posts + "]";
    }
    
    
    public class UserPartForm {
        private String name;
        private int diagnostics;
        private float score;
        private String reliability;
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getDiagnostics() {
            return diagnostics;
        }
        public void setDiagnostics(int totalDiagnostics) {
            this.diagnostics = totalDiagnostics;
        }
        public float getScore() {
            return score;
        }
        public void setScore(float score) {
            this.score = score;
        }
        public String getReliability() {
            return reliability;
        }
        public void setReliability(String reliability) {
            this.reliability = reliability;
        }
        @Override
        public String toString() {
            return "User [name=" + name + ", totalDiagnostics=" + diagnostics + ", score=" + score + ", reliability=" + reliability + "]";
        }
    }
}


