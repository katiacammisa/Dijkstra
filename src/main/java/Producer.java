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
      shared.produce();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
