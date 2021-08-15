# Moral Machine
This repo is a final project for COMP90041 Programming and Software Development, Semester 1, 2020.

**Lecturer: Dr. Tilman Dingler**

The idea of Moral Machines is based on the Trolley Dilemma, a fictional scenario presenting a decision
maker with a moral dilemma: choosing ”the lesser of two evils”. The scenario entails an autonomous car
whose brakes fail at a pedestrian crossing. There will be scenarios that the user has to decide which group to be saved, i.e. either the passengers of the autonomous car, or the pedestrians.

The scenarios will be revolved over several situations:<br/>
- Whether the pedestrian crosses legally (i.e. traffic light is *green* or *red*)
- Characters (human and/or animal) involved in the situation as both passengers and pedestrians
- Characteristics of these characters, include - *but not limited to* - `Gender`, `BodyType`, `AgeCategory`, `Profession` 

The aim of this project is to build a decision-making program interactively with the user which can display user statistics to show survival percentage of the group and characteristics based on past decisions. Otherwise, the program has a built-in algorithm to run the decisions across multiple scenarios. Other several features included in the program:<br/>
- Scenarios can be generated randomly by the program, or can be user-defined with a .csv file
- Audit features that allows the user see survival percentage based on past decisions
- Choose either to save the statistics or just casually running the program without saving

# How-to Run the Moral Machine

## Pre-requisites
- Java Development Kit (JDK) 13

## Running the program
- Go to `/src/`
- By default, running `java EthicalEngine` will invoke 1000 randomly-generated scenarios decided using the built-in decision algorithm. Statistics will be shown as a file `results.log`
- Run `java EthicalEngine -i` or `java EthicalEngine --interactive` for interactive session which the user gets to decide on the program
- Run `java EthicalEngine -h` or `java EthicalEngine --help` for more commands

#
***Completed in June 2020 with 97 marks overall***