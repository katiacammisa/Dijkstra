import java.util.LinkedList;

public class SharedAreaMonitor implements SharedArea {

  private final int SIZE;
  private final LinkedList<Integer> buffer = new LinkedList<>();
  private int prodElement = 0;
  private int consElement = 0;

  public SharedAreaMonitor(int size) {
    SIZE = size;
  }

  public synchronized void produce() throws InterruptedException {
    while (buffer.size() >= SIZE) {
      System.out.println("Producer waiting, buffer: " + buffer.size());
      wait();
    }
    System.out.println("Produce " + prodElement);
    buffer.add(prodElement++);
    if (buffer.size() == 1)
      notify();
  }

  public synchronized void consume() throws InterruptedException {
    while (buffer.size() <= 0) {
      System.out.println("Consumer waiting, buffer: " + buffer.size());
      wait();
    }
    consElement = buffer.removeFirst();
    if (buffer.size() == SIZE - 1)
      notify();
    System.out.println("Consume " + consElement);
  }
}
// TODO Usar public synchronized void y tambien wait()
