package com.example.talentshow.presentation.star;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.talentshow.App;
import com.example.talentshow.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

public class ActivityStarFileLoad extends AppCompatActivity {

    @Inject
    Context appContext;

    String link = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_load_file);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @OnClick(R.id.activity_star_load_file_circle)
    void loadFileClicked(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select video"), 1);
    }

    @OnClick(R.id.activity_star_load_file_continue)
    void continueClicked(){
        Intent intent = new Intent(appContext, ActivityStarVideoBest.class);
        intent.putExtra("VideoLink", link);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("resultCodeVideo", resultCode + "");
        if (resultCode == RESULT_OK && data != null){
            Uri selectedVideo = data.getData();
            Log.d("Video link", selectedVideo.toString());
            link = selectedVideo.toString();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(appContext, ActivityStarNameEnter.class));
    }
}