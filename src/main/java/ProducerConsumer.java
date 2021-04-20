import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProducerConsumer {
  public static void main(String[] args)
          throws InterruptedException {

    Scanner scanner = new Scanner(System.in);

    System.out.println("Ingrese el tamano del buffer");
    int size = scanner.nextInt();

    System.out.println("Ingrese cantidad de producers");
    int amountOfProducers = scanner.nextInt();

    System.out.println("Ingrese cantidad de consumers");
    int amountOfConsumers = scanner.nextInt();

    final SharedArea shared = new SharedArea(size);
    final List<Producer> producers = new ArrayList<>();
    final List<Consumer> consumers = new ArrayList<>();

    for (int i = 0; i < amountOfProducers; i++) producers.add(new Producer(shared));
    for (int i = 0; i < amountOfConsumers; i++) consumers.add(new Consumer(shared));

    producers.forEach(Producer::start);
    consumers.forEach(Consumer::start);

    for (Producer producer : producers) producer.join();
    for (Consumer consumer : consumers) consumer.join();
  }
}
