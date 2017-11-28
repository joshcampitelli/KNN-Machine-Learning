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

Known Issues:					
   - Predict has a bug where the list being passed
   	was empty, working through that bug until 
	deadline approached

The Map Ahead:	
  1) Replace CartesianFeature with a ComplexFeature
  2) Saving/Loading data
  3) Bug fix on the Predict
