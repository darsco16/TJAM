package com.darsco.tjam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private Context context;
    private ArrayList<Entidad> listItems;

    public Adaptador(Context context, ArrayList<Entidad> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Entidad Item = (Entidad) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.news_tjam, null);
        SmartImageView imgFoto = (SmartImageView) convertView.findViewById(R.id.imgFoto);
        TextView txtTitulo = (TextView) convertView.findViewById(R.id.txtTitulo);
        TextView txtResumen = (TextView) convertView.findViewById(R.id.txtResumen);
        TextView txtContenido = (TextView) convertView.findViewById(R.id.txtContenido);

        imgFoto.setImageUrl(Item.getImgFoto());
        txtTitulo.setText(Item.getTitulo());
        txtResumen.setText(Item.getResumen());
        txtContenido.setText(Item.getContenido());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, noticia_tjam.class);
                intent.putExtra("objetoData", Item);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}