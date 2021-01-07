package com.apkdoandroid.wallpaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.apkdoandroid.wallpaper.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdapterGrid extends ArrayAdapter <ParseObject>{
    private Context context;
    private ArrayList<ParseObject> lista;


    public AdapterGrid(@NonNull Context context, int resource, @NonNull ArrayList<ParseObject> objects) {
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
            view = inflater.inflate(R.layout.layout_grid,parent,false);

        }
        if(lista.size() > 0){
            ImageView imagem = view.findViewById(R.id.imageViewGrid);
            ParseObject object = lista.get(position);

            Picasso.get().load(object.getParseFile("img_wallpaper")
                    .getUrl())
            .fit()
            .into(imagem);

        }
        



        return view;

    }


}
