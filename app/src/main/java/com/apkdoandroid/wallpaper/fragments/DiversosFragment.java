package com.apkdoandroid.wallpaper.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.apkdoandroid.wallpaper.ImagemActivity;
import com.apkdoandroid.wallpaper.R;
import com.apkdoandroid.wallpaper.adapter.AdapterGrid;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiversosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiversosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GridView gridView;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> lista;
    private View view;
    private ParseQuery<ParseObject> query ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiversosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiversosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiversosFragment newInstance(String param1, String param2) {
        DiversosFragment fragment = new DiversosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view=   inflater.inflate(R.layout.fragment_diversos, container, false);
        lista = new ArrayList<>();
      adapter = new AdapterGrid(getContext(),0,lista);
        recebendoWallpapers();

        gridView = view.findViewById(R.id.gridDiversos);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject object= lista.get(position);
                String urlImg = "";
                        urlImg= object.getParseFile("img_wallpaper").getUrl();
                Intent intent = new Intent(getContext(), ImagemActivity.class);
                intent.putExtra("url_imagem",urlImg);
                startActivity(intent);
            }
        });
        return view;

    }
    private void recebendoWallpapers() {
        query = ParseQuery.getQuery("Wallpapers");
        query.orderByAscending("objectId");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size() > 0){
                    lista.clear();
                    for(ParseObject x: objects){
                        lista.add(x);
                    }
                    adapter.notifyDataSetChanged();


                }
            }
        });
    }
}