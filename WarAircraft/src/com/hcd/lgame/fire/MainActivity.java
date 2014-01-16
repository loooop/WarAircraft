package com.hcd.lgame.fire;

import org.loon.framework.android.game.LGameAndroid2DActivity;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends LGameAndroid2DActivity {

	@Override
	public void onMain() {
		/* ������Ϸ�Ļ�����СΪ�ֻ���Ļ��С */
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();
		
		int width = display.getWidth();
		int height = display.getHeight();
		if (width > height){
			maxScreen(width, height);
		} else {
			maxScreen(height, width);
		}
		
		this.initialization(false);
		this.setScreen(new GameScreen());
		this.setFPS(30);
		this.setShowFPS(true);
		this.showScreen();
	}


}