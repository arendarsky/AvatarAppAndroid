package com.avatar.ava.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.Screens;
import com.avatar.ava.R;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.Screen;

@InjectViewState
public class MainScreenPresenter extends MvpPresenter<MainScreenView>{

    private Router router;

    private final int LOAD_NEW_VIDEO = 0;

    private final String new_video = "Новое видео";
    private final String casting = "Кастинг";
    private final String rating = "Рейтинг";
    private final String profile = "Профиль";
    private final String notifications = "Уведомления";

    private final int SAVE_BUTTON = 0;
    private final int ADD_BUTTON = 1;
    private final int MENU_POINTS = 2;

    private List<PrevState> previousStates = new ArrayList<>();

    @Inject
    MainScreenPresenter(Router router){
        this.router = router;
    }

    boolean onNavClicked(int id){
        getViewState().clearTopView();
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
            case R.id.nav_contacts:
                getViewState().showMessage("Nothing here yet");
                return true;
            case R.id.nav_profile:
                getViewState().changeTitle(profile);
                router.newRootScreen(new Screens.ProfileScreen());
                return true;
        }
        return false;
    }

    void changeFragment(int code){
        getViewState().clearTopView();
        switch (code){
            case LOAD_NEW_VIDEO:
                getViewState().hideBottomNavBar();
                getViewState().showBackButton();
                getViewState().showSaveButton();
                getViewState().changeTitle(new_video);
                previousStates.add(new PrevState(false, casting, ADD_BUTTON));
                router.navigateTo(new Screens.FileLoadMainScreen());
                break;
        }
    }

    boolean backButtonPressed(boolean fileLoad){
        if(!previousStates.isEmpty()) {
            decodePrevState(previousStates.remove(previousStates.size() - 1));
            if (fileLoad)
                getViewState().showBottomNavBar();

            router.backTo(null);
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
}
