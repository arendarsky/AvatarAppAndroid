package com.avatar.ava.presentation.signing;

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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.presentation.main.MainScreenActivity;
import com.avatar.ava.presentation.signing.fragments.FragmentFileLoad;
import com.avatar.ava.R;


import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import toothpick.Toothpick;

public class EnterActivity extends MvpAppCompatActivity implements EnterView, RegAuthPostman {

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    Context appContext;

    @InjectPresenter
    EnterPresenter presenter;

    @ProvidePresenter
    EnterPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(EnterPresenter.class);
    }

    private Navigator navigator = new SupportAppNavigator(this, R.id.splash_container);
    private final int PICK_IMAGE = 0;
    private final int CAMERA_CODE = 1;
    private final String link = "";
    private final int REQUEST_ID_VIDEO_CAPTURE = 100;
    private final int REQUEST_PICK_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.checkAuth();
        setContentView(R.layout.enter_activty);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @Override
    public void startMain() {
        startActivity(new Intent(appContext, MainScreenActivity.class));
        finish();
    }

    @Override
    public void loadPhotoForAvatar() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    public void pickVideo() {
        if (permissionAlreadyGranted()){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Select video"), REQUEST_PICK_IMAGE);
        }
        else{
            requestPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null){
            //TODO Добавить какую-то реализацию сохранения фотографии
        }
        if (requestCode == REQUEST_PICK_IMAGE && data != null){
            Log.d("Video", data.getData().toString());
            presenter.uploadVideoToServer(data.getData());
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.splash_container);
            if (f instanceof FragmentFileLoad){
                ((FragmentFileLoad) f).setSkipDisabled();
            }
        }

    }

    @Override
    public void fragmentMessage(int resultCode) {
        presenter.startFragment(resultCode);
    }

    @Override
    protected void onResume() {
        navigatorHolder.setNavigator(navigator);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        presenter.backPressed();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    private boolean permissionAlreadyGranted() {

        int result = ContextCompat.checkSelfPermission(appContext, Manifest.permission.CAMERA);
        int result2 = ContextCompat.checkSelfPermission(appContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(appContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED
                && result3 == PackageManager.PERMISSION_GRANTED)
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
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_CODE);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CAMERA_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(appContext, "Permission granted successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Select video"), REQUEST_PICK_IMAGE);
            } else {
                Toast.makeText(appContext, "Permission is denied!", Toast.LENGTH_SHORT).show();
                boolean showRationale = shouldShowRequestPermissionRationale( Manifest.permission.CAMERA );
                if (! showRationale) {
                    openSettingsDialog();
                }
            }
        }
    }
    private void openSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
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
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }

    @Override
    public void showingError(String error) {
        Toast.makeText(appContext, "Loading error. Try again later", Toast.LENGTH_SHORT).show();
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.splash_container);
        if (f instanceof FragmentFileLoad){
            ((FragmentFileLoad) f).setProgressBarInvisible();
        }
    }
}
