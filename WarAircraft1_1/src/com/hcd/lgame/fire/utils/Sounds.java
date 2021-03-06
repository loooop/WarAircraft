package com.hcd.lgame.fire.utils;

import com.hcd.lgame.fire.GameActivity;
import com.hcd.lgame.fire.R;

import android.media.AudioManager;
import android.media.SoundPool;

public class Sounds {

	private static Sounds soundFactory;
	private static SoundPool sp;
	private static int sounds[];
	
	public Sounds(GameActivity ma) {
		sounds = new int[5];
		sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		sounds[0] = sp.load(ma, R.raw.fire, 1);
		sounds[1] = sp.load(ma, R.raw.bomb, 1);
	}
	
	public void palySound(int i) {
		sp.play(sounds[i], 1, 1, 0, 0, 1);
	}
	
	public static synchronized Sounds getInstance(GameActivity ma) {
		if (soundFactory != null) {
			return soundFactory;
		} else {
			soundFactory = new Sounds(ma);
			return soundFactory;
		}
	}
}
