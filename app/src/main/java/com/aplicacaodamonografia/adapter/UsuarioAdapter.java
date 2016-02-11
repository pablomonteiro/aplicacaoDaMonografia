package com.aplicacaodamonografia.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aplicacaodamonografia.R;
import com.aplicacaodamonografia.model.Usuario;

import java.util.List;

/**
 * Created by pablo on 10/02/16.
 */
public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    private LayoutInflater inflate;
    private int idResource;

    public UsuarioAdapter(Context context, int resource, List<Usuario> usuarios) {
        super(context, resource, usuarios);
        this.inflate = LayoutInflater.from(context);
        this.idResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = inflate.inflate(idResource, parent, false);
        TextView nomeDoUsuario = (TextView) layout.findViewById(R.id.user_name);
        TextView telefoneDoUsuario = (TextView) layout.findViewById(R.id.user_phone);
        TextView cidadeDoUsuario = (TextView) layout.findViewById(R.id.user_city);
        TextView estadoDoUsuario = (TextView) layout.findViewById(R.id.user_state);
        TextView bairroDoUsuario = (TextView) layout.findViewById(R.id.user_neighbor);
        Usuario usuario = getItem(position);
        nomeDoUsuario.setText(usuario.getNome());
        telefoneDoUsuario.setText(usuario.getTelefone());
        cidadeDoUsuario.setText(usuario.getEndereco().getCidade());
        estadoDoUsuario.setText(usuario.getEndereco().getUf());
        bairroDoUsuario.setText(usuario.getEndereco().getBairro());
        return layout;
    }
}
