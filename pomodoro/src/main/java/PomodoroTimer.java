import java.util.Date;

public class PomodoroTimer {
    private final long MS_TO_MIN = 1000L * 60L;

    private final int timerInterval;
    private final int breakInterval;
    private int repetitions;
    private volatile boolean isPaused = false;

    private final Object threadLock = new Object();

    public PomodoroTimer(int timerInterval, int breakInterval) {
        this.timerInterval = timerInterval;
        this.breakInterval = breakInterval;
    }

    //Overloaded for repetition
    public PomodoroTimer(int timerInterval, int breakInterval, int repetitions) {
        this.timerInterval = timerInterval;
        this.breakInterval = breakInterval;
        this.repetitions = repetitions;
    }

    public void start() throws InterruptedException {
        for (int i = 0; i < this.repetitions; ++i) {
            runInterval(timerInterval, "Work");
            runInterval(breakInterval, "Break");
        }
    }

    public void runInterval(int interval, String taskName) throws InterruptedException {
        long elapsed = 0;
        final long totalMilliseconds = interval * MS_TO_MIN;

        System.out.println(taskName + " started on " + new Date());
        while (elapsed < totalMilliseconds) {
            synchronized (threadLock) {
                while (isPaused) {
                    System.out.println("Paused at " + (elapsed / MS_TO_MIN) + " minute of " + taskName);
                    threadLock.wait();
                }
            }
            //We check for a pause one a second
            Thread.sleep(1000L);
            elapsed += 1000L;
        }
        System.out.println(taskName + " completed on " + new Date());
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        synchronized (threadLock) {
            isPaused = false;
            threadLock.notify();
        }
    }
}
