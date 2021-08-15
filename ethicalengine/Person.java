package ethicalengine;

/**
 * <p>Final Project COMP90041 due 5 pm (AEST), 30 June 2020<br>
 * MORAL MACHINE - Person.java<br>
 * Student ID: 1047538<br>
 * icakramurti@nutmeg.eng.unimelb.edu.au<br>
 * Master of Information Technology<br>
 * The University of Melbourne<br>
 *<br>
 * Overview:<br>
 * This is the Person class to create a person object derived from Character class</p>
 *
 * @author I Gede Wibawa Cakramurti
 * @since 28 June 2020
 * @version 1.0
 */

public class Person extends Character
{
    public enum AgeCategory {BABY, CHILD, ADULT, SENIOR}
    public enum Profession {DOCTOR, CEO, ARTIST, POLITICIAN, CRIMINAL, HOMELESS, UNEMPLOYED, UNKNOWN, NONE}

    private AgeCategory ageCategory;
    private Profession profession;

    private boolean pregnant;
    private boolean isYou;

    /**
     * Empty constructor of Person will set the default variable as below:<br>
     * age = 0<br>
     * ageCategory = BABY<br>
     * gender = UNKNOWN<br>
     * bodyType = UNSPECIFIED<br>
     * profession = UNKNOWN<br>
     * pregnant = false<br>
     * isYou = false
     */
    public Person()
    {
        super.setAge(0);
        ageCategory = getAgeCategory();
        super.setGender(Gender.UNKNOWN);
        super.setBodyType(BodyType.UNSPECIFIED);
        profession = Profession.UNKNOWN;
        pregnant = false;
        isYou = false;
    }

    /**
     * These values below are set to default:<br>
     * profession = UNKNOWN<br>
     * pregnant = false<br>
     * isYou = false
     *
     * @param age - integer: The age set for Person. If negative age or non integer is provided, it is set to 0.
     * @param gender - enum Gender: The gender for each Person
     * @param bodyType - enum BodyType: The body type for each Person
     */
    public Person(int age, Gender gender, BodyType bodyType)
    {
        super.setAge(age);
        ageCategory = getAgeCategory();
        super.setGender(gender);
        super.setBodyType(bodyType);
        if (getAgeCategory() != AgeCategory.ADULT)
            this.profession = Profession.NONE;
        else
            this.profession = Profession.UNKNOWN;
        pregnant = false;
        isYou = false;
    }

    /**
     * isYou will be set to default (false)
     *
     * @param age - integer: The age set for Person. If negative age or non integer is provided, it is set to 0.
     * @param profession - enum Profession: The profession set for Person
     * @param gender - enum Gender: The gender for each Person
     * @param bodyType - enum BodyType: The body type for each Person
     * @param isPregnant - boolean: if true, set the Person to pregnant. Only Female Adult can be pregnant.
     */
    public Person(int age, Profession profession, Gender gender, BodyType bodyType, boolean isPregnant)
    {
        super.setAge(age);
        ageCategory = getAgeCategory();
        super.setGender(gender);
        super.setBodyType(bodyType);
        if (getAgeCategory() != AgeCategory.ADULT)
            this.profession = Profession.NONE;
        else
            this.profession = profession;
        setPregnant(isPregnant);
        isYou = false;
    }

    /**
     * @param age - integer: The age set for Person. If negative age or non integer is provided, it is set to 0.
     * @param profession - enum Profession: The profession set for Person
     * @param gender - enum Gender: The gender for each Person
     * @param bodyType - enum BodyType: The body type for each Person
     * @param isPregnant - boolean: if true, set the Person to pregnant. Only Female Adult can be pregnant.
     * @param isYou - boolean: if true, set the Person to be You.
     */
    public Person(int age, Profession profession, Gender gender, BodyType bodyType, boolean isPregnant, boolean isYou)
    {
        super.setAge(age);
        ageCategory = getAgeCategory();
        super.setGender(gender);
        super.setBodyType(bodyType);
        if (getAgeCategory() != AgeCategory.ADULT)
            this.profession = Profession.NONE;
        else
            this.profession = profession;
        setPregnant(isPregnant);
        this.isYou = isYou;
    }

    /**
     * @param otherPerson - Person: Copy of the other Person object.
     */
    public Person(Person otherPerson)
    {
        super.setAge(otherPerson.getAge());
        super.setGender(otherPerson.getGender());
        super.setBodyType(otherPerson.getBodyType());
        profession = otherPerson.getProfession();
        setPregnant(otherPerson.isPregnant());
        ageCategory = otherPerson.getAgeCategory();
        isYou = otherPerson.isYou();
    }

    /**
     * @return enum AgeCategory based on age
     */
    @Override
    public AgeCategory getAgeCategory()
    {
        if (super.getAge() >= 0 && super.getAge() < 5)
            ageCategory = AgeCategory.BABY;
        else if (super.getAge() > 4 && super.getAge() < 17)
            ageCategory = AgeCategory.CHILD;
        else if (super.getAge() > 16 && super.getAge() < 69)
            ageCategory = AgeCategory.ADULT;
        else
            ageCategory = AgeCategory.SENIOR;
        return ageCategory;
    }

    /**
     * @return enum Profession
     */
    @Override
    public Profession getProfession()
    {
        if (getAgeCategory() != AgeCategory.ADULT)
            profession = Profession.NONE;
        return profession;
    }

    /**
     * @return boolean pregnant
     */
    @Override
    public boolean isPregnant()
    {
        return pregnant;
    }

    /**
     * Set the pregnant value
     * @param pregnant - boolean: if true, set the Person to pregnant. Only Female Adult can be pregnant.
     */
    public void setPregnant(boolean pregnant)
    {
        if (super.getGender() != Gender.FEMALE || getAgeCategory() != AgeCategory.ADULT)
            this.pregnant = false;
        else
            this.pregnant = pregnant;
    }

    /**
     * @return boolean isYou
     */
    @Override
    public boolean isYou()
    {
        return isYou;
    }

    /**
     * Set the isYou value
     * @param isYou - boolean: if true, set the Person to be You.
     */
    @Override
    public void setAsYou(boolean isYou)
    {
        this.isYou = isYou;
    }

    /**
     * @return [you] |bodyType| |age category| [profession] |gender| [pregnant]
     */
    @Override
    public String toString()
    {
        String s = "";
        if (isYou())
        {
            if (getAgeCategory() == AgeCategory.ADULT && !isPregnant())
                s = "you " + getBodyType().name().toLowerCase() + " " + getAgeCategory().name().toLowerCase() + " "
                        + getProfession().name().toLowerCase() + " " + getGender().name().toLowerCase();
            else if (getAgeCategory() == AgeCategory.ADULT && isPregnant())
                s = "you " + getBodyType().name().toLowerCase() + " " + getAgeCategory().name().toLowerCase() + " "
                        + getProfession().name().toLowerCase() + " " + getGender().name().toLowerCase() + " "
                        + "pregnant";
            else if (getAgeCategory() != AgeCategory.ADULT)
                s = "you " + getBodyType().name().toLowerCase() + " " + getAgeCategory().name().toLowerCase() + " "
                        + getGender().name().toLowerCase();
        }
        else
            {
                if (getAgeCategory() == AgeCategory.ADULT && !isPregnant())
                    s = getBodyType().name().toLowerCase() + " " + getAgeCategory().name().toLowerCase() + " "
                            + getProfession().name().toLowerCase() + " " + getGender().name().toLowerCase() ;
                else if (getAgeCategory() == AgeCategory.ADULT && isPregnant())
                    s = getBodyType().name().toLowerCase() + " " + getAgeCategory().name().toLowerCase() + " "
                            + getProfession().name().toLowerCase() + " " + getGender().name().toLowerCase() + " "
                            + "pregnant";
                else if (getAgeCategory() != AgeCategory.ADULT)
                    s = getBodyType().name().toLowerCase() + " " + getAgeCategory().name().toLowerCase() + " "
                            + getGender().name().toLowerCase();
            }
        return s;
    }

    /**
     * Not valid for Person
     * @return null
     */
    @Override
    public String getSpecies()
    {
        return null;
    }

    /**
     * Not valid for Person
     * @return false
     */
    @Override
    public boolean isPet()
    {
        return false;
    }

}
