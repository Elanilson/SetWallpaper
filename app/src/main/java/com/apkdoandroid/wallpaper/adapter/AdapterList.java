package com.apkdoandroid.wallpaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.apkdoandroid.wallpaper.R;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterList extends ArrayAdapter <ParseObject>{
    private Context context;
    private ArrayList<ParseObject> lista;


    public AdapterList(@NonNull Context context, int resource, @NonNull ArrayList<ParseObject> objects) {
        super(context, 0, objects);
        this.context =context;
        this.lista = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_lista,parent,false);

        }
        if(lista.size() > 0){
            ImageView imagem = view.findViewById(R.id.imageViewList);
            ParseObject object = lista.get(position);
            TextView textView = view.findViewById(R.id.textViewTexto);
            textView.setText(object.getString("categorias"));

            Picasso.get().load(object.getParseFile("img_categoria")
                    .getUrl())
            .fit().centerCrop()
            .into(imagem);

        }
        



        return view;

    }


}
