public interface SharedArea {
  void produce() throws InterruptedException;

  void consume() throws InterruptedException;
}
