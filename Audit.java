import ethicalengine.*;
import ethicalengine.Character;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * <p>Final Project COMP90041 due 5 pm (AEST), 30 June 2020<br>
 * MORAL MACHINE - Audit.java<br>
 * Student ID: 1047538<br>
 * icakramurti@nutmeg.eng.unimelb.edu.au<br>
 * Master of Information Technology<br>
 * The University of Melbourne<br>
 *<br>
 * Overview:<br>
 * This is the Audit class use to run scenarios, check statistics, and save statistics to file</p>
 *
 * @author I Gede Wibawa Cakramurti
 * @since 28 June 2020
 * @version 1.0
 */

public class Audit
{
    private String auditType;
    private int runs;
    private double totalAgeSaved;
    private double totalAgeAppearance;

    // Variable to store statistics
    private ArrayList<String> characteristicList = new ArrayList<>
            (Arrays.asList("green", "red", "person", "animal", "dog", "cat", "bird", "hamster", "pet",
                    "baby", "child", "adult", "senior", "male", "female", "unknown", "doctor",
                    "ceo", "artist", "politician", "criminal", "homeless", "unemployed",
                    "average", "athletic", "overweight", "unspecified", "pregnant", "you"));
    private ArrayList<Integer> appearList = new ArrayList<>();
    private ArrayList<Integer> savedList = new ArrayList<>();
    private ArrayList<Double> percentageList = new ArrayList<>();
    private ArrayList<SortByPercentage> statisticsSorted = new ArrayList<>();

    // Variable for config
    private Scenario[] scenarios;

    /**
     * Empty constructor of Audit will set the default variable as below:<br>
     * Audit type = Unspecified
     */
    public Audit()
    {
        // Initialise statistics
        for (int i = 0; i < characteristicList.size(); i++)
        {
            appearList.add(0);
            savedList.add(0);
        }
        auditType = "Unspecified";
        runs = 0;
        totalAgeSaved = 0;
        totalAgeAppearance = 0;
    }

    /**
     * Constructor for a set of scenarios given<br>
     * Set the audit type to be 'Unspecified'
     * @param scenarios - Scenario[]: list of scenarios to be audited
     */
    public Audit(Scenario[] scenarios)
    {
        this.scenarios = scenarios;

        // Initialise statistics
        for (int i = 0; i < characteristicList.size(); i++)
        {
            appearList.add(0);
            savedList.add(0);
        }
        auditType = "Unspecified";
        runs = 0;
        totalAgeSaved = 0;
        totalAgeAppearance = 0;
    }

    /**
     * The run method will provide results and statistics using EthicalEngine.decide algorithm for given scenarios
     */
    public void run()
    {
        for (Scenario s : scenarios)
        {
            runs++;
            updateStatistics(s, EthicalEngine.decide(s));
        }

        updatePercentage();
        sortStatistics();
    }

    /**
     * The run with param runs will provide results and statistics using EthicalEngine.decide algorithm for
     * a number of runs random scenarios generated by the ScenarioGenerator
     * @param runs - integer: number of runs
     */
    public void run(int runs)
    {
        this.runs += runs;

        ScenarioGenerator scenarioGenerator = new ScenarioGenerator();
        Scenario scenario;

        // Running for i runs
        for (int i = 0; i < runs; i++)
        {
            scenario = scenarioGenerator.generate();
            updateStatistics(scenario, EthicalEngine.decide(scenario));
        }

        updatePercentage();
        sortStatistics();
    }

    /**
     * This method will provide results and statistics for a given scenario and decision
     * @param scenario - Scenario
     * @param decision - EthicalEngine.Decision: whether you save PASSENGERS or PEDESTRIANS
     */
    public void run(Scenario scenario, EthicalEngine.Decision decision)
    {
        runs++;
        updateStatistics(scenario, decision);
        updatePercentage();
        sortStatistics();
    }

    /**
     * A method to show the Audit Type
     * @return String Audit Type
     */
    public String getAuditType()
    {
        return auditType;
    }

    /**
     * A method to set the Audit Type
     * @param auditType - String: Audit Type
     */
    public void setAuditType(String auditType)
    {
        this.auditType = auditType;
    }

    /**
     * A method to save statistics to a file .log for a given path
     * @param filepath - String: path to the file.
     */
    public void printToFile(String filepath)
    {
        try
        {
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter writer = new BufferedWriter(fw);

            writer.write(toString());
            writer.newLine();
            writer.close();
        }
        catch (IOException ioException)
        {
            System.out.println("ERROR: could not print results. Target directory does not exist.");
        }
    }

    /**
     * A helper method to update the statistics into respective ArrayList
     * @param scenario - Scenario
     * @param decision - EthicalEngine.Decision: whether you save PASSENGERS or PEDESTRIANS
     */
    private void updateStatistics(Scenario scenario, EthicalEngine.Decision decision)
    {
        Character[] savedDecision = new Character[scenario.getPassengers().length];
        Character[] notSavedDecision = new Character[scenario.getPedestrians().length];

        if (decision == EthicalEngine.Decision.PASSENGERS)
        {
            savedDecision = scenario.getPassengers();
            notSavedDecision = scenario.getPedestrians();
        }
        else if (decision == EthicalEngine.Decision.PEDESTRIANS)
        {
            savedDecision = scenario.getPedestrians();
            notSavedDecision = scenario.getPassengers();
        }

        // Save every information for the saved passengers in savedList
        for (Character c : savedDecision)
        {
            if (!scenario.isLegalCrossing())
            {
                updateSavedCharacteristic("red");
                updateAppearCharacteristic("red");
            }
            else if (scenario.isLegalCrossing())
            {
                updateSavedCharacteristic("green");
                updateAppearCharacteristic("green");
            }

            if (c instanceof Person)
            {
                // Save is person in saved list
                updateSavedCharacteristic("person");
                updateAppearCharacteristic("person");

                // Save age value in saved list
                totalAgeSaved += c.getAge();
                // Save age appearance times which is the same with Person appearance
                totalAgeAppearance += 1;

                // Save age category
                updateSavedCharacteristic(c.getAgeCategory().name().toLowerCase());
                // Save age category appearance
                updateAppearCharacteristic(c.getAgeCategory().name().toLowerCase());

                // Save gender
                if (c.getGender() != Character.Gender.UNKNOWN)
                {
                    updateSavedCharacteristic(c.getGender().name().toLowerCase());
                    // Save gender category appearance
                    updateAppearCharacteristic(c.getGender().name().toLowerCase());
                }


                // Save profession
                if (c.getProfession() != Person.Profession.NONE)
                {
                    updateSavedCharacteristic(c.getProfession().name().toLowerCase());
                    // Save profession appearance
                    updateAppearCharacteristic(c.getProfession().name().toLowerCase());
                }

                // Save body type
                updateSavedCharacteristic(c.getBodyType().name().toLowerCase());
                // Save body type appearance
                updateAppearCharacteristic(c.getBodyType().name().toLowerCase());

                // Save is pregnant
                if (c.isPregnant())
                {
                    updateSavedCharacteristic("pregnant");
                    // Save is pregnant appearance
                    updateAppearCharacteristic("pregnant");
                }

                // Save is you
                if (c.isYou())
                {
                    updateSavedCharacteristic("you");
                    // Save is you appearance
                    updateAppearCharacteristic("you");
                }
            }
            else // instance of Animal
            {
                // Update characteristic list if there is new animal species
                if (!characteristicList.contains(c.getSpecies().toLowerCase()) && !c.getSpecies().isEmpty())
                {
                    characteristicList.add(c.getSpecies().toLowerCase());
                    savedList.add(0);
                    appearList.add(0);
                }


                // Save is animal in saved list
                updateSavedCharacteristic("animal");
                updateAppearCharacteristic("animal");

                // Save species
                updateSavedCharacteristic(c.getSpecies().toLowerCase());
                // Save species appearance
                updateAppearCharacteristic(c.getSpecies().toLowerCase());

                // Save is pet
                if (c.isPet())
                {
                    updateSavedCharacteristic("pet");
                    // Save is pet appearance
                    updateAppearCharacteristic("pet");
                }
            }
        }

        // Save to appear list of the not saved pedestrians
        for (Character c: notSavedDecision)
        {
            // Updating legal/illegal crossing
            if (!scenario.isLegalCrossing())
                updateAppearCharacteristic("red");
            else if (scenario.isLegalCrossing())
                updateAppearCharacteristic("green");

            if (c instanceof Person)
            {
                // Save is person appearance
                updateAppearCharacteristic("person");
                // Save age category appearance
                updateAppearCharacteristic(c.getAgeCategory().name().toLowerCase());
                // Save gender category appearance
                if (c.getGender() != Character.Gender.UNKNOWN)
                    updateAppearCharacteristic(c.getGender().name().toLowerCase());
                // Save profession appearance
                if (c.getProfession() != Person.Profession.NONE)
                    updateAppearCharacteristic(c.getProfession().name().toLowerCase());
                // Save body type appearance
                updateAppearCharacteristic(c.getBodyType().name().toLowerCase());
                // Save is pregnant appearance
                if (c.isPregnant())
                    updateAppearCharacteristic("pregnant");
                if (c.isYou())
                    updateAppearCharacteristic("you");
            }
            else // instance of Animal
            {
                // Update characteristic list if there is new animal species
                if (!characteristicList.contains(c.getSpecies().toLowerCase()) && !c.getSpecies().isEmpty())
                {
                    characteristicList.add(c.getSpecies().toLowerCase());
                    savedList.add(0);
                    appearList.add(0);
                }
                // Save is animal appearance
                updateAppearCharacteristic("animal");
                // Save species appearance
                updateAppearCharacteristic(c.getSpecies().toLowerCase());
                // Save is pet appearance
                if (c.isPet())
                    updateAppearCharacteristic("pet");
            }
        }
    }

    /**
     * A helper method to update the saved characteristics
     * @param indexOf - String: the characteristic to be saved
     */
    private void updateSavedCharacteristic(String indexOf)
    {
        int characteristicIndex;
        int characteristicVal;

        characteristicIndex = characteristicList.indexOf(indexOf);
        characteristicVal = savedList.get(characteristicIndex);
        savedList.set(characteristicIndex, characteristicVal + 1);
    }

    /**
     * A helper method to update the appear characteristics
     * @param indexOf - String: the characteristic to be saved
     */
    private void updateAppearCharacteristic(String indexOf)
    {
        int characteristicIndex;
        int characteristicVal;

        characteristicIndex = characteristicList.indexOf(indexOf);
        characteristicVal = appearList.get(characteristicIndex);
        appearList.set(characteristicIndex, characteristicVal + 1);
    }

    /**
     * A helper method to update the saved statistics and convert to percentage<br>
     * Format of the percentage is 0.0
     */
    private void updatePercentage()
    {
        percentageList.clear();

        for (int i = 0; i < characteristicList.size(); i++)
        {
            double percentage = Double.valueOf(savedList.get(i)) / Double.valueOf(appearList.get(i));
            percentage = trimDouble(percentage, 1);
            percentageList.add(percentage);
        }
    }

    /**
     * A helper method to sort statistics by percentage, then alphabetically
     * @see SortByPercentage for collection sort helper
     */
    private void sortStatistics()
    {
        statisticsSorted.clear();
        for (int i = 0; i < characteristicList.size(); i++)
            statisticsSorted.add(new SortByPercentage(characteristicList.get(i), savedList.get(i), appearList.get(i),
                    percentageList.get(i)));

        statisticsSorted.sort(Collections.reverseOrder(Comparator.comparing(SortByPercentage::getPercentage))
                .thenComparing(SortByPercentage::getCharacteristic));
    }

    /**
     * A helper method to cut decimals in double value
     * @param value - double: value to be trimmed
     * @param precision - integer: number of decimal points
     * @return double trimmed with precision decimal points
     */
    private double trimDouble(double value, int precision)
    {
        int scale = (int) Math.pow(10, precision);
        return Math.floor(value * scale) / scale;
    }

    /**
     * @return ======================================<br>
     *         # |auditType| Audit<br>
     *         ======================================<br>
     *         - % SAVED AFTER |int run| RUNS<br>
     *         |for each characterstic:|<br>
     *             |characterstic|: |survival ratio|<br>
     *         --<br>
     *         average age: |average|<br>
     */
    @Override
    public String toString()
    {
        String s;

        DecimalFormat df = new DecimalFormat("0.0");
        df.setRoundingMode(RoundingMode.DOWN);

        String averageAge = df.format(totalAgeSaved / totalAgeAppearance);
        StringBuilder characteristics = new StringBuilder();

        s = "======================================\n"
                + "# " + auditType +" Audit\n"
                + "======================================\n"
                + "- % SAVED AFTER " + runs + " RUNS\n";

        for (SortByPercentage sbp : statisticsSorted)
        {
            if (sbp.getAppear() != 0)
                characteristics.append(sbp.getCharacteristic()).append(": ")
                        .append(df.format(sbp.getPercentage())).append("\n");
        }

        s = s + characteristics
                + "--\n" + "average age: " + averageAge;

        return s;
    }

    /**
     * A method to print statistics right away
     */
    public void printStatistic()
    {
        System.out.println(toString());
    }
}
