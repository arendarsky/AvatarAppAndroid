package com.avatar.ava.presentation.main;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;

import com.avatar.ava.presentation.main.fragments.FragmentFileLoadMain;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    private final int LOAD_NEW_VIDEO = 0;

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
        presenter.changeFragment(LOAD_NEW_VIDEO);
    }

    @Override
    public void hideBottomNavBar() {
        bottomNavigationView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showBottomNavBar() {
        bottomNavigationView.setVisibility(View.VISIBLE);
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
}
