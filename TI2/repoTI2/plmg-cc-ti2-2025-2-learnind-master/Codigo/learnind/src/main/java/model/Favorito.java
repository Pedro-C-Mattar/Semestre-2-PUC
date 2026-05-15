package model;
import java.time.LocalDateTime;

public class Favorito {
	private int idFavorito;
    private LocalDateTime dataFavoritado;
    private int idUsuario;
    private int idVideo;

    public Favorito() {
    }

    public Favorito(int idFavorito, LocalDateTime dataFavoritado, int idUsuario, int idVideo) {
        this.idFavorito = idFavorito;
        this.dataFavoritado = dataFavoritado;
        this.idUsuario = idUsuario;
        this.idVideo = idVideo;
    }
    
    public int getIdFavorito() {
        return idFavorito;
    }
    public void setIdFavorito(int idFavorito) {
        this.idFavorito = idFavorito;
    }
    public LocalDateTime getDataFavoritado() {
        return dataFavoritado;
    }
    public void setDataFavoritado(LocalDateTime dataFavoritado) {
        this.dataFavoritado = dataFavoritado;
    }
    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public int getIdVideo() {
        return idVideo;
    }
    public void setIdVideo(int idVideo) {
    	this.idVideo = idVideo;
    }
}
