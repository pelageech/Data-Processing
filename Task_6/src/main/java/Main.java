import java.util.concurrent.BrokenBarrierException;

public class Main {
    public static void main(String[] args) {
        var c = new Company(10);
        var f = new Founder(c);

        try {
            f.start();
        } catch (BrokenBarrierException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
