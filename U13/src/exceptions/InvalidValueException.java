package exceptions;

/**
 * Exception thrown if an invalid value is passed to a function
 * 
 * @author Nils Rollshausen
 */
public class InvalidValueException extends CampusManagementException {

	/**
	 * Creates a new exception with the given message
	 * @param message
	 */
	public InvalidValueException(String message) {
		super(message);
	}

}
