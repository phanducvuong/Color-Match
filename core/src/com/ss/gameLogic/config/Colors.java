package com.ss.gameLogic.config;

import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;
import java.util.List;

public class Colors {

  private static Colors instance;

  public static final Color WHITE = new Color(255/255f, 255/255f, 255/255f, 1f);
  public static final Color DOVE_GRAY = new Color(101/255f, 101/255f, 101/255f, 1f);

  //sun_flower_1
  private static final Color YELLOW_ORANGE_SUN_FLOWER = new Color(255/255f, 166/255f, 59/255f, 1f);
  private static final Color ROYAL_PURPLE_SUN_FLOWER = new Color(90/255f, 54/255f, 173/255f, 1f);

  //sun_flower_2
  private static final Color ROMAN_SUN_FLOWER = new Color(224/255f, 90/255f, 90/255f, 1f);
  private static final Color WINE_BERRY = new Color(96/225f, 27/255f, 63/255f, 1f);

  //sun_flower_3
  private static final Color GAMBOGE_SUN_FLOWER = new Color(227/255f, 150/255f, 14/255f, 1f);
  private static final Color CRETE_SUN_FLOWER = new Color(105/225f, 131/255f, 41/255f, 1f);

  //circle_1
  private static final Color BUTTERCUP = new Color(245/255f, 172/255f, 27/255f, 1f);
  private static final Color VERDIGRIS = new Color(88/255f, 92/255f, 53/255f, 1f);

  //circle_2
  private static final Color VIVID_TANGERINE = new Color(255/255f, 156/255f, 117/255f, 1f);
  private static final Color CERISE = new Color(214/255f, 58/255f, 159/255f, 1f);

  //circle_3
  private static final Color MONTE_CARLO = new Color(129/255f, 204/255f, 190/255f, 1f);
  private static final Color STUDIO = new Color(116/255f, 81/255f, 168/255f, 1f);

  //circle_4
  private static final Color INDOCHINE = new Color(199/255f, 111/255f, 0/255f, 1f);
  private static final Color CAFE_ROYALE = new Color(120/255f, 48/255f, 12/255f, 1f);

  //circle_5
  private static final Color SUNSET_ORANGE = new Color(255/255f, 82/255f, 59/255f, 1f);
  private static final Color PAPRIKA = new Color(138/255f, 0/255f, 53/255f, 1f);

  //flower_1
  private static final Color ROMAN_FLOWER = new Color(222/255f, 100/255f, 100/255f, 1f);
  private static final Color NIGHT_SHADZ = new Color(158/255f, 50/255f, 95/255f, 1f);

  //flower_2
  private static final Color GAMBOGE_FLOWER = new Color(224/255f, 163/255f, 18/255f, 1f);
  private static final Color FERN_FROND = new Color(92/225f, 102/255f, 29/255f, 1f);

  //flower_3
  public static final Color CHETWODE_BLUE = new Color(125/255f, 164/255f, 224/255f, 1f);
  public static final Color GIGAS = new Color(93/225f, 58/255f, 143/255f, 1f);

  //square_1
  public static final Color MONA_LISA = new Color(255/255f, 150/255f, 150/255f, 1f);
  public static final Color ANAKIWA = new Color(140/255f, 190/255f, 255/255f, 1f);

  //square_2
  private static final Color YELLOW_ORANGE_SQUARER = new Color(255/255f, 166/255f, 59/255f, 1f);
  private static final Color OLD_COPPER = new Color(110/255f, 75/255f, 44/255f, 1f);

  //square_3
  private static final Color BRICK_RED = new Color(184/255f, 42/255f, 64/255f, 1f);
  private static final Color CHATHAMS_BLUE = new Color(25/255f, 69/255f, 114/255f, 1f);

  //hexagon_1
  private static final Color RAZZMATAZZ = new Color(237/255f, 18/255f, 80/255f, 1f);
  private static final Color COCOA_BROWN = new Color(60/255f, 37/255f, 37/255f, 1f);

  //hexagon_2
  private static final Color MEDIUM_RED_VIOLET = new Color(186/255f, 43/255f, 181/255f, 1f);
  private static final Color ROSE_BUD_CHERRY = new Color(128/255f, 16/255f, 68/255f, 1f);

  //hexagon_3
  private static final Color PINE_GREEN = new Color(0/255f, 112/255f, 120/255f, 1f);
  private static final Color PORT_GORE = new Color(28/255f, 35/255f, 65/255f, 1f);

  //pinwheel_1
  private static final Color SAN_MARINO = new Color(70/255f, 107/255f, 181/255f, 1f);
  private static final Color HOKEY_POKEY = new Color(199/255f, 151/255f, 47/255f, 1f);

  //pinwheel_2
  private static final Color PERPLE_HEART = new Color(151/255f, 55/255f, 189/255f, 1f);
  private static final Color ROMAN = new Color(214/255f, 86/255f, 86/255f, 1f);

  //pinwheel_3
  private static final Color BLUE_CHILL = new Color(13/255f, 163/255f, 156/255f, 1f);
  private static final Color SEANCE = new Color(117/255f, 36/255f, 138/255f, 1f);

  public static Colors getInstance() {
    return instance==null ? instance = new Colors() : instance;
  }

  public List<Color> colors(String object) {
    List<Color> colors = new ArrayList<>();

    switch (object) {
      case "square_1":
        colors.add(MONA_LISA);
        colors.add(ANAKIWA);
        break;
      case "square_2":
        colors.add(YELLOW_ORANGE_SQUARER);
        colors.add(OLD_COPPER);
        break;
      case "square_3":
        colors.add(BRICK_RED);
        colors.add(CHATHAMS_BLUE);
        break;
      case "flower_1":
        colors.add(ROMAN_FLOWER);
        colors.add(NIGHT_SHADZ);
        break;
      case "flower_2":
        colors.add(GAMBOGE_FLOWER);
        colors.add(FERN_FROND);
        break;
      case "flower_3":
        colors.add(CHETWODE_BLUE);
        colors.add(GIGAS);
        break;
      case "circle_1":
        colors.add(BUTTERCUP);
        colors.add(VERDIGRIS);
        break;
      case "circle_2":
        colors.add(VIVID_TANGERINE);
        colors.add(CERISE);
        break;
      case "circle_3":
        colors.add(MONTE_CARLO);
        colors.add(STUDIO);
        break;
      case "circle_4":
        colors.add(INDOCHINE);
        colors.add(CAFE_ROYALE);
        break;
      case "circle_5":
        colors.add(SUNSET_ORANGE);
        colors.add(PAPRIKA);
        break;
      case "sun_flower_1":
        colors.add(YELLOW_ORANGE_SUN_FLOWER);
        colors.add(ROYAL_PURPLE_SUN_FLOWER);
        break;
      case "sun_flower_2":
        colors.add(ROMAN_SUN_FLOWER);
        colors.add(WINE_BERRY);
        break;
      case "sun_flower_3":
        colors.add(GAMBOGE_SUN_FLOWER);
        colors.add(CRETE_SUN_FLOWER);
        break;
      case "hexagon_1":
        colors.add(RAZZMATAZZ);
        colors.add(COCOA_BROWN);
        break;
      case "hexagon_2":
        colors.add(MEDIUM_RED_VIOLET);
        colors.add(ROSE_BUD_CHERRY);
        break;
      case "hexagon_3":
        colors.add(PINE_GREEN);
        colors.add(PORT_GORE);
        break;
      case "pinwheel_1":
        colors.add(SAN_MARINO);
        colors.add(HOKEY_POKEY);
        break;
      case "pinwheel_2":
        colors.add(PERPLE_HEART);
        colors.add(ROMAN);
        break;
      case "pinwheel_3":
        colors.add(BLUE_CHILL);
        colors.add(SEANCE);
        break;
    }

    return colors;
  }
}
