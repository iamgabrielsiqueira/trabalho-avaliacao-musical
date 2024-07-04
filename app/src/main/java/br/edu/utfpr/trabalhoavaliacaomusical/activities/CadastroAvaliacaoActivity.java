package br.edu.utfpr.trabalhoavaliacaomusical.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.edu.utfpr.trabalhoavaliacaomusical.R;
import br.edu.utfpr.trabalhoavaliacaomusical.classes.Avaliacao;

public class CadastroAvaliacaoActivity extends AppCompatActivity {
    private EditText editTextAlbum, editTextArtista;

    private RatingBar ratingBarAlbum;

    public static final String NOME_ALBUM = "NOME_ALBUM";
    public static final String ARTISTA = "ARTISTA";
    public static final String CLASSIFICACAO = "CLASSIFICACAO";

    public static final String MODO = "MODO";

    public static final int NOVO = 1;
    public static final int EDITAR = 2;

    private int modo;

    private String nomeOriginal;
    private String artistaOriginal;
    private int classificacaoOriginal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_avaliacao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextAlbum = findViewById(R.id.editTextAlbum);
        editTextArtista = findViewById(R.id.editTextArtista);
        ratingBarAlbum = findViewById(R.id.ratingBarAlbum2);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO) {
                setTitle(getString(R.string.nova_avaliacao));
            } else if (modo == EDITAR) {
                setTitle(getString(R.string.editar_avaliacao));

                nomeOriginal = bundle.getString(NOME_ALBUM);
                artistaOriginal = bundle.getString(ARTISTA);
                classificacaoOriginal = bundle.getInt(CLASSIFICACAO);

                editTextAlbum.setText(nomeOriginal);
                editTextArtista.setText(artistaOriginal);
                ratingBarAlbum.setRating(classificacaoOriginal);
            }
        }

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static void novaAvaliacao(AppCompatActivity activity,
                                 @NonNull ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(activity, CadastroAvaliacaoActivity.class);

        intent.putExtra(MODO, NOVO);

        launcher.launch(intent);
    }

    public static void editarAvaliacao(AppCompatActivity activity,
                                       @NonNull ActivityResultLauncher<Intent> launcher,
                                       @NonNull Avaliacao avaliacao) {
        Intent intent = new Intent(activity, CadastroAvaliacaoActivity.class);

        intent.putExtra(MODO, EDITAR);

        intent.putExtra(NOME_ALBUM, avaliacao.getNomeAlbum());
        intent.putExtra(ARTISTA, avaliacao.getNomeArtista());
        intent.putExtra(CLASSIFICACAO, avaliacao.getNota());

        launcher.launch(intent);
    }

    public void salvar() {
        String nomeAlbum = editTextAlbum.getText().toString();
        String artista = editTextArtista.getText().toString();

        int classificacao = (int) ratingBarAlbum.getRating();

        if (nomeAlbum.isEmpty() || artista.isEmpty()) {
            Toast.makeText(this,
                    R.string.preencha_todos_os_campos,
                    Toast.LENGTH_LONG
            ).show();
        } else {
            Intent intent = new Intent();

            intent.putExtra(NOME_ALBUM, nomeAlbum);
            intent.putExtra(ARTISTA, artista);
            intent.putExtra(CLASSIFICACAO, classificacao);

            setResult(CadastroAvaliacaoActivity.RESULT_OK, intent);

            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_avaliacao_opcoes, menu);

        return true;
    }

    private void limpar() {
        editTextAlbum.setText(null);
        editTextArtista.setText(null);
        ratingBarAlbum.setRating(0);

        Toast.makeText(this, R.string.campos_limpos, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemSalvar) {
            salvar();
        } else if (idMenuItem == R.id.menuItemLimpar) {
            limpar();
        } else if (idMenuItem == android.R.id.home) {
            finish();
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }
}