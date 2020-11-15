package labor04.num2;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Warehouse {
	static Random RANDOM = new Random();
	private static FileHandler fileHandler;
	private static Logger logger;
	
	private int capacity;
	private int stock;
	private boolean lastShipment = false;
	
	private ReentrantLock lock = new ReentrantLock();
	private Condition isEmpty = lock.newCondition();
	private Condition isFull = lock.newCondition();
	
	{
		try {
			fileHandler = new FileHandler("src/main/resources/labor04/num2/warehouse.log", false);
		} catch (IOException e) {
			System.err.println("Unexpected error: " + e.getMessage());
		}
		logger = Logger.getGlobal();
		logger.setLevel(Level.INFO);
		logger.addHandler(fileHandler);
	}
	
	public Warehouse (int capacity) {
		this.capacity = capacity;
	}
	
	int increaseStock (int amount) throws InterruptedException {
		lock.lock();
		while (stock + amount > capacity) {
			log("Waiting for consumption!" +
						"\n    Current stock: " + stock);
			isFull.await();
		}
		
		stock += amount;
		isEmpty.signal();
		
		lock.unlock();
		return amount;
	}
	
	int decreaseStock (int amount) throws InterruptedException {
		lock.lock();
		while (stock < amount && !lastShipment) {
			log("Waiting for shipment!" +
						"\n    Current stock: " + stock);
			isEmpty.await();
		}
		
		stock -= amount;
		isFull.signal();
		
		lock.unlock();
		return amount;
	}
	
	void setLastShipment () {
		lock.lock();
		lastShipment = true;
		lock.unlock();
	}
	
	boolean isLastShipment () {
		return lastShipment;
	}
	
	int getStock () {
		return stock;
	}
	
	static void log (String log) {
		logger.info(log);
	}
}
