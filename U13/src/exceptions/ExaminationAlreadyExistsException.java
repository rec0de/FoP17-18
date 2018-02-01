package exceptions;

/**
 * @author Nils Rollshausen
 *
 */
public class ExaminationAlreadyExistsException extends CampusManagementException {

	/**
	 * @param message
	 */
	public ExaminationAlreadyExistsException(String message) {
		super(message);
	}

}
