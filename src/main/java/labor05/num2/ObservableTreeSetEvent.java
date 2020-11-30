package labor05.num2;

import java.time.LocalDateTime;

public class ObservableTreeSetEvent<T> {
	private final int oldSize;
	private final int newSize;
	private final ObservableTreeSet<T> source;
	private final EventTrigger eventTrigger;
	private final LocalDateTime timestamp;
	
	public ObservableTreeSetEvent (int oldSize, int newSize, ObservableTreeSet<T> source, EventTrigger eventTrigger, LocalDateTime timestamp) {
		this.oldSize = oldSize;
		this.newSize = newSize;
		this.source = source;
		this.eventTrigger = eventTrigger;
		this.timestamp = timestamp;
	}
	
	public int getOldSize () {
		return oldSize;
	}
	
	public int getNewSize () {
		return newSize;
	}
	
	public ObservableTreeSet<T> getSource () {
		return source;
	}
	
	public LocalDateTime getTimestamp () {
		return timestamp;
	}
	
	@Override
	public String toString () {
		return "oldSize=" + oldSize +
				", \nnewSize=" + newSize +
				", \nsource=" + source +
				", \ntriggerEvent='" + eventTrigger + '\'' +
				", \ntimestamp=" + timestamp;
	}
}
