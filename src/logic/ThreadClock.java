package logic;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 * Clock Manager Thread
 * @author Carlos
 */
public class ThreadClock extends Thread {

    NumberFormat formatter = new DecimalFormat("00");
    private JLabel label;
    private int min;
    private int seg;
    private final AtomicBoolean pauseFlag = new AtomicBoolean(false);

    public ThreadClock(JLabel label, String name) {
        super(name);
        this.label = label;
        initialize();
    }

    public void run() {
        try {
            for (;;) {
                if (!pauseFlag.get()) {
                    Thread.sleep(1000);
                    this.increment();
                    this.display();
                }
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "End (" + this.getName() + ")");
        }
    }

    private void display() {
        label.setText(String.valueOf(formatter.format(min)) + ":" + String.valueOf(formatter.format(seg)));
        //Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, String.valueOf(formatter.format(min)) + ":" + String.valueOf(formatter.format(seg)));
    }

    private void increment() {
        if (seg == 59) {
            seg = 0;
            if (min == 59) {
                min = 0;
            }else{
               min++;
            }
        }else{
            seg++;
        }
    }

    public void initialize() {
        seg = 0;
        min = 0;
        this.display();
    }

    public void setPause(boolean pause) {
        pauseFlag.set(pause);
    }
}
