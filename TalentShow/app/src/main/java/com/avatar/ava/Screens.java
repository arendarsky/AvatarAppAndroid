package com.avatar.ava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.avatar.ava.presentation.main.MainScreenActivity;
import com.avatar.ava.presentation.main.fragments.FragmentFileLoadMain;
import com.avatar.ava.presentation.main.fragments.casting.CastingFragment;
import com.avatar.ava.presentation.main.fragments.profile.ProfileFragment;
import com.avatar.ava.presentation.signing.fragments.FragmentFileLoadJust;
import com.avatar.ava.presentation.signing.fragments.authorisation.AuthorisationFragment;
import com.avatar.ava.presentation.signing.fragments.registration.FragmentRegistration;
import com.avatar.ava.presentation.signing.fragments.ChooseAuthFragment;
import com.avatar.ava.presentation.main.ActivityStarStatistics;
import com.avatar.ava.presentation.main.FragmentChooseVideoBest;
import com.avatar.ava.presentation.signing.fragments.FragmentEnterFileLoad;
import com.avatar.ava.presentation.main.fragments.rating.RatingFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static final class AuthorisationScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            AuthorisationFragment fragment = AuthorisationFragment.newInstance();
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    public static final class RegistrationScreen extends SupportAppScreen{

        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            FragmentRegistration fragmentRegistration = FragmentRegistration.newInstance();
            fragmentRegistration.setArguments(bundle);
            return fragmentRegistration;
        }
    }

    public static final class RatingScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            RatingFragment ratingFragment = new RatingFragment();
            ratingFragment.setArguments(bundle);
            return ratingFragment;
        }
    }

    public static final class ChooseAuthScreen extends SupportAppScreen{

        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            ChooseAuthFragment chooseAuthFragment = new ChooseAuthFragment();
            chooseAuthFragment.setArguments(bundle);
            return chooseAuthFragment;
        }
    }

    public static final class StarNameEnterScreen extends SupportAppScreen{
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, FragmentRegistration.class);
        }
    }

    public static final class FileLoadScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            FragmentEnterFileLoad fragment = new FragmentEnterFileLoad();
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    public static final class MainScreen extends SupportAppScreen{
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, MainScreenActivity.class);
        }
    }

    public static final class CastingScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            CastingFragment fragment = new CastingFragment();
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    public static final class ChooseBestScreen extends SupportAppScreen{

        //TODO раскоменить и удалить незакоменченное

           @Override
           public Fragment getFragment() {
               Bundle bundle = new Bundle();
               FragmentChooseVideoBest fragment = new FragmentChooseVideoBest();
               fragment.setArguments(bundle);
               return fragment;
           }

     //   @Override
     //   public Intent getActivityIntent(Context context) {
     //       return new Intent(context, FragmentChooseVideoBest.class);
     //   }
    }

    public static final class StarStatisticsScreen extends SupportAppScreen{
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, ActivityStarStatistics.class);
        }
    }

    public static final class FileLoadMainScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            FragmentFileLoadMain fragment = new FragmentFileLoadMain();
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    public static final class FileLoadJustScreen extends SupportAppScreen{

        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            FragmentFileLoadJust fragment = new FragmentFileLoadJust();
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    public static final class ProfileScreen extends SupportAppScreen{

        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            ProfileFragment fragment = new ProfileFragment();
            fragment.setArguments(bundle);
            return fragment;
        }
    }
}
