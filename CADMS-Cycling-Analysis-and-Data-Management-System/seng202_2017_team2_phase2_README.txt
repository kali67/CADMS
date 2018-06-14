This is how to deploy the application know as CADMS. 

When downloaded, a zip file will be recieved by the user. This zip file will contain the jar file required to run the program.
To run the jar file, extract the zip file in the place of your choice.
For windows:
   -Java 8 will be required to be installed on the device.
   -Double click on the jar file and tha application will load.
For linux:
   -Navigate to where the extracted files are located in the terminal.
   -Type java -jar seng202_2017_team2_phase2, press enter and the applciation will load.

To import the project to intelliJ
 -Open intellij
 -File->New -> project from existing sources
 -Navigate to SENG202Team2 folder and select ok
 -Select import project from external model, the choose maven
 -Use automatically generated options, slect next until finish pops up. Select finish, then overwrite .idea if prompted to.

 -When imported:
    -Right click on sqlite-jdbc-3.20.0.jar
    -Select add as library. Select ok
    -Right click GMapsFx-2.12.0.jar
    -select add as library. Select ok
    -Right click on opencsv-4.0.jar
    -select add as library. Select ok
 -The source code should now be available to be opened and ran at the users leisure.
