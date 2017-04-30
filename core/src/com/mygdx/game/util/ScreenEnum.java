package com.mygdx.game.util;

import com.mygdx.game.Screens.AbstractScreen;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.LevelSelectScreen;
import com.mygdx.game.Screens.MainMenuScreen;

public enum ScreenEnum {

    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },

    LEVEL_SELECT {
        public AbstractScreen getScreen(Object... params) {
            return new LevelSelectScreen();
        }
    },

    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen((Integer) params[0]);
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}
