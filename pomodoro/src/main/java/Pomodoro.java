import java.util.Scanner;

public class Pomodoro {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String command;

        System.out.print("Enter timer interval: ");
        int timerInterval = s.nextInt();

        System.out.print("Enter break interval: ");
        int breakInterval = s.nextInt();

        PomodoroTimer pomodoro = new PomodoroTimer(timerInterval, breakInterval);

        Thread pomodoroTimerThread = new Thread(() -> {
            try {
                pomodoro.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pomodoroTimerThread.start();

        while (pomodoroTimerThread.isAlive()) {
            command = s.next();
            if (command.equalsIgnoreCase("pause")) {
                pomodoro.pause();
            } else if (command.equalsIgnoreCase("resume")) {
                pomodoro.resume();
            }
        }
    }
}
