package exceptions;

/**
 * Exception thrown if a user attempts to register a duplicate student
 * 
 * @author Nils Rollshausen
 */
public class StudentRegistrationException extends CampusManagementException {

	/**
	 * Creates a new exception with the given message
	 * @param message
	 */
	public StudentRegistrationException(String message) {
		super(message);
	}

}
