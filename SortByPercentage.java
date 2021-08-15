/**
 * <p>Final Project COMP90041 due 5 pm (AEST), 30 June 2020<br>
 * MORAL MACHINE - SortByPercentage.java<br>
 * Student ID: 1047538<br>
 * icakramurti@nutmeg.eng.unimelb.edu.au<br>
 * Master of Information Technology<br>
 * The University of Melbourne<br>
 *<br>
 * Overview:<br>
 * This is a helper class to sort the statistics in Audit by using Collection Sort</p>
 *
 * @author I Gede Wibawa Cakramurti
 * @since 28 June 2020
 * @version 1.0
 */

public class SortByPercentage
{
    private String characteristic;
    private int saved;
    private int appear;
    private double percentage;

    /**
     * Only one constructor to help Audit class in sorting the statistics
     * @param characteristic - String: characteristic
     * @param saved - integer: number of respective characteristic being saved
     * @param appear - integer: number of respective characteristic appearance
     * @param percentage - double: the percentage of the characteristic survival (saved / appear)
     */
    public SortByPercentage(String characteristic, int saved, int appear, double percentage)
    {
        this.characteristic = characteristic;
        this.saved = saved;
        this.appear = appear;
        this.percentage = percentage;
    }

    public String getCharacteristic()
    {
        return characteristic;
    }

    public int getSaved()
    {
        return saved;
    }

    public int getAppear()
    {
        return appear;
    }

    public double getPercentage()
    {
        return percentage;
    }

    @Override
    public String toString()
    {
        return characteristic + " " + saved + " " + appear + " " + percentage;
    }

}
