package exceptions;

/**
 * Exception thrown if a user attempts to create a duplicate exam
 * 
 * @author Nils Rollshausen
 */
public class ExaminationAlreadyExistsException extends CampusManagementException {

	/**
	 * Creates a new exception with the given message
	 * @param message
	 */
	public ExaminationAlreadyExistsException(String message) {
		super(message);
	}

}
