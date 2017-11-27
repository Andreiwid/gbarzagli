package br.edu.ifsp.tcc.gbarzagli.embrapa.share.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Diagnostic extends PersistedObject {
    
    @Column
    private String description;
    
    @Column
    private Date date;
    
    @Column
    private int score = 0;
    
    @ManyToOne
    @JoinColumn(name = "fk_post", referencedColumnName = "id")
    private Post post;
    
    @ManyToOne
    @JoinColumn(name = "fk_researcher", referencedColumnName = "id")
    private Researcher researcher;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    @Override
    public String toString() {
        return "Diagnostic [description=" + description + ", date=" + date + ", score=" + score + ", post=" + post + ", researcher=" + researcher + "]";
    }

    
    
}
