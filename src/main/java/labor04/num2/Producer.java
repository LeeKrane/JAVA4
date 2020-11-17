package labor04.num2;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Producer extends Thread {
	private final Warehouse warehouse;
	private final int pauseTimeMin;
	private final int pauseTimeMax;
	private final int productionAmountMin;
	private final int productionAmountMax;
	private final int shipments;
	
	private final BooleanProperty waiting;
	
	public Producer (Warehouse warehouse, int pauseTimeMin, int pauseTimeMax, int productionAmountMin, int productionAmountMax, int shipments) {
		this.warehouse = warehouse;
		this.pauseTimeMin = pauseTimeMin;
		this.pauseTimeMax = pauseTimeMax;
		this.productionAmountMin = productionAmountMin;
		this.productionAmountMax = productionAmountMax;
		this.shipments = shipments;
		waiting = new SimpleBooleanProperty();
	}
	
	@Override
	public void run () {
		waiting.setValue(false);
		for (int i = 0; i < shipments; i++) {
			try {
				warehouse.log("Shipment Completed!" +
									  	"\n    Shipped: " + warehouse.increaseStock(Warehouse.RANDOM.nextInt(productionAmountMax - productionAmountMin) + productionAmountMin, waiting) +
									  	"\n    Shipment: " + i +
									  	"\n    New stock: " + warehouse.getStock());
				sleep(Warehouse.RANDOM.nextInt(pauseTimeMax - pauseTimeMin) + pauseTimeMin);
			} catch (InterruptedException e) {
				System.err.println("Unexpected error: " + e.getMessage());
			}
		}
		warehouse.setLastShipment();
		warehouse.log("Producer is done!");
	}
	
	public boolean isWaiting () {
		return waiting.get();
	}
	
	public BooleanProperty waitingProperty () {
		return waiting;
	}
}
