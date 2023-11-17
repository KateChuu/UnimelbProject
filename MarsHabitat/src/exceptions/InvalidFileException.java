/**
 * @author: Man Hua, Chu
 * @email: manhuac@student.unimelb.edu.au
 * @studentNumber: 1403798
 */
package exceptions;

/**
 * This exception is thrown when the Mars habitat contains different-length rows or the boundary is not defined by '#'.
 */
public class InvalidFileException extends Exception{
    /**
     * This constructor sets up the error message of the exception
     */
    public InvalidFileException() {
        // define my own error message
        super("Invalid file content, Aborting mission.");
    }

    /**
     * This constructor sets the error message of the exception to a customised message
     * @param message The customised error message
     */
    public InvalidFileException(String message) {
        super(message);
    }
}
