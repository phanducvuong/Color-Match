package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Locale;
import java.util.MissingResourceException;

public class C {

    public static class remote {
        public static int adsTime = 50;
        static void initRemoteConfig() {

        }
    }

    public static class lang {
        private static I18NBundle locale;
        public static String title = "";
        public static String adsTimeLbl = "";
        public static String playnow = "";
        public static String continuee = "";
        public static String ignore = "";
        public static String gameOver = "";
        public static String result = "";
        public static String STT = "";
        public static String player = "";
        public static String score = "";
        public static String coin = "";
        public static String tks = "";
        public static String lvUp = "";
        public static String lv = "";
        public static String getItem = "";
        public static String itemDefault = "";
        public static String shop = "";

        static void initLocalize() {
            FileHandle specFilehandle = Gdx.files.internal("i18n/lang_" + "id");
            FileHandle baseFileHandle = Gdx.files.internal("i18n/lang");

            try {
                locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
            }
            catch (MissingResourceException e) {
                locale = I18NBundle.createBundle(baseFileHandle, new Locale(""));
            }

            title = locale.get("title");
            adsTimeLbl = locale.format("adsTime", remote.adsTime);
            playnow = locale.get("playnow");
            continuee = locale.get("continue");
            ignore = locale.get("ignore");
            gameOver = locale.get("game_over");
            result = locale.get("result");
            STT = locale.get("STT");
            player = locale.get("player");
            score = locale.get("score");
            coin = locale.get("coin");
            tks = locale.get("tks");
            lvUp = locale.get("lvUp");
            lv = locale.get("lv");
            getItem = locale.get("getItem");
            itemDefault = locale.get("itemDefault");
            shop = locale.get("shop");
        }
    }

    public static void init() {
        remote.initRemoteConfig();
        lang.initLocalize();
    }
}
