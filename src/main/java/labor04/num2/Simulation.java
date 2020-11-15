package labor04.num2;

// TODO: Write FX for the Warehouse (Last Task)

public class Simulation {
	public static void main (String[] args) {
		Warehouse warehouse = new Warehouse(20);
		Producer producer = new Producer(warehouse, 1000, 5000, 5, 10, 35);
		Consumer consumer = new Consumer(warehouse, 3000, 6000, 7);
		
		producer.start();
		consumer.start();
	}
}
