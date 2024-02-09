import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        var exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleWithFixedDelay(new Sort(list),0, 5, TimeUnit.SECONDS);

        Scanner scanner = new Scanner(System.in);
        for (;;) {
            String next= scanner.nextLine();
            if (next.equals(":exit")) {
                System.out.println("Exiting...");
                exec.shutdown();
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
