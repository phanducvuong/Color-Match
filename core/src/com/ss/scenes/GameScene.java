package com.ss.scenes;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;

public class GameScene extends GScreen {

    private TextureAtlas GSAtlas;
    private Group group = new Group();

    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        GStage.addToLayer(GLayer.ui, group);
        GSAtlas = GAssetsManager.getTextureAtlas("menu.atlas");
        Image image = GUI.createImage(GSAtlas, "newgame");
        image.setPosition(GMain.screenWidth / 2, GMain.screenHeight / 2, Align.center);
        group.addActor(image);
        group.clear();
    }

    @Override
    public void run() {

    }
}
