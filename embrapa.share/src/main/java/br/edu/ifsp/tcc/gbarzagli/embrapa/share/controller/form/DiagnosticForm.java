package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller.form;

public class DiagnosticForm {

    private Long postId;
    private String description;

    
    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "DiagnosticForm [post=" + postId + ", description=" + description + "]";
    }
    
    
    
}
