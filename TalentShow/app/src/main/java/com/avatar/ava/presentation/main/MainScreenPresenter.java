package com.avatar.ava.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.Screens;
import com.avatar.ava.R;


import javax.inject.Inject;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.Screen;

@InjectViewState
public class MainScreenPresenter extends MvpPresenter<MainScreenView>{

    private Router router;

    private final int LOAD_NEW_VIDEO = 0;

    @Inject
    MainScreenPresenter(Router router){
        this.router = router;
    }

    boolean onNavClicked(int id){
        switch (id){
            case R.id.nav_casting:
                router.newRootScreen(new Screens.CastingScreen());
                return true;
            case R.id.nav_rating:
                router.newRootScreen(new Screens.StarRatingScreen());
                return true;
            case R.id.nav_notify:
            case R.id.nav_contacts:
            case R.id.nav_profile:
                getViewState().showMessage("Nothing here yet");
                return true;
        }
        return false;
    }

    void changeFragment(int code){
        switch (code){
            case LOAD_NEW_VIDEO:
                router.navigateTo(new Screens.FileLoadScreen());
                break;
        }
    }
}
