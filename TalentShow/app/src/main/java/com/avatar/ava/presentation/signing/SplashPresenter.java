package com.avatar.ava.presentation.signing;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.Screens;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;


@InjectViewState
public class SplashPresenter extends MvpPresenter<SplashView> {

    private Interactor interactor;
    private Router router;

    private final int START_REG = 0;
    private final int START_AUTH = 1;
    private final int SKIP_AUTH = 2;
    private final int CHOOSE_VIDEO = 3;
    private final int AUTH_FINISHED = 4;
    private final int LOAD_AVATAR = 5;
    private final int VIDEO_SCREEN = 6;
    private final int BACK = 7;
    private final int CHOOSE_SECONDS = 8;

    @Inject
    public SplashPresenter(Interactor interactor, Router router) {
        this.interactor = interactor;
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        router.newRootScreen(new Screens.ChooseAuthScreen());
    }

    void checkAuth(){
        if (interactor.checkAuth()) getViewState().startMain();
    }

    void startFragment(int code){
        switch (code){
            case START_AUTH:
                router.navigateTo(new Screens.AuthorisationScreen());
//                router.navigateTo(new Screens.FileLoadScreen());
                break;
            case START_REG:
                router.navigateTo(new Screens.RegistrationScreen());
                break;
            case SKIP_AUTH:
            case AUTH_FINISHED:
                getViewState().startMain();
                break;
            case LOAD_AVATAR:
                getViewState().loadPhotoForAvatar();
                break;
            case CHOOSE_VIDEO:
                getViewState().pickVideo();
                break;
            case VIDEO_SCREEN:
                router.navigateTo(new Screens.FileLoadScreen());
                break;
            case BACK:
                router.backTo(null);
                break;
            case CHOOSE_SECONDS:
                router.navigateTo(new Screens.ChooseBestScreen());
                break;

        }
    }

    void uploadVideoToServer(Uri videoUri){
//        Disposable disposable = interactor.getUnwatchedVideos(1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(arrayList -> getViewState().startingNextActivity(),
//                        e -> getViewState().showingError());
        Disposable disposable = interactor.uploadVideo(videoUri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().startMain(),
                        e -> getViewState().showingError(""));
    }

    void backPressed(){
//        router.backTo();
    }
}
