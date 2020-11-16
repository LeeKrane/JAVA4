package labor04.num2;

public class Producer extends Thread {
	private final Warehouse warehouse;
	private final int pauseTimeMin;
	private final int pauseTimeMax;
	private final int productionAmountMin;
	private final int productionAmountMax;
	private final int shipments;
	
	public Producer (Warehouse warehouse, int pauseTimeMin, int pauseTimeMax, int productionAmountMin, int productionAmountMax, int shipments) {
		this.warehouse = warehouse;
		this.pauseTimeMin = pauseTimeMin;
		this.pauseTimeMax = pauseTimeMax;
		this.productionAmountMin = productionAmountMin;
		this.productionAmountMax = productionAmountMax;
		this.shipments = shipments;
	}
	
	@Override
	public void run () {
		for (int i = 0; i < shipments; i++) {
			try {
				sleep(Warehouse.RANDOM.nextInt(pauseTimeMax - pauseTimeMin) + pauseTimeMin);
				warehouse.log("Shipment Completed!" +
									  	"\n    Shipped: " + warehouse.increaseStock(Warehouse.RANDOM.nextInt(productionAmountMax - productionAmountMin) + productionAmountMin) +
									  	"\n    Shipment: " + i +
									  	"\n    New stock: " + warehouse.getStock());
			} catch (InterruptedException e) {
				System.err.println("Unexpected error: " + e.getMessage());
			}
		}
		warehouse.setLastShipment();
		warehouse.log("Producer is done!");
	}
}
