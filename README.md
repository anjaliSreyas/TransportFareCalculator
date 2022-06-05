# TransportFareCalculator
How to run: 
Pull the repo into intelij or any other IDE, will ask to add kotlin in class path, do that.
Go to Application class and run the main method.
The output is available in output.csv file and input can provided in TapInfo.csv file.


Assumptions made to solve the problem: 
1. The input csv will not have null values, or values in in correct format.
2. The errors does not required to be propogated to the user.

Improvements that can be made: 
1. In real world scenario, we would expect a stream of tap on and tap offs instead a list and the inteface can be modified to accept that.
2. Make the input csv reader class generic.
3. Have null checks for the input feilds.
