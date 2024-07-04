package br.edu.utfpr.trabalhoavaliacaomusical.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.trabalhoavaliacaomusical.modelo.Avaliacao;

@Database(entities = {Avaliacao.class}, version = 1, exportSchema = false)
public abstract class AvaliacoesDatabase extends RoomDatabase {
    public abstract AvaliacaoDAO getAvaliacaoDao();

    private static AvaliacoesDatabase instance;

    public static AvaliacoesDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (AvaliacoesDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context,
                            AvaliacoesDatabase.class,
                            "avaliacoes.db"
                    ).allowMainThreadQueries().build();
                }
            }
        }

        return instance;
    }
}
