package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingConstants;
import logic.ThreadClock;
import logic.ThreadLabel;

/**
 * Clock MultiThread - Practice 2
 *
 * @author Carlos
 */
public class UIClock extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel labelClock;
    private JButton btnStart;
    private JButton btnPause;
    private JButton btnStop;
    private JButton btnInitialize;
    private JButton btnExit;
    private JLabel label1;
    private JLabel label2;
    private ThreadClock t3;
    private Thread t1;
    private Thread t2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIClock frame = new UIClock();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public UIClock() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 141);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel2 = new JPanel();
        contentPane.add(panel2, BorderLayout.SOUTH);
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        btnStart = new JButton("Start");
        panel2.add(btnStart);

        btnPause = new JButton("Pause");
        btnPause.setEnabled(false);
        panel2.add(btnPause);

        btnStop = new JButton("Stop");
        btnStop.setEnabled(false);
        panel2.add(btnStop);

        btnInitialize = new JButton("Initialize");
        btnInitialize.setEnabled(false);
        panel2.add(btnInitialize);

        btnExit = new JButton("Exit");
        panel2.add(btnExit);

        JPanel panel1 = new JPanel();
        contentPane.add(panel1, BorderLayout.NORTH);
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        label1 = new JLabel("<<");
        label1.setFont(new Font("Tahoma", Font.BOLD, 30));
        panel1.add(label1);

        labelClock = new JLabel("00:00");
        labelClock.setFont(new Font("Tahoma", Font.PLAIN, 30));
        labelClock.setHorizontalAlignment(SwingConstants.CENTER);
        labelClock.setPreferredSize(new Dimension(100, 50));
        panel1.add(labelClock);

        label2 = new JLabel(">>");
        label2.setFont(new Font("Tahoma", Font.BOLD, 30));
        panel1.add(label2);

        // Action listeners
        btnStart.addActionListener(this);
        btnPause.addActionListener(this);
        btnStop.addActionListener(this);
        btnInitialize.addActionListener(this);
        btnExit.addActionListener(this);

        // Start threads label1 and label2
        ThreadLabel et1 = new ThreadLabel(label1, "Label 1 - left");
        t1 = new Thread(et1);
        t1.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        ThreadLabel et2 = new ThreadLabel(label2, "Label 2 - right");
        t2 = new Thread(et2);
        t2.setPriority(Thread.MIN_PRIORITY);
        t2.start();
    }

    public void actionPerformed(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        if (sourceButton == btnExit) {
            this.exit();
        } else if (sourceButton == btnStart) {
            if (t3 == null || !t3.isAlive()) {
                t3 = new ThreadClock(labelClock, "Counter clock");
                t3.setPriority(Thread.NORM_PRIORITY);
                t3.start();

            }
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            btnPause.setEnabled(true);
            btnInitialize.setEnabled(true);
        } else if (sourceButton == btnStop) {
            if (t3.isAlive()) {
                t3.interrupt();
            }
            t3.initialize();
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
            btnPause.setEnabled(false);
            btnInitialize.setEnabled(false);
        } else if (sourceButton == btnInitialize) {
            if (t3 != null) {
                t3.initialize();
            }
        } else if (sourceButton == btnPause) {
            if (btnPause.getText().equalsIgnoreCase("Pause")) {
                btnPause.setText("Resume");
                t3.setPause(true);
                btnStop.setEnabled(false);
                btnInitialize.setEnabled(false);
            } else {
                btnPause.setText("Pause");
                t3.setPause(false);

                btnStop.setEnabled(true);
                btnInitialize.setEnabled(true);
            }
        }
    }

    private void exit() {
        if (t3 != null && t3.isAlive()) {
            t3.interrupt();
        }
        if (t1.isAlive()) {
            t1.interrupt();
        }
        if (t2.isAlive()) {
            t2.interrupt();
        }
        this.dispose();
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "End of application.");
        System.exit(0);
    }
}
