package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.mygdx.game.util.ScreenEnum;
import com.mygdx.game.util.ScreenManager;


public class MyGdxGame extends Game {

    @Override
    public void create () {
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU );
    }
}
