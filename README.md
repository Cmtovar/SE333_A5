Assignment 5 - Unit, Mocking, and Integration Testing

Project Overview 
This project regards automated testing with GitHub Actions for a bookstore management system.

It includes:
[1] unit tests with mocked database interactions (seen in BarnesAndNobleTest.java),
[2] integration tests with real database interactions (seen in AmazonIntegrationTest.java),
[3] automated CI/CD pipelines with static analysis (seen in github actions tab of the repo).



Part 1 - BarnesAndNoble Tests
I created specification based tests for the getPriceForCart() method. 

The prompt for this part seemed very open-ended. 
I aimed to handle null input, empty order, sufficent stock, and insufficient stock. 

I used Mockito to mock the BookDatabase and BuyBookProcess dependencies. 
I added a structural test that makes sure mutliple books of different prices and quantities are processed in the for loop. 

Part 2 - GitHub Actions Workflow  
