package com.ss.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.core.action.exAction.GTemporalAction;
import com.ss.core.action.exAction.GTween;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Colors;
import com.ss.gameLogic.interfaces.IChangeLbItem;
import com.ss.gameLogic.objects.Items;
import com.ss.gameLogic.objects.Shape;

public class StartScene implements IChangeLbItem {

  private BitmapFont bitmap = GAssetsManager.getBitmapFont("font_color_match.fnt");

  private Group gUI;
  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private Color c1 = Colors.MONA_LISA, c2 = Colors.ANAKIWA;
  private Color c = new Color(106 / 255f, 106 / 255f, 106 / 255f, 1);
  private Color cGetItem = new Color(255 / 255f, 82 / 255f, 59 / 255f, 1);
  private Color cTxt = new Color(92 / 255f, 87 / 255f, 69 / 255f, 1);

  public Group gStart;
  private Group gBtnStartGame = new Group();
  private Group gBtnShop = new Group();
  private Group gBtnRank = new Group();
  private Image txtColor, txtMatch;
  private Image bgTotalCoinStart;
  private Image soundOn, soundOff;
  private Image iconShop, iconRank, btnPlayNow, btnShop, btnRank;
  private Label lbPlayNow, lbTotalCoinStart, lbBestScoreTxt, lbBestScoreNum;

  public Group gEndGame;
  private Image bgResult, bgRanking, bannerRanikng, btnX2Coin, imgAdsEG, imgStar, btnNoThank, bgCoinInRound, bgTotalCoin;
  private Label lbResult, lbSTT, lbPlayer, lbScore, lbTotalCoin, lbCoinInRound, lbX2Coin, lbNoThank, lbScoreInRound;

  public Group gContinue;
  private Group gBtnContinue = new Group();
  private Group gBtnIgnore = new Group();
  private Image btnContinue, btnIgnore;
  private Label lbLose, lbContinue, lbIgnore;
  private Image imgAds;

  public Group gLevelUp;
  private Label lbLevelUp;
  private Label lbLevel;

  public Label lbScoreInGame;
  private int countAdsFullScreen = 0;

  private Group gShop;
  private Group gItem = new Group();
  private Group gGetItem = new Group();
  private Image bannerItem, bgItem, bgShop, imgItemSelected, bgGetItem, btnX;
  private Label lbShop, lbItem, lbGetItem, lbNoEnoughCoin, lbCoin;

  private GameScene G;
  private Shape shape = Shape.getInstance();
  public int turnToContinue = 0;

  private IPlatform plf = GMain.platform;

  private long coinPre = GMain.prefs.getLong("coin");
  private boolean isBuy = false;

  public StartScene(Group gUI, GameScene G) {
    this.gUI = gUI;
    this.G = G;

    gStart = new Group();
    gEndGame = new Group();
    gContinue = new Group();
    gLevelUp = new Group();
    gShop = new Group();

    gUI.addActor(gStart);
    gUI.addActor(gContinue);
//    gUI.addActor(gEndGame);
    gUI.addActor(gLevelUp);
    gUI.addActor(gShop);

    initStartScene();
    initContinueScene();
    initEndGameScene();
    initLbLevel();

    lbScoreInGame = new Label("0", new Label.LabelStyle(bitmap, c2));
    lbScoreInGame.setFontScale(.7f);
    lbScoreInGame.setPosition(gUI.getWidth() / 2 - lbScoreInGame.getWidth() * lbScoreInGame.getFontScaleX() / 2, 20);
    lbScoreInGame.setVisible(false);
    gUI.addActor(lbScoreInGame);

    initScroll();

    clickGGetItem();
    clickGContinue();
    clickGIgnore();
    clickGBtnStartGame();
    clickGBtnShop();
    clickGBtnRank();
  }

  ///////////////////////////////////////////////INIT///////////////////////////////////////////////

  private void initScroll() {
    gItem = new Group();

    bgShop = GUI.createImage(textureAtlas, "bg_shop");
    bgItem = GUI.createImage(textureAtlas, "bg_item");

    btnX = GUI.createImage(textureAtlas, "btnX");
    assert btnX != null;
    btnX.setPosition(bgShop.getWidth()/2 - btnX.getWidth()/2, bgShop.getHeight() - btnX.getHeight()/2);

    lbShop = new Label(C.lang.shop, new Label.LabelStyle(bitmap, c1));
    lbShop.setFontScale(.5f);
    lbShop.setPosition(bgShop.getX() + bgShop.getWidth()/2 - lbShop.getWidth()/2, 15);
    lbShop.setAlignment(Align.center);

    bannerItem = GUI.createImage(textureAtlas, "banner_item");

    assert bgItem != null;
    gItem.setSize(bgItem.getWidth(), bgItem.getHeight() - 35);
    bgItem.setPosition(bgShop.getWidth() / 2, bgShop.getHeight() / 2 + 110, Align.center);
    gItem.setPosition(bgItem.getX(), bgItem.getY());
    gItem.setOrigin(bgItem.getWidth() / 2, bgItem.getHeight() / 2);
    gItem.rotateBy(180);

    assert bannerItem != null;
    bannerItem.setPosition(bgItem.getX(), bgItem.getY() - 43);
    bannerItem.setColor(c2);

    lbItem = new Label(C.lang.itemDefault, new Label.LabelStyle(bitmap, null));
    lbItem.setFontScale(.4f);
    lbItem.setAlignment(Align.center);
    lbItem.setPosition(bannerItem.getX() + bannerItem.getWidth() / 2 - lbItem.getWidth() / 2, bannerItem.getY());

    //todo: check perference in android => set drawable to imgItemSelected
    imgItemSelected = GUI.createImage(textureAtlas, "item_square_1");
    assert imgItemSelected != null;
    imgItemSelected.setPosition(bgShop.getX() + bgShop.getWidth() / 2 - imgItemSelected.getWidth() / 2, 130);

    //init gGetItem
    bgGetItem = GUI.createImage(textureAtlas, "bg_get_item");
    assert bgGetItem != null;
    bgGetItem.setColor(c);

    Image star = GUI.createImage(textureAtlas, "star");
    assert star != null;
    star.setPosition(bgGetItem.getX() + 5, bgGetItem.getY() + bgGetItem.getHeight() / 2);

    lbGetItem = new Label(C.lang.getItem, new Label.LabelStyle(bitmap, null));
    lbGetItem.setFontScale(.3f);
    lbGetItem.setScale(0);
    lbGetItem.setPosition(bgGetItem.getX() + bgGetItem.getWidth()/2 - lbGetItem.getWidth()/2, bgGetItem.getY() - lbGetItem.getHeight()/2 + 15);
    lbGetItem.setAlignment(Align.center);

    lbCoin = new Label("1500", new Label.LabelStyle(bitmap, null));
    lbCoin.setFontScale(.35f);
    lbCoin.setScale(0);
    lbCoin.setAlignment(Align.center);
    lbCoin.setPosition(lbGetItem.getX() + 10, lbGetItem.getY() + 55);

    gGetItem.addActor(bgGetItem);
    gGetItem.addActor(lbGetItem);
    gGetItem.addActor(star);
    gGetItem.addActor(lbCoin);
    gGetItem.setPosition(imgItemSelected.getX() + 190, imgItemSelected.getY() + 50);
    gGetItem.setVisible(false);
    gGetItem.setOrigin(bgGetItem.getWidth()/2, bgGetItem.getHeight()/2);

    gShop.addActor(bgShop);
    gShop.addActor(lbShop);
    gShop.addActor(bgItem);
    gShop.addActor(imgItemSelected);
    gShop.addActor(gGetItem);
    gShop.addActor(bannerItem);
    gShop.addActor(gItem);
    gShop.addActor(lbItem);
    gShop.addActor(btnX);
    gShop.setPosition(GStage.getWorldWidth() / 2 - bgShop.getWidth() / 2, GStage.getWorldHeight() / 2 - bgShop.getHeight() / 2);
    gShop.setOrigin(bgShop.getWidth()/2, bgShop.getHeight()/2);

    //init table items
    Table scrollTable = new Table();
    for (int i = 0; i < shape.listItems.size(); i += 3) {
      try {
        scrollTable.add(shape.listItems.get(i).getItem()).center().padRight(10).padBottom(10);
        shape.listItems.get(i).iChangeLbItem = this;

        scrollTable.add(shape.listItems.get(i + 1).getItem()).center().padRight(10).padBottom(10);
        shape.listItems.get(i + 1).iChangeLbItem = this;

        scrollTable.add(shape.listItems.get(i + 2).getItem()).center().padBottom(10);
        shape.listItems.get(i + 2).iChangeLbItem = this;

        scrollTable.row();
      } catch (Exception ex) {
      }
    }

    ScrollPane scroller = new ScrollPane(scrollTable);
    Table table = new Table();
    table.setFillParent(true);
    table.add(scroller).fill().expand();

    gItem.addActor(table);

    gShop.setScale(0);
  }

  private void initStartScene() {
    bgTotalCoinStart = GUI.createImage(textureAtlas, "bg_coin");
    assert bgTotalCoinStart != null;
    bgTotalCoinStart.setPosition(gUI.getWidth() / 2 - bgTotalCoinStart.getWidth() / 2, 0);

    lbTotalCoinStart = new Label(GMain.prefs.getLong("coin")+"", new Label.LabelStyle(bitmap, null));
    lbTotalCoinStart.setFontScale(.5f);
    lbTotalCoinStart.setAlignment(Align.center);
    lbTotalCoinStart.setPosition(bgTotalCoinStart.getX() + bgTotalCoinStart.getWidth()/2 - lbTotalCoinStart.getWidth()/2 + 20,
            bgTotalCoinStart.getY() + bgTotalCoinStart.getHeight()/2 - lbTotalCoinStart.getHeight()/2 + 8);

    txtColor = GUI.createImage(textureAtlas, "text_color");
    assert txtColor != null;
    txtColor.setPosition(gUI.getWidth() / 2 - txtColor.getWidth() / 2, 120);
    txtColor.setColor(c1);

    txtMatch = GUI.createImage(textureAtlas, "text_match");
    assert txtMatch != null;
    txtMatch.setPosition(txtColor.getX(), txtColor.getY() + 140);
    txtMatch.setColor(c2);

    lbBestScoreTxt = new Label(C.lang.bestScore, new Label.LabelStyle(bitmap, Color.WHITE));
    lbBestScoreTxt.setAlignment(Align.center);
    lbBestScoreTxt.setFontScale(.8f);
    lbBestScoreTxt.setPosition(gUI.getWidth()/2 - lbBestScoreTxt.getWidth()/2, gUI.getHeight()/2 - 130);

    //todo: setScore when start game from prefs
    lbBestScoreNum = new Label(GMain.prefs.getLong("bestScore")+"", new Label.LabelStyle(bitmap, Color.WHITE));
    lbBestScoreNum.setAlignment(Align.center);
    lbBestScoreNum.setFontScale(.9f);
    lbBestScoreNum.setPosition(gUI.getWidth()/2 - lbBestScoreNum.getWidth()/2, lbBestScoreTxt.getY() + 100);

    btnShop = GUI.createImage(textureAtlas, "bg_icon");
    assert btnShop != null;
    btnShop.setPosition(gUI.getWidth() / 2 - btnShop.getWidth() / 2 - 120, gUI.getHeight() / 2 + 280);
    btnShop.setColor(c2);

    iconShop = GUI.createImage(textureAtlas, "icon_shop");
    assert iconShop != null;
    iconShop.setPosition(btnShop.getX() + btnShop.getWidth() / 2, btnShop.getY() + btnShop.getHeight() / 2, Align.center);

    btnRank = GUI.createImage(textureAtlas, "bg_icon");
    assert btnRank != null;
    btnRank.setPosition(gUI.getWidth() / 2 - btnRank.getWidth() / 2 + 120, gUI.getHeight() / 2 + 280);
    btnRank.setColor(c2);

    iconRank = GUI.createImage(textureAtlas, "icon_rank");
    assert iconRank != null;
    iconRank.setPosition(btnRank.getX() + btnRank.getWidth() / 2, btnRank.getY() + btnRank.getHeight() / 2, Align.center);

    btnPlayNow = GUI.createImage(textureAtlas, "btn_play_now");
    assert btnPlayNow != null;
    btnPlayNow.setColor(c1);

    lbPlayNow = new Label(C.lang.playnow, new Label.LabelStyle(bitmap, null));
    lbPlayNow.setFontScale(.8f);
    lbPlayNow.setAlignment(Align.center);
    lbPlayNow.setPosition(btnPlayNow.getX() + btnPlayNow.getWidth()/2 - lbPlayNow.getWidth()/2,
            btnPlayNow.getY() + btnPlayNow.getHeight()/2 - lbPlayNow.getHeight()/2 - 10);

    gStart.addActor(bgTotalCoinStart);
    gStart.addActor(lbTotalCoinStart);

    gStart.addActor(txtColor);
    gStart.addActor(txtMatch);

    gStart.addActor(lbBestScoreTxt);
    gStart.addActor(lbBestScoreNum);

    gBtnShop.addActor(btnShop);
    gBtnShop.addActor(iconShop);
    gBtnShop.setOrigin(btnShop.getX() + btnShop.getWidth() / 2, btnShop.getY() + btnShop.getHeight() / 2);
    gStart.addActor(gBtnShop);

    gBtnRank.addActor(btnRank);
    gBtnRank.addActor(iconRank);
    gBtnRank.setOrigin(btnRank.getX() + btnRank.getWidth() / 2, btnRank.getY() + btnRank.getHeight() / 2);
    gStart.addActor(gBtnRank);

    gBtnStartGame.setPosition(gUI.getWidth() / 2 - btnPlayNow.getWidth() / 2, iconRank.getY() + 150);
    gBtnStartGame.setOrigin(btnPlayNow.getX() + btnPlayNow.getWidth() / 2, btnPlayNow.getY() + btnPlayNow.getHeight() / 2);
    gBtnStartGame.addActor(btnPlayNow);
    gBtnStartGame.addActor(lbPlayNow);
    gStart.addActor(gBtnStartGame);

    gStart.setPosition(-GStage.getWorldWidth(), 0);
  }

  private void initContinueScene() {
    lbLose = new Label(C.lang.gameOver, new Label.LabelStyle(bitmap, c));
    lbLose.setFontScale(1.5f);
    lbLose.setPosition(gUI.getWidth()/2 - lbLose.getWidth()*lbLose.getFontScaleX()/2, 180);

    btnContinue = GUI.createImage(textureAtlas, "btn_continue");
    assert btnContinue != null;
    btnContinue.setPosition(gUI.getWidth() / 2 - btnContinue.getWidth() / 2, 950);
    btnContinue.setColor(c1);

    imgAds = GUI.createImage(textureAtlas, "ads");
    assert imgAds != null;
    imgAds.setPosition(btnContinue.getX() + 70, btnContinue.getY() + btnContinue.getHeight() / 2, Align.center);

    lbContinue = new Label(C.lang.continuee, new Label.LabelStyle(bitmap, null));
    lbContinue.setFontScale(.6f);
    lbContinue.setScale(0);
    lbContinue.setPosition(btnContinue.getX() + btnContinue.getWidth()/2 - lbContinue.getWidth()*lbContinue.getFontScaleX()/2 + 40,
            btnContinue.getY() + btnContinue.getHeight()/2 - lbContinue.getHeight()*lbContinue.getFontScaleY()/2 - 15);

    btnIgnore = GUI.createImage(textureAtlas, "btn_fr_world");
    assert btnIgnore != null;
    btnIgnore.setPosition(0, 0);
    btnIgnore.setColor(c);

    lbIgnore = new Label(C.lang.ignore, new Label.LabelStyle(bitmap, null));
    lbIgnore.setFontScale(.4f);
    lbIgnore.setScale(0);
    lbIgnore.setPosition(btnIgnore.getX() + btnIgnore.getWidth() / 2 - lbIgnore.getWidth() * lbIgnore.getFontScaleX() / 2,
            btnIgnore.getY() + btnIgnore.getHeight()/2 - lbIgnore.getHeight() * lbIgnore.getFontScaleY() / 2 - 25);

    gBtnIgnore.setSize(btnIgnore.getWidth(), btnIgnore.getHeight());
    gBtnIgnore.setPosition(gUI.getWidth() / 2 - btnIgnore.getWidth() / 2, btnContinue.getY() + 130);
    gBtnIgnore.setOrigin(btnIgnore.getX() + btnIgnore.getWidth() / 2, btnIgnore.getY() + btnIgnore.getHeight() / 2);
    gBtnIgnore.addActor(btnIgnore);
    gBtnIgnore.addActor(lbIgnore);

    gContinue.addActor(gBtnIgnore);
    gContinue.addActor(lbLose);

    gBtnContinue.addActor(btnContinue);
    gBtnContinue.addActor(lbContinue);
    gBtnContinue.addActor(imgAds);
    gBtnContinue.setOrigin(btnContinue.getX() + btnContinue.getWidth() / 2, btnContinue.getY() + btnContinue.getHeight() / 2);

    gContinue.addActor(gBtnContinue);

    gContinue.setPosition(0, -GStage.getWorldHeight());
  }

  private void initEndGameScene() {
    bgResult = GUI.createImage(textureAtlas, "bg_result");
    assert bgResult != null;
    bgResult.setPosition(gUI.getWidth() / 2, gUI.getHeight() / 2, Align.center);

    bgTotalCoin = GUI.createImage(textureAtlas, "bg_coin");
    assert bgTotalCoin != null;
    bgTotalCoin.setPosition(gUI.getWidth() / 2 - bgTotalCoin.getWidth() / 2, 0);

    lbTotalCoin = new Label("3568799", new Label.LabelStyle(bitmap, null));
    lbTotalCoin.setFontScale(.3f);
    lbTotalCoin.setPosition(gUI.getWidth() / 2 - lbTotalCoin.getWidth() * lbTotalCoin.getFontScaleX() / 2 + 23, -38);

    lbResult = new Label(C.lang.result, new Label.LabelStyle(bitmap, c));
    lbResult.setFontScale(.5f);
    lbResult.setPosition(gUI.getWidth() / 2 - lbResult.getWidth() * lbResult.getFontScaleX() / 2, 90);

    lbScoreInRound = new Label("345687", new Label.LabelStyle(bitmap, c1));
    lbScoreInRound.setFontScale(.7f);
    lbScoreInRound.setPosition(gUI.getWidth() / 2 - lbScoreInRound.getWidth() * lbScoreInRound.getFontScaleX() / 2, lbResult.getY() + 70);

    bgCoinInRound = GUI.createImage(textureAtlas, "bg_coin_in_round");
    assert bgCoinInRound != null;
    bgCoinInRound.setPosition(gUI.getWidth() / 2, lbScoreInRound.getY() + 250, Align.center);

    lbCoinInRound = new Label("659875", new Label.LabelStyle(bitmap, null));
    lbCoinInRound.setFontScale(.35f);
    lbCoinInRound.setPosition(gUI.getWidth() / 2 - lbCoinInRound.getWidth() * lbCoinInRound.getFontScaleX() / 2 + 20, bgCoinInRound.getY() - 65);

    bgRanking = GUI.createImage(textureAtlas, "bg_ranking");
    assert bgRanking != null;
    bgRanking.setPosition(gUI.getWidth() / 2, gUI.getHeight() / 2 + 70, Align.center);

    bannerRanikng = GUI.createImage(textureAtlas, "banner_ranking");
    assert bannerRanikng != null;
    bannerRanikng.setPosition(gUI.getWidth() / 2 - bannerRanikng.getWidth() / 2, bgRanking.getY() - 30);
    bannerRanikng.setColor(c2);

    lbSTT = new Label(C.lang.STT, new Label.LabelStyle(bitmap, null));
    lbSTT.setFontScale(.25f);
    lbSTT.setPosition(bannerRanikng.getX() + 20, bannerRanikng.getY() - 50);

    lbPlayer = new Label(C.lang.player, new Label.LabelStyle(bitmap, null));
    lbPlayer.setFontScale(.25f);
    lbPlayer.setPosition(bannerRanikng.getX() + bannerRanikng.getWidth() / 2 - lbPlayer.getWidth() * lbPlayer.getFontScaleX() / 2, bannerRanikng.getY() - 50);

    lbScore = new Label(C.lang.score, new Label.LabelStyle(bitmap, null));
    lbScore.setFontScale(.25f);
    lbScore.setPosition(bannerRanikng.getX() + bannerRanikng.getWidth() - 110, bannerRanikng.getY() - 50);

    btnX2Coin = GUI.createImage(textureAtlas, "btn_continue");
    assert btnX2Coin != null;
    btnX2Coin.setPosition(gUI.getWidth() / 2, bgRanking.getY() + bgRanking.getHeight() + 70, Align.center);
    btnX2Coin.setColor(c1);

    imgAdsEG = GUI.createImage(textureAtlas, "ads");
    assert imgAdsEG != null;
    imgAdsEG.setPosition(btnX2Coin.getX() + 80, btnX2Coin.getY() + btnX2Coin.getHeight() / 2, Align.center);

    imgStar = GUI.createImage(textureAtlas, "star");
    assert imgStar != null;
    imgStar.setPosition(imgAdsEG.getX() + 170, imgAdsEG.getY() + imgAdsEG.getHeight() / 2, Align.center);

    lbX2Coin = new Label(C.lang.coin, new Label.LabelStyle(bitmap, null));
    lbX2Coin.setFontScale(.25f);
    lbX2Coin.setPosition(btnX2Coin.getX() + btnX2Coin.getWidth() + 175, imgStar.getY() + imgStar.getHeight() / 2 - 30, Align.center);

    btnNoThank = GUI.createImage(textureAtlas, "btn_fr_world");
    assert btnNoThank != null;
    btnNoThank.setPosition(gUI.getWidth() / 2 - btnNoThank.getWidth() / 2, btnX2Coin.getY() + btnX2Coin.getHeight() + 10);
    btnNoThank.setColor(c);

    lbNoThank = new Label(C.lang.tks, new Label.LabelStyle(bitmap, null));
    lbNoThank.setFontScale(.25f);
    lbNoThank.setPosition(btnNoThank.getX() - lbNoThank.getWidth() * lbNoThank.getFontScaleX() / 2 + btnNoThank.getWidth() / 2, btnNoThank.getY() - lbNoThank.getHeight() * lbNoThank.getFontScaleX() / 2 - 40);

    gEndGame.addActor(bgResult);
    gEndGame.addActor(bgTotalCoin);
    gEndGame.addActor(lbTotalCoin);
    gEndGame.addActor(lbResult);
    gEndGame.addActor(lbScoreInRound);
    gEndGame.addActor(bgCoinInRound);
    gEndGame.addActor(lbCoinInRound);
    gEndGame.addActor(bgRanking);
    gEndGame.addActor(bannerRanikng);
    gEndGame.addActor(lbSTT);
    gEndGame.addActor(lbPlayer);
    gEndGame.addActor(lbScore);
    gEndGame.addActor(btnX2Coin);
    gEndGame.addActor(imgStar);
    gEndGame.addActor(lbX2Coin);
    gEndGame.addActor(imgAdsEG);
    gEndGame.addActor(btnNoThank);
    gEndGame.addActor(lbNoThank);

    gEndGame.setPosition(GStage.getWorldWidth(), 0);
  }

  private void initLbLevel() {
    lbLevelUp = new Label(C.lang.lvUp, new Label.LabelStyle(bitmap, c));
    lbLevelUp.setFontScale(.8f);
    lbLevelUp.getColor().a = 0;
    lbLevelUp.setPosition(gUI.getWidth()/2 - lbLevelUp.getWidth() * lbLevelUp.getFontScaleX() / 2, 150);

    lbLevel = new Label(C.lang.lv, new Label.LabelStyle(bitmap, c));
    lbLevel.setFontScale(.8f);
    lbLevel.getColor().a = 0;
    lbLevel.setAlignment(Align.center);
    lbLevel.setPosition(gUI.getWidth() / 2 - lbLevel.getWidth() * lbLevel.getFontScaleX() / 2, 230);

    gLevelUp.addActor(lbLevelUp);
    gLevelUp.addActor(lbLevel);
  }

  ///////////////////////////////////////////////INIT///////////////////////////////////////////////

  ///////////////////////////////////////////////EFFECT/////////////////////////////////////////////

  public void eftLbScore(float score) {
    long ss = Long.parseLong(lbScoreInGame.getText().toString());
    lbScoreInGame.addAction(GTemporalAction.add(.4f, (p, a) -> {
      long s = (int) (ss + score * p);
      lbScoreInGame.setText(String.valueOf(s));

      int length = lbScoreInGame.getText().length;
      lbScoreInGame.setPosition(gUI.getWidth() / 2 - lbScoreInGame.getWidth() * length * lbScoreInGame.getFontScaleX() / 2, 20);
    }));
  }

  public void eftLbLevel(long lv) {
    lbLevel.setText(C.lang.lv + lv);

    GTween.action(lbLevelUp, Actions.alpha(1, .5f, Interpolation.linear),
            () -> GTween.setTimeout(gLevelUp, .75f,
                    () -> lbLevelUp.addAction(Actions.alpha(0, 1.5f, Interpolation.linear)))
    );

    GTween.action(lbLevel, Actions.alpha(1, .5f, Interpolation.linear),
            () -> GTween.setTimeout(gLevelUp, .75f,
            () -> lbLevel.addAction(Actions.alpha(0, 1.5f, Interpolation.linear)))
    );
  }

  public void eftStart(boolean isStart, Runnable run) {
    if (!isStart)
      GTween.action(gStart, Actions.moveBy(-GStage.getWorldWidth(), 0, .5f, Interpolation.swingIn), run);
    else
      GTween.action(gStart, Actions.moveBy(GStage.getWorldWidth(), 0, .5f, Interpolation.swingOut), run);
  }

  public void eftEndGame() {
    lbScoreInGame.setVisible(false);
    GTween.action(gEndGame, Actions.moveBy(-GStage.getStageWidth(), 0, 1f, Interpolation.swingOut), null);
  }

  public void eftGShop() {
    float x = gShop.getScaleX();
    if (x == 1)
      GTween.action(gShop, Actions.scaleTo(0, 0, .25f, Interpolation.linear),
              ()-> gBtnStartGame.setTouchable(Touchable.enabled));
    else
      GTween.action(gShop, Actions.scaleTo(1, 1, .35f, Interpolation.swingOut),
              () -> gBtnStartGame.setTouchable(Touchable.disabled));
  }

  public void eftContinue(boolean isContinue) {

    long c = Long.parseLong(lbTotalCoinStart.getText().toString()) + G.coinInGame;
    GMain.prefs.putLong("coin", c);
    GMain.prefs.flush();

    if (isContinue) {
      GTween.action(gContinue, Actions.moveBy(0, -GStage.getWorldHeight(), .5f, Interpolation.swingIn),
              () -> {
                G.setTouchStage(true);
                G.endGame = false;
                GTween.setTimeout(gUI, 1f,
                        () -> {
                          G.turn = turnToContinue - 1;
                          G.nextObj();
                        });
              });
    } else {
      gBtnContinue.setTouchable(Touchable.enabled);
      gBtnIgnore.setTouchable(Touchable.enabled);
      GTween.action(gContinue, Actions.moveBy(0, GStage.getWorldHeight(), 1f, Interpolation.bounceOut), null);
    }
  }

  ///////////////////////////////////////////////EFFECT/////////////////////////////////////////////

  ///////////////////////////////////////////////EVENT CLICK////////////////////////////////////////

  private void clickGContinue() {
    gBtnContinue.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Runnable run = () -> {
          if (plf.isVideoRewardReady()) {
            plf.ShowVideoReward((boolean success) -> {
              if (success)
                eftContinue(true);
            });
          }
          else
            gBtnContinue.setTouchable(Touchable.enabled);
        };
        gBtnContinue.setTouchable(Touchable.disabled);
        eftBtn(gBtnContinue, run);
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void clickGIgnore() {
    gBtnIgnore.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        gBtnIgnore.setTouchable(Touchable.disabled);
        lbScoreInGame.setVisible(false);

        Runnable run = () -> {
          G.polygonAct.setScalePolyAct(1);
          lbTotalCoinStart.setText(updateCoin() + "");
          updateBestScore();

          GTween.action(gContinue, Actions.moveBy(0, -GStage.getWorldHeight(), .5f, Interpolation.swingIn),
                  () -> eftStart(true, null));
        };
        eftBtn(gBtnIgnore, run);

        countAdsFullScreen++;
        if (countAdsFullScreen % 3 == 0) {
          plf.ShowFullscreen();
          countAdsFullScreen = 0;
        }
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void clickGBtnStartGame() {
    gBtnStartGame.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Runnable run = () -> eftStart(false,
                () -> {
                  resetLbScoreInGame();
                  G.newGame();
                  lbScoreInGame.setVisible(true);
                });
        eftBtn(gBtnStartGame, run);
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void clickGBtnShop() {
    gBtnShop.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Runnable run = () -> eftGShop();
        eftBtn(gBtnShop, run);
        return super.touchDown(event, x, y, pointer, button);
      }
    });

    btnX.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        eftGShop();
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void clickGBtnRank() {
    gBtnRank.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        eftBtn(gBtnRank, null);
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  ///////////////////////////////////////////////EVENT CLICK////////////////////////////////////////

  private void setColorForUI(Color c1, Color c2) {

    //gStartScene
    txtColor.setColor(c1);
    txtMatch.setColor(c2);
    btnShop.setColor(c2);
    btnRank.setColor(c2);
    btnPlayNow.setColor(c1);

    //gContinue
    btnContinue.setColor(c1);

    //gEndGame
    bannerRanikng.setColor(c2);
    btnX2Coin.setColor(c1);

    bannerItem.setColor(c2);
    lbScoreInGame.setColor(c2);
    lbShop.setColor(c1);
  }

  private void eftBtn(Group btn, Runnable run) {
    GTween.action(btn, Actions.scaleTo(.9f, .9f, .1f, Interpolation.fastSlow),
            () -> GTween.action(btn, Actions.scaleTo(1f, 1f, .1f, Interpolation.fastSlow), run));
  }

  private void resetLbScoreInGame() {
    lbScoreInGame.setText(0);
    lbScoreInGame.setPosition(gUI.getWidth() / 2 - lbScoreInGame.getWidth() * lbScoreInGame.getFontScaleX() / 2, 20);
  }

  private Items itemSelected;
  @Override
  public void changeLbItem(Items items) {

    if (items.coin == 0) {
      lbItem.setText(C.lang.itemDefault);
      gGetItem.setVisible(false);
    }
    else {
      lbItem.setText("Item " + items.coin);
      lbCoin.setText(items.coin + "");

      if (items.isLock) {
        gGetItem.setVisible(true);

        if (coinPre >= items.coin) {
          bgGetItem.setColor(cGetItem);
          isBuy = true;
        } else {
          bgGetItem.setColor(c);
          isBuy = false;
        }
      }else gGetItem.setVisible(false);
    }

    if (!items.isLock) {
      G.changeShapeMainCenter(items.name, items.idShape, items.name);
      G.changeShaderAct(items.name);
      setColorForUI(shape.c1, shape.c2);
    }

    itemSelected = items;
    imgItemSelected.setDrawable(items.item.getDrawable());
  }

  private void clickGGetItem() {
    gGetItem.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        Runnable run = () -> {
          if (coinPre >= itemSelected.coin) {
            gGetItem.setVisible(false);
            itemSelected.isLock = false;

            shape.writeFileJson(itemSelected);

            coinPre -= itemSelected.coin;
            lbTotalCoinStart.setText(coinPre+"");
            GMain.prefs.putLong("coin", coinPre);
            GMain.prefs.flush();

            G.changeShapeMainCenter(itemSelected.name, itemSelected.idShape, itemSelected.name);
            G.changeShaderAct(itemSelected.name);
            setColorForUI(shape.c1, shape.c2);
          }
          else
            Gdx.app.log("NO", "NO HAVE ENOUGH MONEY");
        };
        eftBtn(gGetItem, run);

        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private long updateCoin() {
    coinPre = Long.valueOf(lbTotalCoinStart.getText().toString()) + G.coinInGame;
    return coinPre;
  }

  private void saveCoinInPrefs() {

  }

  private void updateBestScore() {
    long sB = Long.parseLong(lbBestScoreNum.getText().toString());
    long sN = Long.parseLong(lbScoreInGame.getText().toString());

    if (sB < sN) {
      lbBestScoreNum.setText(sN+"");
      GMain.prefs.putLong("bestScore", sN);
      GMain.prefs.flush();
    }
  }
}
