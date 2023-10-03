import java.util.LinkedList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        TimerTask timerTask = new Sort(list);
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, TimeUnit.SECONDS.toMillis(5));

        Scanner scanner = new Scanner(System.in);
        for (;;) {
            String s = scanner.nextLine();
            if (s.equals(":exit")) {
                System.out.println("Exiting...");
                timer.cancel();
                break;
            }

            synchronized (list) {
                list.add(s);
            }
        }
    }
}
