package hospital.exceptions;

/**
 * Created by Andrei on 13/01/2017.
 */
public class InvalidDataException extends Exception {

    private static final long serialVersionUID = -6365767915174351745L;

    public InvalidDataException(String message) {
        super(message);
    }

}