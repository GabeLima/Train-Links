import java.util.InputMismatchException;
import java.util.Scanner;
public class Station{
	/*
	 * Excess variables explanations:
	 * 
	 * @param numTracks
	 * Keeps track of the number of tracks we have
	 * 
	 */
	private Track head;
	private Track tail;
	private Track cursor;
	private int numTracks;
	
	public Station() {
	}
	
	/*
	 * This method adds a track to the Linked List of Tracks
	 * 
	 * @throws TrackExistsException
	 * When the method trackExists() finds a conflicting track Number
	 * 
	 * @param numTracks
	 * Keeps track of the Number of tracks in the linked lists of tracks
	 */
	public void addTrack(Track newTrack) throws TrackExistsException {
			moveCursor(newTrack);
			trackExists(newTrack.getTrackNumber());
			if (cursor == null){
				cursor = newTrack;
				head = cursor;
				tail = head;
			}
			else if(cursor.getNext() == null) {
				cursor.setNext(newTrack);
				newTrack.setPrev(cursor);
				cursor = newTrack;
			}
			else {
				cursor.getNext().setPrev(newTrack);
				newTrack.setNext(cursor.getNext());
				cursor.setNext(newTrack);
				newTrack.setPrev(cursor);
				cursor = newTrack;
			}
			numTracks++;
			cursor.setTrackNumber(newTrack.getTrackNumber());
		} 
	/*
	 * This method removes a track from the Linked List of Tracks
	 * 
	 * @throws NoSelectedTrainException 
	 * When the user attempts to remove a track when there is no selected track
	 * @param numTracks
	 * Keeps track of the Number of tracks in the linked lists of tracks
	 */
	public Track removeSelectedTrack() throws NoSelectedTrainException {
		Track tempPtr = cursor;
		if (cursor == null) {
			throw new NoSelectedTrainException(" Nothing is selected, can't "
			  + "remove anything");
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
		numTracks--;
		return tempPtr;
	}
	/*
	 * This method prints the selected track
	 */
	public void printSelectedTrack() throws EmptyListException {
		System.out.println("Track " + cursor.getTrackNumber() + " (" + 
		  cursor.getUtilizationRate() + "% Utilization Rate)");
		  cursor.printTrack();
	}
	/*
	 * This method prints all tracks
	 * 
	 * @throws EmptyListException 
	 * When the user tries to print tracks yet no tracks have been added
	 */
	public void printAllTracks() throws EmptyListException {
		Track tempPtr = head;
		if (tempPtr == null) {
			throw new EmptyListException("Can't print a track with no trains");
		}
			while(tempPtr != null) {
				if (tempPtr == cursor) {
					System.out.println("Track* " + tempPtr.getTrackNumber() + 
					  " ( " + tempPtr.getUtilizationRate() + "% " + 
					  "Utilization Rate)");
				}
				else
					System.out.println("Track " + tempPtr.getTrackNumber() + 
					  " ( " + tempPtr.getUtilizationRate() + 
					  "% Utilization Rate)");
				tempPtr.printTrack();
				tempPtr = tempPtr.getNext();
			}
	}
	/*
	 * This method iterates through the Linked List to select the user specified
	 * track
	 * 
	 * @throws NullPointerException
	 * When the user hasn't added any tracks yet and is trying to select a track
	 */
	public boolean selectTrack(int trackToSelect)throws NullPointerException {
		Track tempPtr = head;
		if (tempPtr == null) {
			throw new NullPointerException("You have to add tracks before you "
			  + "select them. ");
		}
		else if (tempPtr.getTrackNumber() == trackToSelect) {
			cursor = tempPtr;
			return true;
		}
		while(tempPtr.getNext() != null) {
			if (tempPtr.getTrackNumber() == trackToSelect) {
				cursor = tempPtr;
				return true;
			}
			tempPtr = tempPtr.getNext();
		}
		return false;
		}
	public String toString() {
		return "";
	}
	/*
	 * This method checks whether or not a track exists by comparing track
	 * numbers
	 * 
	 * @throws TrackExistsException 
	 * When two track numbers are the same
	 */
	public void trackExists(int trackBeingAdded) throws TrackExistsException { 
		Track tempPtr = head;
		 if (head != null) {
			if (tempPtr.getTrackNumber() == trackBeingAdded) {
				throw new TrackExistsException("Track not added: Track " +
				  trackBeingAdded + " already exists.");
			}
			while(tempPtr.getNext() != null) {
				if (tempPtr.getTrackNumber() == trackBeingAdded) {
					throw new TrackExistsException("Track not added: " + 
				      trackBeingAdded + " already exists.");
				}
				tempPtr = tempPtr.getNext();
			}
		}
	}
/*
 * 	This method is used to sort the Linked Lists of Tracks into ascending order
 *  by swapping the cursor around. By moving the cursor, there exists an 
 *  else{} statement in addTrack() that will add sort said Track
 */
	public void moveCursor(Track newTrack) {
		Track tempPtr = head;
		if (head != null) {
			if (newTrack.getTrackNumber() > tempPtr.getTrackNumber()) {
				cursor  = tempPtr;
			}
			while (tempPtr.getNext() != null) {
				if (newTrack.getTrackNumber() > tempPtr.getNext().getTrackNumber()) {
					cursor  = tempPtr.getNext();
				}
				tempPtr = tempPtr.getNext();
			}
		}
	}
	/*
	 * This method prints station information
	 * 
	 * @throws NullPointerException 
	 * When there are no tracks and the user attempts to print station 
	 * information
	 */ 
	public void stationInfo()throws NullPointerException {
		Track tempPtr = head;
		if (tempPtr != null) {
			System.out.println("Station (" + numTracks + " tracks)");
		}
		else
			throw new NullPointerException("There are no tracks, hence you "
			  + "cannot print this station.");
		while(tempPtr != null) {
			System.out.println("Track " + tempPtr.getTrackNumber() + ": " + 
		      tempPtr.getNumTrains() + " trains arriving (" + 
			  tempPtr.getUtilizationRate() +" Utilization Rate)");
			tempPtr = tempPtr.getNext();
		}
	}
	/*
	 * ALL exceptions thrown are handled in main accordingly, and print their
	 * respective error messages to the user accordingly
	 * 
	 * 
	 */
	public static void main(String[] args) {
		Station stationObj = new Station();
		boolean quitFlag = false;
		/*
		 *  I'm going to leave this like this, even though it exceeds the 80 
		 * character limit, purely because it is more aesthetically pleasing
		 */
		System.out.println("|-----------------------------------------------------------------------------|\r\n" + 
				"| Train Options                       | Track Options                         |\r\n" + 
				"|    A. Add new Train                 |    TA. Add Track                      |\r\n" + 
				"|    N. Select next Train             |    TR. Remove selected Track          |\r\n" + 
				"|    V. Select previous Train         |    TS. Switch Track                   |\r\n" + 
				"|    R. Remove selected Train         |   TPS. Print selected Track           |\r\n" + 
				"|    P. Print selected Train          |   TPA. Print all Tracks               |\r\n" + 
				"|-----------------------------------------------------------------------------|\r\n" + 
				"| Station Options                                                             |\r\n" + 
				"|   SI. Print Station Information                                             |\r\n" + 
				"|    Q. Quit                                                                  |\r\n" + 
				"|-----------------------------------------------------------------------------|");
		Scanner stdin = new Scanner(System.in);
		while(!quitFlag) {
			try {
				System.out.print("Enter a selection: ");
				System.out.println("");
				String selection = "";
				selection = stdin.next();
				switch(selection.toLowerCase()) {
					case("a"):
						System.out.println("Enter train number: ");
						int trainNumber = stdin.nextInt();
						stdin.nextLine();
						System.out.println("Enter train destination: ");	
						String destination = stdin.nextLine();
						System.out.println("Enter train arrival time: ");
						String arrivalTime = stdin.next();
						stdin.nextLine();
						System.out.println("Enter train transfer time: ");	
						String transferTime = stdin.next();
						if (stationObj.cursor != null) {
							stationObj.cursor.addTrain(new Train(trainNumber, destination, arrivalTime, transferTime));
							System.out.println("Train No. " + trainNumber + 
							  " to " + destination +" added to Track " + 
							  stationObj.cursor.getTrackNumber());
						}
						else
							System.out.println("Train not added: There is no "
							  + "Track to add the Train to!");
						break;
					case("n"):
						stationObj.cursor.selectNextTrain();
						System.out.println("Cursor has been moved to next "
						  + "train.");
						break;
					case("v"):
						stationObj.cursor.selectPrevTrain();
						System.out.println("Cursor has been moved to previous ."
						  + "train.");
						break;
					case("r"):
						Train tempTrain = stationObj.cursor.removeSelectedTrain();
						
						System.out.println("Train No." + 
						  tempTrain.getTrainNumber() + "to " + 
						  tempTrain.getDestination()+ "has been removed from "
						  + "Track " + stationObj.cursor.getTrackNumber() + ".");
						break;
						
					case("p"):
						if (stationObj.cursor.getTrackCursor() != null) {
							stationObj.cursor.printSelectedTrain();
						}
							else
								System.out.println("Cannot print a train that "
								  + "doesn't exist");
						
						break;
					case("ta"):
						System.out.println("Enter a track number");
						int trackNum = stdin.nextInt();
						stationObj.addTrack(new Track(trackNum));
						System.out.println("Track " + trackNum + " added to "
						  + "the Station");
						break;
						
					case("tr"):
						stationObj.removeSelectedTrack();
						System.out.println("Selected track successfully removed");
						break;
						
					case("ts"):
						System.out.println("Enter a track number");
						int trackNum1 = stdin.nextInt();
						if (stationObj.selectTrack(trackNum1)) {
							System.out.println("Track successfully selected");
						}
						else
							System.out.println("Track number doesn't exist");
						break;
					case("tps"):
						stationObj.printSelectedTrack();
						break;
						
					case("tpa"):
						stationObj.printAllTracks();
						break;
	
					case("si"):
						stationObj.stationInfo();
						break;
					case("q"):
						System.out.println("Program terminating normally...");
						quitFlag = true;
						break;
					case("cn"):
						System.out.println(stationObj.cursor.getTrackNumber());
					}
					
				}catch(EmptyListException ex) {
					System.out.println(ex.getMessage());
				}catch(InvalidTimeException ex) {
					System.out.println(ex.getMessage());
				}catch(NoSelectedTrainException ex) {
					System.out.println(ex.getMessage());
				}catch(ScheduleConflictException ex) {
					System.out.println(ex.getMessage());
				}catch(TrackExistsException ex) {
					System.out.println(ex.getMessage());
				}catch(TrainExistsException ex) {
					System.out.println(ex.getMessage());
				}catch(InputMismatchException E) {
					System.out.println("Please enter input correctly");
				}catch(NumberFormatException E) {
					System.out.println("Please enter input correctly");
				}catch(NullPointerException E) {
					System.out.println(E.getMessage());
				}catch(Exception E) {
					System.out.println("Congrats, you broke the program beyond "
					+ "the scope of my comprehension");
				}
		}
	stdin.close();
	}
}
