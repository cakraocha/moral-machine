package ethicalengine;

import java.util.ArrayList;
import java.util.Random;

/**
 * <p>Final Project COMP90041 due 5 pm (AEST), 30 June 2020<br>
 * MORAL MACHINE - ScenarioGenerator.java<br>
 * Student ID: 1047538<br>
 * icakramurti@nutmeg.eng.unimelb.edu.au<br>
 * Master of Information Technology<br>
 * The University of Melbourne<br>
 *<br>
 * Overview:<br>
 * This is the ScenarioGenerator class use to generate different kinds of scenarios</p>
 *
 * @author I Gede Wibawa Cakramurti
 * @since 28 June 2020
 * @version 1.0
 */

public class ScenarioGenerator
{
    private Random random = new Random();

    public static final int DEFAULT_PASSCOUNTMIN = 1;
    public static final int DEFAULT_PASSCOUNTMAX = 5;
    public static final int DEFAULT_PEDSCOUNTMIN = 1;
    public static final int DEFAULT_PEDSCOUNTMAX = 5;

    private int passengerCountMin;
    private int passengerCountMax;
    private int pedestrianCountMin;
    private int pedestrianCountMax;

    /**
     * Empty constructor for ScenarioGenerator with default values<br>
     * Passengers size min = 1<br>
     * Passengers size max = 5<br>
     * Pedestrians size min = 1<br>
     * Pedestrians size max = 5
     */
    public ScenarioGenerator()
    {
        passengerCountMin = DEFAULT_PASSCOUNTMIN;
        passengerCountMax = DEFAULT_PASSCOUNTMAX;
        pedestrianCountMin = DEFAULT_PEDSCOUNTMIN;
        pedestrianCountMax = DEFAULT_PEDSCOUNTMAX;
    }

    /**
     * Constructor with seed for ScenarioGenerator with default values<br>
     * Passengers size min = 1<br>
     * Passengers size max = 5<br>
     * Pedestrians size min = 1<br>
     * Pedestrians size max = 5
     * @param seed - long: number for pseudorandom (seed)
     */
    public ScenarioGenerator(long seed)
    {
        random.setSeed(seed);
        passengerCountMin = DEFAULT_PASSCOUNTMIN;
        passengerCountMax = DEFAULT_PASSCOUNTMAX;
        pedestrianCountMin = DEFAULT_PEDSCOUNTMIN;
        pedestrianCountMax = DEFAULT_PEDSCOUNTMAX;
    }

    /**
     * Constructor with defined seed and minimum / maximum size for passengers and pedestrians
     * @param seed - long: number for pseudorandom (seed)
     * @param passengerCountMinimum - integer: number for minimum size of passengers
     * @param passengerCountMaximum - integer: number for maximum size of passengers
     * @param pedestrianCountMinimum - integer: number for minimum size of pedestrians
     * @param pedestrianCountMaximum - integer: number for maximum size of pedestrians
     */
    public ScenarioGenerator(long seed, int passengerCountMinimum, int passengerCountMaximum,
                             int pedestrianCountMinimum, int pedestrianCountMaximum)
    {
        random.setSeed(seed);
        passengerCountMin = passengerCountMinimum;
        passengerCountMax = passengerCountMaximum;
        pedestrianCountMin = pedestrianCountMinimum;
        pedestrianCountMax = pedestrianCountMaximum;
    }

    /**
     * Set the minimum size for passengers
     * @param min - integer: number for minimum size of passengers
     */
    public void setPassengerCountMin(int min)
    {
        passengerCountMin = min;
    }

    /**
     * Set the maximum size for passengers
     * @param max - integer: number for maximum size of passengers
     */
    public void setPassengerCountMax(int max)
    {
        passengerCountMax = max;
    }

    /**
     * Set the minimum size for pedestrians
     * @param min - integer: number for minimum size of pedestrians
     */
    public void setPedestrianCountMin(int min)
    {
        pedestrianCountMin = min;
    }

    /**
     * Set the maximum size for pedestrians
     * @param max - integer: number for maximum size of pedestrians
     */
    public void setPedestrianCountMax(int max)
    {
        pedestrianCountMax = max;
    }

    /**
     * Generate a random Person object
     * @return Person
     */
    public Person getRandomPerson()
    {
        // random age distribution https://www.indexmundi.com/world/age_structure.html
        int ageRandomGenerator = random.nextInt(100);
        int ageInput;

        if (ageRandomGenerator < 25)
            ageInput = random.nextInt(15);
        else if (ageRandomGenerator < 41)
            ageInput = random.nextInt((24 - 15) + 1) + 15;
        else if (ageRandomGenerator < 82)
            ageInput = random.nextInt((54 - 25) + 1) + 25;
        else if (ageRandomGenerator < 91)
            ageInput = random.nextInt((64 - 55) + 1) + 55;
        else
            ageInput = random.nextInt((115 - 65) + 1) + 65;

        // random for profession
        int profRandomGenerator = random.nextInt(8);
        Person.Profession professionInput;

        switch (profRandomGenerator)
        {
            case 0:
                professionInput = Person.Profession.DOCTOR;
                break;
            case 1:
                professionInput = Person.Profession.CEO;
                break;
            case 2:
                professionInput = Person.Profession.ARTIST;
                break;
            case 3:
                professionInput = Person.Profession.POLITICIAN;
                break;
            case 4:
                professionInput = Person.Profession.CRIMINAL;
                break;
            case 5:
                professionInput = Person.Profession.HOMELESS;
                break;
            case 6:
                professionInput = Person.Profession.UNEMPLOYED;
                break;
            default:
                professionInput = Person.Profession.UNKNOWN;
        }

        // random for gender
        int genderRandomGenerator = random.nextInt(3);
        Character.Gender genderInput;

        if (genderRandomGenerator == 0)
            genderInput = Character.Gender.MALE;
        else if (genderRandomGenerator == 1)
            genderInput = Character.Gender.FEMALE;
        else
            genderInput = Character.Gender.UNKNOWN;

        // random for body type
        int bodyTypeRandomGenerator = random.nextInt(4);
        Character.BodyType bodyTypeInput;

        if (bodyTypeRandomGenerator == 0)
            bodyTypeInput = Character.BodyType.AVERAGE;
        else if (bodyTypeRandomGenerator == 1)
            bodyTypeInput = Character.BodyType.ATHLETIC;
        else if (bodyTypeRandomGenerator == 2)
            bodyTypeInput = Character.BodyType.OVERWEIGHT;
        else
            bodyTypeInput = Character.BodyType.UNSPECIFIED;

        // random for pregnancy
        int pregnancyRandomGenerator = random.nextInt(2);
        boolean pregnancy = false;

        if (pregnancyRandomGenerator == 1)
            pregnancy = true;

        return new Person(ageInput, professionInput, genderInput, bodyTypeInput, pregnancy);

    }

    /**
     * Generate a random Animal object<br>
     * For species, values that are available: dog, cat, bird, and hamster
     * @return Animal
     */
    public Animal getRandomAnimal()
    {
        // random for species
        String speciesInput;
        int speciesRandomGenerator = random.nextInt(4);

        switch (speciesRandomGenerator)
        {
            case 0:
                speciesInput = "dog";
                break;
            case 1:
                speciesInput = "cat";
                break;
            case 2:
                speciesInput = "bird";
                break;
            case 3:
                speciesInput = "hamster";
                break;
            default:
                speciesInput = "";
        }

        // random for pet
        boolean isPet = random.nextInt(2) == 1;

        return new Animal(speciesInput, isPet);

    }

    /**
     * Generate a random scenario
     * @return Scenario
     */
    public Scenario generate()
    {
        // random for number of both passengers and pedestrians
        int passRandomGenerator = random.nextInt((passengerCountMax - passengerCountMin) + 1)
                + passengerCountMin;
        int pedsRandomGenerator = random.nextInt((pedestrianCountMax - pedestrianCountMin) + 1)
                + pedestrianCountMin;

        ArrayList<Character> passListBuilder = new ArrayList<>();
        ArrayList<Character> pedsListBuilder = new ArrayList<>();

        // random for character generation
        int charRandomGenerator;

        // random for characters in passenger
        for (int i = 0; i < passRandomGenerator; i++)
        {
            charRandomGenerator = random.nextInt(3); // Current random generator is 2/3 for Person
            if (charRandomGenerator < 2)
            {
                passListBuilder.add(getRandomPerson());
            }
            else
                passListBuilder.add(getRandomAnimal());
        }

        // random for characters in pedestrian
        for (int i = 0; i < pedsRandomGenerator; i++)
        {
            charRandomGenerator = random.nextInt(3); // Current random generator is 2/3 for Person
            if (charRandomGenerator < 2)
                pedsListBuilder.add(getRandomPerson());
            else
                pedsListBuilder.add(getRandomAnimal());
        }

        // random whether it is legal crossing
        boolean isLegalCrossing = random.nextInt(2) == 1;

        // random of having you either in passenger or pedestrian
        int randomYouGenerator = random.nextInt(3);

        if (randomYouGenerator == 1) // 1 here means passengers has you
        {
            for (Character c : passListBuilder)
            {
                if (c instanceof Person)
                {
                    c.setAsYou(true);
                    break;
                }
            }
        }

        if (randomYouGenerator == 2) // 2 here means pedestrians has you
        {
            for (Character c : pedsListBuilder)
            {
                if (c instanceof Person)
                {
                    c.setAsYou(true);
                    break;
                }
            }
        }

        // Convert arraylist to array
        Character[] passList = new Character[passListBuilder.size()];
        Character[] pedsList = new Character[pedsListBuilder.size()];

        passList = passListBuilder.toArray(passList);
        pedsList = pedsListBuilder.toArray(pedsList);

        return new Scenario(passList, pedsList, isLegalCrossing);
    }
}
