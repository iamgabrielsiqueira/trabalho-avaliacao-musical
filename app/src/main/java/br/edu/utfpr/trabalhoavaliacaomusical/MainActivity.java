package br.edu.utfpr.trabalhoavaliacaomusical;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.utfpr.trabalhoavaliacaomusical.activities.CadastroAvaliacaoActivity;
import br.edu.utfpr.trabalhoavaliacaomusical.activities.SobreActivity;
import br.edu.utfpr.trabalhoavaliacaomusical.adapter.AvaliacaoAdapter;
import br.edu.utfpr.trabalhoavaliacaomusical.classes.Avaliacao;
import br.edu.utfpr.trabalhoavaliacaomusical.utils.UtilsGui;

public class MainActivity extends AppCompatActivity {

    private List<Avaliacao> avaliacoes;
    private ListView listViewAvaliacoes;
    private AvaliacaoAdapter adapter;

    private ActionMode actionMode;
    private View viewSelecionada;
    private int posicaoSelecionada = -1;

    public static final String ARQUIVO = "br.edu.utfpr.trabalhoavaliacaomusical.PREFERENCIAS";
    public static final String ORDENACAO_ASCENDENTE = "ORDENACAO_ASCENDENTE";
    private boolean ordenacaoAscendente = true;

    private final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(@NonNull ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();

            menuInflater.inflate(R.menu.menu_avaliacao_selecionada, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, @NonNull MenuItem item) {
            int idMenuItem = item.getItemId();

            if (idMenuItem == R.id.menuItemEditar) {
                editarAvaliacao();
            } else if (idMenuItem == R.id.menuItemExcluir) {
                excluirAvaliacao();
            } else {
                return false;
            }

            mode.finish();

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null) {
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            viewSelecionada = null;

            listViewAvaliacoes.setEnabled(true);
        }
    };

    private void excluirAvaliacao() {
        Avaliacao avaliacao = avaliacoes.get(posicaoSelecionada);

        String mensagem = getString(R.string.voce_realmente_deseja_apagar)
                + "\n"
                + "\""
                + avaliacao.getNomeAlbum()
                + "\"";

        DialogInterface.OnClickListener listener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    avaliacoes.remove(posicaoSelecionada);
                    adapter.notifyDataSetChanged();

                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        UtilsGui.confirmaAcao(this, mensagem, listener);
    }

    private void editarAvaliacao() {
        Avaliacao avaliacao = avaliacoes.get(posicaoSelecionada);
        CadastroAvaliacaoActivity.editarAvaliacao(this, launcherEditarAvaliacao, avaliacao);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewAvaliacoes = findViewById(R.id.listViewAvaliacoes);

        listViewAvaliacoes.setOnItemClickListener((parent, view, position, id) -> {
            Avaliacao avaliacao = (Avaliacao) listViewAvaliacoes.getItemAtPosition(position);

            String msg = getString(R.string.o_album) +
                    avaliacao.getNomeAlbum() +
                    getString(R.string.foi_selecionado);

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        });

        listViewAvaliacoes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewAvaliacoes.setOnItemLongClickListener((parent, view, position, id) -> {
            if (actionMode != null) {
                return false;
            }

            posicaoSelecionada = position;

            view.setBackgroundColor(Color.LTGRAY);

            listViewAvaliacoes.setEnabled(false);

            viewSelecionada = view;

            actionMode = startSupportActionMode(mActionModeCallback);

            return false;
        });

        listViewAvaliacoes.setOnItemClickListener((parent, view, position, id) -> {
            posicaoSelecionada = position;

            editarAvaliacao();
        });

        lerPreferenciaOrdenacaoAscendente();

        populaLista();

        setTitle(R.string.app_name);

        System.out.println(avaliacoes.size());
        System.out.println(adapter.getCount());
    }

    private void populaLista() {
        avaliacoes = new ArrayList<>();

        Avaliacao avaliacao = new Avaliacao("Teste", "Teste", 3);
        avaliacoes.add(avaliacao);

        adapter = new AvaliacaoAdapter(this, avaliacoes);

        listViewAvaliacoes.setAdapter(adapter);
    }

    ActivityResultLauncher<Intent> launcherNovaAvaliacao = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> {
                if (o.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = o.getData();

                    if (intent != null) {
                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
                            String nomeAlbum = bundle.getString(CadastroAvaliacaoActivity.NOME_ALBUM);
                            String artista = bundle.getString(CadastroAvaliacaoActivity.ARTISTA);
                            int classificacao = bundle.getInt(CadastroAvaliacaoActivity.CLASSIFICACAO);

                            Avaliacao avaliacao = new Avaliacao(nomeAlbum, artista, classificacao);

                            avaliacoes.add(avaliacao);

                            ordenarLista();
                        }
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> launcherEditarAvaliacao = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> {
                if (o.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = o.getData();

                    if (intent != null) {
                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
                            String album = bundle.getString(CadastroAvaliacaoActivity.NOME_ALBUM);
                            String artista = bundle.getString(CadastroAvaliacaoActivity.ARTISTA);
                            int classificacao = bundle.getInt(CadastroAvaliacaoActivity.CLASSIFICACAO);

                            Avaliacao avaliacao = avaliacoes.get(posicaoSelecionada);

                            avaliacao.setNomeAlbum(album);
                            avaliacao.setNomeArtista(artista);
                            avaliacao.setNota(classificacao);

                            posicaoSelecionada = -1;

                            ordenarLista();
                        }
                    }
                }
            }
    );

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem menuItemOrdenacao = menu.findItem(R.id.menuItemOrdenacao);

        atualizarIconeOrdenacao(menuItemOrdenacao);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemAdicionar) {
            CadastroAvaliacaoActivity.novaAvaliacao(this, launcherNovaAvaliacao);
        } else if (idMenuItem == R.id.menuItemSobre) {
            SobreActivity.mostrarTelaSobre(this);
        } else if (idMenuItem == R.id.menuItemOrdenacao) {
            salvarPreferenciaOrdenacaoAscendente(!ordenacaoAscendente);
            atualizarIconeOrdenacao(item);
            ordenarLista();
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void atualizarIconeOrdenacao(MenuItem menuItemOrdenacao) {
        if (ordenacaoAscendente) {
            menuItemOrdenacao.setIcon(R.drawable.ic_ordenacao_crescente);
        } else {
            menuItemOrdenacao.setIcon(R.drawable.ic_ordenacao_decrescente);
        }
    }

    private void ordenarLista() {
        if (ordenacaoAscendente) {
            Collections.sort(avaliacoes, Avaliacao.ordenacaoCrescente);
        } else {
            Collections.sort(avaliacoes, Avaliacao.ordenacaoDecrescente);
        }

        adapter.notifyDataSetChanged();
    }

    private void lerPreferenciaOrdenacaoAscendente() {
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        ordenacaoAscendente = shared.getBoolean(ORDENACAO_ASCENDENTE, ordenacaoAscendente);
    }

    private void salvarPreferenciaOrdenacaoAscendente(boolean novoValor) {
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);

        ordenacaoAscendente = shared.getBoolean(ORDENACAO_ASCENDENTE, ordenacaoAscendente);

        SharedPreferences.Editor editor = shared.edit();

        editor.putBoolean(ORDENACAO_ASCENDENTE, novoValor);

        editor.apply();

        ordenacaoAscendente = novoValor;
    }
}