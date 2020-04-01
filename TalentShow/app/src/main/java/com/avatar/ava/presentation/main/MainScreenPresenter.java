package com.avatar.ava.presentation.main;

import android.net.Uri;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.Screens;
import com.avatar.ava.R;
import com.avatar.ava.domain.Interactor;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainScreenPresenter extends MvpPresenter<MainScreenView>{

    private Router router;
    private Interactor interactor;

    private final int LOAD_NEW_VIDEO_SCREEN = 4;
    private final int LOAD_VIDEO = 5;
    private final int CAPTURE_VIDEO = 8;
    private final int CHOOSE_SECONDS_SCREEN = 6;
    private final int PROFILE_SETTINGS = 6;
    private final int PROFILE_CHANGE_PASSWORD = 7;


    private final String new_video = "Новое видео";
    private final String casting = "Кастинг";
    private final String rating = "Рейтинг";
    private final String profile = "Профиль";
    private final String notifications = "Уведомления";
    private final String best30 = "Лучшие 30";

    private final int SAVE_BUTTON = 0;
    private final int ADD_BUTTON = 1;
    private final int MENU_POINTS = 2;

    private final boolean SHOW_BACK = true;
    private final boolean HIDE_BACK = false;

    private List<PrevState> previousStates = new ArrayList<>();
    private Uri selectedFileUri;

    @Inject
    MainScreenPresenter(Router router, Interactor interactor){
        this.router = router;
        this.interactor = interactor;
    }

    boolean onNavClicked(int id){
        getViewState().clearTopView();
        getViewState().stopVideoFromCasting();
        switch (id){
            case R.id.nav_casting:
                getViewState().changeTitle(casting);
                getViewState().showAddButton();
                router.newRootScreen(new Screens.CastingScreen());
                return true;
            case R.id.nav_rating:
                getViewState().changeTitle(rating);
                router.newRootScreen(new Screens.RatingScreen());
                return true;
            case R.id.nav_notify:
                getViewState().changeTitle(notifications);
                router.newRootScreen(new Screens.NotificationsScreen());
                return true;
            case R.id.nav_profile:
                getViewState().changeTitle(profile);
                getViewState().showMenuPoints();
                getViewState().showExit();
                router.newRootScreen(new Screens.ProfileScreen());
                return true;
        }
        return false;
    }

    void changeFragment(int code){
        getViewState().clearTopView();
        switch (code){
            case LOAD_NEW_VIDEO_SCREEN:
                getViewState().hideBottomNavBar();
                getViewState().showBackButton();
                getViewState().showSaveButton();
                getViewState().changeTitle(new_video);
                previousStates.add(new PrevState(HIDE_BACK, casting, ADD_BUTTON));
                router.navigateTo(new Screens.FileLoadMainScreen());
                break;
            case LOAD_VIDEO:
                getViewState().pickVideo();
                break;
            case CAPTURE_VIDEO:
                getViewState().captureVideo();
                break;
            case PROFILE_SETTINGS:
                getViewState().changeTitle("Настройки");
                previousStates.add(new PrevState(false, profile, MENU_POINTS));
                router.navigateTo(new Screens.ProfileSettingsScreen());
                break;
            case PROFILE_CHANGE_PASSWORD:
                getViewState().changeTitle("Изм. пароля");
                getViewState().showSavePassword();
                router.navigateTo(new Screens.ChangePasswordScreen());
                break;
        }
    }

    public void openBest30Screen(Uri uri){
        selectedFileUri = uri;
        getViewState().clearTopView();
        getViewState().showBackButton();
        getViewState().changeTitle(best30);
        getViewState().showSaveButton();
        previousStates.add(new PrevState(SHOW_BACK, new_video, SAVE_BUTTON));
        router.navigateTo(new Screens.ChooseBestMainScreen(uri));
    }

    boolean backButtonPressed(boolean fileLoad){
        if(!previousStates.isEmpty()) {
            decodePrevState(previousStates.remove(previousStates.size() - 1));
            if (fileLoad)
                getViewState().showBottomNavBar();

            router.exit();
            return false;
        }
        else return true;
    }

    //Хранит информацию о прошлом фрагмента для вьюхи сверху при уходе на уровень выше
    private class PrevState {
        boolean backButton;
        String title;
        int code;
        PrevState(boolean a, String b, int c){
            backButton = a;
            title = b;
            code = c;
        }
    }

    private void retunToRoot(){
        decodePrevState(previousStates.remove(0));
        previousStates.clear();
        router.backTo(null);
        getViewState().showBottomNavBar();
    }

    private void decodePrevState(PrevState prevState){
        getViewState().clearTopView();
        if (prevState.backButton) getViewState().showBackButton();
        getViewState().changeTitle(prevState.title);
        switch (prevState.code){
            case SAVE_BUTTON:
                getViewState().showSaveButton();
                break;
            case ADD_BUTTON:
                getViewState().showAddButton();
                break;
            case MENU_POINTS:
                getViewState().showMenuPoints();
                break;
        }
    }

//    void composeVideo(Uri videoUri){
//        openSecondsScreen(videoUri);
//        interactor.composeVideo(videoUri)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
////                .doOnSubscribe(d -> openBest30Screen(videoUri))
//                .subscribe(new DisposableCompletableObserver() {
//                               @Override
//                               public void onComplete() {
//                                    openBest30Screen(videoUri);
//                               }
//
//                               @Override
//                               public void onError(Throwable e) {
//                                   getViewState().showMessage("Ошибка при сжатии видео");
//                               }
//                           }
//                );
//    }

    void uploadAndSetInterval(Float beginTime, Float endTime){
        if (endTime - beginTime < 30) {
            interactor.uploadAndSetInterval(selectedFileUri, beginTime, endTime)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableCompletableObserver() {
                                   @Override
                                   public void onComplete() {
                                       getViewState().setLoadVideoToServer(false);
                                       retunToRoot();
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       getViewState().hideProgressBar();
                                       getViewState().showMessage("Ошибка при загрузке видео");
                                   }
                               }
                    );
        }
        else getViewState().showMessage("Выбранный фрагмент больше 30 секунд");
    }

    void exitAcc(){
        interactor.exitFromAccount();
    }
}
