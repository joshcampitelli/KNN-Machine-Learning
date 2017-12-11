SYSC 3110 Turn_Down_A_Bower Group: Deliverable 2
------------------------------------------------
Josh Campitelli 	101010050
Ryan Ribeiro 		100997936
Ethan Morrill 		100956097
Logan MacGillivray	100997792
------------------------------------------------

In this deliverable, we have provided the base  
functioning for the machine learning system.    
						
The program is run from the main class, where   
two MachineLearning objects are created.  Each  
MachineLearning object has a Storage object,    
which holds all of our GenericMetric objects.   
						
The main class will then add GenericMetric      
objects to each MachineLearning object.  This   
allows the program to "learn" how to predict a  
price for the particular case.  		

-----------------------------------------------
UPDATE
-----------------------------------------------
Now the program has a working GUI.  The GUI	
asks users to enter specifc data in order to 
create the problems. This GUI consists of three
JFrames.

The first JFrame has all the problems in a JList.
From there, new problems will be created, which 
ask the user for what kind of metrics they want
measuring their features.

Once a problem has been created, the user will
be able to access the ProblemWindow.  This will
allow the user to create instances of the 
problem where they can specify what features are 
to be added. 

-----------------------------------------------
UPDATE 2
-----------------------------------------------
The program now supports complex features.  The
program is able to support multiple different  
scenarios.

-----------------------------------------------
UPDATE 3 - The Final Update
-----------------------------------------------
SAVING IS NOW A THING!!! The program allows a  
user to save an instance of MachineLearning in 
a .jerl file.  They can then reopen the program
and open any .jerl file to the program.  There
is also error handling in incorrect filetypes 
and files not existing.

Users cannot build complex features; however, a
complex feature can be used whenever a complex 
metric is selected.  If we had more time (as we
realised this last minute) we would have set up
a recursive way of creating complex metrics that
would take on the appearnace of starting a new
instance of MachineLearning.  The windows would
appear in the same order, and the distance of 
the complex metric would be a sum of the distances
of its "simpler" metrics.

Unit tests have also been updated to support new
additions. 

Known Issues:					
   - If the user tries to "Learn Instance" and 
   	then cancel, they will be prompted for a
	value again
   - Unable to add complex metrics made from 
   	"simpler" metrics

Moving Forward:
 Continue to work on (or overhaul) the project 
 	after the semester is over.
