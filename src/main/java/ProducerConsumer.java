import java.util.concurrent.ThreadLocalRandom;
import java.util.LinkedList;

public class ProducerConsumer {
  public static void main(String[] args)
          throws InterruptedException {
    final SharedArea shared = new SharedArea(10);

    // Create producer thread
    Thread prod_thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          shared.produce();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    // Create consumer thread
    Thread cons_thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          shared.consume();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    prod_thread.start();
    cons_thread.start();

    prod_thread.join();
    cons_thread.join();
  }

  public static class SharedArea {

    private final int SIZE;
    private LinkedList<Integer> buffer = new LinkedList<>();
    private Semaphore mutex;
    private Semaphore empty;
    private Semaphore full;

    public SharedArea(int size) {
      SIZE = size;
      this.mutex = new Semaphore(1);
      this.empty = new Semaphore(size);
      this.full = new Semaphore(0);
    }

    public void produce() throws InterruptedException {
      int element = 0;
      while (true) {
        int delay = ThreadLocalRandom.current().nextInt(0, 1500 + 1);
        Thread.sleep(delay);
        empty.down();
        mutex.down();
        System.out.println("Produce " + element);
        buffer.add(element++);
        mutex.up();
        full.up();
      }
    }

    public void consume() throws InterruptedException {
      int element;
      while (true) {
        Thread.sleep(1000);
        full.down();
        mutex.down();
        element = buffer.removeFirst();
        System.out.println("            Consume " + element);
        mutex.up();
        empty.up();
      }
    }
  }
}
