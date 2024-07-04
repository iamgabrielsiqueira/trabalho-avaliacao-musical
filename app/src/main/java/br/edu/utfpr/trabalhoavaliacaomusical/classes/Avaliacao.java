package br.edu.utfpr.trabalhoavaliacaomusical.classes;

import java.sql.Date;
import java.util.Comparator;

public class Avaliacao {
    private long id;
    private String nomeArtista;
    private String nomeAlbum;
    private int nota;
    private final Date dataAvaliado;

    public Avaliacao() {
        this.id = 0;
        this.nomeArtista = "";
        this.nomeAlbum = "";
        this.nota = 0;
        this.dataAvaliado = new Date(System.currentTimeMillis());
    }

    public Avaliacao(String nomeArtista, String nomeAlbum, int nota) {
        this.id = 0;
        this.nomeArtista = nomeArtista;
        this.nomeAlbum = nomeAlbum;
        this.nota = nota;
        this.dataAvaliado = new Date(System.currentTimeMillis());
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

    public Date getDataAvaliado() {
        return dataAvaliado;
    }

    public static Comparator<Avaliacao> ordenacaoCrescente =
            (o1, o2) -> o1.getNomeAlbum().compareToIgnoreCase(o2.getNomeAlbum());

    public static Comparator<Avaliacao> ordenacaoDecrescente =
            (o1, o2) -> -1 * o1.getNomeAlbum().compareToIgnoreCase(o2.getNomeAlbum());
}
