# Interval
Repo for CS 160 team project. Team Black Panther (Hugo, Jade, Luksawee, Toretto)
Old releases can be found here: https://github.com/ProfHugo/2021-fall-cs160-team-black-panther/releases

## About Interval
#### For people who are health-conscious and goal-oriented
#### Who need help managing their tasks and progress,
#### Interval is a mobile application
#### That provides activity planning and reminders based on designated availability, allowing the user to manage lifestyle routines, health goals, appointments, and tasks.
#### Unlike other services that focus on one aspect such as reminders, alarms, or task scheduling,
#### Our product provides useful smart-scheduler services including automatic scheduling and progress tracking at little to no user cost.

## Project Structure Overview:
#### client: Contains the Android Studio project repository of the application client.
#### database: Contains MySQL dump of currently used database schemas.
#### docker: Contains a download link to the latest Docker image of the backend server.
#### server: Contains the Gradle project repository of the backend server.

## How to Setup Dev Environment (Android Client)
#### Prerequisite: Already cloned git repository to local machine.
1. Download and install the latest version of Android Studio. Run it. 
2. Go to Tools -> SDK Manager and ensure that Android 11 (SDK 30) is installed)
3. In the same window, navigate to SDK Tools and install Android Emulator, Android SDK Platform-Tools, Google USB Driver, and Google Web Driver
4. Install AEHD for AMD Processors (if local machine uses an AMD CPU) or Intel x86 Emulator Accelerator (if local machine uses an Intel CPU)
5. Navigate to Tools -> AVD Manager and follow the instructions to create a virtual device.
6. To open the project, navigate to File -> Open and find the “client” folder inside the cloned repository. Highlight it and click ok.
7. You are now ready to contribute to the Interval Android Application Client.

## How to Setup Dev Environment (Backend Server, Eclipse)
#### Prerequisite: Already cloned git repository to local machine.
1. Download and install the latest version of Eclipse. Run it.
2. Navigate to File -> Import -> Gradle/Existing Gradle Project, then locate ..\2021-fall-cs160-team-black-panther\server and input the path as the Project root directory.
3. Follow directions to finish importing the backend server into Eclipse.
4. You are now ready to contribute to the Interval Backend Server.

## How to Setup Dev Environment (MySQL Database)
1. Download and install MySQL80 (including MySQL Workbench).
2. Create a new MySQL connection named “Interval Test” and add a username = usertest and password = password
3. Enter the connection using Workbench.
4. Navigate to Server -> Data Import, then locate the appropriate dump folder (ex: ../2021-fall-cs160-team-black-panther\database\current dump) and click start import.
5. The database schema should now be loaded. You are now ready to contribute to the Interval Backend MySQL Database.

## Communication/Support
#### For technical support or inquiry, write to here https://github.com/Team-Black-Panther/2021-fall-cs160-team-black-panther/discussions/58.
#### If you're joining the development team, send an email to interval_tbp@gmail.com with your Discord ID attached to be granted access to our team chat.

## Opening an Issue
#### Github issues should be reserved for bug reports and feature requests only.
##### Example Title 1: Feature: Adding Recurring Reminder Option
##### Example Title 2: Bug: Disappearing Event Entries After Forced Shutdown of the Client

## Creating/Consuming a Pull Request
#### In no circumstances should the main branch be modified directly.
#### To contribute, create a new branch from main and modify that branch.
#### Once the corresponding feature/bugfix is ready, create a pull request from that branch to main.
#### Two team members who have not worked on the branch previously must review and approve the pull request before it can be merged.
#### Once the pull request is merged, delete the branch.
