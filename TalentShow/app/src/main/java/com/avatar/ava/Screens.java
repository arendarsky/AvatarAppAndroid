package com.avatar.ava;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.avatar.ava.presentation.main.fragments.FragmentChooseBestMain;
import com.avatar.ava.presentation.main.fragments.FragmentFileLoadMain;
import com.avatar.ava.presentation.main.fragments.FullScreenVideoDialog;
import com.avatar.ava.presentation.main.fragments.casting.CastingFragment;
import com.avatar.ava.presentation.main.fragments.notifications.FragmentNotifications;
import com.avatar.ava.presentation.main.fragments.profile.ProfileFragment;
import com.avatar.ava.presentation.main.fragments.profile.profileSettings.changePassword.ChangePasswordFragment;
import com.avatar.ava.presentation.main.fragments.profile.profileSettings.ProfileSettingsFragment;
import com.avatar.ava.presentation.main.fragments.profile.publicProfile.PublicProfileFragment;
import com.avatar.ava.presentation.signing.fragments.ConfirmMailFragment;
import com.avatar.ava.presentation.signing.fragments.FragmentFileLoadJust;
import com.avatar.ava.presentation.signing.fragments.OnBoarding1Fragment;
import com.avatar.ava.presentation.signing.fragments.OnBoarding2Fragment;
import com.avatar.ava.presentation.signing.fragments.authorisation.AuthorisationFragment;
import com.avatar.ava.presentation.signing.fragments.registration.FragmentRegistration;
import com.avatar.ava.presentation.signing.fragments.ChooseAuthFragment;
import com.avatar.ava.presentation.signing.fragments.FragmentChooseVideoBest;
import com.avatar.ava.presentation.signing.fragments.FragmentEnterFileLoad;
import com.avatar.ava.presentation.main.fragments.rating.RatingFragment;

import ru.terrakok.cicerone.Screen;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static final class AuthorisationScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return AuthorisationFragment.newInstance();
        }
    }

    public static final class RegistrationScreen extends SupportAppScreen{

        @Override
        public Fragment getFragment() {
            return FragmentRegistration.newInstance();
        }
    }

    public static final class RatingScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return new RatingFragment();
        }
    }

    public static final class ChooseAuthScreen extends SupportAppScreen{

        @Override
        public Fragment getFragment() {
            return new ChooseAuthFragment();
        }
    }

    public static final class FileLoadScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return new FragmentEnterFileLoad();
        }
    }


    public static final class CastingScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return new CastingFragment();
        }
    }

    public static final class ChooseBestScreen extends SupportAppScreen{

        private Uri uri;

        public ChooseBestScreen(Uri uri) {
            super();
            this.uri = uri;
        }

        @Override
           public Fragment getFragment() {
               Bundle bundle = new Bundle();
               bundle.putParcelable("uri", uri);
               FragmentChooseVideoBest fragment = new FragmentChooseVideoBest();
               fragment.setArguments(bundle);
               return fragment;
           }
    }

    public static final class ChooseBestMainScreen extends SupportAppScreen{

        private Uri uri;

        public ChooseBestMainScreen(Uri uri) {
            super();
            this.uri = uri;
        }

        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            FragmentChooseBestMain fragment = new FragmentChooseBestMain();
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    public static final class ChangePasswordScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return new ChangePasswordFragment();
        }
    }

    public static final class FileLoadMainScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return new FragmentFileLoadMain();
        }
    }

    public static final class ProfileSettingsScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return new ProfileSettingsFragment();
        }
    }

    public static final class FileLoadJustScreen extends SupportAppScreen{

        @Override
        public Fragment getFragment() {
            return new FragmentFileLoadJust();
        }
    }

    public static final class ProfileScreen extends SupportAppScreen{

        @Override
        public Fragment getFragment() {
            return new ProfileFragment();
        }
    }

    public static final class NotificationsScreen extends SupportAppScreen{

        @Override
        public Fragment getFragment() {
            return new FragmentNotifications();
        }
    }

    public static final class ConfirmMailScreen extends SupportAppScreen{

        @Override
        public Fragment getFragment() {
            return new ConfirmMailFragment();
        }
    }

    public static final class PublicProfileScreen extends SupportAppScreen{

        private int id;

        public PublicProfileScreen(int id) {
            super();
            this.id = id;
        }

        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            PublicProfileFragment fragment = new PublicProfileFragment();
            bundle.putInt("id", this.id);
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    public static final class OnBoarding1Screen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return new OnBoarding1Fragment();
        }
    }

    public static final class OnBoarding2Screen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return new OnBoarding2Fragment();
        }
    }
}
