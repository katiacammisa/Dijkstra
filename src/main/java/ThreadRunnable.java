public interface ThreadRunnable extends Runnable {

  void start();

  void join() throws InterruptedException;
}
