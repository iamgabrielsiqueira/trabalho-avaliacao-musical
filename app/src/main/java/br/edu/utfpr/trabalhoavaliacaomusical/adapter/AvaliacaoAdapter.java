package br.edu.utfpr.trabalhoavaliacaomusical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.sql.Date;
import java.util.List;

import br.edu.utfpr.trabalhoavaliacaomusical.R;
import br.edu.utfpr.trabalhoavaliacaomusical.classes.Avaliacao;

public class AvaliacaoAdapter extends BaseAdapter {
    private final Context context;
    private final List<Avaliacao> avaliacoes;

    private static class AvaliacaoHolder {
        public TextView textViewNomeAlbum;
        public TextView textViewNomeArtista;
        public TextView textViewDataAvaliado;
        public RatingBar ratingBarClassificacao;
    }

    public AvaliacaoAdapter(Context context, List<Avaliacao> avaliacoes) {
        this.context = context;
        this.avaliacoes = avaliacoes;
    }

    @Override
    public int getCount() {
        return avaliacoes.size();
    }

    @Override
    public Object getItem(int position) {
        return avaliacoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AvaliacaoHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
            );

            convertView = inflater.inflate(R.layout.lista_avaliacoes, parent, false);

            holder = new AvaliacaoHolder();

            holder.textViewNomeAlbum = convertView.findViewById(R.id.textViewNomeAlbum);
            holder.textViewNomeArtista = convertView.findViewById(R.id.textViewNomeArtista);
            holder.textViewDataAvaliado = convertView.findViewById(R.id.textViewDataAvaliado);
            holder.ratingBarClassificacao = convertView.findViewById(R.id.ratingBarClassificacao);

            convertView.setTag(holder);

        } else {

            holder = (AvaliacaoHolder) convertView.getTag();
        }

        holder.textViewNomeAlbum.setText(avaliacoes.get(position).getNomeAlbum());
        holder.textViewNomeArtista.setText(avaliacoes.get(position).getNomeArtista());
        holder.textViewDataAvaliado.setText(this.formatarData(avaliacoes.get(position).getDataAvaliado()));
        holder.ratingBarClassificacao.setRating(avaliacoes.get(position).getNota());

        return convertView;
    }

    @NonNull
    private String formatarData(@NonNull Date data) {
        String ano = data.toString().substring(0, 4);
        String mes = data.toString().substring(5, 7);
        String dia = data.toString().substring(8, 10);

        return dia + "/" + mes + "/" + ano;
    }
}
