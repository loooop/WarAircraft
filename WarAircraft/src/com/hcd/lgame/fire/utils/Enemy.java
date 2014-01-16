package com.hcd.lgame.fire.utils;

import org.loon.framework.android.game.action.sprite.Sprite;

import com.hcd.lgame.fire.Images;
import com.hcd.lgame.fire.utils.Constent;

/**
 * 敌人飞机类
 * @author Salvador
 *不同飞机子弹打击的次数不一样
 */
public class Enemy {

	private Sprite sprite;
	private int shutNum;
	private String type;
	
	public Enemy(String type) {
		if (type.equals(Constent.SMALL_AIRPLANE)){
			this.sprite = new Sprite(Images.getInstance().getImage(2));
			this.shutNum = 1;
		} else if (type.equals(Constent.MIDDLE_AIRPLANE)) {
			this.sprite = new Sprite(Images.getInstance().getImage(0));
			this.shutNum = 5;
		} else if (type.equals(Constent.LARGE_AIRPLANE)) {
			this.sprite = new Sprite(Images.getInstance().getImage(3));
			this.shutNum = 10;
		}
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setShutNum(int shutNum) {
		this.shutNum = shutNum;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public int getShutNum() {
		return shutNum;
	}
}
