package exceptions;

/**
 * Exception thrown if a user attempts to add a duplicate grade
 * 
 * @author Nils Rollshausen
 */
public class GradeAlreadyExistsException extends CampusManagementException {

	/**
	 * Creates a new exception with the given message
	 * @param message
	 */
	public GradeAlreadyExistsException(String message) {
		super(message);
	}

}
