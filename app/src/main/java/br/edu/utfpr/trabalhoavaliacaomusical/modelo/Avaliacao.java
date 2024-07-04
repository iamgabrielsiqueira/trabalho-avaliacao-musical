package br.edu.utfpr.trabalhoavaliacaomusical.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.util.Comparator;

@Entity
public class Avaliacao {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String artista;
    private String album;
    private int nota;
    private String data;

    public Avaliacao(String artista, String album, int nota, String data) {
        this.id = 0;
        this.artista = artista;
        this.album = album;
        this.nota = nota;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static Comparator<Avaliacao> ordenacaoCrescente =
            (o1, o2) -> o1.getAlbum().compareToIgnoreCase(o2.getAlbum());

    public static Comparator<Avaliacao> ordenacaoDecrescente =
            (o1, o2) -> -1 * o1.getAlbum().compareToIgnoreCase(o2.getAlbum());
}
