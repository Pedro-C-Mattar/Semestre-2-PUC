package model;

public class Atividade {
	private int idAtividade;
    private String conteudo;
    
    public Atividade() {
    }

    public Atividade(int idAtividade, String conteudo) {
        this.idAtividade = idAtividade;
        this.conteudo = conteudo;
    }

    public int getIdAtividade() {
        return idAtividade;
    }
    public void setIdAtividade(int idAtividade) {
        this.idAtividade = idAtividade;
    }
    public String getConteudo() {
        return conteudo;
    }
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
