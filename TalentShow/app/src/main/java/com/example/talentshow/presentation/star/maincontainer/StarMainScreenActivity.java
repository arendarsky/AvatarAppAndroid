package com.example.talentshow.presentation.star.maincontainer;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.talentshow.App;
import com.example.talentshow.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import toothpick.Toothpick;

public class StarMainScreenActivity extends MvpAppCompatActivity implements StarMainScreenView{

    @BindView(R.id.star_bottom_nav_bar)
    BottomNavigationView bottomNavigationView;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    Context appContext;

    @InjectPresenter
    StarMainScreenPresenter presenter;

    @ProvidePresenter
    StarMainScreenPresenter providePresenter(){
        return Toothpick.openScope(App.class).getInstance(StarMainScreenPresenter.class);
    }

    private Navigator navigator = new SupportAppNavigator(this,
            R.id.activity_star_frame_container);

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
        setContentView(R.layout.activity_star_frame);
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.star_nav_rating);
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
}
