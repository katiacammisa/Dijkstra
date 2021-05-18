import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class SharedAreaImpl implements SharedArea {

  private final int SIZE;
  private final LinkedList<Integer> buffer = new LinkedList<>();
  private final Semaphore mutex;
  private final Semaphore empty;
  private final Semaphore full;
  private int prodElement = 0;
  private int consElement = 0;

  public SharedAreaImpl(int size) {
    SIZE = size;
    this.mutex = new Semaphore(1);
    this.empty = new Semaphore(size);
    this.full = new Semaphore(0);
  }

  @Override
  public void produce() throws InterruptedException {
    while (true) {
      int delay = ThreadLocalRandom.current().nextInt(0, 1500 + 1);
      Thread.sleep(delay);
      empty.down();
      mutex.down();
      System.out.println("Produce " + prodElement);
      buffer.add(prodElement++);
      mutex.up();
      full.up();
    }
  }

  @Override
  public void consume() throws InterruptedException {
    while (true) {
      Thread.sleep(1000);
      full.down();
      mutex.down();
      consElement = buffer.removeFirst();
      System.out.println("            Consume " + consElement);
      mutex.up();
      empty.up();
    }
  }
}
