package com.avatar.ava;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import toothpick.config.Module;

class NavigationModule extends Module{

    NavigationModule(){
        Cicerone<Router> cicerone = Cicerone.create();

        bind(Router.class).toInstance(cicerone.getRouter());

        bind(NavigatorHolder.class).toInstance(cicerone.getNavigatorHolder());
    }
}
