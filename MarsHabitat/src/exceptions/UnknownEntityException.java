/**
 * @author: Man Hua, Chu
 * @email: manhuac@student.unimelb.edu.au
 * @studentNumber: 1403798
 */
package exceptions;

/**
 * This exception is thrown when there is unknown symbol in the Mars habitat
 */
public class UnknownEntityException extends Exception{
    /**
     * This constructor sets up the error message of the exception
     */
    public UnknownEntityException() {
        // define my own error message
        super("An unknown item found in Martian land. Aborting mission.");
    }
    /**
     * This constructor sets the error message of the exception to a customised message
     * @param message The customised error message
     */
    public UnknownEntityException(String message) {
        super(message);
    }
}
