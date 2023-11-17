/**
 * @author: Man Hua, Chu
 * @email: manhuac@student.unimelb.edu.au
 * @studentNumber: 1403798
 */
package exceptions;

/**
 * This exception is thrown when the space entity reaches the boundaries.
 */
public class InvalidLocationException extends Exception{
    /**
     * This constructor sets up the error message of the exception
     */
    public InvalidLocationException() {
        // define my own error message
        super("Invalid Location, Boundary reached.");
    }
    /**
     * This constructor sets the error message of the exception to a customised message
     * @param message The customised error message
     */
    public InvalidLocationException(String message) {
        super(message);
    }
}
