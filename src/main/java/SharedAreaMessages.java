import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SharedAreaMessages implements SharedArea {

  private int prodElement = 0;
  private int consElement = 0;
  private final ServerSocket serverSocket = new ServerSocket(6666);
  private final Socket producerSocket;
  private final Socket consumerSocket;
  private final PrintStream producerOut, consumerOut;
  private final BufferedReader producerIn, consumerIn;

  public SharedAreaMessages(int size) throws IOException {
    consumerSocket = new Socket("localhost", 6666);
    producerSocket = serverSocket.accept();
    producerOut = new PrintStream(producerSocket.getOutputStream());
    producerIn = new BufferedReader(new InputStreamReader(producerSocket.getInputStream()));
    consumerOut = new PrintStream(consumerSocket.getOutputStream());
    consumerIn = new BufferedReader(new InputStreamReader(consumerSocket.getInputStream()));
    for (int i = 0; i < size; i++) {
      consumerOut.println();
    }
  }

  @Override
  public void produce() throws IOException {
    producerIn.readLine();
    int message = ++prodElement;
    System.out.println("El productor produce: " + message);
    producerOut.println(message);
    producerOut.flush();
  }

  @Override
  public void consume() throws IOException {
    String message = consumerIn.readLine();
    consumerOut.println();
    System.out.println("El consumidor consume: " + message);
  }
}
