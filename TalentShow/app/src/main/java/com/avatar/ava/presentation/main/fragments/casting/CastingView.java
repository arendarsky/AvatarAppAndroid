package com.avatar.ava.presentation.main.fragments.casting;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.avatar.ava.domain.entities.PersonDTO;

public interface CastingView extends MvpView {

    void likeClicked();
    void dislikeClicked();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void loadNewVideo(PersonDTO personDTO);
    void showError(String error);

    void showNoMoreVideos();
    void doSwipe(boolean swipe);
}
