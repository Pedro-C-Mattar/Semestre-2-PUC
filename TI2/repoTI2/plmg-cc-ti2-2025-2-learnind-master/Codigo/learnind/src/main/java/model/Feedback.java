package model;

import java.time.LocalDateTime;

public class Feedback {
    private int idFeedback;
    private int idVideo;
    private String conteudo;
    private LocalDateTime dataFeedback;

    public Feedback() {
    }

    public Feedback(int idFeedback, int idVideo, String conteudo, LocalDateTime dataFeedback) {
        this.idFeedback = idFeedback;
        this.idVideo = idVideo;
        this.conteudo = conteudo;
        this.dataFeedback = dataFeedback;
    }

    public int getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }

    public int getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(int idVideo) {
        this.idVideo = idVideo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataFeedback() {
        return dataFeedback;
    }

    public void setDataFeedback(LocalDateTime dataFeedback) {
        this.dataFeedback = dataFeedback;
    }
}

