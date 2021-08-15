package ethicalengine;

/**
 * <p>Final Project COMP90041 due 5 pm (AEST), 30 June 2020<br>
 * MORAL MACHINE - Scenario.java<br>
 * Student ID: 1047538<br>
 * icakramurti@nutmeg.eng.unimelb.edu.au<br>
 * Master of Information Technology<br>
 * The University of Melbourne<br>
 *<br>
 * Overview:<br>
 * This is the Scenario class to create scenario(s) consisting of passengers and pedestrians</p>
 *
 * @author I Gede Wibawa Cakramurti
 * @since 28 June 2020
 * @version 1.0
 */

public class Scenario
{
    private Character[] passengers;
    private Character[] pedestrians;

    private boolean isLegalCrossing;

    /**
     * Empty constructor does not define anything
     */
    public Scenario()
    {

    }

    /**
     * Sets the elements in a Scenario<br>
     * If there are multiple you in either passengers or pedestrians, will throw an Exception of multiple you.
     * @param passengers - Character[]: An array for passengers consisting of both Person and Animal.
     * @param pedestrians - Character[]: An array for pedestrians consisting of both Person and Animal.
     * @param isLegalCrossing - boolean: if true, the pedestrian light is green and is a legal crossing for pedestrian.
     */
    public Scenario(Character[] passengers, Character[] pedestrians, boolean isLegalCrossing)
    {
        // Check multiple you in both passengers and pedestrians
        int youCounter = 0;
        for (Character c: passengers)
        {
            if (c.isYou())
                youCounter++;
        }
        for (Character c: pedestrians)
        {
            if (c.isYou())
                youCounter++;
        }

        try
        {
            if (youCounter > 1)
                throw new Exception("'You' cannot be more than 1.");
            this.passengers = passengers;
            this.pedestrians = pedestrians;
            this.isLegalCrossing = isLegalCrossing;
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    /**
     * @return boolean: if true, then you are in passengers
     */
    public boolean hasYouInCar()
    {
        for (Character c: passengers)
        {
            if (c.isYou())
                return true;
        }
        return false;
    }

    /**
     * @return boolean: if true, then you are in pedestrians
     */
    public boolean hasYouInLane()
    {
        for (Character c: pedestrians)
        {
            if (c.isYou())
                return true;
        }
        return false;
    }

    /**
     * @return Character[] passengers
     */
    public Character[] getPassengers()
    {
        return passengers;
    }

    /**
     * @return Character[] pedestrians
     */
    public Character[] getPedestrians()
    {
        return pedestrians;
    }

    /**
     * @return boolean: if true, the pedestrian light is green and is a legal crossing for pedestrian.
     */
    public boolean isLegalCrossing()
    {
        return isLegalCrossing;
    }

    /**
     * Set the legal crossing status
     * @param isLegalCrossing boolean: if true, the pedestrian light is green and is a legal crossing for pedestrian.
     */
    public void setLegalCrossing(boolean isLegalCrossing)
    {
        this.isLegalCrossing = isLegalCrossing;
    }

    /**
     * @return integer: number of passengers
     */
    public int getPassengerCount()
    {
        return passengers.length;
    }

    /**
     * @return integer: number of pedestrians
     */
    public int getPedestrianCount()
    {
        return pedestrians.length;
    }

    /**
     * @return ======================================<br>
     *         # Scenario<br>
     *         ======================================<br>
     *         Legal Crossing: |yes/no|<br>
     *         Passengers (|getPassengerCount|)<br>
     *         - |character.toString|<br>
     *         Pedestrians (|getPedestrianCount|)<br>
     *         |character.toString|<br>
     */
    @Override
    public String toString()
    {
        String s;
        StringBuilder numPerson = new StringBuilder();

        s = "======================================\n"
                + "# Scenario\n"
                + "======================================\n";

        if (isLegalCrossing())
            s = s + "Legal Crossing: yes\n";
        else
            s = s + "Legal Crossing: no\n";

        s = s + "Passengers (" + getPassengerCount() + ")\n";
        for (Character c: passengers)
            numPerson.append("- ").append(c.toString()).append("\n");
        s = s + numPerson + "Pedestrians (" + getPedestrianCount() + ")\n";
        numPerson = new StringBuilder();
        for (int i = 0; i < pedestrians.length - 1; i++)
            numPerson.append("- ").append(pedestrians[i].toString()).append("\n");
        s = s + numPerson;
        s = s + "- " + pedestrians[pedestrians.length - 1];

        return s;
    }

}
