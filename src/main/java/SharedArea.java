import java.io.IOException;

public interface SharedArea {
  void produce() throws InterruptedException, IOException;

  void consume() throws InterruptedException, IOException;
}
