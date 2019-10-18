import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
  
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
  
// This class contains all of the code to generate the GUI and handle mouse and keyboard events.
// The StopWatchGUI class extends JFrame.
// Every button has its own local inner class. Which implements ActionListener.
 
public class StopWatchGUI extends JFrame {
 
    private static final long serialVersionUID = -5697534085966933863L;
    private final Toolkit t = getToolkit();
    private final Dimension s = t.getScreenSize();
  
    private final JPanel toppanel = new JPanel();
    private final JPanel centerpanel = new JPanel();
    private final JPanel bottompanel = new JPanel();
  
    private final JButton btstart = new JButton("Start");
    private final JButton btreset = new JButton("Reset");
    private final JButton btlap = new JButton("Lap");
  
    private final JLabel labeltimer = new JLabel("00:00.000");
    private final JTextArea textarea = new JTextArea(9, 20);
  
    private StopWatchTimer sw = new StopWatchTimer(this);
  
    private boolean started = false;
    private String startorpause = "start";
  
    private int roundNumber = 0;
  
     // This method creates the GUI and set up all of the variables. The GUI will
     // appear in the center of the screen.
 
    public void createGui() {
        setResizable(false);
        setSize(500, 300);
        setTitle("TechAndCrack StopWatch");
        setLocation(s.width / 2 - getWidth() / 2, s.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
  
        textarea.setEditable(false);
        textarea.setLineWrap(true);
        JScrollPane scrollpane = new JScrollPane(textarea);
  
        toppanel.add(scrollpane);
        toppanel.setLayout(new GridLayout());
        add(toppanel, BorderLayout.NORTH);
  
        labeltimer.setForeground(Color.GRAY);
        labeltimer.setFont(new Font("Courier New", Font.BOLD, 36));
        centerpanel.add(labeltimer);
        add(centerpanel, BorderLayout.CENTER);
  
        add(bottompanel, BorderLayout.PAGE_END);
        bottompanel.setLayout(new GridLayout());
        bottompanel.add(btstart);
        bottompanel.add(btreset);
        bottompanel.add(btlap);
  
        btstart.addActionListener(new buttonstart());
        btreset.addActionListener(new buttonreset());
        btlap.addActionListener(new buttonlap());
  
        setVisible(true);
    }
  
    // This method sets the TimerLabel to the String parameter. The class
    // StopWatchTimer calls this method while running and by doing so set the
    // correct time to the label.
 
    public void setTimerTextLabel(String s) {
        labeltimer.setText(s);
    }
  
        // This local inner class implements ActionListener. Whenever the button
        // Start is clicked this class handles that event. It uses threads to
        // calculate the time. The button Start handles both the Start, pause and
        // continue events.
 
    private class buttonstart implements ActionListener {
  
        @Override
        public void actionPerformed(ActionEvent arg0) {
  
            Thread threadStopWatchTimer = new Thread(sw);
  
            if (startorpause == "start") {
                if (!started) {
                    started = true;
                    startorpause = "pause";
                    btstart.setText("Pause");
                    sw.start();
                    threadStopWatchTimer.start();
                }
            } else if (startorpause == "pause") {
                started = false;
                startorpause = "start";
                btstart.setText("Continue");
                sw.pause();
            }
        }
    }
  
        // This local inner class implements ActionListener. Whenever the button
        // Reset is clicked this class handles that event. It resets everything to
        // the default value and clears the JTextArea of text.
 
    private class buttonreset implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
  
            sw.reset();
            started = false;
            startorpause = "start";
            btstart.setText("Start");
            labeltimer.setText("00:00.000");
            textarea.setText("");
            roundNumber = 0;
        }
    }
  
     // This local inner class implements ActionListener. Whenever the button Lap
     // is clicked this class handles that event. When clicked this class appends
     // a text line to the JTextArea with the current round number and time.
 
    private class buttonlap implements ActionListener {
  
        @Override
        public void actionPerformed(ActionEvent e) {
  
            if (sw.getTimeResult() != null) {
                if (started) {
                    roundNumber++;
                    textarea.append("Lap: " + roundNumber + " Time: "
                            + sw.getTimeResult() + "\n");
                }
            }
        }
    }
}