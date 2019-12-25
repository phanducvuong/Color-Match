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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

public class StartScene {

  private BitmapFont bitmap = GAssetsManager.getBitmapFont("font_color_match.fnt");

  private Group gUI;
  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private Color c1 = Colors.MONA_LISA, c2 = Colors.ANAKIWA;
  private Color c = new Color(106/255f, 106/255f, 106/255f, 1);
  private Color cTxt = new Color(92/255f, 87/255f, 69/255f, 1);

  public Group gStart;
  private Group gBtnStartGame = new Group();
  private Group gBtnShop = new Group();
  private Group gBtnRank = new Group();
  private Image txtColor, txtMatch;
  private Image bgTotalCoinStart;
  private Image soundOn, soundOff;
  private Image iconShop, iconRank, btnPlayNow, btnShop, btnRank;
  private Label lbPlayNow, lbTotalCoinStart;

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
  private long score = 0;
  private int countAdsFullScreen = 0;

  private Group gShop;
  private Group gItem = new Group();
  private Image bannerItem, bgItem, bgShop;
  private Label lbShop, lbItem;

  private GameScene G;
  public int turnToContinue = 0;

  private IPlatform plf = GMain.platform;

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
    lbScoreInGame.setFontScale(.5f);
    lbScoreInGame.setPosition(gUI.getWidth()/2 - lbScoreInGame.getWidth()*lbScoreInGame.getFontScaleX()/2, -30);
    lbScoreInGame.setVisible(false);
    gUI.addActor(lbScoreInGame);

    clickGContinue();
    clickGIgnore();
    clickGBtnStartGame();
    clickGBtnShop();
    clickGBtnRank();

    initScroll();
  }

  ///////////////////////////////////////////////INIT///////////////////////////////////////////////

  private void initScroll() {
    gItem = new Group();

    bgShop = GUI.createImage(textureAtlas, "bg_shop");
    bgItem = GUI.createImage(textureAtlas, "bg_item");

    bannerItem = GUI.createImage(textureAtlas, "banner_item");

    assert bgItem != null;
    gItem.setSize(bgItem.getWidth(), bgItem.getHeight() - 35);
    bgItem.setPosition(bgShop.getWidth()/2, bgShop.getHeight()/2 + 110, Align.center);
    gItem.setPosition(bgItem.getX(), bgItem.getY());
    gItem.setOrigin(bgItem.getWidth()/2, bgItem.getHeight()/2);
    gItem.rotateBy(180);

    assert bannerItem != null;
    bannerItem.setPosition(bgItem.getX(), bgItem.getY() - 43);
    bannerItem.setColor(c2);

    gShop.addActor(bgShop);
    gShop.addActor(bgItem);
    gShop.addActor(bannerItem);
    gShop.addActor(gItem);
    gShop.setPosition(GStage.getWorldWidth()/2 - bgShop.getWidth()/2, GStage.getWorldHeight()/2 - bgShop.getHeight()/2);

    Table scrollTable = new Table();
    for (int i = 0; i < 40; i++) {
      Image img = GUI.createImage(textureAtlas, "item");
      Image img1 = GUI.createImage(textureAtlas, "item");
      Image img2 = GUI.createImage(textureAtlas, "item");

      img.setName(i+"");
      img1.setName(i+"");
      img2.setName(i+"");

      if (i %2 == 0) {
        img.setColor(Color.RED);
        img1.setColor(Color.BLUE);
        img2.setColor(Color.CORAL);
      }

      img.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          super.clicked(event, x, y);
          Gdx.app.log("NAME", img.getName());
        }
      });

      img1.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          super.clicked(event, x, y);
          Gdx.app.log("NAME", img.getName());
        }
      });

      scrollTable.add(img).center().padRight(25).padBottom(10);
      scrollTable.add(img1).center().padRight(25).padBottom(10);
      scrollTable.add(img2).center().padBottom(10);
      scrollTable.row();
    }

    ScrollPane scroller = new ScrollPane(scrollTable);
    Table table = new Table();
    table.setFillParent(true);
    table.add(scroller).fill().expand();

    gItem.addActor(table);
  }

  private void initStartScene() {
    bgTotalCoinStart = GUI.createImage(textureAtlas, "bg_coin");
    assert  bgTotalCoinStart != null;
    bgTotalCoinStart.setPosition(gUI.getWidth()/2 - bgTotalCoinStart.getWidth()/2, 0);

    lbTotalCoinStart = new Label("356879", new Label.LabelStyle(bitmap, null));
    lbTotalCoinStart.setFontScale(.3f);
    lbTotalCoinStart.setPosition(gUI.getWidth()/2 - lbTotalCoinStart.getWidth()*lbTotalCoinStart.getFontScaleX()/2 + 23, - 38);

    txtColor = GUI.createImage(textureAtlas, "text_color");
    assert txtColor != null;
    txtColor.setPosition(gUI.getWidth()/2 - txtColor.getWidth()/2, 120);
    txtColor.setColor(c1);

    txtMatch = GUI.createImage(textureAtlas, "text_match");
    assert txtMatch != null;
    txtMatch.setPosition(txtColor.getX(), txtColor.getY() + 140);
    txtMatch.setColor(c2);

    btnShop = GUI.createImage(textureAtlas, "bg_icon");
    assert btnShop != null;
    btnShop.setPosition(gUI.getWidth()/2 - btnShop.getWidth()/2 - 120, gUI.getHeight()/2 + 280);
    btnShop.setColor(c2);

    iconShop = GUI.createImage(textureAtlas, "icon_shop");
    assert iconShop != null;
    iconShop.setPosition(btnShop.getX() + btnShop.getWidth()/2, btnShop.getY() + btnShop.getHeight()/2, Align.center);

    btnRank = GUI.createImage(textureAtlas, "bg_icon");
    assert btnRank != null;
    btnRank.setPosition(gUI.getWidth()/2 - btnRank.getWidth()/2 + 120, gUI.getHeight()/2 + 280);
    btnRank.setColor(c2);

    iconRank = GUI.createImage(textureAtlas, "icon_rank");
    assert iconRank != null;
    iconRank.setPosition(btnRank.getX() + btnRank.getWidth()/2, btnRank.getY() + btnRank.getHeight()/2, Align.center);

    btnPlayNow = GUI.createImage(textureAtlas, "btn_play_now");
    assert btnPlayNow != null;
    btnPlayNow.setColor(c1);

    lbPlayNow = new Label(C.lang.playnow, new Label.LabelStyle(bitmap, null));
    lbPlayNow.setFontScale(.5f);
    lbPlayNow.setScale(0);
    lbPlayNow.setPosition(btnPlayNow.getX()+btnPlayNow.getWidth()/2 - lbPlayNow.getPrefWidth()/2, btnPlayNow.getY()+btnPlayNow.getHeight()/2 - 135);

    gStart.addActor(bgTotalCoinStart);
    gStart.addActor(lbTotalCoinStart);

    gStart.addActor(txtColor);
    gStart.addActor(txtMatch);

    gBtnShop.addActor(btnShop);
    gBtnShop.addActor(iconShop);
    gBtnShop.setOrigin(btnShop.getX()+btnShop.getWidth()/2, btnShop.getY()+btnShop.getHeight()/2);
    gStart.addActor(gBtnShop);

    gBtnRank.addActor(btnRank);
    gBtnRank.addActor(iconRank);
    gBtnRank.setOrigin(btnRank.getX()+btnRank.getWidth()/2, btnRank.getY()+btnRank.getHeight()/2);
    gStart.addActor(gBtnRank);

    gBtnStartGame.setPosition(gUI.getWidth()/2 - btnPlayNow.getWidth()/2, iconRank.getY() + 150);
    gBtnStartGame.setOrigin(btnPlayNow.getX()+btnPlayNow.getWidth()/2, btnPlayNow.getY()+btnPlayNow.getHeight()/2);
    gBtnStartGame.addActor(btnPlayNow);
    gBtnStartGame.addActor(lbPlayNow);
    gStart.addActor(gBtnStartGame);

    lbPlayNow.debug();

    gStart.setPosition(-GStage.getWorldWidth(), 0);
  }

  private void initContinueScene() {
    lbLose = new Label(C.lang.gameOver, new Label.LabelStyle(bitmap, cTxt));
    lbLose.setPosition(gUI.getWidth()/2, 90, Align.center);

    btnContinue = GUI.createImage(textureAtlas, "btn_continue");
    assert btnContinue != null;
    btnContinue.setPosition(gUI.getWidth()/2 - btnContinue.getWidth()/2, 950);
    btnContinue.setColor(c1);

    imgAds = GUI.createImage(textureAtlas, "ads");
    assert imgAds != null;
    imgAds.setPosition(btnContinue.getX() + 70, btnContinue.getY() + btnContinue.getHeight()/2, Align.center);

    lbContinue = new Label(C.lang.continuee, new Label.LabelStyle(bitmap, null));
    lbContinue.setFontScale(.4f);
    lbContinue.setScale(0);
    lbContinue.setPosition(gUI.getWidth()/2 - lbContinue.getWidth()*lbContinue.getFontScaleX()/2 + 40, btnContinue.getY() + btnContinue.getHeight()/2 - 115);

    btnIgnore = GUI.createImage(textureAtlas, "btn_fr_world");
    assert btnIgnore != null;
    btnIgnore.setPosition(0, 0);
    btnIgnore.setColor(c);

    lbIgnore = new Label(C.lang.ignore, new Label.LabelStyle(bitmap, null));
    lbIgnore.setFontScale(.3f);
    lbIgnore.setScale(0);
    lbIgnore.setPosition(btnIgnore.getX() + btnIgnore.getWidth()/2 - lbIgnore.getWidth()*lbIgnore.getFontScaleX()/2, btnIgnore.getY() - btnIgnore.getHeight()/2 - 38);

    gBtnIgnore.setSize(btnIgnore.getWidth(), btnIgnore.getHeight());
    gBtnIgnore.setPosition(gUI.getWidth()/2 - btnIgnore.getWidth()/2, btnContinue.getY() + 130);
    gBtnIgnore.setOrigin(btnIgnore.getX() + btnIgnore.getWidth()/2, btnIgnore.getY() + btnIgnore.getHeight()/2);
    gBtnIgnore.addActor(btnIgnore);
    gBtnIgnore.addActor(lbIgnore);

    gContinue.addActor(gBtnIgnore);
    gContinue.addActor(lbLose);

    gBtnContinue.addActor(btnContinue);
    gBtnContinue.addActor(lbContinue);
    gBtnContinue.addActor(imgAds);
    gBtnContinue.setOrigin(btnContinue.getX() + btnContinue.getWidth()/2, btnContinue.getY() + btnContinue.getHeight()/2);

    gContinue.addActor(gBtnContinue);

    gContinue.setPosition(0, -GStage.getWorldHeight());
  }

  private void initEndGameScene() {
    bgResult = GUI.createImage(textureAtlas, "bg_result");
    assert bgResult != null;
    bgResult.setPosition(gUI.getWidth()/2, gUI.getHeight()/2, Align.center);

    bgTotalCoin = GUI.createImage(textureAtlas, "bg_coin");
    assert  bgTotalCoin != null;
    bgTotalCoin.setPosition(gUI.getWidth()/2 - bgTotalCoin.getWidth()/2, 0);

    lbTotalCoin = new Label("3568799", new Label.LabelStyle(bitmap, null));
    lbTotalCoin.setFontScale(.3f);
    lbTotalCoin.setPosition(gUI.getWidth()/2 - lbTotalCoin.getWidth()*lbTotalCoin.getFontScaleX()/2 + 23, - 38);

    lbResult = new Label(C.lang.result, new Label.LabelStyle(bitmap, c));
    lbResult.setFontScale(.5f);
    lbResult.setPosition(gUI.getWidth()/2 - lbResult.getWidth()*lbResult.getFontScaleX()/2, 90);

    lbScoreInRound = new Label("345687", new Label.LabelStyle(bitmap, c1));
    lbScoreInRound.setFontScale(.7f);
    lbScoreInRound.setPosition(gUI.getWidth()/2 - lbScoreInRound.getWidth()*lbScoreInRound.getFontScaleX()/2, lbResult.getY() + 70);

    bgCoinInRound = GUI.createImage(textureAtlas, "bg_coin_in_round");
    assert bgCoinInRound != null;
    bgCoinInRound.setPosition(gUI.getWidth()/2, lbScoreInRound.getY() + 250, Align.center);

    lbCoinInRound = new Label("659875", new Label.LabelStyle(bitmap, null));
    lbCoinInRound.setFontScale(.35f);
    lbCoinInRound.setPosition(gUI.getWidth()/2 - lbCoinInRound.getWidth()*lbCoinInRound.getFontScaleX()/2 + 20, bgCoinInRound.getY() - 65);

    bgRanking = GUI.createImage(textureAtlas, "bg_ranking");
    assert bgRanking != null;
    bgRanking.setPosition(gUI.getWidth()/2, gUI.getHeight()/2 + 70, Align.center);

    bannerRanikng = GUI.createImage(textureAtlas, "banner_ranking");
    assert bannerRanikng != null;
    bannerRanikng.setPosition(gUI.getWidth()/2 - bannerRanikng.getWidth()/2, bgRanking.getY() - 30);
    bannerRanikng.setColor(c2);

    lbSTT = new Label(C.lang.STT, new Label.LabelStyle(bitmap, null));
    lbSTT.setFontScale(.25f);
    lbSTT.setPosition(bannerRanikng.getX() + 20, bannerRanikng.getY() - 50);

    lbPlayer = new Label(C.lang.player, new Label.LabelStyle(bitmap, null));
    lbPlayer.setFontScale(.25f);
    lbPlayer.setPosition(bannerRanikng.getX() + bannerRanikng.getWidth()/2 - lbPlayer.getWidth()*lbPlayer.getFontScaleX()/2, bannerRanikng.getY() - 50);

    lbScore = new Label(C.lang.score, new Label.LabelStyle(bitmap, null));
    lbScore.setFontScale(.25f);
    lbScore.setPosition(bannerRanikng.getX() + bannerRanikng.getWidth() - 110, bannerRanikng.getY() - 50);

    btnX2Coin = GUI.createImage(textureAtlas, "btn_continue");
    assert btnX2Coin != null;
    btnX2Coin.setPosition(gUI.getWidth()/2, bgRanking.getY() + bgRanking.getHeight() + 70, Align.center);
    btnX2Coin.setColor(c1);

    imgAdsEG = GUI.createImage(textureAtlas, "ads");
    assert imgAdsEG != null;
    imgAdsEG.setPosition(btnX2Coin.getX() + 80, btnX2Coin.getY() + btnX2Coin.getHeight()/2, Align.center);

    imgStar = GUI.createImage(textureAtlas, "star");
    assert imgStar != null;
    imgStar.setPosition(imgAdsEG.getX() + 170, imgAdsEG.getY() + imgAdsEG.getHeight()/2, Align.center);

    lbX2Coin = new Label(C.lang.coin, new Label.LabelStyle(bitmap, null));
    lbX2Coin.setFontScale(.25f);
    lbX2Coin.setPosition(btnX2Coin.getX() + btnX2Coin.getWidth() + 175, imgStar.getY() + imgStar.getHeight()/2 - 30, Align.center);

    btnNoThank = GUI.createImage(textureAtlas, "btn_fr_world");
    assert btnNoThank != null;
    btnNoThank.setPosition(gUI.getWidth()/2 - btnNoThank.getWidth()/2, btnX2Coin.getY() + btnX2Coin.getHeight() + 10);
    btnNoThank.setColor(c);

    lbNoThank = new Label(C.lang.tks, new Label.LabelStyle(bitmap, null));
    lbNoThank.setFontScale(.25f);
    lbNoThank.setPosition(btnNoThank.getX() - lbNoThank.getWidth()*lbNoThank.getFontScaleX()/2 + btnNoThank.getWidth()/2, btnNoThank.getY() - lbNoThank.getHeight()*lbNoThank.getFontScaleX()/2 - 40);

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
    lbLevelUp.setFontScale(.5f);
    lbLevelUp.getColor().a = 0;
    lbLevelUp.setPosition(gUI.getWidth()/2 - lbLevelUp.getWidth()*lbLevelUp.getFontScaleX()/2, 150);

    lbLevel = new Label(C.lang.lv, new Label.LabelStyle(bitmap, c));
    lbLevel.setFontScale(.5f);
    lbLevel.getColor().a = 0;
    lbLevel.setPosition(gUI.getWidth()/2 - lbLevel.getWidth()*lbLevel.getFontScaleX()/2, 230);

    gLevelUp.addActor(lbLevelUp);
    gLevelUp.addActor(lbLevel);
  }

  ///////////////////////////////////////////////INIT///////////////////////////////////////////////

  ///////////////////////////////////////////////EFFECT/////////////////////////////////////////////

  public void eftLbScore(float score) {
    long ss = Integer.parseInt(lbScoreInGame.getText().toString());
    lbScoreInGame.addAction(GTemporalAction.add(.4f, (p, a) -> {
      long s = (int) (ss + score*p);
      lbScoreInGame.setText(String.valueOf(s));

      float x = lbScoreInGame.getText().length * 96 * lbScoreInGame.getFontScaleX()/2;
      lbScoreInGame.setPosition(gUI.getWidth()/2 - x, -30);
    }));
  }

  public void eftLbLevel(long lv) {
    lbLevel.setText(C.lang.lv + lv);
    float x = gUI.getWidth()/2 - lbLevel.getText().length * 96 * lbLevel.getFontScaleX()/2;
    lbLevel.setX(x);

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

  public void eftContinue(boolean isContinue) {
    if (isContinue) {
      //todo: set score for lbScore
      GTween.action(gContinue, Actions.moveBy(0, -GStage.getWorldHeight(), .5f, Interpolation.swingIn),
              () -> {
                  lbScoreInGame.setVisible(true);
                  G.setTouchStage(true);
                  G.endGame = false;
                  GTween.setTimeout(gUI, 1f,
              () -> {
                  G.turn = turnToContinue - 1;
                  G.nextObj();
              });
      });
    }
    else {
      gBtnContinue.setTouchable(Touchable.enabled);
      gBtnIgnore.setTouchable(Touchable.enabled);
      lbScoreInGame.setVisible(false);
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
    gBtnIgnore.addListener(new InputListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        gBtnIgnore.setTouchable(Touchable.disabled);
        Runnable run = () -> {
          G.polygonAct.setScalePolyAct(1);
          GTween.action(gContinue, Actions.moveBy(0, -GStage.getWorldHeight(), .5f, Interpolation.swingIn),
                  () -> eftStart(true, null));
        };
        eftBtn(gBtnIgnore, run);

        countAdsFullScreen++;
        if (countAdsFullScreen %3 == 0) {
          plf.ShowFullscreen();
          countAdsFullScreen = 0;
        }
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void clickGBtnStartGame() {
    gBtnStartGame.addListener(new InputListener(){
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
    gBtnShop.addListener(new InputListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        eftBtn(gBtnShop, null);
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void clickGBtnRank() {
    gBtnRank.addListener(new InputListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        eftBtn(gBtnRank, null);
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  ///////////////////////////////////////////////EVENT CLICK////////////////////////////////////////

  public void setColorForUI(Color c1, Color c2) {

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
  }

  private void eftBtn(Group btn, Runnable run) {
    GTween.action(btn, Actions.scaleTo(.9f, .9f, .1f, Interpolation.fastSlow),
            () -> GTween.action(btn, Actions.scaleTo(1f, 1f, .1f, Interpolation.fastSlow), run));
  }

  private void resetLbScoreInGame() {
    lbScoreInGame.setText(0);
    float x = lbScoreInGame.getText().length * 96 * lbScoreInGame.getFontScaleX()/2;
    lbScoreInGame.setPosition(gUI.getWidth()/2 - x, -30);
  }
}
