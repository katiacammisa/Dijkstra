public class Semaphore {

  private int variable;

  public Semaphore(int variable) {
    this.variable = variable;
  }

  public void up() {
    // System.out.println("upEmpty:" + this.empty);
    synchronized (this) {
      this.variable++;
      notify();
    }
  }

  public void down() throws InterruptedException {
    // System.out.println("downMutex:" + this.mutex);
    synchronized(this) {
      while (this.variable <= 0)
        wait();
      this.variable--;
    }
  }
}
