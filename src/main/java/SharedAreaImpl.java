import java.util.LinkedList;

public class SharedAreaImpl implements SharedArea {

  private final LinkedList<Integer> buffer = new LinkedList<>();
  private final Semaphore mutex;
  private final Semaphore empty;
  private final Semaphore full;
  private int prodElement = 0;
  private int consElement = 0;

  public SharedAreaImpl(int size) {
    this.mutex = new Semaphore(1);
    this.empty = new Semaphore(size);
    this.full = new Semaphore(0);
  }

  @Override
  public void produce() throws InterruptedException {
    empty.down();
    mutex.down();
    System.out.println("Produce " + prodElement);
    buffer.add(prodElement++);
    mutex.up();
    full.up();
  }

  @Override
  public void consume() throws InterruptedException {
    full.down();
    mutex.down();
    consElement = buffer.removeFirst();
    System.out.println("Consume " + consElement);
    mutex.up();
    empty.up();
  }
}
