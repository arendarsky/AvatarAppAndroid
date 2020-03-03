package com.example.talentshow.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.R;
import com.example.talentshow.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainScreenPresenter extends MvpPresenter<MainScreenView>{

    private Router router;

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
            case R.id.nav_notify:
            case R.id.nav_contacts:
            case R.id.nav_profile:
                getViewState().showMessage("Nothing here yet");
                return true;
        }
        return false;
    }
}
