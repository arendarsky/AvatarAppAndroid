package com.avatar.ava.presentation.main;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;

import com.avatar.ava.presentation.main.fragments.FragmentChooseBestMain;
import com.avatar.ava.presentation.main.fragments.FragmentFileLoadMain;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import toothpick.Toothpick;

public class MainScreenActivity extends MvpAppCompatActivity implements MainScreenView, MainScreenPostman {

    @BindView(R.id.bottom_nav_bar)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.main_frame_text1)
    TextView fragmentHeader;

    @BindView(R.id.main_frame_save)
    TextView saveButton;

    @BindView(R.id.main_frame_add)
    View addButton;

    @BindView(R.id.main_frame_menu_points)
    View menuPoints;

    @BindView(R.id.main_frame_back)
    ConstraintLayout backButton;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    Context appContext;

    @InjectPresenter
    MainScreenPresenter presenter;

    private final int LOAD_NEW_VIDEO_SCREEN = 4;

    private final int CAMERA_CODE = 1;
    private final int REQUEST_PICK_IMAGE = 2;

    @ProvidePresenter
    MainScreenPresenter providePresenter(){
        return Toothpick.openScope(App.class).getInstance(MainScreenPresenter.class);
    }

    private Navigator navigator = new SupportAppNavigator(this,
            R.id.activity_main_frame_container);

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    return presenter.onNavClicked(menuItem.getItemId());
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Toothpick.inject(this, Toothpick.openScope(App.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.nav_casting);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }

    //TODO вызов метода ниже из фрагмента кастинга с нужным кодом. Все коды будут указаны в презентере

    @Override
    public void fragmentAction(int code) {
        presenter.changeFragment(code);
    }

    @Override
    public void changeTitle(String title) {
        fragmentHeader.setText(title);
    }

    @Override
    public void showBackButton() {
        backButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMenuPoints() {
        menuPoints.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSaveButton() {
        saveButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAddButton() {
        addButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearTopView() {
        backButton.setVisibility(View.INVISIBLE);
        menuPoints.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        addButton.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.main_frame_add)
    public void addButtonClicked(){
        presenter.changeFragment(LOAD_NEW_VIDEO_SCREEN);
    }

    @Override
    public void hideBottomNavBar() {
        bottomNavigationView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showBottomNavBar() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void pickVideo() {
        showBackButton();
        showSaveButton();
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
        if (requestCode == REQUEST_PICK_IMAGE && data != null){
            Log.d("Video", data.getData().toString());

            presenter.composeVideo(data.getData());
        }
    }

    @OnClick(R.id.main_frame_back)
    public void backButtonClicked(){
        presenter.backButtonPressed(
                getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_container)
                        instanceof FragmentFileLoadMain
        );
    }

//    @Override
//    public void onBackPressed() {
//        presenter.backButtonPressed(
//                getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_container)
//                        instanceof FragmentFileLoadMain
//        );
//    }


    @Override
    public void onBackPressed() {
        boolean closeApp = presenter.backButtonPressed(
                getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_container)
                        instanceof FragmentFileLoadMain
        );
        if (closeApp) super.onBackPressed();
    }

    @OnClick(R.id.main_frame_save)
    void saveClicked(){
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.activity_main_frame_container);
        if (currentFragment instanceof FragmentChooseBestMain){
            List<Float> tmp = ((FragmentChooseBestMain) currentFragment).getInterval();
            presenter.uploadAndSetInterval(tmp.get(0), tmp.get(1));
        }
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


}
