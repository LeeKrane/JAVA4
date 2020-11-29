package labor04.num2;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.*;

public class Warehouse {
	static Random RANDOM = new Random();
	private FileHandler fileHandler;
	private final Logger logger;
	
	private final int capacity;
	private final IntegerProperty stock;
	private final BooleanProperty lastShipment = new SimpleBooleanProperty(false);
	
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
	
	int increaseStock (int amount, BooleanProperty waiting) throws InterruptedException {
		lock.lock();
		while (stock.intValue() + amount > capacity) {
			waiting.setValue(true);
			log("Waiting for consumption!" +
						"\n    Current stock: " + stock.intValue());
			isFull.await();
		}
		waiting.setValue(false);
		
		stock.setValue(stock.intValue() + amount);
		isEmpty.signal();

		lock.unlock();
		return amount;
	}
	
	int decreaseStock (int amount, BooleanProperty waiting) throws InterruptedException {
		lock.lock();
		while (stock.intValue() < amount && !lastShipment.get()) {
			waiting.setValue(true);
			log("Waiting for shipment!" +
						"\n    Current stock: " + stock.intValue());
			isEmpty.await();
		}
		waiting.setValue(false);
		
		stock.setValue(stock.intValue() - amount);
		isFull.signal();
		
		lock.unlock();
		return amount;
	}
	
	void setLastShipment () {
		lock.lock();
		lastShipment.setValue(true);
		lock.unlock();
	}
	
	public boolean isLastShipment () {
		return lastShipment.get();
	}
	
	public BooleanProperty lastShipmentProperty () {
		return lastShipment;
	}
	
	public int getStock () {
		return stock.get();
	}
	
	public IntegerProperty stockProperty () {
		return stock;
	}
	
	void log (String log) {
		logger.info(log);
	}
	
	void addHandler (Handler handler) {
		logger.addHandler(handler);
	}
}
