package com.hcd.lgame.fire;

import java.util.LinkedList;

import org.loon.framework.android.game.action.sprite.Sprite;
import org.loon.framework.android.game.core.graphics.LColor;
import org.loon.framework.android.game.core.graphics.Screen;
import org.loon.framework.android.game.core.graphics.device.LGraphics;
import org.loon.framework.android.game.core.timer.LTimerContext;

import com.hcd.lgame.fire.utils.Constent;
import com.hcd.lgame.fire.utils.Enemy;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GameScreen extends Screen{

	//我军飞机
	private Sprite ownAirPlane;
	//敌军飞机
	private LinkedList<Enemy> enemys = new LinkedList<Enemy>();
	
	//飞机子弹
	private LinkedList<Sprite> bullets = new LinkedList<Sprite>();
	//游戏得分
	private double score;
	private Handler handler;
	//手指触摸位置与飞机位置的坐标偏移量
	private double offsetX, offsetY;
	
	private boolean gameOver = false;
	
	public GameScreen() {
		initRole();
	}
	
	private void initRole() {
		score = 0;
		this.setBackground(LColor.lightGray);
		ownAirPlane = new Sprite(Images.getInstance().getImage(1));
		ownAirPlane.setLocation(250, this.getHeight() - ownAirPlane.getHeight());
		/* 将精灵加入精灵管理器 */
		getSprites().add(ownAirPlane);
		
		getSprites().setVisible(true);
		
		startThread();
	}
	
	public void startThread() {
		handler = new Handler();
		handler.post(new BulletThread());
		handler.post(new FireThread());
		handler.post(new EnemyThread());
		handler.post(new EnemyMoveThread());
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
	public boolean onTouchDown(MotionEvent me) {
		//现在手指的坐标
		double x = (int)me.getX();
		double y = (int)me.getY();
		//现在飞机的坐标
		int x1 = ownAirPlane.x();
		int y1 = ownAirPlane.y();
		offsetX = x1 - x;
		offsetY = y1 - y;
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent me) {
		
		//移动中飞机坐标位置
		double move_x, move_y;
		move_x = (int) (me.getX() + offsetX);
		move_y = (int) (me.getY() + offsetY);
		//飞机不能飞出屏幕意外的位置
		if (move_x <= 0) {move_x = 0;}
		if (move_x > this.getWidth() - ownAirPlane.getWidth()){
			move_x = this.getWidth() - ownAirPlane.getWidth();
		}
		if (move_y <= 0) {move_y = 0;}
		if (move_y > this.getHeight() - ownAirPlane.getHeight()){
			move_y = this.getHeight() - ownAirPlane.getHeight();
		}
		ownAirPlane.setLocation(move_x, move_y);
		//fireBullet();
		return false;
	}

	@Override
	public boolean onTouchUp(MotionEvent arg0) {
		return false;
	}

	@Override
	public void draw(LGraphics g) {
		g.drawString("Bullets:" + bullets.size(), 0, 70);
		g.drawString("Enemys:" + enemys.size(), 0, 90);
		g.drawString("Score:"+score, 0, 110);
	}

	@Override
	public void onTouch(float arg0, float arg1, MotionEvent arg2, int arg3,
			int arg4) {
		
	}
	
	/**
	 * 子弹飞行线程
	 * @author Salvador
	 */
	class BulletThread implements Runnable {

		@Override
		public void run() {
			if (!gameOver) {
				for (int i = 0; i < bullets.size(); i++) {
					GameScreen.this.bullets.get(i).setLocation(GameScreen.this.bullets.get(i).getX(),
							GameScreen.this.bullets.get(i).getY() - 15);
					//移除飞出屏幕外的子弹
					if (GameScreen.this.bullets.get(i).getY() <= 0) {
						GameScreen.this.getSprites().remove(bullets.get(i));
						bullets.remove(i);
					}
				}
				handler.postDelayed(this, 30);
			}
		}
	}
	
	class FireThread implements Runnable {

		@Override
		public void run() {
			if (!gameOver) {
				fireBullet();
				for (int i = 0; i < bullets.size(); i++) {
					for (int j = 0; j < enemys.size(); j++) {
						if (bullets.get(i).isRectToRect(enemys.get(j).getSprite())) {
							enemys.get(j).setShutNum(enemys.get(j).getShutNum() - 1);
							if (enemys.get(j).getShutNum() <= 0) {
								if (enemys.get(j).getType().equals(Constent.SMALL_AIRPLANE)) {
									score += 100;
								} else if (enemys.get(j).getType().equals(Constent.MIDDLE_AIRPLANE)) {
									score += 300;
								} else if (enemys.get(j).getType().equals(Constent.LARGE_AIRPLANE)) {
									score += 500;
								} 
								getSprites().remove(enemys.get(j).getSprite());
								enemys.remove(j);
							}
							//getSprites().remove(enemys.get(j).getSprite());
							getSprites().remove(bullets.get(i));
							bullets.remove(i);
						}
					}
				}
				handler.postDelayed(this, 100);
			}
		}
	}

	/**
	 * 敌人飞机飞出线程
	 * @author Salvador
	 */
	class EnemyThread implements Runnable {

		@Override
		public void run() {
			if (!gameOver) {
				//加入敌人小型飞机
				Enemy enemy = new Enemy(Constent.SMALL_AIRPLANE);
				enemy.getSprite().setLocation(Math.random()*(GameScreen.this.getWidth() - enemy.getSprite().getWidth()), 0);
				//enemy.setLocation(Math.random()*(ExampleScreen.this.getWidth() - enemy.getWidth()), 0);
				GameScreen.this.getSprites().add(enemy.getSprite());
				GameScreen.this.enemys.add(enemy);
				//加入敌人中型飞机
				enemy = new Enemy(Constent.MIDDLE_AIRPLANE);
				enemy.getSprite().setLocation(Math.random()*(GameScreen.this.getWidth() - enemy.getSprite().getWidth()), 0);
				GameScreen.this.getSprites().add(enemy.getSprite());
				GameScreen.this.enemys.add(enemy);
				
				handler.postDelayed(this, 1000);
			}
		}
	}
	
	/**
	 * 敌人飞机移动线程
	 * @author Salvador
	 *
	 */
	class EnemyMoveThread implements Runnable {

		@Override
		public void run() {
			if (!gameOver) {
				for (int i = 0; i < GameScreen.this.enemys.size(); i++) {
					Sprite enemy = GameScreen.this.enemys.get(i).getSprite();
					GameScreen.this.enemys.get(i).getSprite().setLocation(GameScreen.this.enemys.get(i).getSprite().getX(), 
							GameScreen.this.enemys.get(i).getSprite().getY() + 10);
					if (GameScreen.this.enemys.get(i).getSprite().getY() >= GameScreen.this.getHeight()) {
						enemys.remove(i);
						GameScreen.this.getSprites().remove(GameScreen.this.enemys.get(i).getSprite());
					}
					if (enemy.isRectToRect(GameScreen.this.ownAirPlane)) {
						GameScreen.this.enemys.remove(i);
						GameScreen.this.getSprites().remove(enemy);
						GameScreen.this.getSprites().remove(GameScreen.this.ownAirPlane);
						//游戏结束重新开始
						getSprites().removeAll();
						gameOver = true;
						restart();
						return ;
						//handler. ;
					}
				}
			}
			handler.postDelayed(this, 100);
		}
		
	}
	
	@Override
	public void alter(LTimerContext timer) {
		super.alter(timer);
	}
	
	public void fireBullet() {
		Sprite bullet = new Sprite(Images.getInstance().getImage(3));
		bullet.setLocation(ownAirPlane.getWidth() / 2 + ownAirPlane.x() - bullet.getWidth() / 2 + 1, ownAirPlane.getY() - bullet.getHeight() - 10);
		bullets.add(bullet);
		getSprites().add(bullet);
	}
	/**
	 * 重新开始游戏
	 */
	public void restart() {
		removeAll();
		refresh();
		enemys.clear();
		bullets.clear();
		gameOver = false;
		initRole();
	}
	
}
