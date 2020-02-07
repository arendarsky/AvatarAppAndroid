package com.example.talentshow.presentation.star.mainscreen;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.R;
import com.example.talentshow.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class StarMainScreenPresenter extends MvpPresenter<StarMainScreenView>{

    private Router router;

    @Inject
    StarMainScreenPresenter(Router router){
        this.router = router;
    }

    boolean onNavClicked(int id){
        switch (id){
            case R.id.star_nav_casting:
            case R.id.star_nav_statistics:
                getViewState().showMessage("Nothing here yet");
                return true;
            case R.id.star_nav_rating:
                router.newRootScreen(new Screens.StarRatingScreen());
                return true;
        }
        return false;
    }
}
