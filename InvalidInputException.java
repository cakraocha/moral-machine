/**
 * <p>Final Project COMP90041 due 5 pm (AEST), 30 June 2020<br>
 * MORAL MACHINE - InvalidInputException.java<br>
 * Student ID: 1047538<br>
 * icakramurti@nutmeg.eng.unimelb.edu.au<br>
 * Master of Information Technology<br>
 * The University of Melbourne<br>
 *<br>
 * Overview:<br>
 * This is an Exception class to handle invalid input from the user</p>
 *
 * @author I Gede Wibawa Cakramurti
 * @since 28 June 2020
 * @version 1.0
 */

public class InvalidInputException extends Exception
{
    public InvalidInputException()
    {
        super("Invalid response. Please try again.");
    }

    public InvalidInputException(String message)
    {
        super(message);
    }
}
