package com.ss.core.effect;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ss.GMain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimationEffect {

    private static TextureAtlas textureAtlas = GMain.textureAtlas;
    public static HashMap<String, TextureRegion[]> anims = new HashMap<>();
    public static TextureRegion[] listFrameObj;

    public static void LoadAnimation() {
        listFrameObj = new TextureRegion[7];
        for (int i = 0; i < 7; i++)
            listFrameObj[i] = textureAtlas.findRegion("obj_anim"+i);

        anims.put("obj_anim", listFrameObj);
    }
}
