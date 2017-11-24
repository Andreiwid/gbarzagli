package br.edu.ifsp.tcc.gbarzagli.embrapa.share.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Represents a image
 * 
 * @author <a href="mailTo:gabriel.barzagli@hotmail.com"> Gabriel Viseli Barzagli (gabriel.barzagli@hotmail.com) </a>
 *
 */
@Entity
public class Image extends PersistedObject {

    @Column
    private String path;

    @ManyToOne
    @JoinColumn(name = "fk_post", referencedColumnName = "id")
    private Post post;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Image [path=" + path + ", post=" + post + "]";
    }

    
}
