# PlivoAPITestPRoject

Plivo API Test Project

PreSetup JDK 8 or above Any IDE preferrably Intellij

Project Structure: 
A Util class which contain the common rest util part where all CRUD operations are generalized.
Properties class where all the test related contants are stored 
Tests class which consists of our actual tests 
Common class consists of commom methods which is used all over the project
testng.xml file

To run the tests execute below command from your project location in your terminal: 
mvn test -DsuiteFile=testng.xml 
or it can be run directly from IDE as well

