package com.avatar.ava.presentation.signing;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
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

import com.amplitude.api.Amplitude;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.MainScreenActivity;
import com.avatar.ava.presentation.signing.fragments.FragmentChooseVideoBest;

import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import toothpick.Toothpick;

@SuppressWarnings("FieldCanBeLocal")
public class EnterActivity extends MvpAppCompatActivity implements EnterView, RegAuthPostman {

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    Context appContext;

    @InjectPresenter
    EnterPresenter presenter;

    @ProvidePresenter
    EnterPresenter getPresenter() {
        return Toothpick.openScope(App.class).getInstance(EnterPresenter.class);
    }

    private Navigator navigator = new SupportAppNavigator(this, R.id.splash_container);
    private final int CAMERA_CODE = 1;
    private final int REQUEST_PICK_IMAGE = 2;
    private final int UPLOAD_VIDEO = 10;

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
    public void pickVideo() {
        if (permissionAlreadyGranted()) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Select video"), REQUEST_PICK_IMAGE);
        } else {
            requestPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && data != null) {
            presenter.openSecondsScreen(data.getData());
        }
    }

    @Override
    public void fragmentMessage(int resultCode) {
        if (resultCode == UPLOAD_VIDEO){
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.splash_container);
            List<Float> tmp;
            if (currentFragment instanceof FragmentChooseVideoBest) {
                tmp = ((FragmentChooseVideoBest) currentFragment).getInterval();
                presenter.uploadVideoToServer(tmp.get(0), tmp.get(1));
            }
        }
        presenter.startFragment(resultCode);
    }

    @Override
    protected void onResume() {
        navigatorHolder.setNavigator(navigator);
        super.onResume();
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
        return result == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED
                && result3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
                if (!showRationale) {
                    openSettingsDialog();
                }
            }
        }
    }

    private void openSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
        builder.setTitle("Required Permissions");
        builder.setMessage("This app require permission to use awesome feature. Grant them in app settings.");
        builder.setPositiveButton("Take Me To SETTINGS", (dialog, which) -> {
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }

    @Override
    public void showingError(String error) {
        Toast.makeText(appContext, "Loading error. Try again later", Toast.LENGTH_SHORT).show();
    }
}
