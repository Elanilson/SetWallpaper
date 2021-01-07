package com.apkdoandroid.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

public class ImagemActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageView;
    private ImageButton setWallpaper, setDownload;
    private String url_imagem ="";
    private static final int REQUESTCODE = 1;
    private static final int REQUESTCODE_Store = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem);
        getSupportActionBar().hide();
        toolbar = findViewById(R.id.toolbarImagem);
        imageView = findViewById(R.id.imageViewFoto);
        setWallpaper = findViewById(R.id.imageButtonWallpaper);
        setDownload = findViewById(R.id.imageButtonDownload);

        Bundle dados = getIntent().getExtras();
        if(dados != null){
            url_imagem = dados.getString("url_imagem");

        }

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_voltar_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        Picasso.get().load(url_imagem)
                .fit()
                .centerInside()
                .into(imageView);

        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarWallpaper();
            }
        });
        setDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImg();

            }
        });



    }

    private void downloadImg() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUESTCODE_Store);
        }else{
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url_imagem));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,Uri.parse(url_imagem).getLastPathSegment());
            downloadManager.enqueue(request);
        }
    }

    private void salvarWallpaper() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SET_WALLPAPER )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SET_WALLPAPER},REQUESTCODE);
        }else{
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
                    try {
                        manager.setBitmap(bitmap);
                        Toast.makeText(ImagemActivity.this, "Wallpaper Aplicado", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.get().load(url_imagem).into(target);

        }
    }
}