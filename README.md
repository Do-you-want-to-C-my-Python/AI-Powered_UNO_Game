[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/lSSXQhBk)
# 2024F CS2910 Programming Project Specification

**Release Date: October 31, 2024 17:00**

**Due Date:  December 5, 2024 23:59**

# Assignment Description:
You will be developing a simulation for a popular board game of your choice, following 
the principles of Test-Driven Development (TDD), applying object-oriented design (OOD) 
principles, and reflecting on the use of generative AI tools. The assignment is divided 
into four phases with a recommended amount of time to spend on each phase.  For 
implementation phases, you will emphasize object-oriented design, write failing tests, 
implement code to pass the tests, and refactoring as needed.  You will use your code
to conduct a small experiment.  Additionally, you will have the opportunity to reflect 
on your use of generative AI tools in the project.

# Objectives
- Develop a well-documented board game simulation using Test-Driven Development (TDD) and 
object-oriented design (OOD) principles.
- Create multiple player strategies, each designed by your team to address specific game scenarios.
- Integrate player strategies into the game simulation.
- Generate a report with data and analyses comparing player strategies' performance.
- Create a reflective assessment of the use of generative AI tools during the project.

# Project Phases

## Phase 1: Board Game Implementation (2 week)

In this phase you will implement the class structure for a board game.  It should be sufficient that you can
use these classes in conjunction with player related classes to play a complete game.

**Tasks:**
- Select a popular board game. This board game choice should have sufficient structure that you have at least 1 collection 
(aggregation relationship) and one generalization (inheritance relationship) hierarchy, adhering to object-oriented 
design principles. 
- Write failing tests to ensure the core game mechanics are correctly implemented.
- Implement the board game classes for those tests.
- Refactor the code to enhance clarity and maintainability.

## Phase 2: Player Classes and Strategies (1 week)

Using the class structure from **Phase 1** create a set of player classes and choose 3 strategies and implement the players
making choices.  Strategies can be as complex or as simple as you like.

**Tasks:**
-	Develop at least three distinct player strategies for your game.
-	Apply object-oriented design principles to design cohesive player and related classes.
-	Write failing tests for each player strategy to ensure they make valid and effective moves.
-	Implement the player strategy code.
- Integrate the player strategies into the game simulation while maintaining object-oriented design principles.

## Phase 3: Simulation and Comparison (1 week)

Using the class structures from **Phase 1** and **Phase 2**, to write a simulation and complete an experiment comparing your chosen strategies.

**Tasks:**
- Identify data that will provide you with insight into what is the best strategy.  All
simulations should report win rate, but you should add data that is appropriate for your game such as: currency collection, 
resource acquisition, average moves etc. to provide insight into what is happening in your simulation.
- Write a simulation class called SimulationExperiment which runs a simulation experiment where it plays
a series of games.  This class should have a main method that will run the complete simulation experiment
that is appropriate for your game and chosen player strategies: 
  - If you have a 2-player game, you should pair each of the 3 strategies against each other.  You should run a minimum of 20 trials for 
  each pairing of these strategies.  More data may reveal more about how your strategies behave in different situations.
  - If you have a 3 or more-player game, you should run a minimum of 60 trials with all strategies in play.  You may 
  conduct more trials with more than 3 players combining different strategies which may reveal more about how your 
  strategies behave in different combinations.
  - Report human-readable, well-formatted, experimental data for use in analysis. 
- Collect and analyze data on strategy performance based on win rates and additional data you have identified.
- Document your findings and comparisons of the player strategies. From the data you should identify which strategy 
is best, justify that choice, and explain why one strategy does better than the others.

## Phase 4: Finalization, and Reporting (1 week) 

**Tasks:**
- All reporting should be completed in the file located at the same level as the _src_ directory named **REPORT.md**. Please
leave the headings in this file unchanged.  This file is in Git markdown, a very 
simple formatting language which can can read more about here: 
[Git Markdown Instructions](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax).
You can edit this file in IntelliJ alongside the rest of your code.  The file has **Examples** of most markdown you would need
for this specific report. You may delete any _italicized_ text and any examples provided.
- All classes should be documented as specified in the Documentation Requirements below.
- All classes should testable as specified in Testing Requirements described below.
- Prepare a report that contains the following (word counts are maximums): 
  - A summary description of the game that you chose.  This should include the name of the game, the goal of the 
  game and a brief description of the key objects in the game.  Please provide a link to the game and/or its rules online. (100 words)
  - For each of 3 strategies you implemented, name the strategy, and then description of it (100 words each) 
  - A description of your procedure for running the experiment on your code. This should include information 
  regarding the setup of the experiment in terms of what it runs and how it compares the player strategies, 
  the number of trials the experiment and what data was collected. (250 words)
  - A presentation on the results of your simulation of the strategies in table(s) or appropriate graphic(s) with 
  a short summary. (250 words)
  - An interpretation of your data explaining why one strategy is better than the other supported with data 
  from your experiment. (500 words)
  - A reflection on your experiences with generative AI during this project.  Where did you use it, how did it help, 
  where did it hinder your development (500 words)?  

# Technical Requirements

## Code Requirements
- All code should be in the Java programming language using Java 17 or higher using Java programming style conventions.
- Your code should demonstrate good application of the principles of modularity, coupling, cohesion, and encapsulation within your object architecture.
- Data types and data structures should be appropriately chosen.  You may use classes from the Java Collections Framework (JCF) if appropriate.  Most commonly this will be ArrayList, Queue, Stack and PriorityQueue. 
- You should have a  SimulationExperiment class which contains a runnable main program that reproduces your experiment.
- You may use exceptions to manage failure conditions, which will simplify aspects of coding and testing, however this is
not a marked component.

## Testing Requirements
- You should use the provided test harness which applies JUnit5 via Gradle.  Your classes should go in the main subdirectory
while the tests for those classes should be in the test subdirectory.  There is an example of a simple class demonstrating
how one can implement the tests.  Tests must all run by calling _gradle test_.
- You should use JUnit5 assertion methods for your tests like those demonstrated in lab.
- All test methods should include a comment describing what you are testing and the key test cases in a short Javadoc string.

## Documentation Requirements
- You should write in a self-documenting code style which uses block commenting appropriately.
- All code should be documented with appropriate, descriptive docstrings and block level commenting to understand what the code does.
- You should follow all conventions of Javadoc strings including a description of the method purpose, parameters and return values.
- You should generate a Javadoc reference for your codebase.  This is done in IntelliJ through the Tools menu. Please put the Javadoc 
reference for your codebase in the provided _javadoc_ directory which is at the same level as your _src_ project in the directory tree. 

# Rubric

The following is the rubric of where marks will be awarded in this project.  

## Rubric Categories
- **Game Design (20 marks):** Average projects will produce an implementation that accurately 
represents the board game chosen by the group using principles of cohesion to have meaningful representation of objects, reduce 
coupling between classes to ensure future extension, and will be fully encapsulated for future compatibility and evolution. Excellent solutions 
will have exceptional quality in the above and/or use advanced features in Java such as abstract 
classes or interfaces appropriately. It is strongly recommended you document design choices in your class/method header Javadoc 
documentation.
- **Strategy Design (10 marks):** Average OOD will have all strategies implemented, provide an object structure for players 
playing the game with a given strategy, and follow good principles of encapsulation and modularity.  Excellent solutions will
use Java advanced features cleverly. It is strongly recommended you document design choices in your class/method header Javadoc
documentation.
- **Testing (10 marks):** Average TDD will have tests for the majority of classes and coverage of most branches.  Excellent
  solutions will have very high branch coverage.
- **Experiment (10 marks):** Average experiments will implement the simulation as specified, choose and describe 
the data used in analysis, accurately report procedure and results and a meaningful analysis of the outcome of their experiment. 
Excellent experiments will have robust and well justified measures, deep analysis and reflect on the lessons learned from the experiment.
- **Code Documentation (5 marks):** Average classes will be self-documenting code with well-formed with Javadoc comments for the
majority of methods and good block level comments.  Excellent documentation will do all of the above throughout all
of their project.
- **Reflection (5 marks):** Average reflections will engage with the provided prompts to have descriptions and reasoning 
regarding their application of AI.  Excellent reflections will take into account context in which events occur, 
questions assumption that were made at the time, describes alternatives, thinks about consequences of decisions/actions 
on others, and engages in reflective skepticism.

## Potential Bonus - 5% of Course Final Mark
There are a potential bonus that may be awarded at the discretion of the marker/instructor.  Up to 5% could be
added to your final mark to a maximum of 100% in the course.  Examples of where this may be awarded
is as follows:
- Complex class structure due to choosing a very complex game which is good or excellent in its design. 
- Advanced use of Java features such as functional interfaces, abstract classes above or beyond what might
be expected at the second year level.
- Advanced use of algorithms in the strategies for playing the game.
- Advanced use of Exceptions to manage different aspects of the code beyond the second year level.
- Insightful reflections on the use of generative AI above and beyond what is expected in the criteria.

If you have one of the above components, or another aspect you would like considered, please provide a description and
why you think it should be considered in the appropriate section of your report.  It is recommended that you complete the 
requirements of the project and commit a completed project before trying to add bonus material.

# Submission
All submission will be done through the Github Classroom.  Your team will accept the project, similar to how you have
accepted lab assignments.  You will commit your code, and all code in the repository at the submission date will be
marked. 

As a note, it is expected that students will be regularly be committing code to the Github repository.  Late submissions
due to technical issues will not normally be accepted. 

# Team Dynamics
For those working in a pair, it is expected that each individual will contribute in meaningful and substantive ways to
the project.  If a complaint is raised by one or both of the members of a working pair, or during final marking, the 
instructor will examine  the Github logs as evidence regarding contribution. This does not mean that each team member must have the same number of commits, but both members should be pushing code to the repository.  At the discretion of the instructor, under performing members will have deductions associated with their contribution, up to and including 0 for the project for a complete absence of 
meaningful and substantive contributions.  

If there are serious problems arise in multiple groups, the instructor may introduce a short survey halfway through 
the project period regarding relative contributions.  If this occurs, it will be announced through Moodle.

# Academic Misconduct

## Academic Integrity Statement

In your project package for this project you will include a text file in the project folder, at the same
folder level as _src_ named **INTEGRITY.md**. 

**Please read this file carefully and update it to have your names and student numbers.  Students who
do not complete this statement will have a deduction of 10% from the project mark**.

## Academic Integrity and Generative AI
It is clear from this project description that you are permitted to use generative AI as part of your toolset.  However,
this does not mean that the rest of UPEI Academic Regulation 20 is not applicable to this assignment.  It is expected that
you will abide by the rules regarding plagiarism, misrepresentation, solicitation and fabrication of results, as well as any
other forms of academic misconduct.

As a caution, groups working on the same game (which will likely happen) will naturally have variation in how they are implemented.
It is highly improbable that two groups would have near identical implementations with the number of choices in this project. Further,
it is highly improbable that two groups would have near identical implementations just because they used the same generative AI
tools.  Such a claim would not be accepted in an academic misconduct investigation.  A reminder that as per the syllabus, a team 
that has academic misconduct upheld will lose receive 0, losing 15% of the final mark, and an additional 15% from their final mark.
Individuals with previous academic misconduct allegations upheld will further have a maximum mark of 50% possible in
the course.




