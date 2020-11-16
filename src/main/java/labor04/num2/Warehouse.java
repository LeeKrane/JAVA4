package labor04.num2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Warehouse {
	static Random RANDOM = new Random();
	private FileHandler fileHandler;
	private final Logger logger;
	
	private final int capacity;
	private final IntegerProperty stock; // IntegerProperty / Eigene Klasse
	private boolean lastShipment = false;
	
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition isEmpty = lock.newCondition();
	private final Condition isFull = lock.newCondition();
	
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
		stock = new SimpleIntegerProperty(0);
	}
	
	int increaseStock (int amount) throws InterruptedException {
		lock.lock();
		while (stock.intValue() + amount > capacity) {
			log("Waiting for consumption!" +
						"\n    Current stock: " + stock.intValue());
			isFull.await();
		}
		
		stock.setValue(stock.intValue() + amount);
		isEmpty.signal();

		lock.unlock();
		return amount;
	}
	
	int decreaseStock (int amount) throws InterruptedException {
		lock.lock();
		while (stock.intValue() < amount && !lastShipment) {
			log("Waiting for shipment!" +
						"\n    Current stock: " + stock.intValue());
			isEmpty.await();
		}
		
		stock.setValue(stock.intValue() - amount);
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
		return stock.intValue();
	}
	
	IntegerProperty getStockProperty () {
		return stock;
	}
	
	int getCapacity () {
		return capacity;
	}
	
	void log (String log) {
		logger.info(log);
	}
}
