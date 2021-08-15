package ethicalengine;

/**
 * <p>Final Project COMP90041 due 5 pm (AEST), 30 June 2020<br>
 * MORAL MACHINE - Animal.java<br>
 * Student ID: 1047538<br>
 * icakramurti@nutmeg.eng.unimelb.edu.au<br>
 * Master of Information Technology<br>
 * The University of Melbourne<br>
 *<br>
 * Overview:<br>
 * This is the Animal class to create an animal object derived from Character class</p>
 *
 * @author I Gede Wibawa Cakramurti
 * @since 28 June 2020
 * @version 1.0
 */

public class Animal extends Character
{
    private String species;
    public static final String DEFAULTSPECIES = "dog";

    private boolean isPet;
    private boolean isYou;

    /**
     * Empty constructor of Animal will set the default variable as below:<br>
     * species = dog<br>
     * isPet = false
     */
    public Animal()
    {
        species = DEFAULTSPECIES;
        isPet = false;
    }

    /**
     * isPet value will be set to false
     * @param species - String: The species of the animal.
     */
    public Animal(String species)
    {
        this.species = species.toLowerCase();
        isPet = false;
    }

    /**
     * Constructor for setting both species and isPet
     * @param species - String: The species of the animal.
     * @param isPet - boolean: if true, animal object is pet.
     */
    public Animal(String species, boolean isPet)
    {
        this.species = species.toLowerCase();
        this.isPet = isPet;
    }

    /**
     * @param otherAnimal - Animal: Copy of the other Animal object.
     */
    public Animal(Animal otherAnimal)
    {
        this.species = otherAnimal.species;
        this.isPet = otherAnimal.isPet;
    }

    /**
     * @return String species of the animal
     */
    @Override
    public String getSpecies()
    {
        return species;
    }

    /**
     * Set the animal species
     * @param species - String: The species of the animal.
     */
    public void setSpecies(String species)
    {
        this.species = species;
    }

    /**
     * @return boolean isPet
     */
    @Override
    public boolean isPet()
    {
        return isPet;
    }

    /**
     * Set the isPet status of the Animal
     * @param pet - boolean: if true, animal object is pet.
     */
    public void setPet(boolean pet)
    {
        isPet = pet;
    }

    /**
     * @return |species| [is pet]
     */
    @Override
    public String toString()
    {
        String s;
        if (isPet())
            s = species + " " + "is pet";
        else
            s = species;

        return s;
    }

    /**
     * Not valid for Animal
     * @return false
     */
    @Override
    public boolean isYou()
    {
        return isYou;
    }

    /**
     * Not valid for Animal
     * @param isYou not valid
     */
    @Override
    public void setAsYou(boolean isYou)
    {
        this.isYou = isYou;
    }

    /**
     * Not valid for Animal
     * @return null
     */
    @Override
    public Person.AgeCategory getAgeCategory()
    {
        return null;
    }

    /**
     * Not valid for Animal
     * @return null
     */
    @Override
    public Person.Profession getProfession()
    {
        return null;
    }

    /**
     * Not valid for Animal
     * @return false
     */
    @Override
    public boolean isPregnant()
    {
        return false;
    }

}
