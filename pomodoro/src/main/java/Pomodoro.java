import java.util.Scanner;

public class Pomodoro {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String command;

        System.out.print("Enter timer interval: ");
        int timerInterval = s.nextInt();

        System.out.print("Enter break interval: ");
        int breakInterval = s.nextInt();

        System.out.print("How many times do you want the timer to repeat, <Enter> if none: ");
        String input = s.nextLine();
        //If <Enter> is pressed (.isEmpty() will return true), set the default repetitions to 1
        int repetitions = input.isEmpty() ? 1 : Integer.parseInt(input);

        PomodoroTimer pomodoro = new PomodoroTimer(timerInterval, breakInterval, repetitions);

        //We make a thread and start the timer
        Thread pomodoroTimerThread = new Thread(() -> {
            try {
                pomodoro.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pomodoroTimerThread.start();

        //Commands to pause/unpause the timer
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
