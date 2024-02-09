import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public final class Founder {
    private final List<Runnable> workers;
    private final CyclicBarrier barrier;
    private final Company company;
    public Founder(final Company company) {
        this.company = company;
        var companyCount = company.getDepartmentsCount();

        barrier = new CyclicBarrier(companyCount + 1);

        this.workers = new ArrayList<>(companyCount);
        for (int i = 0; i < companyCount; i++) {
            var ii = i;
            workers.add(() -> {
                company.getFreeDepartment(ii).performCalculations();

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    System.out.println(e.getMessage() + ": " + Thread.currentThread().getName());
                }
            });
        }
    }
    public void start() throws BrokenBarrierException, InterruptedException {

        for (final Runnable worker : workers) {
            new Thread(worker).start();
        }

        barrier.await();
        company.showCollaborativeResult();
    }
}