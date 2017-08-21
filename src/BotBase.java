
import javafx.scene.paint.Color;
import fedorabots.client.*;
import java.util.List;
import fedorabots.client.sensor.*;
import fedorabots.client.event.*;

public class BotBase extends Robot {

    //Using constants is good style, no magic numbers
    public static final Color BOT_COLOR = Color.SIENNA;
    public static final int DIFFICULTY = 5;

    public static void main(String[] args){
        new BotBase().run();
    }

    public void run() {

        setColor(Color.SIENNA);
        //Swap to a networked game when you want to compete with others
        joinLocalGame(DIFFICULTY);
        while (!isDead()) {
            if (canShoot())
            {
                int a = 1;
                setAcceleration(a,5);
                List<DetectedEntity> nearby = nearbyEntities();
                if (nearby.size() > 0) {
                    double y = nearby.get(0).getY();
                    double x = nearby.get(0).getX();
                    setBlasterRotation(90-180 * Math.atan2(-y + getY(), x - getX())/Math.PI);
                    shoot();
                }
            }
        }

                //Do something, how will your robot work?
            }

        }

