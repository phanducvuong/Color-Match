package com.ss.core.effect;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ss.GMain;

import java.util.HashMap;

public class AnimationEffect {

    private static TextureAtlas animAtlas = GMain.animAtlas;
    public static HashMap<String, TextureRegion[]> animLeft = new HashMap<>();
    public static HashMap<String, TextureRegion[]> animRight = new HashMap<>();
    public static TextureRegion[] listFrameL;
    public static TextureRegion[] listFrameR;

    public static void LoadAnimation() {
        listFrameL = new TextureRegion[3];
        listFrameR = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            listFrameL[i] = animAtlas.findRegion("pl_"+i);
            listFrameR[i] = animAtlas.findRegion("pr_"+i);
        }

        animRight.put("anim_left", listFrameL);
        animLeft.put("anim_right", listFrameR);
    }
}
