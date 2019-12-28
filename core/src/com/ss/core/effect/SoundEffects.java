package com.ss.core.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;

public class SoundEffects {

    private static SoundEffects instance;

    private HashMap<String, Music> s;
    private static FileHandle bg_music = Gdx.files.internal("sounds/bg_music.mp3");
    private static FileHandle music_1 = Gdx.files.internal("sounds/1.mp3");
    private static FileHandle music_2 = Gdx.files.internal("sounds/2.mp3");
    private static FileHandle game_over = Gdx.files.internal("sounds/game_over.mp3");
    private static FileHandle music_4 = Gdx.files.internal("sounds/4.mp3");
    private static FileHandle music_5 = Gdx.files.internal("sounds/5.mp3");
    private static FileHandle music_6 = Gdx.files.internal("sounds/6.mp3");
    private static FileHandle music_7 = Gdx.files.internal("sounds/7.mp3");
    private static FileHandle music_8 = Gdx.files.internal("sounds/8.mp3");
    private static FileHandle music_9 = Gdx.files.internal("sounds/9.mp3");

    public boolean isMute = false;

    public static SoundEffects getInstance() {
        return instance == null ? instance = new SoundEffects() : instance;
    }

    public void initSound() {
        s = new HashMap<>();
        s.put("bg_music", Gdx.audio.newMusic(bg_music));
        s.put("game_over", Gdx.audio.newMusic(game_over));
        s.put("0", Gdx.audio.newMusic(music_1));
        s.put("1", Gdx.audio.newMusic(music_2));
        s.put("2", Gdx.audio.newMusic(music_4));
        s.put("3", Gdx.audio.newMusic(music_5));
        s.put("4", Gdx.audio.newMusic(music_6));
        s.put("5", Gdx.audio.newMusic(music_7));
        s.put("6", Gdx.audio.newMusic(music_8));
        s.put("7", Gdx.audio.newMusic(music_9));

        s.get("bg_music").setLooping(true);
    }

    public void play(String ss) {
        if (!isMute)
            s.get(ss).play();
    }

    public void stop(String ss) {
        s.get(ss).stop();
    }
}
