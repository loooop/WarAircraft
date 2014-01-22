package com.hcd.lgame.fire.utils;

import org.loon.framework.android.game.core.graphics.LImage;
import org.loon.framework.android.game.core.resource.LPKResource;
import org.loon.framework.android.game.utils.GraphicsUtils;

/**
 * ������ϷͼƬ������
 * @author Salvador
 *
 */
public class Images {

	private static Images imageFactory;
	private static LImage images[];
	public Images() {
		images = new LImage[100];
		
		images[0] = GraphicsUtils.loadImage("res/mplane.png");
		images[1] = GraphicsUtils.loadImage("res/ownplane.png");
		images[2] = GraphicsUtils.loadImage("res/splane.png");
		images[3] = GraphicsUtils.loadImage("res/bullet.png");
		
		final String res="res/res.lpk";
		images[4] = LPKResource.openImage(res, "win.png");
		images[5] = GraphicsUtils.loadImage("res/background.jpg");
		images[6] = GraphicsUtils.loadImage("res/bullets.png");
		images[7] = GraphicsUtils.loadImage("res/button.png");
	}
	
	public LImage getImage(int i) {
		return images[i];
	}
	
	public static synchronized Images getInstance() {
		if (imageFactory != null) {
			return imageFactory;
		} else {
			imageFactory = new Images();
			return imageFactory;
		}
	}
}