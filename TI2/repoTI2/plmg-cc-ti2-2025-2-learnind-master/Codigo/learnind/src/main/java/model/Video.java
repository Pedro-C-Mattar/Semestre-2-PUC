package model;

public class Video {
	private int idVideo;
    private String titulo;
    private String descricao;
    private String url;
    private Integer idCategoria;
    private Integer idAtividade;
    private Integer idUsuario;
    
    public Video() {
    }

    public Video(int idVideo, String titulo, String descricao, String url, Integer idCategoria, Integer idAtividade, Integer idUsuario) {
        this.idVideo = idVideo;
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.idCategoria = idCategoria;
        this.idAtividade = idAtividade;
        this.idUsuario = idUsuario;
    }
    
    public int getIdVideo() {
        return idVideo;
    }
    public void setIdVideo(int idVideo) {
        this.idVideo = idVideo;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
    public Integer getIdAtividade() {
        return idAtividade;
    }
    public void setIdAtividade(Integer idAtividade) {
        this.idAtividade = idAtividade;
    }
    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
