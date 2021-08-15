package ethicalengine;

/**
 * <p>Final Project COMP90041 due 5 pm (AEST), 30 June 2020<br>
 * MORAL MACHINE - Character.java<br>
 * Student ID: 1047538<br>
 * icakramurti@nutmeg.eng.unimelb.edu.au<br>
 * Master of Information Technology<br>
 * The University of Melbourne<br>
 *<br>
 * Overview:<br>
 * This is the abstract class for Person.java and Animal.java</p>
 *
 * @author I Gede Wibawa Cakramurti
 * @since 28 June 2020
 * @version 1.0
 */

public abstract class Character
{
    public enum Gender {MALE, FEMALE, UNKNOWN}
    public enum BodyType {AVERAGE, ATHLETIC, OVERWEIGHT, UNSPECIFIED}

    private Gender gender;
    private BodyType bodyType;
    private int age;

    /**
     * Empty constructor of Character will set the default variable as below:<br>
     * age = 0<br>
     * gender = UNKNOWN<br>
     * bodyType = UNSPECIFIED
     */
    public Character()
    {
        age = 0;
        gender = Gender.UNKNOWN;
        bodyType = BodyType.UNSPECIFIED;
    }

    /**
     * @param age - integer: The age set for character. If negative age or non integer is provided, it is set to 0.
     * @param gender - enum Gender: The gender for each character.
     * @param bodyType - enum BodyType: The body type for each character.
     */
    public Character(int age, Gender gender, BodyType bodyType)
    {
        setAge(age);
        this.gender = gender;
        this.bodyType = bodyType;
    }

    /**
     * @param c - Character: Copy of the other Character object.
     */
    public Character(Character c)
    {
        this.age = c.age;
        this.gender = c.gender;
        this.bodyType = c.bodyType;
    }

    /**
     * @return gender
     */
    public Gender getGender()
    {
        return gender;
    }

    /**
     * @param gender - enum Gender: The gender for each character.
     */
    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    /**
     * @return bodyType
     */
    public BodyType getBodyType()
    {
        return bodyType;
    }

    /**
     * @param bodyType - enum BodyType: The body type for each character.
     */
    public void setBodyType(BodyType bodyType)
    {
        this.bodyType = bodyType;
    }

    /**
     * @return age
     */
    public int getAge()
    {
        return age;
    }

    /**
     * @param age - integer: The age set for character. If negative age or non integer is provided, it is set to 0.
     */
    public void setAge(int age)
    {
        try
        {
            if (age < 0)
                throw new Exception("Age cannot be negative. Age set to default (0).");
            this.age = age;
        }
        catch (NumberFormatException NFEx)
        {
            System.out.println("Age must be a number. Age set to default (0).");
            this.age = 0;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            this.age = 0;
        }
    }

    public abstract boolean isYou();

    public abstract void setAsYou(boolean isYou);

    public abstract Person.AgeCategory getAgeCategory();

    public abstract Person.Profession getProfession();

    public abstract boolean isPregnant();

    public abstract String getSpecies();

    public abstract boolean isPet();

}
