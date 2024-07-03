package br.edu.utfpr.trabalhoavaliacaomusical;

import java.sql.Date;

public class Avaliacao {
    private long id;
    private String nomeArtista;
    private String nomeAlbum;
    private int nota;
    private String descricao;
    private Date dataAvaliado;

    public Avaliacao() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
    }

    public String getNomeAlbum() {
        return nomeAlbum;
    }

    public void setNomeAlbum(String nomeAlbum) {
        this.nomeAlbum = nomeAlbum;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataAvaliado() {
        return dataAvaliado;
    }

    public void setDataAvaliado(Date dataAvaliado) {
        this.dataAvaliado = dataAvaliado;
    }
}
