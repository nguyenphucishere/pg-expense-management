package web.exceptionClass;

public class DuplicateUserException extends Throwable {
	
    public DuplicateUserException() {
		super();
	}
    
    public DuplicateUserException(String message) {
        super(message);
    }

    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DuplicateUserException(Throwable cause) {
        super(cause);
    }
}
