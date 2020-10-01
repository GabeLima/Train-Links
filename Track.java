public class Track extends Station{
	/*
	 * Excess Variable Explanations:
	 * @param numTrains
	 * keeps track of the number of trains in a track
	 * 
	 */
	private Train head;
	private Train tail;
	private Train cursor;
	private Track next;
	private Track prev;
	private double utilizationRate;
	private int trackNumber;
	private int numTrains;
	public Track() {
		
	}
	/*
	 * This constructor is used to keep track of the trackNumber, which is 
	 * a unique number to each track.
	 * 
	 */
	public Track(int number) {
		trackNumber = number;
	}
	/*
	 * This method, addTrain(), adds a train to this track
	* 
	 * 
	 * @throws ScheduleConflictException
	 * When the method scheduleConflict() finds a conflicting arrival time
	 * with the listed trains.
	 * @throws TrainExistsException
	 * When the method exists() finds a train with the same train Number as the 
	 * one trying to be added
	 * @throws InvalidTimeException
	 * When the method validTime() finds that the entered time is not valid
	 * @throws EmptyListException
	 * When either method sortTrains() or isSorted() is called and the list is 
	 * empty
	 * @param numTrains
	 * Keeps track of the number of trains in the track
	 * 
	 * 
	 */
	public void addTrain(Train newTrain) throws ScheduleConflictException, TrainExistsException, InvalidTimeException, EmptyListException {
		validTime(newTrain);
		exists(newTrain);
		scheduleConflict(newTrain);
		if (cursor == null){
			cursor = newTrain;
			head = cursor;
			tail = head;
		}
		else if(cursor.getNext() == null) {
			cursor.setNext(newTrain);
			newTrain.setPrev(cursor);
			cursor = newTrain;
		}
		else {
			cursor.getNext().setPrev(newTrain);
			newTrain.setNext(cursor.getNext());
			cursor.setNext(newTrain);
			newTrain.setPrev(cursor);
			cursor = newTrain;
		}
		sortTrains(); // its getting stuck here
		numTrains++;
	}
	/*
	 * This method prints a vertical representation of the selected Train
	 */
	public void printSelectedTrain() {
		System.out.println(cursor.toString());
	}
	/*
	 * This method removes the current selected train
	 * 
	 * @returns tempPtr
	 * Returns an object of type Train for printing purposes
	 * @throws NoSelectedTrainException 
	 * When there is no selected train
	 */
	public Train removeSelectedTrain() throws NoSelectedTrainException { 
		Train tempPtr = cursor;
		if (tempPtr == null) {
			throw new NoSelectedTrainException(" Nothing is selected, can't"
			  + " remove anything");
		}
		else if (cursor.getNext() == null && cursor.getPrev() == null) {
			cursor = null;
		}
		else if (cursor.getPrev() == null) {
			cursor.getNext().setPrev(null);
			cursor = cursor.getNext();
		}
		else if (cursor.getNext() == null) {
			cursor.getPrev().setNext(null);
			cursor = cursor.getPrev();
		}
		else {
			cursor.getNext().setPrev(cursor.getPrev());
			cursor.getPrev().setNext(cursor.getNext());
			cursor = cursor.getNext();
		}
		numTrains--;
		return tempPtr;
	}
	
	/*
	 * Sorts the Linked Lists of train in tangent with the method isSorted()
	 * by swapping the data, rather than the pointers
	* 
	 * @param tempStore
	 * A temporary variable used to store Train data when swapping
	 * 
	 * 
	 */
	public void sortTrains() throws EmptyListException{
		Train tempPtr = head;
		if (tempPtr == null) {
			throw new EmptyListException("Empty List");
		}
		while(isSorted() == false) {
			while(tempPtr.getNext() != null) {
				if (tempPtr.getNext().getAT() > tempPtr.getAT()) {
					Train tempStore = tempPtr.getNext();
					tempPtr.getNext().setData(tempPtr);
					tempPtr.setData(tempStore);
				}
				tempPtr = tempPtr.getNext();
			}
		}
	}
	/*
	 * Checks whether or not the Linked List of trains is sorted, works as a 
	 * child method to the sortTrains() method
	 * 
	 * @throws EmptyListsException
	 * When the Linked List head is empty
	 */
	public boolean isSorted()throws EmptyListException {
		Train tempPtr = head;
		if (tempPtr == null) {
			throw new EmptyListException("Cannot sort nothing. ");
		}
		else if (tempPtr.getNext() == null) {
			return true;
		}
		while (tempPtr.getNext() != null) {
			if (tempPtr.getAT() > tempPtr.getNext().getAT()) {
				return false;
			}
			tempPtr = tempPtr.getNext();
		}
		return true;
	}
	/*
	 * This method selects the next train in the Linked List
	* 
	 * @throws NoSelectedTrainException
	 * When there is no next train in the list
	 */
	public void selectNextTrain() throws NoSelectedTrainException{
		if(cursor.getNext() == null) {
			throw new NoSelectedTrainException("You can't select the next train"
			  + " if there is no next train");
		}
		else if (cursor.getNext() != null) {
			cursor = cursor.getNext();
		}
	}
	/*
	 * This method selects the prior train in the Linked List
	* 
	 * @throws NoSelectedTrainException
	 * When there is no prior train in the list
	 */
	public void selectPrevTrain() throws NoSelectedTrainException{
		if(cursor.getPrev() == null) {
			throw new NoSelectedTrainException("You can't select the next train "
			  + "if there is no next train");
		}
		else if (cursor.getPrev() != null) {
			cursor = cursor.getPrev();
		}
	}
	/*
	 * This method returns a String representation of a Track header
	 */
	public String toString() {
		return "Selected  Train Number     Train Destination         "
		  + "Arrival Time     Departure Time  ";
	}
	/*
	 * This method prints the Linked List of Trains of this Track
	* 
	 *@param header
	 * Used to keep track of whether or not the header for the linked list, aka
	 * the toString() method, was printed. 
	 */
	public void printTrack() throws EmptyListException{
		System.out.println("test");
		Train tempPtr = head;
		boolean header = false;
		if (tempPtr == null) {
			throw new EmptyListException("Can't print a track with no trains");
		}
		
		while (tempPtr != null) {
			if (tempPtr.getTrainNumber() == cursor.getTrainNumber()) {
				tempPtr.setSelected("*");
			}
			else
				tempPtr.setSelected(" ");
			if (header == false) {
				System.out.println(toString());
				System.out.println(tempPtr.printHorizontally());
				header = true;
			}
			else {
				System.out.println(tempPtr.printHorizontally());
			}
			tempPtr = tempPtr.getNext();
		}
	}
	/*
	 * This method checks whether or not the entered arrival time is in standard
	 * military time format
	 * 
	 * @throws InvalidTimeException 
	 * When the arrivalTime is not in standard military format
	 */
	public void validTime(Train testTrain) throws InvalidTimeException {
		//boolean tempFlag = false;
		if (!(testTrain.getAT() < 2400 && testTrain.getAT()>= 0000)) {
			throw new InvalidTimeException("Train not added: Invalid arrival "
			  + "time.");
		}
		else if(!(testTrain.getAT() % 100 < 60)) {
			throw new InvalidTimeException("Train not added: Invalid arrival "
			  + "time.");
		}
	}
	/*
	 * This method checks whether or not the train already exists in the Linked
	 * List by comparing train numbers
	* 
	 * @throws TrainExistsException
	 * When two train numbers are found to be equal 
	 */
	public void exists(Train testTrain) throws TrainExistsException {
		Train tempPtr = head;
		if (head != null) {
			if (testTrain.getTrainNumber() == tempPtr.getTrainNumber()) {
				throw new TrainExistsException("This train already exists");
			}
			while (tempPtr.getNext() != null) {
				if (testTrain.getTrainNumber() == tempPtr.getNext().getTrainNumber()) {
					throw new TrainExistsException("This train already exists");
				}
				tempPtr = tempPtr.getNext();
			}
		}
	}
	/*
	 * This method checks whether or not there will be a schedule conflict with
	 * a train being added to the linked list of trains in this track
	* 
	 * @throws ScheduleConflictException 
	 * When the arrival time of the train being added conflicts with the time 
	 * of other trains currently in the track
	 */
	public void scheduleConflict(Train testTrain) throws ScheduleConflictException { //theres a conflict in timing if the arrival time is between AT and TT
		Train tempPtr = head;
		while(tempPtr != null) {
			if (testTrain.getAT() <= tempPtr.getDepartureTime() && testTrain.getAT() >= tempPtr.getAT()) {
				throw new ScheduleConflictException("Train not added: There "
				  + "is a Train already scheduled on Track " + getTrackNumber()
				  +" at that time!");
			}
			tempPtr = tempPtr.getNext();
		}
	}
	public void setNext(Track nextTrack) {
		next = nextTrack; 
	}
	public void setPrev(Track prevTrack) {
		prev = prevTrack;
	}
	public Track getNext() {
		return next;
	}
	public Track getPrev() {
		return prev;
	}
	public int getTrackNumber() {
		return trackNumber;
	}
	public void setTrackNumber(int newTrackNumber) {
		trackNumber = newTrackNumber;
	}
	/*
	 * This method calculates and returns the sum of all trains 
	 * utilization rates
	 */
	public double getUtilizationRate() {
		Train tempPtr = head;
		while (tempPtr != null) {
			utilizationRate += tempPtr.getTransferTime();
			tempPtr = tempPtr.getNext();
		}
		utilizationRate /= 144; 
		return Math.round(utilizationRate * 1000.0) / 100.0;
	}
	public Train getTrackCursor() {
		return cursor;
	}
	public int getNumTrains() {
		return numTrains;
	}
}
