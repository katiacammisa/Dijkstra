import java.util.concurrent.ThreadLocalRandom;
import java.util.LinkedList;

public class ProducerConsumer {
  public static void main(String[] args)
          throws InterruptedException
  {
    final SharedArea shared = new SharedArea(10);

    // Create producer thread
    Thread prod_thread = new Thread(new Runnable() {
      @Override
      public void run()
      {
        try {
          shared.produce();
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    // Create consumer thread
    Thread cons_thread = new Thread(new Runnable() {
      @Override
      public void run()
      {
        try {
          shared.consume();
        }
        catch (InterruptedException e) {
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

    final int SIZE;
    private LinkedList<Integer> buffer = new LinkedList<>();;
    private int mutex = 1;
    private int empty;
    private int full = 0;

    public SharedArea(int size) {
      SIZE = size;
      this.empty = size;
    }

    public void produce() throws InterruptedException
    {
      int element = 0;
      while (true) {
        int delay = ThreadLocalRandom.current().nextInt(0, 1500 + 1);
        Thread.sleep(delay);
        downEmpty();
        downMutex();
        System.out.println("Produce " + element);
        buffer.add(element++);
        upMutex();
        upFull();
      }
    }

    public void consume() throws InterruptedException
    {
      int element;
      while (true) {
        Thread.sleep(1000);
        downFull();
        downMutex();
        element = buffer.removeFirst();
        System.out.println("            Consume " + element);
        upMutex();
        upEmpty();
      }
    }

    private void downEmpty() throws InterruptedException {
      // System.out.println("downEmpty:" + this.empty);
      synchronized(this) {
        while (this.empty <= 0)
          wait();
        this.empty--;
      }
    }

    private void upEmpty() {
      // System.out.println("upEmpty:" + this.empty);
      synchronized(this) {
        this.empty++;
        notify();
      }
    }

    private void downMutex() throws InterruptedException {
      // System.out.println("downMutex:" + this.mutex);
      synchronized(this) {
        while (this.mutex <= 0)
          wait();
        this.mutex--;
      }
    }

    private void upMutex() {
      // System.out.println("upMutex:" + this.mutex);
      synchronized(this) {
        this.mutex++;
        notify();
      }
    }

    private void downFull() throws InterruptedException {
      // System.out.println("downFull:" + this.full);
      synchronized(this) {
        while (this.full <= 0)
          wait();
        this.full--;
      }
    }

    private void upFull() {
      // System.out.println("upFull:" + this.full);
      synchronized(this) {
        this.full++;
        notify();
      }
    }

  }
}
