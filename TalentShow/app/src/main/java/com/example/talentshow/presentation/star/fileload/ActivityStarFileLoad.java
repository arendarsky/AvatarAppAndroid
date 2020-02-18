package com.example.talentshow.presentation.star.fileload;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.CastingActivity;
import com.example.talentshow.presentation.star.ActivityStarNameEnter;
import com.example.talentshow.presentation.star.ActivityStarVideoBest;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

public class ActivityStarFileLoad extends MvpAppCompatActivity implements StarFileLoadView{

    private int CAMREA_CODE = 1;

    @InjectPresenter
    StarFileLoadPresenter presenter;

    @ProvidePresenter
    StarFileLoadPresenter providePresenter(){
        return Toothpick.openScope(App.class).getInstance(StarFileLoadPresenter.class);
    }

    @Inject
    Context appContext;

    String link = "";
    int REQUEST_ID_VIDEO_CAPTURE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_load_file);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @OnClick(R.id.activity_star_load_file_circle)
    void loadFileClicked(){
        //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(Intent.createChooser(intent, "Select video"), 1);
//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);


        // Start camera and wait for the results.
//        startActivityForResult(intent, REQUEST_ID_VIDEO_CAPTURE);

        if (permissionAlreadyGranted()) {
            Intent intentС = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(intentС, REQUEST_ID_VIDEO_CAPTURE);
            return;
        }

        requestPermission();
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
            presenter.uploadVideoToServer(selectedVideo);
        }
        if(requestCode == REQUEST_ID_VIDEO_CAPTURE && data != null){
            Uri selectedVideo = data.getData();
            Log.d("Video link", selectedVideo.toString());
            link = selectedVideo.toString();
            presenter.uploadVideoToServer(selectedVideo);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(appContext, ActivityStarNameEnter.class));
    }


    @Override
    public void startingNextActivity() {
        startActivity(new Intent(appContext, CastingActivity.class));
    }

    @Override
    public void showingError() {
        Toast.makeText(appContext, "Loading error. Try again later", Toast.LENGTH_SHORT).show();
    }

    private boolean permissionAlreadyGranted() {

        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMREA_CODE);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CAMREA_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();
                boolean showRationale = shouldShowRequestPermissionRationale( Manifest.permission.CAMERA );
                if (! showRationale) {
                    openSettingsDialog();
                }
            }
        }
    }

    private void openSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityStarFileLoad.this);
        builder.setTitle("Required Permissions");
        builder.setMessage("This app require permission to use awesome feature. Grant them in app settings.");
        builder.setPositiveButton("Take Me To SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

}
