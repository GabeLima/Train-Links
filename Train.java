// Gabriello Lima, 112803276, R01
public class Train extends Track{
	/*
	 * Excess Variable explanations:
	 * @param stringArrivalTime
	 * To preserve input format, Arrival time is taken as a string and parsed 
	 * for usage
	 * @param stringTransferTime
	 * To preserve input format, Arrival time is taken as a string and parsed 
	 * for usage
	 * @param data
	 * Used to temporarily store and then swap Train data for sorting
	 * @param selected
	 * Keeps track of whether or not this train is selected with an asterisk
	 * @param departureTime
	 * The departure time of the train, which is transferTime + arrivalTime
	 */
	private Train next;
	private Train prev;
	private int trainNumber;
	private String destination;
	private int arrivalTime;
	private int transferTime;
	private Object data;
	private String stringArrivalTime;
	private String stringTransferTime;
	private int departureTime;
	private String selected = " ";
	
	public Train() {
	
	}
	public Train(int trainNumber, String destination, String arrivalTime, String transferTime) {
		this.trainNumber = trainNumber;
		this.destination = destination;
		this.arrivalTime = Integer.parseInt(arrivalTime, 10);
		stringArrivalTime = arrivalTime;
		stringTransferTime = transferTime;
		this.transferTime = Integer.parseInt(transferTime);
		if ((this.transferTime + this.arrivalTime) % 100 < 60) {
			departureTime = this.transferTime + this.arrivalTime;
		}
		else
			departureTime = this.transferTime + this.arrivalTime + 40;
		
	}
	/*
	 * Sets the train scheduled to arrive after this one
	 */
	public void setNext(Train nextTrain) {
		next = nextTrain;
	}
	/*
	 * Sets the train scheduled to prior to this one
	 */
	public void setPrev(Train prevTrain) {
		prev = prevTrain;
	}
	public Train getNext() {
		return next;
	}
	public Train getPrev() {
		return prev;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Train) {
			Train objTrain = (Train) obj;
			if(trainNumber == objTrain.getTrainNumber()) {
				return true;
			}
		}
		return false;
	}
	/*
	 * Returns a vertical, string representation of this Train
	 */
	public String toString() {
		return "Train Number: " + trainNumber + "\nTrain Destination: " + 
		  destination + "\nArrival Time: " + stringArrivalTime + "\nDeparture "
		  + "Time: "+ departureTime;
	}
	/*
	 * Returns a horizontal, string representation of this Train
	 */
	public String printHorizontally() {
		return  selected + "             " + trainNumber +"          "
		  + "" + destination + "                    " +stringArrivalTime +
		  "              " + departureTime;
	}
	public int getTrainNumber() {
		return trainNumber;
	}
	public int getTransferTime() {
		return transferTime;
	}
	public int getAT() {
		return arrivalTime;
	}
	public Object getData() {
		return data;
	}
	public void setData(Train newData){
		data = newData;
	}
	public int getDepartureTime() {
		return departureTime;
	}
	/*
	 * Used to denote whether or not this train is selected or not with an 
	 * asterisk.
	 */
	public void setSelected(String s1) {
		selected = s1;
	}
	public String getDestination() {
		return destination;
	}
}