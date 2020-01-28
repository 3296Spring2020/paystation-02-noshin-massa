# paystation-02-noshin-massa
paystation-02-noshin-massa created by GitHub Classroom

## Meeting Requirements
The requirements for this lab was to learn to work with your team members by using git through github. We were supposed to take turns completing the code and for each commit I pushed it to git and pulled code after my partner completed his part. Everything that was asked was implemented and there was no additional improvements.
## Sarah's specific contribution
I have contributed the cancel method and the reset method in the PayStationImpl.java file. In the PayStationImplTest.java file I worked on the last four testing requirements. These requirements verified that the map doesn't contain a key for a coin not entered,  verified that the map contains a mixture of coins,  verified  that cancel clears the map,  and verified that buying time clears the map.

## Jared's specific contribution
Essentially we broke the requirements (tests to be written) into two halves, I took the first four, and Sarah took the second.  So, I implemented the `empty()` method, and added a new variable in the `PayStationImpl.java` file for tracking the machine's profits.  I also ensured canceled entries didn't contribute to profits by only re-calculating profits after a `buy()` call is executed.  