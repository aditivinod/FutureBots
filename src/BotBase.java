
import javafx.beans.binding.ListBinding;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import fedorabots.client.*;
import java.awt.image.BufferedImage;
import java.util.List;
import boofcv.*;

import org.w3c.dom.events.KeyboardEvent;

import fedorabots.client.sensor.*;
import fedorabots.client.event.*;

public class BotBase extends Robot {

	public static final Color BOT_COLOR = Color.FIREBRICK;
	public static final int DIFFICULTY = 9;
	int v = 0;
	int t = 0;
	int i = 0;
	public static void main(String[] args){
		new BotBase().run();
	}
	public void run() {

		setColor(Color.FIREBRICK);
		joinLocalGame(DIFFICULTY);
		//joinNetworkGame((short)0);
		while (!isDead()) {
			if (v==0) {
				//System.out.println(getHealth());
				shoot();
				int a = 50;
				setAcceleration (a,a);
				List<DetectedEntity> nearby = nearbyEntities();
				if (nearby.size() > 0) {
					for(DetectedEntity e: nearby) {
						//double y = nearby.get(0).getY();
						//double x = nearby.get(0).getX();
						double y = e.getY();
						double x = e.getX();
						double c = getX();
						double b = getY();
						double dist = Math.sqrt((x-c)+(y-b));
						if (e instanceof DetectedObstacle) {
							if (dist < 100){
								setAcceleration(c-x,b-y);
							}
						}
						if (e instanceof DetectedRobot) {
							setBlasterRotation(90-180 * Math.atan2(-y + getY(), x - getX())/Math.PI);
							shoot();
							if (dist < 100) {
								setAcceleration(x-c,y-b);
							}
						}		
					}
				}
			}	
			if (v==2) {
				List<DetectedEntity> nearby = nearbyEntities();
				if (nearby.size() > 0) {
					for(DetectedEntity e: nearby) {
						double y = e.getY();
						double x = e.getX();
						setBlasterRotation(90-180 * Math.atan2(-y + getY(), x - getX())/Math.PI);
						shoot(); 
					}
				}
			}
			if (t==1) {
				while(i<330) {
					setBlasterRotation(i);
					shoot();
					i = i +30;
					long prevtime = System.currentTimeMillis();
					while(System.currentTimeMillis() - 500 < prevtime) {
						double g = getVx()*0.99;
						double h = getVy()*0.99;
						setAcceleration(0-g, 0-h);
					}
				}
			}
		}
	}


	//	try {
	//		Thread.sleep(10);
	//	} catch (InterruptedException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
	//		}

	public void onKeyPressed(KeyEvent event) {
		if (event.getCode()==KeyCode.G) {
			v = 1;
		}
		if (v==1 || v==2) {
			if (event.getCode()==KeyCode.A) {
				setBlasterRotation(270);
			}  
			if (event.getCode()==KeyCode.D) {
				setBlasterRotation(90);
			}
			if (event.getCode()==KeyCode.W) {
				setBlasterRotation(0);
			}
			if (event.getCode()==KeyCode.S) {
				setBlasterRotation(180);
			}
			if (event.getCode()==KeyCode.LEFT) {
				setAcceleration(-100,0);
			}
			if (event.getCode()==KeyCode.RIGHT) {
				setAcceleration(100,0);
			}
			if (event.getCode()==KeyCode.UP) {
				setAcceleration(0, -100);
			}
			if (event.getCode()==KeyCode.DOWN) {
				setAcceleration(0, 100);
			}
			if (event.getCode()==KeyCode.SPACE) {
				shoot();
			}
			if (event.getCode()==KeyCode.F) {
				double g = getVx()*0.99;
				double h = getVy()*0.99;
				setAcceleration(0-g, 0-h);
			}
			if (event.getCode()==KeyCode.R) {
				t = 1;
			}
			if (event.getCode()==KeyCode.H) {
				v = 0;
			}
			if (event.getCode()==KeyCode.T) {
				v = 2;
			}
			//scurrent <- scurrent + Kp*E +Ki*ft0 E d u;
		}
	}
}






