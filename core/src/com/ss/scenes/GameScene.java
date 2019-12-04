package com.ss.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import static com.badlogic.gdx.math.Interpolation.*;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.core.action.exAction.GTween;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GTools;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.logic.Logic;
import com.ss.gameLogic.logic.ShapeLogic;
import com.ss.gameLogic.objects.Object;

public class GameScene extends GScreen {

    private TextureAtlas textureAtlas = GMain.textureAtlas;
    private Group gMain = new Group();
    private Group gLogic = new Group();
    private Group gUI = new Group();
    private Object square, box;
    private IPlatform plf = GMain.platform;
    private Logic logic;
    private Stage s;
    private float gsWidth = GStage.getWorldWidth()/2;

    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        s = GStage.getStage();
        GStage.addToLayer(GLayer.ui, gMain);
        gMain.addActor(gUI);
        gMain.addActor(gLogic);
        gUI.setSize(720, 1280);
        gUI.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2, Align.center);

        createShapeMain();
        initAsset();
        initLogic();
        eventClick();

        createBox();
    }

    private void initLogic() {
        logic = Logic.getInstance();
        logic.degree = 45;
        logic.calRC(square.getShape());
    }

    private void initAsset() {
        Image bg = GUI.createImage(textureAtlas, "bg");
        assert bg != null;
        bg.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
        gMain.addActor(bg);

        gUI.setZIndex(1000);
        gLogic.setZIndex(1000);
    }

    private void eventClick() {
        stageClick();
    }

    private void stageClick() {
        s.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (x < gsWidth) {
                    square.getShape().addAction(rotateBy(-45, .15f, fastSlow));
                    square.setVerShapeMain(-45, .5f);
                }
                else {
                    square.getShape().addAction(rotateBy(45, .15f, fastSlow));
                    square.setVerShapeMain(45, .5f);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private void createShapeMain() {
        square = new Object(gUI, gLogic, "square");
        square.getShape().setOrigin(Align.center);
        square.getShape().setPosition(gUI.getWidth()/2, gUI.getHeight()/2, Align.center);
        square.getShape().setScale(.5f);
        square.setVerShapeMain(0, .5f);
    }

    private void createBox() {
        box = new Object(gUI, gLogic, "box");
        box.setPos(1);

        moveShape();
    }

    private void moveShape() {
        if (square != null && box != null
            && Intersector.overlapConvexPolygons(box.getPolygon(), square.getPolygon())) {
            Gdx.app.log("COLLISION", "COLLISION");
            return;
        }

        assert box != null;
        GTween.action(box.getShape(), moveBy(5, 5, .15f, linear), this::moveShape);
        box.setVertices(logic.calVertices(box.getShape(), 5, 1));
    }

    @Override
    public void run() {

    }
}
