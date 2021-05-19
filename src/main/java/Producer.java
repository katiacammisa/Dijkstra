import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Producer implements ThreadRunnable {

  // Create producer thread
  private final SharedArea shared;
  private final Thread prod_thread;

  public Producer(SharedArea shared) {
    this.shared = shared;
    this.prod_thread = new Thread(this);
  }

  @Override
  public void start() {
    prod_thread.start();
  }

  @Override
  public void join() throws InterruptedException {
    prod_thread.join();
  }

  @Override
  public void run() {
    try {
      while (true) {
        int delay = ThreadLocalRandom.current().nextInt(0, 1500 + 1);
        Thread.sleep(delay);
        shared.produce();
      }
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
  }
}
