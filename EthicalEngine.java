import ethicalengine.*;
import ethicalengine.Character;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * <p>Final Project COMP90041 due 5 pm (AEST), 30 June 2020<br>
 * MORAL MACHINE - EthicalEngine.java<br>
 * Student ID: 1047538<br>
 * icakramurti@nutmeg.eng.unimelb.edu.au<br>
 * Master of Information Technology<br>
 * The University of Melbourne<br>
 *<br>
 * Overview:<br>
 * This is the Main class to run Ethical Engine whole application</p>
 *
 * @author I Gede Wibawa Cakramurti
 * @since 28 June 2020
 * @version 1.0
 */

public class EthicalEngine
{
    enum Decision {PEDESTRIANS, PASSENGERS}
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args)
    {
        // Default Path
        String systemLogPath = "results.log";
        String userLogPath = "user.log";

        // If no argument given, run audit by the decision algorithm
        String argument = "";
        try
        {
            argument = args[0];
        }
        catch (IndexOutOfBoundsException IOOBEx)
        {
            Audit audit = new Audit();
            audit.setAuditType("Decide Algorithm");
            audit.run(1000);
            audit.printStatistic();
            audit.printToFile(systemLogPath);
            System.exit(0);
        }

        // Launch config mode if -c or --config argument given
        if (argument.equals("-c") || argument.equals("--config"))
        {
            try
            {
                Audit configAudit = new Audit(config(args[1]));
                configAudit.run();

                // If a path is given, change path, otherwise use the default path
                try
                {
                    if (args[2].equals("-r") || args[2].equals("--results"))
                    {
                        try
                        {
                            systemLogPath = args[3].toLowerCase();
                        }
                        catch (Exception e)
                        {
                            System.out.println(helpToString());
                            System.exit(0);
                        }
                    }

                }
                catch (Exception ignore)
                {

                }

                configAudit.printToFile(systemLogPath);
            }
            catch (FileNotFoundException FNFEx)
            {
                System.out.println("ERROR: could not find config file.");
            }
            catch (IOException IOEx)
            {
                IOEx.printStackTrace();
            }
        }

        // Launch interactive mode if -i or --interactive is given
        else if (argument.equals("-i") || argument.equals("--interactive"))
        {
            // Setup for interactive with config mode
            boolean interactiveConfig = false;
            String interactiveConfigArgs = "";

            try
            {
                interactiveConfigArgs = args[1];
                interactiveConfig = true;
            }
            catch (Exception ignored)
            {

            }

            // This code will run only if there is an argument after -i, which is -c or --config
            if ((interactiveConfigArgs.equals("-c") || interactiveConfigArgs.equals("--config"))
                    && interactiveConfig) // Interactive with specified scenarios or config
            {
                try
                {
                    // If a path is given, change path, otherwise use the default path
                    try
                    {
                        if (args[3].equals("-r") || args[3].equals("--results"))
                        {
                            try
                            {
                                userLogPath = args[4].toLowerCase();
                            }
                            catch (Exception e)
                            {
                                System.out.println(helpToString());
                                System.exit(0);
                            }
                        }
                    }
                    catch (Exception ignore)
                    {

                    }

                    // Setup for the interactive config mode
                    Scenario[] scenarios = config(args[2]);
                    Audit auditInteractiveConfig = new Audit(scenarios);
                    auditInteractiveConfig.setAuditType("User");

                    // Launch interactive config mode
                    printWelcome();

                    String userAnswer;
                    boolean userConsent = userConsentInput();

                    int indexInteractiveRun = 0;
                    int numInteractiveRun = 0;

                    while (true)
                    {
                        for (int i = indexInteractiveRun; i < scenarios.length; i++)
                        {
                            interactiveDecision(scenarios[i], auditInteractiveConfig);
                            indexInteractiveRun++;
                            numInteractiveRun++;
                            if (numInteractiveRun == 3)
                            {
                                numInteractiveRun = 0;
                                break;
                            }
                        }
                        auditInteractiveConfig.printStatistic();
                        if (indexInteractiveRun == scenarios.length)
                        {
                            if (userConsent)
                                auditInteractiveConfig.printToFile(userLogPath);
                            System.out.println("That's all. Press Enter to quit.");
                            scan.nextLine();
                            System.exit(0);
                        }
                        else
                            {
                                System.out.println("Would you like to continue? (yes/no)");
                                userAnswer = scan.nextLine();
                                if (!userAnswer.toLowerCase().equals("yes") && !userAnswer.toLowerCase().equals("y"))
                                {
                                    auditInteractiveConfig.printStatistic();
                                    if (userConsent)
                                        auditInteractiveConfig.printToFile(userLogPath);
                                    System.out.println("That's all. Press Enter to quit.");
                                    scan.nextLine();
                                    System.exit(0);
                                }
                            }
                    }
                }
                catch (FileNotFoundException FNFEx)
                {
                    System.out.println("ERROR: could not find config file.");
                }
                catch (IOException IOEx)
                {
                    IOEx.printStackTrace();
                }

                // Exit the program once all config has been shown or user do not want to continue
                System.out.println("That's all. Press Enter to quit.");
                scan.nextLine();
                System.exit(0);
            }

            // Launch interactive mode with randomised scenario
            try
            {
                // If a path is given, change path, otherwise use the default path
                try
                {
                    if (args[1].equals("-r") || args[1].equals("--results"))
                    {
                        try
                        {
                            userLogPath = args[2].toLowerCase();
                        }
                        catch (Exception e)
                        {
                            System.out.println(helpToString());
                            System.exit(0);
                        }
                    }
                }
                catch (Exception ignore)
                {

                }

                // Launch interactive mode with randomised scenario
                printWelcome();

                String userAnswer;
                boolean userConsent = userConsentInput();

                Audit auditInteractive = new Audit();
                auditInteractive.setAuditType("User");
                ScenarioGenerator scenarioGenerator = new ScenarioGenerator();
                Scenario scenario;

                // Number of scenarios per round default set to be 3
                int numberInteractiveRun = 3;

                while (true)
                {
                    for (int i = 0; i < numberInteractiveRun; i++)
                    {
                        scenario = scenarioGenerator.generate();
                        interactiveDecision(scenario, auditInteractive);
                    }
                    auditInteractive.printStatistic();
                    System.out.println("Would you like to continue? (yes/no)");
                    userAnswer = scan.nextLine();
                    if (!userAnswer.toLowerCase().equals("yes") && !userAnswer.toLowerCase().equals("y"))
                    {
                        auditInteractive.printStatistic();
                        if (userConsent)
                            auditInteractive.printToFile(userLogPath);
                        System.out.println("That's all. Press Enter to quit.");
                        scan.nextLine();
                        System.exit(0);
                    }
                }
            }
            catch (IOException IOEx)
            {
                IOEx.printStackTrace();
            }
        }
        else
            System.out.println(helpToString());
    }

    /**
     * Decide Algorithm<br>
     * If it is illegal crossing, always save passengers<br>
     * If it is legal crossing, check first if there is any baby or pregnant woman, save the respective group<br>
     * If there is none or both have baby or pregnant woman, choose group which has more characters<br>
     * If number of characters are the same in both group, choose random
     * @param scenario - Scenario
     * @return EthicalEngine Decision {PASSENGERS or PEDESTRIANS}
     */
    public static Decision decide(Scenario scenario)
    {
        Decision decision;
        Random random = new Random();

        // Check if there is any baby or pregnant woman in pass and peds
        boolean hasBabyOrPregnantInPass = false;
        boolean hasBabyOrPregnantInPeds = false;

        for (Character c : scenario.getPassengers())
        {
            if (c.isPregnant() || c.getAgeCategory() == Person.AgeCategory.BABY)
                hasBabyOrPregnantInPass = true;
        }

        for (Character c : scenario.getPedestrians())
        {
            if (c.isPregnant() || c.getAgeCategory() == Person.AgeCategory.BABY)
                hasBabyOrPregnantInPeds = true;
        }

        // Decide Algorithm
        if (!(scenario.isLegalCrossing()))
        {
            if (hasBabyOrPregnantInPass && !hasBabyOrPregnantInPeds)
                decision = Decision.PASSENGERS;
            else if (hasBabyOrPregnantInPeds && !hasBabyOrPregnantInPass)
                decision = Decision.PEDESTRIANS;
            else if (scenario.getPassengerCount() > scenario.getPedestrianCount())
                decision = Decision.PASSENGERS;
            else if (scenario.getPedestrianCount() > scenario.getPassengerCount())
                decision = Decision.PEDESTRIANS;
            else
                {
                    if (random.nextInt(2) == 0)
                        decision = Decision.PASSENGERS;
                    else
                        decision = Decision.PEDESTRIANS;
                }
        }
        else
            decision = Decision.PASSENGERS;

        return decision;
    }

    /**
     * A helper method in main to print the welcome message from welcome.ascii
     * @throws FileNotFoundException - the welcome.ascii must be in the same directory as EthicalEngine.java
     * @throws IOException - catering the FileReader and BufferedReader IO Exception
     */
    private static void printWelcome() throws FileNotFoundException, IOException
    {
        FileReader fr = new FileReader("welcome.ascii");
        BufferedReader reader = new BufferedReader(fr);
        String line;
        while ((line = reader.readLine()) != null)
            System.out.println(line);
    }

    /**
     * A helper method in main to save the User Consent for statistics result saving
     * @return boolean if true then user is consent
     */
    private static boolean userConsentInput()
    {
        String userAnswer;
        boolean userConsent = false;
        boolean consentInputError = true;

        System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
        userAnswer = scan.nextLine();

        while (consentInputError)
        {
            try
            {
                if (userAnswer.toLowerCase().equals("yes"))
                {
                    userConsent = true;
                    consentInputError = false;
                }
                else if (userAnswer.toLowerCase().equals("no"))
                    consentInputError = false;
                else
                    throw new InvalidInputException("Invalid response." + " " +
                            "Do you consent to have your decisions saved to a file? (yes/no)");
            }
            catch (InvalidInputException IIEx)
            {
                System.out.println(IIEx.getMessage());
                userAnswer = scan.nextLine();
            }
        }

        return userConsent;
    }

    /**
     * A helper method to read csv file for a given path
     * @param path - String: path to the csv file
     * @return Scenario[]
     * @throws FileNotFoundException - if the path given does not exist
     * @throws IOException - catering the FileReader and BufferedReader IO Exception
     */
    private static Scenario[] config(String path) throws FileNotFoundException, IOException
    {
        // Setup the elements to store characters and scenarios from csv file
        ArrayList<Character> configPassList = new ArrayList<>();
        ArrayList<Character> configPedsList = new ArrayList<>();
        ArrayList<Scenario> configScenarioList = new ArrayList<>();

        FileReader fr = new FileReader(path);
        BufferedReader reader = new BufferedReader(fr);

        String line;
        reader.readLine();
        int lineCount = 1;
        boolean legalCrossing = false;
        String[] character = null;
        boolean IDFError;

        // Start reading from line 2, because line 1 is for title only
        while (true)
        {
            line = reader.readLine();
            lineCount++;

            // If it is the end of the line, store the previous passengers and pedestrians to a Scenario,
            // then to scenarios
            if (line == null)
            {
                Character[] configPassArray = new Character[configPassList.size()];
                Character[] configPedsArray = new Character[configPedsList.size()];

                configPassArray = configPassList.toArray(configPassArray);
                configPedsArray = configPedsList.toArray(configPedsArray);

                configScenarioList.add(new Scenario(configPassArray, configPedsArray, legalCrossing));
                reader.close();
                break;
            }

            // If the there is no match for the characteristic, throw InvalidDataFormatException
            IDFError = false;

            try
            {
                character = line.toLowerCase().split(",");
                if (character.length != 10 && (!character[0].equals("scenario:green")
                        && !character[0].equals("scenario:red")))
                    throw new InvalidDataFormatException(lineCount);
            }
            catch (InvalidDataFormatException IDFEx)
            {
                System.out.println(IDFEx.getMessage());
                IDFError = true;
            }

            if (IDFError)
                continue;

            // Start storing characters to passengers and pedestrian, build scenario, then store to scenarios
            if (character[0].equals("scenario:green") || character[0].equals("scenario:red"))
            {
                // Check if both configPassList and configPedsList is empty to determine first scenario
                if (configPassList.isEmpty() && configPedsList.isEmpty())
                    legalCrossing = character[0].equals("scenario:green");

                // Otherwise, loop through by adding previously built scenario to configScenarioList
                else
                    {
                        Character[] configPassArray = new Character[configPassList.size()];
                        Character[] configPedsArray = new Character[configPedsList.size()];

                        configPassArray = configPassList.toArray(configPassArray);
                        configPedsArray = configPedsList.toArray(configPedsArray);

                        configScenarioList.add(new Scenario(configPassArray, configPedsArray, legalCrossing));
                        legalCrossing = character[0].equals("scenario:green");

                        // Clear the passengers and pedestrians list to create a new scenario
                        configPassList = new ArrayList<>();
                        configPedsList = new ArrayList<>();
                    }
            }
            else
            {
                if (character[0].equals("person") && character[9].equals("passenger"))
                    configPassList.add(personBuilder(character, lineCount));
                else if (character[0].equals("person") && character[9].equals("pedestrian"))
                    configPedsList.add(personBuilder(character, lineCount));
                else if (character[0].equals("animal") && character[9].equals("passenger"))
                    configPassList.add(animalBuilder(character));
                else if (character[0].equals("animal") && character[9].equals("pedestrian"))
                    configPedsList.add(animalBuilder(character));
            }
        }

        Scenario[] configScenarioArray = new Scenario[configScenarioList.size()];
        configScenarioArray = configScenarioList.toArray(configScenarioArray);

        return configScenarioArray;
    }

    /**
     * A helper method for config method in building a Person
     * @param character - String[]: one line in the csv file
     * @param lineCount - integer: the line tracking with respective line
     * @return Person
     */
    private static Person personBuilder(String[] character, int lineCount)
    {
        int ageInput;
        try
        {
            ageInput = Integer.parseInt(character[2]);
        }
        catch (NumberFormatException NFEx)
        {
            System.out.printf("WARNING: invalid number format in config file in line %d\n",
                    lineCount);
            ageInput = 0;
        }

        Character.Gender genderInput;
        try
        {
            if (character[1].isEmpty())
                genderInput = Character.Gender.UNKNOWN;
            else
                genderInput = Character.Gender.valueOf(character[1].toUpperCase());
        }
        catch (InvalidCharacteristicException ICEx)
        {
            System.out.printf("WARNING: invalid characteristic in config file in line %d\n", lineCount);
            genderInput = Character.Gender.UNKNOWN;
        }

        Character.BodyType bodyTypeInput;
        try
        {
            if (character[3].isEmpty())
                bodyTypeInput = Character.BodyType.UNSPECIFIED;
            else
                bodyTypeInput = Character.BodyType.valueOf(character[3].toUpperCase());
        }
        catch (InvalidCharacteristicException ICEx)
        {
            System.out.printf("WARNING: invalid characteristic in config file in line %d\n", lineCount);
            bodyTypeInput = Character.BodyType.UNSPECIFIED;
        }

        Person.Profession professionInput;
        try
        {
            if (character[4].isEmpty())
                professionInput = Person.Profession.UNKNOWN;
            else
                professionInput = Person.Profession.valueOf(character[4].toUpperCase());
        }
        catch (InvalidCharacteristicException ICEx)
        {
            System.out.printf("WARNING: invalid characteristic in config file in line %d\n", lineCount);
            professionInput = Person.Profession.UNKNOWN;
        }

        boolean pregnancy = character[5].equals("true");
        boolean isYou = character[6].equals("true");

        return new Person(ageInput, professionInput, genderInput, bodyTypeInput, pregnancy, isYou);
    }

    /**
     * A helper method for config method in building an Animal<br>
     * Since there is no specific enum characteristic, there is no line count given<br>
     * If there is an error in defining the isPet, it will be set to false
     * @param character - String[]: one line in the csv file
     * @return Animal
     */
    private static Animal animalBuilder(String[] character)
    {
        boolean isPet = character[8].equals("true");

        return new Animal(character[7], isPet);
    }

    /**
     * A helper method for the interactive mode in accommodating bad user input
     * @param scenario - Scenario
     * @param auditInteractive - Audit
     */
    private static void interactiveDecision(Scenario scenario, Audit auditInteractive)
    {
        String userAnswer;
        boolean error = true;
        do
            {
                System.out.println(scenario.toString());
                System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
                userAnswer = scan.nextLine();
                if (userAnswer.equals("passenger") || userAnswer.equals("passengers")
                        || userAnswer.equals("1"))
                {
                    auditInteractive.run(scenario, Decision.PASSENGERS);
                    error = false;
                }
                else if (userAnswer.equals("pedestrian") || userAnswer.equals("pedestrians")
                        || userAnswer.equals("2"))
                {
                    auditInteractive.run(scenario, Decision.PEDESTRIANS);
                    error = false;
                }
                else
                    {
                        System.out.println("You're entering the wrong command. Please try again.\n");
                        try
                        {
                            Thread.sleep(1500);
                        }
                        catch (InterruptedException InterEx)
                        {
                            InterEx.printStackTrace();
                        }
                    }
            } while (error);
    }

    /**
     * @return String: command instructions by typing -h or --help
     */
    private static String helpToString()
    {
        return "EthicalEngine - COMP90041 - Final Project\n"
                + "\n"
                + "Usage: java EthicalEngine [arguments]\n"
                + "\n"
                + "Arguments:\n"
                + "   -c or --config      " + "Optional: path to config file\n"
                + "   -h or --help        " + "Print Help (this message) and exit\n"
                + "   -r or --results     " + "Optional: path to results log file\n"
                + "   -i or --interactive " + "Optional: launches interactive mode";
    }
}
