package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.ss.GMain;

import java.util.ArrayList;
import java.util.List;

public class Config {
  public static float ratio = Gdx.graphics.getWidth() / 720;
  public static final float MOVEBY = 600;
  public static final float ODDPOINTSTART = 31;

  //time for obj in pos1 and pos 4
  public static final float DELTATIME = 2f;

  //remote config
  public static float TIMESHOW_MAX = GMain.platform.GetConfigFloatValue("TIMESHOW_MAX", 1.15f);
  public static float DURATION_MAX = GMain.platform.GetConfigIntValue("DURATION_MAX", 6);
  public static int NUMOBJECT_NEXTLEVEL = GMain.platform.GetConfigIntValue("NUMOBJECT_NEXTLEVEL", 10);

  public static final Vector2 POST1 = new Vector2(60, 340);
  public static final Vector2 POST2 = new Vector2(342, 220);
  public static final Vector2 POST3 = new Vector2(624, 340);
  public static final Vector2 POST4 = new Vector2(624, 900);
  public static final Vector2 POST5 = new Vector2(342, 1020);
  public static final Vector2 POST6 = new Vector2(60, 900);

  public static final Vector2 POST_BACK1 = new Vector2(23, 280);
  public static final Vector2 POST_BACK2 = new Vector2(337, 146);
  public static final Vector2 POST_BACK3 = new Vector2(651, 280);
  public static final Vector2 POST_BACK4 = new Vector2(649, 905);
  public static final Vector2 POST_BACK5 = new Vector2(335, 1040);
  public static final Vector2 POST_BACK6 = new Vector2(20, 905);

  public static List<Vector2> arrPos = new ArrayList<>();
  public static void addArrPos() {
    arrPos.add(POST1);
    arrPos.add(POST2);
    arrPos.add(POST3);
    arrPos.add(POST4);
    arrPos.add(POST5);
    arrPos.add(POST6);
  }
}
