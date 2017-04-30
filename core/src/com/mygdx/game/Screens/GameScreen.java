package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.util.ScreenEnum;
import com.mygdx.game.util.UIFactory;

public class GameScreen extends AbstractScreen implements Runnable{

    private Texture txtrBg;
    private Texture txtrBack,txtrLost;
    private Texture txtrLevelImage;

    // Current level
    private int level;

    public Thread t1;
    public GameScreen(Integer level) {
        super();
        this.level = level.intValue();
        txtrBg   = new Texture( Gdx.files.internal("img/game_bg.png") );
        txtrBack = new Texture( Gdx.files.internal("img/btn_back.png") );
        txtrLost = new Texture( Gdx.files.internal("img/lose.png") );
       new Thread(GameScreen.this).start();
    }

    @Override
    public void buildStage() {
        // Adding actors
        //Image bg = new Image(txtrBg);
        //addActor(bg);
        ImageButton btnBack = UIFactory.createButton(txtrBack);
        btnBack.setPosition(260.f, 220.f, Align.center);
        addActor(btnBack);




        txtrLevelImage = new Texture( Gdx.files.internal("img/btn_level_" + level + ".png") );
        Image imgLevel = new Image(txtrLevelImage);
        imgLevel.setPosition(getWidth() / 2, getHeight() / 2, Align.center);

         btnBack.addListener( UIFactory.createListener( ScreenEnum.LEVEL_SELECT ) );

    }

    @Override
    public void dispose() {
        super.dispose();
        txtrBg.dispose();
        txtrBack.dispose();
        txtrLevelImage.dispose();
    }

    @Override
    public void run() {


        while (true)
        {


            if(curukYedimi==true)
            {
                ImageButton btnLost = UIFactory.createButton(txtrLost);

                try {

                    addActor(btnLost);
                    btnLost.addListener( UIFactory.createListener( ScreenEnum.LEVEL_SELECT ));

             curukYedimi =false;
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }
}