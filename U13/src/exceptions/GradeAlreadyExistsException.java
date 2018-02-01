package exceptions;

/**
 * @author Nils Rollshausen
 *
 */
public class GradeAlreadyExistsException extends CampusManagementException {

	/**
	 * @param message
	 */
	public GradeAlreadyExistsException(String message) {
		super(message);
	}

}
