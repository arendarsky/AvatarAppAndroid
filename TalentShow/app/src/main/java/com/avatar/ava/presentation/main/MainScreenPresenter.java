package com.avatar.ava.presentation.main;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.avatar.ava.R;
import com.avatar.ava.Screens;
import com.avatar.ava.domain.Interactor;
import com.avatar.ava.presentation.main.fragments.casting.CastingFragment;
import com.avatar.ava.presentation.main.fragments.notifications.FragmentNotifications;
import com.avatar.ava.presentation.main.fragments.rating.RatingFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@SuppressWarnings("FieldCanBeLocal")
@InjectViewState
public class MainScreenPresenter extends MvpPresenter<MainScreenView>{

    private Router router;
    private Interactor interactor;
    private FirebaseAnalytics analytics;

    private final int LOAD_NEW_VIDEO_SCREEN = 4;
    private final int LOAD_VIDEO = 5;
    private final int CAPTURE_VIDEO = 8;
    private final int PROFILE_SETTINGS = 6;
    private final int PROFILE_CHANGE_PASSWORD = 7;
    private final int BACK = 9;

    private final int SAVE_BUTTON = 0;
    private final int ADD_BUTTON = 1;
    private final int MENU_POINTS = 2;
    private final int NOTHING = 4;

    private final boolean SHOW_BACK = true;
    private final boolean HIDE_BACK = false;

    private List<PrevState> previousStates = new ArrayList<>();
    private Uri selectedFileUri;


    @Inject
    MainScreenPresenter(Router router, Interactor interactor, FirebaseAnalytics analitics){
        this.router = router;
        this.interactor = interactor;
        this.analytics = analitics;
    }

    @StateStrategyType(SingleStateStrategy.class)
    boolean onNavClicked(int id){
        getViewState().clearTopView();
        getViewState().stopVideoFromCasting();
        switch (id){
            case R.id.nav_casting:
                getViewState().changeTitle(MainScreenTitles.CASTING);
                getViewState().showAddButton();
                getViewState().showInfoIcon();
                router.newRootScreen(new Screens.CastingScreen());
                return true;
            case R.id.nav_rating:
                getViewState().changeTitle(MainScreenTitles.RATING);
                getViewState().showInfoIcon();
                router.newRootScreen(new Screens.RatingScreen());
                return true;
            case R.id.nav_notify:
                getViewState().changeTitle(MainScreenTitles.NOTIFICATIONS);
                getViewState().showInfoIcon();
                router.newRootScreen(new Screens.NotificationsScreen());
                return true;
            case R.id.nav_profile:
                getViewState().changeTitle(MainScreenTitles.PROFILE);
                getViewState().showMenuPoints();
                getViewState().showInfoIcon();
                router.newRootScreen(new Screens.ProfileScreen());
                return true;
        }
        return false;
    }

    @StateStrategyType(AddToEndSingleStrategy.class)
    void changeFragment(int code){
        getViewState().clearTopView();
        switch (code){
            case LOAD_NEW_VIDEO_SCREEN:
                getViewState().hideBottomNavBar();
                getViewState().showBackButton();
                getViewState().showSaveButton();
                getViewState().changeTitle(MainScreenTitles.NEW_VIDEO);
                previousStates.add(new PrevState(HIDE_BACK, MainScreenTitles.CASTING, ADD_BUTTON));
                router.navigateTo(new Screens.FileLoadMainScreen());
                break;
            case LOAD_VIDEO:
                getViewState().pickVideo();
                break;
            case CAPTURE_VIDEO:
                getViewState().captureVideo();
                break;
            case PROFILE_SETTINGS:
                getViewState().changeTitle(MainScreenTitles.SETTINGS);
                getViewState().showBackButton();
                getViewState().showSaveButton();
                previousStates.add(new PrevState(HIDE_BACK, MainScreenTitles.PROFILE, MENU_POINTS));
                router.navigateTo(new Screens.ProfileSettingsScreen());
                break;
            case PROFILE_CHANGE_PASSWORD:
                getViewState().changeTitle(MainScreenTitles.CHANGE_PASSWORD);
                getViewState().showSavePassword();
                getViewState().showBackButton();
                previousStates.add(new PrevState(SHOW_BACK, MainScreenTitles.SETTINGS, SAVE_BUTTON));
                router.navigateTo(new Screens.ChangePasswordScreen());
                break;
            case BACK:
                this.backButtonPressed(false);
                break;
        }
    }

    @StateStrategyType(AddToEndSingleStrategy.class)
    void openBest30Screen(Uri uri){
        selectedFileUri = uri;
        getViewState().clearTopView();
        getViewState().showBackButton();
        getViewState().changeTitle(MainScreenTitles.BEST30);
        getViewState().showSaveButton();
        previousStates.add(new PrevState(SHOW_BACK, MainScreenTitles.NEW_VIDEO, SAVE_BUTTON));
        router.navigateTo(new Screens.ChooseBestMainScreen(uri));
    }

    @StateStrategyType(AddToEndSingleStrategy.class)
    void openPublicProfile(int id, Fragment prevScreen){
        getViewState().clearTopView();
        getViewState().showBackButton();
        getViewState().changeTitle(MainScreenTitles.PUBLIC_PROFILE);
        if (prevScreen instanceof CastingFragment)
            previousStates.add(new PrevState(HIDE_BACK, MainScreenTitles.CASTING, ADD_BUTTON));
        if (prevScreen instanceof RatingFragment)
            previousStates.add(new PrevState(HIDE_BACK, MainScreenTitles.RATING, NOTHING));
        if (prevScreen instanceof FragmentNotifications)
            previousStates.add(new PrevState(HIDE_BACK, MainScreenTitles.NOTIFICATIONS, NOTHING));
        router.navigateTo(new Screens.PublicProfileScreen(id));
    }

    @StateStrategyType(AddToEndSingleStrategy.class)
    void openInstruction(MainScreenTitles title) {
        getViewState().clearTopView();
        switch (title) {
            case CASTING: {
                previousStates.add(new PrevState(HIDE_BACK, title, ADD_BUTTON));
                break;
            }
            case PROFILE: {
                previousStates.add(new PrevState(HIDE_BACK, title, MENU_POINTS));
                break;
            }
            default: {
                previousStates.add(new PrevState(HIDE_BACK, title, NOTHING));
                break;
            }
        }
        router.navigateTo(new Screens.InstructionScreen(title));
    }

    @StateStrategyType(AddToEndSingleStrategy.class)
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

    private class PrevState {
        boolean backButton;
        MainScreenTitles title;
        int code;
        PrevState(boolean a, MainScreenTitles b, int c) {
            backButton = a;
            title = b;
            code = c;
        }
    }

    private void returnToRoot(){
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

    void uploadAndSetInterval(Float beginTime, Float endTime){
        if (endTime - beginTime <= 30) {
            Completable completable = interactor.uploadAndSetInterval(selectedFileUri, beginTime, endTime);
            if(completable == null){
                getViewState().showMessage("Ошибка при загрузке видео. Видео не найдено");
            }else completable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableCompletableObserver() {
                                   @Override
                                   protected void onStart() {
                                       super.onStart();
                                       returnToRoot();
                                   }

                                   @Override
                                   public void onComplete() {
                                       getViewState().hideProfileLoadingString();
                                       getViewState().showMessage("Видео успешно загрузилось");
                                   }

                                   @Override
                                   public void onError(Throwable e) {

                                       getViewState().hideProfileLoadingString();
                                       Log.d("Video_loading_error", e.toString());
//                                       getViewState().showMessage(e.toString());
                                       Bundle event = new Bundle();
                                       event.putString("Video_loading_error", e.toString());
                                       analytics.logEvent("loading_error", event);
                                       getViewState().showMessage("Ошибка при загрузке видео. Попробуйте позже");
                                   }
                               }
                    );
        }
        else {
            getViewState().showSaveButton();
        }
    }

    void setInterval(String name, Float start, Float end){
        Disposable disposable = interactor.setInterval(name, start, end).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            returnToRoot();
                        },
                        error -> {

                        });
    }

    void setFirebaseToken(String token){
        Disposable disposable = interactor.setFirebaseToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {

                        },
                        error -> {

                        });
    }

    void exitAcc(){
        interactor.exitFromAccount();
    }
}
