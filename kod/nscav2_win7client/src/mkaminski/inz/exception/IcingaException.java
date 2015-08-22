package mkaminski.inz.exception;

/**
 * Class used as an exception. Raised, when events strange or unexpected for
 * application occurs
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class IcingaException extends RuntimeException {
	/**
	 * UID of Icinga Exception class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor
	 */
	public IcingaException() {
		super();
	}

	/**
	 * 
	 * @param errorMessage
	 *            message that will be shown in error log
	 */
	public IcingaException(String errorMessage) {
		super(errorMessage);
	}
}
