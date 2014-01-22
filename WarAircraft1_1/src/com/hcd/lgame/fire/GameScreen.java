package com.hcd.lgame.fire;

import java.util.ArrayList;
import org.loon.framework.android.game.action.sprite.Sprite;
import org.loon.framework.android.game.core.graphics.Screen;
import org.loon.framework.android.game.core.graphics.device.LGraphics;
import org.loon.framework.android.game.core.graphics.window.LButton;
import org.loon.framework.android.game.core.graphics.window.LPaper;
import com.hcd.lgame.fire.utils.Constent;
import com.hcd.lgame.fire.utils.Enemy;
import com.hcd.lgame.fire.utils.ExitApplication;
import com.hcd.lgame.fire.utils.Images;
import com.hcd.lgame.fire.utils.Sounds;

import android.os.Handler;

import android.view.KeyEvent;
import android.view.MotionEvent;

public class GameScreen extends Screen{

	//�Ҿ��ɻ�
	private Sprite ownAirPlane;
	//�о��ɻ�
	private ArrayList<Enemy> enemys = new ArrayList<Enemy>();
	//�ɻ��ӵ�
	private ArrayList<Sprite> bullets = new ArrayList<Sprite>();
	//��Ϸ�÷�
	private double score;
	private static Handler handler = new Handler();
	//��ָ����λ����ɻ�λ�õ�����ƫ����
	private double offsetX, offsetY;
	private Sounds soundsFactory;
	private boolean gameOver;
	public LButton startBut, aboutBut, exitBut;
	private LPaper beginPaper;
	private boolean init;

	public GameScreen(GameActivity ga) {
		soundsFactory = new Sounds(ga);
	}
	
	@Override
	public void onLoad() {
		setBackground(Images.getInstance().getImage(5));
		reset();
	}

	private void reset() {
		gameOver = false;
		init = false;
		score = 0;
		initUI();
	}
	
	private void initUI() {
		
		ownAirPlane = new Sprite(Images.getInstance().getImage(1));
		ownAirPlane.setLocation((getWidth() - ownAirPlane.getWidth())/2, getHeight()-ownAirPlane.getHeight());
		initRole();
	}

	private void initRole() {

		beginPaper = new LPaper(10, 10, this.getWidth()*3/4, getHeight()*3/4);
		//beginPaper.setBackground(LColor.gray);
		startBut = new LButton("res/button.png") {
			@Override
			public void doClick() {
				if (!init) {
					if (!gameOver) {
						getSprites().add(ownAirPlane);
						//����һ�����߳����ɵ��˵�С�ɻ�
						new Thread(new Runnable() {
							@Override
							public void run() {
								handler.post(new Runnable() {
									@Override
									public void run() {
										if (!gameOver) {
											//�������С�ͷɻ�
											Enemy enemy = new Enemy(Constent.SMALL_AIRPLANE);
											enemy.getSprite().setLocation(Math.random()*(GameScreen.this.getWidth() - enemy.getSprite().getWidth()), 0);
											//enemy.setLocation(Math.random()*(ExampleScreen.this.getWidth() - enemy.getWidth()), 0);
											GameScreen.this.getSprites().add(enemy.getSprite());
											GameScreen.this.enemys.add(enemy);
											//����������ͷɻ�
											enemy = new Enemy(Constent.MIDDLE_AIRPLANE);
											enemy.getSprite().setLocation(Math.random()*(GameScreen.this.getWidth() - enemy.getSprite().getWidth()), 0);
											GameScreen.this.getSprites().add(enemy.getSprite());
											GameScreen.this.enemys.add(enemy);
											
											handler.postDelayed(this, 500);
										}
									}
								});
							}
						}).start();
						//����һ���ɻ��ƶ����߳�
						new Thread(new Runnable() {
							@Override
							public void run() {
								handler.post(new Runnable() {
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
													//��Ϸ�������¿�ʼ
													getSprites().removeAll();
													gameOver = true;
													return ;
													//handler. ;
												}
											}
										}
										handler.postDelayed(this, 100);
									}
								});
							}
						}).start();
						//����һ���ӵ������߳�
						new Thread(new Runnable() {
							@Override
							public void run() {
								handler.post(new Runnable() {
									@Override
									public void run() {
										if (!gameOver) {
											for (int i = 0; i < bullets.size(); i++) {
												GameScreen.this.bullets.get(i).setLocation(GameScreen.this.bullets.get(i).getX(),
														GameScreen.this.bullets.get(i).getY() - 8);
												
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
															soundsFactory.palySound(1);
															getSprites().remove(enemys.get(j).getSprite());
															enemys.remove(j);
														}
														//getSprites().remove(enemys.get(j).getSprite());
														getSprites().remove(bullets.get(i));
														bullets.remove(i);
													}
												}
												
												//�Ƴ��ɳ���Ļ����ӵ�
												if (GameScreen.this.bullets.get(i).getY() <= 0) {
													GameScreen.this.getSprites().remove(bullets.get(i));
													bullets.remove(i);
												}
											}
											handler.postDelayed(this, 30);
										}
									}
								});
							}
						}).start();
						//����һ�������ӵ��߳�
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								handler.post(new Runnable() {
									
									@Override
									public void run() {
										if (!gameOver) {
											Sprite bullet = new Sprite(Images.getInstance().getImage(3));
											bullet.setLocation(ownAirPlane.getWidth() / 2 + ownAirPlane.x() - bullet.getWidth() / 2 + 1, ownAirPlane.getY() - bullet.getHeight() - 10);
											bullets.add(bullet);
											soundsFactory.palySound(0);
											getSprites().add(bullet);
											handler.postDelayed(this, 100);
										}
									}
								});
							}
						}).start();
					}
				}
				remove(beginPaper);
			}
		};
		startBut.setLocation((beginPaper.getWidth() - startBut.getWidth()) / 2, 100);
		startBut.setText("��ʼ��Ϸ");
		
		aboutBut = new LButton("res/button.png") {
			@Override
			public void doClick() {
				
			}
		};
		aboutBut.setLocation((beginPaper.getWidth() - aboutBut.getWidth()) / 2, 200);
		aboutBut.setText("����");
		
		exitBut = new LButton("res/button.png") {
			@Override
			public void doClick() {
				ExitApplication.getInstance().exit();
			}
		};
		exitBut.setLocation((beginPaper.getWidth() - exitBut.getWidth()) / 2, 300);
		exitBut.setText("�˳�");
		beginPaper.add(startBut);
		beginPaper.add(aboutBut);
		beginPaper.add(exitBut);
		centerOn(beginPaper);
		add(beginPaper);
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
		if (!gameOver) {
			//������ָ������
			double x = (int)me.getX();
			double y = (int)me.getY();
			//���ڷɻ�������
			int x1 = ownAirPlane.x();
			int y1 = ownAirPlane.y();
			offsetX = x1 - x;
			offsetY = y1 - y;
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent me) {
		if (!gameOver){
			//�ƶ��зɻ�����λ��
			double move_x, move_y;
			move_x = (int) (me.getX() + offsetX);
			move_y = (int) (me.getY() + offsetY);
			//�ɻ����ܷɳ���Ļ�����λ��
			if (move_x <= 0) {move_x = 0;}
			if (move_x > this.getWidth() - ownAirPlane.getWidth()){
				move_x = this.getWidth() - ownAirPlane.getWidth();
			}
			if (move_y <= 0) {move_y = 0;}
			if (move_y > this.getHeight() - ownAirPlane.getHeight()){
				move_y = this.getHeight() - ownAirPlane.getHeight();
			}
			ownAirPlane.setLocation(move_x, move_y);
			return true;
		}
		//fireBullet();
		return false;
	}

	@Override
	public boolean onTouchUp(MotionEvent arg0) {
		return false;
	}

	@Override
	public void draw(LGraphics arg0) {
		
	}

	@Override
	public void onTouch(float arg0, float arg1, MotionEvent arg2, int arg3,
			int arg4) {
		
	}
}
