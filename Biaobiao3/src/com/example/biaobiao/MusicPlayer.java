package com.example.biaobiao;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class MusicPlayer {
	
	private static final int BULLET = 0;
	public static final int BURST = 1;
	public static final int SHAKE = 2;
	
	private float leftVolume;
	private float rightVolume;
	private float rate;
	private int maxSound;
	private int loop;
	private int sound[];
	private boolean useAble;
	
	private int[] music;
	private SoundPool spool;
	private static MediaPlayer player;
	
	private static MusicPlayer musicPlayer;
	
	private MusicPlayer(Context context){
		music = new int[]{ R.raw.biaosound, R.raw.diesound, R.raw.shake_sound};
		
		maxSound = 5;
		leftVolume = 0.04F;
		rightVolume = 0.03F;
		rate = 1;
		loop = 0;
		useAble = true;
		spool = new SoundPool(maxSound, AudioManager.STREAM_SYSTEM, 10);
		
		sound = new int[music.length];
		for(int i = 0; i < music.length; i++){
			sound[i] = spool.load(context, music[i], 0);
		}
		
		player = MediaPlayer.create(context, R.raw.background);
		player.setLooping(true);
	}
	
	public static MusicPlayer getMusicPlayer(Context context){
		if(musicPlayer==null){
			musicPlayer = new MusicPlayer(context);
		}
		try{
			player.getCurrentPosition();
		} catch(Exception e) {
			player = MediaPlayer.create(context, R.raw.background);
			player.setLooping(true);
		}
		return musicPlayer;
	}
	
	public void playSound(int num){
		if(useAble){
			spool.play(sound[num], leftVolume, rightVolume, 0, loop, rate);
		}
	}
	
	public void startBGM(){
		if (!player.isPlaying())
			player.start();
	}

	public void stopBGM(){
		player.stop();
		try {
			player.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void release(){
		player.release();
	}
	
	public boolean isPlayingBGM(){
		return player.isPlaying();
	}

	public float getLeftVolume() {
		return leftVolume;
	}

	public void setLeftVolume(float leftVolume) {
		this.leftVolume = leftVolume;
	}

	public float getRightVolume() {
		return rightVolume;
	}

	public void setRightVolume(float rightVolume) {
		this.rightVolume = rightVolume;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public int getMaxSound() {
		return maxSound;
	}

	public void setMaxSound(int maxSound) {
		this.maxSound = maxSound;
		spool = new SoundPool(maxSound, AudioManager.STREAM_SYSTEM, 10);
	}

	public int getLoop() {
		return loop;
	}

	public void setLoop(int loop) {
		this.loop = loop;
	}

	public boolean isUseAble() {
		return useAble;
	}

	public void setUseAble(boolean useAble) {
		this.useAble = useAble;
	}

	public static int getBullet() {
		return BULLET;
	}
	public static int getDie() {
		return BURST;
	}

}
