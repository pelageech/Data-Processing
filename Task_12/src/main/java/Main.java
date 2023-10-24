import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        TimerTask timerTask = new Sort(list);
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, TimeUnit.SECONDS.toMillis(5));

        Scanner scanner = new Scanner(System.in);
        for (;;) {
            String next= scanner.nextLine();
            if (next.equals(":exit")) {
                System.out.println("Exiting...");
                timer.cancel();
                break;
            }

            synchronized (list) {
                list.addAll(split(next));
            }
        }
    }

    private static List<String> split(String s) {
        List<String> l = new ArrayList<>();
        for (int i = 0; i <= s.length() / 80; i++) {
            l.add(s.substring(i*80, Math.min((i + 1) * 80, s.length())));
        }
        return l;
    }
}
