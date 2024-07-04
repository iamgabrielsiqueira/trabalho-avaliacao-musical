package br.edu.utfpr.trabalhoavaliacaomusical.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import br.edu.utfpr.trabalhoavaliacaomusical.R;

public class UtilsGui {
    public static void aviso(Context context, int idTexto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.aviso);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(idTexto);

        builder.setNeutralButton(R.string.ok, null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void confirmaAcao(Context context, String mensagem,
                                    DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.confirmacao);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(mensagem);

        builder.setPositiveButton(R.string.sim, listener);
        builder.setNegativeButton(R.string.nao, listener);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
