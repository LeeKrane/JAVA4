package labor04.num2;

public class Consumer extends Thread {
	private final Warehouse warehouse;
	private final int pauseTimeMin;
	private final int pauseTimeMax;
	private final int consumptionAmount;
	
	public Consumer (Warehouse warehouse, int pauseTimeMin, int pauseTimeMax, int consumptionAmount) {
		this.warehouse = warehouse;
		this.pauseTimeMin = pauseTimeMin;
		this.pauseTimeMax = pauseTimeMax;
		this.consumptionAmount = consumptionAmount;
	}
	
	@Override
	public void run () {
		while (!warehouse.isLastShipment() || warehouse.getStock() > 0) {
			try {
				sleep(Warehouse.RANDOM.nextInt(pauseTimeMax - pauseTimeMin) + pauseTimeMin);
				warehouse.log("Consumption Completed!" +
									  	"\n    Consumed: " +
									  		warehouse.decreaseStock(warehouse.isLastShipment()
												? Math.min(warehouse.getStock(), consumptionAmount)
												: consumptionAmount) +
									  	"\n    New stock: " + warehouse.getStock());
			} catch (InterruptedException e) {
				System.err.println("Unexpected error: " + e.getMessage());
			}
		}
		warehouse.log("Consumer is done!");
	}
}
