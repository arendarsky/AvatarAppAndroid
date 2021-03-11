package com.avatar.ava.di;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import toothpick.config.Module;

public class NavigationModule extends Module{

    public NavigationModule(){
        Cicerone<Router> cicerone = Cicerone.create();

        bind(Router.class).toInstance(cicerone.getRouter());

        bind(NavigatorHolder.class).toInstance(cicerone.getNavigatorHolder());
    }
}
