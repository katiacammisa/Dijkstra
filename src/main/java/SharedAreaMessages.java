import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SharedAreaMessages implements SharedArea {

  private final int SIZE;
  private final ConcurrentLinkedDeque<Socket> queue = new ConcurrentLinkedDeque<>();
  private final ServerSocket serverSocket = new ServerSocket(6666);
  private int prodElement = 0;
  private int consElement = 0;

  public SharedAreaMessages(int SIZE) throws IOException {
    this.SIZE = SIZE;
    System.out.println("Created succesfully");
  }

  @Override
  public void produce() throws InterruptedException, IOException {
    System.out.println("produce method");
    while (true) {
      Socket clientSocket = serverSocket.accept();
      queue.offer(clientSocket);
      synchronized (queue) {
        System.out.println("Produced");
        queue.notify();
      }
    }
  }

  @Override
  public void consume() throws InterruptedException, IOException {
    System.out.println("consume method");
    Socket socket;
    while (true) {
      socket = queue.poll();
      synchronized (queue) {
        queue.wait();
      }
      if (socket != null) {
        socket.close();
        System.out.println("Consumed");
      }
    }
  }
}
