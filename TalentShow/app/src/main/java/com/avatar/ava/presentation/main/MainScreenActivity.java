package com.avatar.ava.presentation.main;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.amplitude.api.Amplitude;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.BottomSheetFragments.ProfileBottomSheet;
import com.avatar.ava.presentation.main.BottomSheetFragments.ProfileVideoBottomSheet;
import com.avatar.ava.presentation.main.fragments.FragmentChooseBestMain;
import com.avatar.ava.presentation.main.fragments.FragmentFileLoadMain;
import com.avatar.ava.presentation.main.fragments.FullScreenVideoDialog;
import com.avatar.ava.presentation.main.fragments.casting.CastingFragment;
import com.avatar.ava.presentation.main.fragments.profile.ProfileFragment;
import com.avatar.ava.presentation.main.fragments.profile.profileSettings.ProfileSettingsFragment;
import com.avatar.ava.presentation.main.fragments.profile.profileSettings.changePassword.ChangePasswordFragment;
import com.avatar.ava.presentation.signing.EnterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.EnumSet;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import toothpick.Toothpick;


@SuppressWarnings("FieldCanBeLocal")
public class MainScreenActivity extends MvpAppCompatActivity implements MainScreenView,
        MainScreenPostman, ProfileBottomSheet.ItemClickListener, ProfileVideoBottomSheet.ItemClickListener {

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

    @BindView(R.id.main_frame_save_profile_btn)
    TextView saveProfile;

    @BindView(R.id.main_frame_save_password_btn)
    TextView savePassword;

    @BindView(R.id.main_frame_back)
    ConstraintLayout backButton;

    @BindView(R.id.main_frame_exit)
    TextView exit;

    @BindView(R.id.main_frame_info_icon)
    ImageView info;

    @BindView(R.id.main_frame_profile_back)
    ConstraintLayout backProfileButton;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    Context appContext;

    @InjectPresenter
    MainScreenPresenter presenter;

    private final int LOAD_NEW_VIDEO_SCREEN = 4;
    private final int PROFILE_SETTINGS = 6;

    private final int CAMERA_CODE = 1;
    private final int REQUEST_PICK_IMAGE = 2;
    private final int CAPTURE_VIDEO = 3;

    private final EnumSet<MainScreenTitles> infoTitle = EnumSet.of(
            MainScreenTitles.CASTING,
            MainScreenTitles.RATING,
            MainScreenTitles.PROFILE,
            MainScreenTitles.NOTIFICATIONS);

    private MainScreenTitles currentScreen;

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

    private View.OnClickListener openInstructionListener = view ->
            presenter.openInstruction(currentScreen);

    String TAG = "FirebaseMessage";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Toothpick.inject(this, Toothpick.openScope(App.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        info.setOnClickListener(openInstructionListener);
        if (savedInstanceState == null) bottomNavigationView.setSelectedItemId(R.id.nav_casting);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        String id = task.getResult().getId();
                        Log.d(TAG, token + " " + id);
                        // Log and toast
                        /*String msg = getString(R.string.msg, token);
                        Log.d(TAG, msg);*/
                        Toast.makeText(MainScreenActivity.this, token, Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fragmentAction(int code) {
        presenter.changeFragment(code);
    }

    @Override
    public void openPublicProfile(int id) {
        stopVideoFromCasting();
        presenter.openPublicProfile(id,
                getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_container));
    }

    @Override
    public void closeFragment() {
        presenter.backButtonPressed(
                getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_container)
                        instanceof FragmentFileLoadMain
        );
    }

    @Override
    public void openFullScreen(String videoName) {
        FullScreenVideoDialog dialog = new FullScreenVideoDialog();
        Bundle bundle = new Bundle();
        bundle.putString("videoName", videoName);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), FullScreenVideoDialog.TAG);

    }

    @Override
    public void changeTitle(MainScreenTitles title) {
        fragmentHeader.setText(title.toString());
        if (infoTitle.contains(title)) {
            info.setVisibility(View.VISIBLE);
        }
        currentScreen = title;
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
    public void showSaveProfile(){
        saveProfile.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSavePassword(){
        savePassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void showExit(){
        exit.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInfoIcon(){
        info.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProfileBack(){
        backProfileButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearTopView() {
        backButton.setVisibility(View.INVISIBLE);
        menuPoints.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        addButton.setVisibility(View.INVISIBLE);
        saveProfile.setVisibility(View.INVISIBLE);
        savePassword.setVisibility(View.INVISIBLE);
        exit.setVisibility(View.INVISIBLE);
        info.setVisibility(View.INVISIBLE);
        backProfileButton.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.main_frame_add)
    public void addButtonClicked(){
        presenter.changeFragment(LOAD_NEW_VIDEO_SCREEN);
    }

    @OnClick(R.id.main_frame_menu_points)
    public void menuPointsClick(){
        ProfileBottomSheet profileBottomSheet =
                ProfileBottomSheet.newInstance();
        profileBottomSheet.show(getSupportFragmentManager(),
                ProfileBottomSheet.TAG);
    }

    @OnClick(R.id.main_frame_save_profile_btn)
    public void saveProfileClick(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        ProfileFragment profileFragment = (ProfileFragment) fragmentManager.findFragmentById(ProfileFragment.ProfileID);
        if (profileFragment != null) {
            Amplitude.getInstance().logEvent("saveprofile_button_tapped");
            profileFragment.editProfile();
        }
        clearTopView();
        changeTitle(MainScreenTitles.PROFILE);
        showExit();
        showMenuPoints();
    }

    @OnClick(R.id.main_frame_save_password_btn)
    public void savePasswordClick(){
        //changePass
        FragmentManager fragmentManager = getSupportFragmentManager();
        ChangePasswordFragment changePasswordFragment = (ChangePasswordFragment) fragmentManager.findFragmentById(ChangePasswordFragment.ChangePasswordID);
        if (changePasswordFragment != null) {
            changePasswordFragment.changePassword();
        }
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
        else requestPermission();
    }

    @Override
    public void captureVideo() {
        if (permissionAlreadyGranted()) {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(intent, CAPTURE_VIDEO);
        }
        else requestPermission();
    }

    @Override
    public void hideProgressBar() {
        Fragment fragment = getSupportFragmentManager().
                findFragmentById(R.id.activity_main_frame_container);
        if (fragment instanceof FragmentChooseBestMain) ((FragmentChooseBestMain) fragment).hideProgressBar();
    }

    @Override
    public void hideProfileLoadingString() {
        Fragment fragment = getSupportFragmentManager().
                findFragmentById(R.id.activity_main_frame_container);
        if (fragment instanceof ProfileFragment) ((ProfileFragment) fragment).hideLoadingString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && data != null)
            presenter.openBest30Screen(data.getData());
        if (requestCode == CAPTURE_VIDEO && data != null)
            presenter.openBest30Screen(data.getData());
    }

    @OnClick(R.id.main_frame_back)
    public void backButtonClicked(){
        presenter.backButtonPressed(
                getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_container)
                        instanceof FragmentFileLoadMain
        );
    }

    @Override
    public void onBackPressed() {
        if (!(getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_container)
                instanceof FullScreenVideoDialog)) {
            boolean closeApp = presenter.backButtonPressed(
                    getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_container)
                            instanceof FragmentFileLoadMain
            );
            if (closeApp) super.onBackPressed();
        }
        else super.onBackPressed();
    }

    @Override
    public void setLoadVideoToServer(boolean loadVideoToServer) {

    }

    @OnClick(R.id.main_frame_save)
    void saveClicked(){
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.activity_main_frame_container);

        if (currentFragment instanceof FragmentChooseBestMain){
            Amplitude.getInstance().logEvent("newvideo_save_button_tapped");
            List<Float> tmp = ((FragmentChooseBestMain) currentFragment).getInterval();
            saveButton.setVisibility(View.INVISIBLE);
            presenter.uploadAndSetInterval(tmp.get(0), tmp.get(1));
        }

        if (currentFragment instanceof ProfileSettingsFragment) presenter.backButtonPressed(false);

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


    @OnClick(R.id.main_frame_profile_back)
    public void profileBackClicked(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        ProfileFragment profileFragment = (ProfileFragment) fragmentManager.findFragmentById(ProfileFragment.ProfileID);
        if (profileFragment != null) {
            profileFragment.backEdit();
        }
        clearTopView();
        changeTitle(MainScreenTitles.PROFILE);
        showExit();
        showMenuPoints();
    }

    @Override
    public void onItemClick(int item) {
        switch (item){
            case ProfileBottomSheet.EDIT:
                Amplitude.getInstance().logEvent("editprofile_button_tapped");
                FragmentManager fragmentManager = getSupportFragmentManager();
                ProfileFragment profileFragment = (ProfileFragment) fragmentManager.findFragmentById(ProfileFragment.ProfileID);
                if (profileFragment != null) {
                    profileFragment.editProfile();
                }
                clearTopView();
                changeTitle(MainScreenTitles.CHANGE_PROFILE);
                showProfileBack();
                showSaveProfile();
                break;
            case ProfileBottomSheet.SETTINGS:
                presenter.changeFragment(PROFILE_SETTINGS);
        }
    }

    public MainScreenPresenter getPresenter(){
        return presenter;
    }

    @Override
    public void onItemVideoClick(int item) {
        Log.d("ProfileLog", "main");
        FragmentManager fragmentManager = getSupportFragmentManager();
        ProfileFragment profileFragment = (ProfileFragment) fragmentManager.findFragmentById(ProfileFragment.ProfileID);
        switch (item){
            case ProfileVideoBottomSheet.DELETE:
                if (profileFragment != null) {
                    profileFragment.deleteVideo();
                }
                break;
            case ProfileVideoBottomSheet.CASTING:
                if (profileFragment != null) {
                    profileFragment.setCastingVideo();
                }
                break;
        }
    }

    @OnClick(R.id.main_frame_exit)
    public void exitFromAcc(){
        presenter.exitAcc();
        startActivity(new Intent(appContext, EnterActivity.class));
        finish();
    }

    @Override
    public void stopVideoFromCasting(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        CastingFragment castingFragment = null;
        if (fragmentManager.findFragmentById(CastingFragment.CASTING_ID) instanceof CastingFragment){
            castingFragment = (CastingFragment) fragmentManager.findFragmentById(CastingFragment.CASTING_ID);
        }
        if(castingFragment != null)
            castingFragment.stopVideo();
    }
}
