package com.avatar.ava.presentation.main.fragments.instruction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.MainScreenTitles;

import javax.inject.Inject;

@InjectViewState
public class InstructionPresenter extends MvpPresenter<InstructionView> {

    private Context appContext;

    @Inject
    InstructionPresenter(Context appContext) {
        this.appContext = appContext;
    }

    void setInfo(MainScreenTitles title) {
        switch (title) {
            case RATING: {
                getViewState().setInfoText(appContext.getResources().getString(R.string.info_rating));
                break;
            }
            case CASTING: {
                String like = "%like%";
                String dislike = "%dislike%";
                String plus = "%plus%";
                String infoText = appContext.getResources().getString(R.string.info_casting);

                Drawable likeImageDr = appContext.getResources().getDrawable(R.drawable.like);
                Drawable dislikeImageDr = appContext.getResources().getDrawable(R.drawable.dislike);
                Drawable plusImageDr = appContext.getResources().getDrawable(R.drawable.plus_icon);
                likeImageDr.setBounds(0, 0, 33, 30);
                dislikeImageDr.setBounds(0, 0, 28, 28);
                plusImageDr.setBounds(0, 0, 28, 28);

                Spannable span = Spannable.Factory.getInstance().newSpannable(infoText);

                span.setSpan(new ImageSpan(likeImageDr, DynamicDrawableSpan.ALIGN_BASELINE),
                        infoText.indexOf(like), infoText.indexOf(like) + like.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                span.setSpan(new ImageSpan(dislikeImageDr, DynamicDrawableSpan.ALIGN_BASELINE),
                        infoText.indexOf(dislike), infoText.indexOf(dislike) + dislike.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                span.setSpan(new ImageSpan(plusImageDr, DynamicDrawableSpan.ALIGN_BASELINE),
                        infoText.indexOf(plus), infoText.indexOf(plus) + plus.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                getViewState().setInfoText(span);
                break;
            }
            case PROFILE: {
                getViewState().setInfoText(appContext.getResources().getString(R.string.info_profile));
                break;
            }
            case NOTIFICATIONS: {
                getViewState().setInfoText(appContext.getResources().getString(R.string.info_notify));
                break;
            }
        }
    }
}
