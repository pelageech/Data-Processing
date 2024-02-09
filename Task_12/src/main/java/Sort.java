import java.util.LinkedList;
import java.util.TimerTask;

public class Sort extends TimerTask {
    private final LinkedList<String> list;

    public Sort(LinkedList<String> list) {
        this.list = list;
    }
    public void sortLinkedList() {
        if (list.isEmpty()) {
            return;
        }
        synchronized (list) {
            list.sort((o1, o2) -> {
                if (o1.length() < o2.length()) {
                    return -1;
                } else if (o1.length() > o2.length()) {
                    return 1;
                }
                return o1.compareTo(o2);
            });
        }
        for (var v : list) {
            System.out.print(v + " ");
        }
        System.out.println();
    }

    @Override
    public void run() {
        sortLinkedList();
    }
}
