package com.example.talentshow.presentation.signing;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.Screens;
import com.example.talentshow.domain.Interactor;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;


@InjectViewState
public class SplashPresenter extends MvpPresenter<SplashView> {

    private Interactor interactor;
    private Router router;

    private final int START_REG = 0;
    private final int START_AUTH = 1;
    private final int SKIP_AUTH = 2;
    private final int LOAD_FILE = 3;
    private final int AUTH_FINISHED = 4;


    @Inject
    public SplashPresenter(Interactor interactor, Router router) {
        this.interactor = interactor;
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        router.navigateTo(new Screens.ChooseAuthScreen());
    }

    void checkAuth(){
        if (interactor.checkAuth()) getViewState().startMain();
    }

    void startFragment(int code){
        switch (code){
            case START_AUTH:
                router.navigateTo(new Screens.AuthorisationScreen());
            case START_REG:
                router.navigateTo(new Screens.RegistrationScreen());
            case SKIP_AUTH:
                getViewState().startMain();
        }
    }
}
