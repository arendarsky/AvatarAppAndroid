package com.example.talentshow.presentation.signing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.CastingFragment;
import com.example.talentshow.presentation.star.maincontainer.StarMainScreenActivity;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import toothpick.Toothpick;

public class SplashActivity extends MvpAppCompatActivity implements SplashView, RegAuthPostman {

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    Context appContext;

    @InjectPresenter
    SplashPresenter presenter;

    @ProvidePresenter
    SplashPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(SplashPresenter.class);
    }

    private Navigator navigator = new SupportAppNavigator(this, R.id.splash_container);
    private final int PICK_IMAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @Override
    public void startMain() {
        startActivity(new Intent(appContext, CastingFragment.class));
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null){
            //TODO Добавить какую-то реализацию сохранения фотографии
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
}
