package com.hcd.lgame.fire;

import org.loon.framework.android.game.LGameAndroid2DActivity;

import com.hcd.lgame.fire.utils.ExitApplication;

import android.view.Display;
import android.view.WindowManager;

public class HomeActivity extends  LGameAndroid2DActivity{

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
		this.setScreen(new HomeScreen());
		this.setFPS(30);
		this.setShowFPS(true);
		this.showScreen();
		ExitApplication.getInstance().addActivity(this);
	}

}
