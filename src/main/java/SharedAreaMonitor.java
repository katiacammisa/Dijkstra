import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class SharedAreaMonitor implements SharedArea {

  private final int SIZE;
  private final LinkedList<Integer> buffer = new LinkedList<>();
  private int prodElement = 0;
  private int consElement = 0;

  public SharedAreaMonitor(int size) {
    SIZE = size;
  }

  public synchronized void produce() throws InterruptedException {
    while (true) {
      int delay = ThreadLocalRandom.current().nextInt(0, 1500 + 1);
      Thread.sleep(delay);
      while (buffer.size() >= SIZE) {
        System.out.println("Producer waiting, buffer: " + buffer.size());
        wait();
      }
      System.out.println("Produce " + prodElement);
      buffer.add(prodElement++);
      notify();
      wait();
    }
  }

  public synchronized void consume() throws InterruptedException {
    while (true) {
      Thread.sleep(1000);
      while (buffer.size() <= 0) {
        System.out.println("Consumer waiting, buffer: " + buffer.size());
        wait();
      }
      consElement = buffer.removeFirst();
      notify();
      System.out.println("Consume " + consElement);
      wait();
    }
  }
}
// TODO Usar public synchronized void y tambien wait()
