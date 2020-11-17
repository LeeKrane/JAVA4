package labor04.num2;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Consumer extends Thread {
	private final Warehouse warehouse;
	private final int pauseTimeMin;
	private final int pauseTimeMax;
	private final int consumptionAmount;
	
	private final BooleanProperty waiting;
	
	public Consumer (Warehouse warehouse, int pauseTimeMin, int pauseTimeMax, int consumptionAmount) {
		this.warehouse = warehouse;
		this.pauseTimeMin = pauseTimeMin;
		this.pauseTimeMax = pauseTimeMax;
		this.consumptionAmount = consumptionAmount;
		waiting = new SimpleBooleanProperty();
	}
	
	@Override
	public void run () {
		waiting.setValue(false);
		while (!warehouse.isLastShipment() || warehouse.getStock() > 0) {
			try {
				warehouse.log("Consumption Completed!" +
									  	"\n    Consumed: " +
									  		warehouse.decreaseStock(warehouse.isLastShipment()
												? Math.min(warehouse.getStock(), consumptionAmount)
												: consumptionAmount, waiting) +
									  	"\n    New stock: " + warehouse.getStock());
				sleep(Warehouse.RANDOM.nextInt(pauseTimeMax - pauseTimeMin) + pauseTimeMin);
			} catch (InterruptedException e) {
				System.err.println("Unexpected error: " + e.getMessage());
			}
		}
		warehouse.log("Consumer is done!");
	}
	
	public boolean isWaiting () {
		return waiting.get();
	}
	
	public BooleanProperty waitingProperty () {
		return waiting;
	}
}
