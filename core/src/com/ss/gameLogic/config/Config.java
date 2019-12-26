package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.ss.GMain;
import com.ss.core.util.GUI;

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

  ////////////////////////////////////NAME ITEM/////////////////////////////////////////////////////
  public static final String CIRCLE_1 = "item_circle_1";
  public static final String CIRCLE_2 = "item_circle_2";
  public static final String CIRCLE_3 = "item_circle_3";
  public static final String CIRCLE_4 = "item_circle_4";
  public static final String CIRCLE_5 = "item_circle_5";
  public static final String FLOWER_1 = "item_flower_1";
  public static final String FLOWER_2 = "item_flower_2";
  public static final String FLOWER_3 = "item_flower_3";
  public static final String HEXAGON_1 = "item_hexagon_1";
  public static final String HEXAGON_2 = "item_hexagon_2";
  public static final String HEXAGON_3 = "item_hexagon_3";
  public static final String PINWHEEL_1 = "item_pinwheel_1";
  public static final String PINWHEEL_2 = "item_pinwheel_2";
  public static final String PINWHEEL_3 = "item_pinwheel_3";
  public static final String SQUARE_1 = "item_square_1";
  public static final String SQUARE_2 = "item_square_2";
  public static final String SQUARE_3 = "item_square_3";
  public static final String SUN_FLOWER_1 = "item_sun_flower_1";
  public static final String SUN_FLOWER_2 = "item_sun_flower_2";
  public static final String SUN_FLOWER_3 = "item_sun_flower_3";
}
