package com.example.talentshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.talentshow.presentation.signing.ChooseRoleActivity;
import com.example.talentshow.presentation.signing.EmailEnterActivity;
import com.example.talentshow.presentation.signing.EmailSuccessCodeActivity;
import com.example.talentshow.presentation.star.ActivityStarNameEnter;
import com.example.talentshow.presentation.star.ActivityStarStatistics;
import com.example.talentshow.presentation.star.ActivityStarVideoBest;
import com.example.talentshow.presentation.star.fileload.ActivityStarFileLoad;
import com.example.talentshow.presentation.star.rating.StarRatingFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static final class StarRatingScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            StarRatingFragment starRatingFragment = new StarRatingFragment();
            starRatingFragment.setArguments(bundle);
            return starRatingFragment;
        }
    }

    public static final class ChooseRoleScreen extends SupportAppScreen{
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, ChooseRoleActivity.class);
        }
    }

    public static final class EmailEnterScreen extends SupportAppScreen{
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, EmailEnterActivity.class);
        }
    }

    public static final class EmailSuccessCodeScreen extends SupportAppScreen{
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, EmailSuccessCodeActivity.class);
        }
    }

    public static final class StarNameEnterScreen extends SupportAppScreen{
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, ActivityStarNameEnter.class);
        }
    }

    public static final class StarFileLoadScreen extends SupportAppScreen{
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, ActivityStarFileLoad.class);
        }
    }

    public static final class StarMainScreen extends SupportAppScreen{
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, StarMainScreen.class);
        }
    }

    public static final class StarVideoBestScreen extends SupportAppScreen{
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, ActivityStarVideoBest.class);
        }
    }

    public static final class StarStatisticsScreen extends SupportAppScreen{
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, ActivityStarStatistics.class);
        }
    }
}
