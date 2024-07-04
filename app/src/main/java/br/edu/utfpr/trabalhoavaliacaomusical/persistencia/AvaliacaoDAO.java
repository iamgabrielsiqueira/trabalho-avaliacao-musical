package br.edu.utfpr.trabalhoavaliacaomusical.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.trabalhoavaliacaomusical.modelo.Avaliacao;

@Dao
public interface AvaliacaoDAO {
    @Insert
    long insert(Avaliacao avaliacao);

    @Delete
    int delete(Avaliacao avaliacao);

    @Update
    int update(Avaliacao avaliacao);

    @Query("SELECT * FROM avaliacao ORDER BY album ASC")
    List<Avaliacao> queryAllAscending();

    @Query("SELECT * FROM avaliacao ORDER BY album DESC")
    List<Avaliacao> queryAllDownward();

    @Query("SELECT * FROM avaliacao WHERE id = :id")
    Avaliacao queryForId(long id);
}
