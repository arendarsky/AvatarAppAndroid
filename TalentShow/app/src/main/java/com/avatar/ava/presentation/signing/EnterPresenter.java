package com.avatar.ava.presentation.signing;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.Screens;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.Screen;


@SuppressWarnings("FieldCanBeLocal")
@InjectViewState
public class EnterPresenter extends MvpPresenter<EnterView> {

    private Interactor interactor;
    private Router router;

    private final int START_REG = 0;
    private final int START_AUTH = 1;
    private final int SKIP_AUTH = 2;
    private final int CHOOSE_VIDEO = 3;
    private final int AUTH_FINISHED = 4;
    private final int VIDEO_SCREEN = 6;
    private final int BACK = 7;
    private final int VIDEO_SCREEN_JUST = 9;
    private final int CONFIRM_MAIL = 11;
    private final int SECOND_ONBOARD = 12;
    private static final int START_MAIN = 13;
    private static final int FIRST_ONBOARD = 14;
    private static final int RESET_PASSWORD = 15;


    private boolean registration = false;

    private Uri selectedFileUri;

    @Inject
    EnterPresenter(Interactor interactor, Router router) {
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
                break;
            case START_REG:
                router.navigateTo(new Screens.RegistrationScreen());
                registration = true;
                break;
            case AUTH_FINISHED:
//                if (registration) router.newRootScreen(new Screens.FileLoadJustScreen());
                if (registration) router.newRootScreen(new Screens.OnBoarding1Screen());
                else getViewState().startMain();
                break;
            case CHOOSE_VIDEO:
                getViewState().pickVideo();
                break;
            case VIDEO_SCREEN:
                router.navigateTo(new Screens.FileLoadScreen());
                break;
            case BACK:
                router.exit();
                break;
            case VIDEO_SCREEN_JUST:
                router.newRootScreen(new Screens.FileLoadJustScreen());
                break;
            case CONFIRM_MAIL:
                router.newRootScreen(new Screens.ConfirmMailScreen());
                break;
            case START_MAIN:
            case SKIP_AUTH:
                getViewState().startMain();
                break;
            case SECOND_ONBOARD:
                router.navigateTo(new Screens.OnBoarding2Screen());
                break;
            case FIRST_ONBOARD:
                router.navigateTo(new Screens.OnBoarding1Screen());
                break;
            case RESET_PASSWORD:
                router.navigateTo(new Screens.ResetPasswordScreen());
                break;
        }
    }

    void uploadVideoToServer(Float startTime, Float endTime){
        if (endTime - startTime < 30) {
            interactor.uploadAndSetInterval(selectedFileUri, startTime, endTime)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableCompletableObserver() {
                                   @Override
                                   public void onComplete() {
                                       startFragment(FIRST_ONBOARD);
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       getViewState().showingError("");
                                   }
                               }
               );
        }
        else getViewState().showingError("Выбранный фрагмент больше 30 секунд");
    }

    void openSecondsScreen(Uri fileUri){
        selectedFileUri = fileUri;
        router.navigateTo(new Screens.ChooseBestScreen(fileUri));
    }
}
