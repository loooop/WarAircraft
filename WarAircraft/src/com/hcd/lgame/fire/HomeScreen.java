package com.hcd.lgame.fire;

import org.loon.framework.android.game.core.graphics.LColor;
import org.loon.framework.android.game.core.graphics.Screen;
import org.loon.framework.android.game.core.graphics.device.LGraphics;

import com.hcd.lgame.fire.utils.Images;

import android.view.KeyEvent;
import android.view.MotionEvent;

public class HomeScreen extends Screen{
	
	public HomeScreen() {

		setBackground(Images.getInstance().getImage(5));
	}

	@Override
	public boolean onKeyDown(int arg0, KeyEvent arg1) {
		return false;
	}

	@Override
	public boolean onKeyUp(int arg0, KeyEvent arg1) {
		return false;
	}

	@Override
	public boolean onTouchDown(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onTouchUp(MotionEvent arg0) {
		return false;
	}

	@Override
	public void draw(LGraphics g) {}

	@Override
	public void onTouch(float arg0, float arg1, MotionEvent arg2, int arg3,
			int arg4) {
		
	}

}
