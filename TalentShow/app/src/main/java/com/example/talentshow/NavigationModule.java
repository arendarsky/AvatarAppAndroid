package com.example.talentshow;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import toothpick.config.Module;

public class NavigationModule extends Module{

    private Cicerone<Router> cicerone;

    public NavigationModule(){
        cicerone = Cicerone.create();

        bind(Router.class).toInstance(cicerone.getRouter());

        bind(NavigatorHolder.class).toInstance(cicerone.getNavigatorHolder());
    }
}
