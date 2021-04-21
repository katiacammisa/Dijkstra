import java.util.LinkedList;

public class SharedAreaMonitor {

  private final int SIZE;
  private final LinkedList<Integer> buffer = new LinkedList<>();
  private int prodElement = 0;
  private int consElement = 0;
  // TODO Usar public synchronized void y tambien wait()

  public SharedAreaMonitor(int size) {
    SIZE = size;
  }

  public void produce() throws InterruptedException {

  }

  public void consume() throws InterruptedException {

  }
}
