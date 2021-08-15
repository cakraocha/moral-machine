/**
 * <p>Final Project COMP90041 due 5 pm (AEST), 30 June 2020<br>
 * MORAL MACHINE - InvalidDataFormatException.java<br>
 * Student ID: 1047538<br>
 * icakramurti@nutmeg.eng.unimelb.edu.au<br>
 * Master of Information Technology<br>
 * The University of Melbourne<br>
 *<br>
 * Overview:<br>
 * This is an Exception class to handle invalid data format when reading a csv file</p>
 *
 * @author I Gede Wibawa Cakramurti
 * @since 28 June 2020
 * @version 1.0
 */

public class InvalidDataFormatException extends Exception
{
    public InvalidDataFormatException()
    {
        super("WARNING: invalid data format.");
    }

    public InvalidDataFormatException(String message)
    {
        super(message);
    }

    public InvalidDataFormatException(int lineCount)
    {
        super(String.format("WARNING: invalid data format in config file in line %d", lineCount));
    }
}
