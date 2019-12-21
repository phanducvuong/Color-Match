package com.ss.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Colors;

public class StartScene {

  private BitmapFont bitmap = GAssetsManager.getBitmapFont("font_color_match.fnt");

  private Group gUI;
  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private Color c1 = Colors.MONA_LISA, c2 = Colors.ANAKIWA;

  private Group gStart;
  private Image txtColor, txtMatch;
  private Image soundOn, soundOff;
  private Image iconShop, iconRank, btnPlayNow, bg_icon_shop, bg_icon_rank;
  private Label lbPlayNow;

  private Group gEndGame;


  private Group gContinue;

  public StartScene(Group gUI) {
    this.gUI = gUI;

    gStart = new Group();
    gEndGame = new Group();
    gContinue = new Group();

    gUI.addActor(gStart);

    initStartScene();
  }

  private void initStartScene() {
    txtColor = GUI.createImage(textureAtlas, "text_color");
    assert txtColor != null;
    txtColor.setPosition(gUI.getWidth()/2 - txtColor.getWidth()/2, 120);
    txtColor.setColor(c1);

    txtMatch = GUI.createImage(textureAtlas, "text_match");
    assert txtMatch != null;
    txtMatch.setPosition(txtColor.getX(), txtColor.getY() + 140);
    txtMatch.setColor(c2);

    bg_icon_shop = GUI.createImage(textureAtlas, "bg_icon");
    assert bg_icon_shop != null;
    bg_icon_shop.setPosition(gUI.getWidth()/2 - bg_icon_shop.getWidth()/2 - 120, gUI.getHeight()/2 + 280);
    bg_icon_shop.setColor(c2);

    iconShop = GUI.createImage(textureAtlas, "icon_shop");
    assert iconShop != null;
    iconShop.setPosition(bg_icon_shop.getX() + bg_icon_shop.getWidth()/2, bg_icon_shop.getY() + bg_icon_shop.getHeight()/2, Align.center);

    bg_icon_rank = GUI.createImage(textureAtlas, "bg_icon");
    assert bg_icon_rank != null;
    bg_icon_rank.setPosition(gUI.getWidth()/2 - bg_icon_rank.getWidth()/2 + 120, gUI.getHeight()/2 + 280);
    bg_icon_rank.setColor(c2);

    iconRank = GUI.createImage(textureAtlas, "icon_rank");
    assert iconRank != null;
    iconRank.setPosition(bg_icon_rank.getX() + bg_icon_rank.getWidth()/2, bg_icon_rank.getY() + bg_icon_rank.getHeight()/2, Align.center);

    btnPlayNow = GUI.createImage(textureAtlas, "btn_play_now");
    assert btnPlayNow != null;
    btnPlayNow.setPosition(gUI.getWidth()/2 - btnPlayNow.getWidth()/2, iconRank.getY() + 150);
    btnPlayNow.setColor(c1);

    lbPlayNow = new Label(C.lang.playnow, new Label.LabelStyle(bitmap, null));
//    lbPlayNow.setFontScale(2f);
    lbPlayNow.setPosition(btnPlayNow.getX() + btnPlayNow.getWidth()/2, btnPlayNow.getY() + btnPlayNow.getHeight()/2, Align.center);

    gStart.addActor(txtColor);
    gStart.addActor(txtMatch);

    gStart.addActor(bg_icon_shop);
    gStart.addActor(bg_icon_rank);
    gStart.addActor(iconShop);
    gStart.addActor(iconRank);
    gStart.addActor(btnPlayNow);
    gStart.addActor(lbPlayNow);
  }

}
