import java.text.SimpleDateFormat;
  
 
 // This class is responsable for maintaining the time.
 // It implements Runnable in order to calculate the time.
 
public class StopWatchTimer implements Runnable {
  
    private StopWatchTimer swGui;
    private long starttime = 0;
    private long endtime = 0;
    private boolean isrunning = false;
    private long timepause = 0;
    private String timeresult;
  
     // The constructor has a StopWatchGUI parameter. The object is used to
     // access the StopWatchGUI class in order to set the text of a label to the
     // correct time.
 
    public StopWatchTimer( StopWatchTimer sw ) {
        this.swGui = sw;
    }
  
     // The method run is executed when the thread is started from the
     // StopWatchGUI. It calculates the time that has spend and formats it. Then
     // it assigns the time to the label in the GUI.
 
    @Override
    public void run() {
  
        try {
            while (isrunning) {
                timeresult = (formatTime(startCounting()));
                swGui.setTimerTextLabel(timeresult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
  
    }
  
     // Returns the String variable timeresult.
 
    public String getTimeResult() {
        return timeresult;
    }
  
     // This method is called when you want to start the timer. This needs to be
     // called before the thread.start() is called.
 
    public void start() {
        if (isrunning == false) {
            starttime = System.nanoTime() - (timepause - starttime);
        } else {
            starttime = System.nanoTime();
        }
        isrunning = true;
    }
  
     // This method is called when you want to pause the timer.
 
    public void pause() {
        isrunning = false;
        endtime = System.nanoTime();
        timepause = System.nanoTime();
    }
  
     // This method is called when you want to reset the timer.
 
    public void reset() {
        starttime = 0;
        endtime = 0;
        timepause = 0;
        isrunning = false;
    }
  
     // This method is called inside the run method. It returns the elapsed time.
     // If the boolean isrunning is false. Then it will return the end time.
 
    public long startCounting() {
        long elapsed;
        if (isrunning) {
            elapsed = System.nanoTime() - starttime;
        } else {
            elapsed = endtime - starttime;
  
        }
        return elapsed / 1000000;
    }
  
     // This method is called inside the run method. It has a parameter that
     // represents the time and reformats that time. It then returns the
     // reformatted time.
 
    public String formatTime(long elapsedTicks) {
  
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss.SSS");
        String time = formatter.format(elapsedTicks);
        return time;
    }
}