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
[![Build Status](https://github.com/Cmtovar/SE333_A5/actions/workflows/SE333_CI.yml/badge.svg)](https://github.com/Cmtovar/SE333_A5/actions)

I configured the CI/CD pipeline using Github Actions. See above for the build status. 
I used referred to the [GitHub docs](https://docs.github.com/en/actions/how-tos/monitor-workflows/add-a-status-badge) to find my badge.svg url. 
This workflow will trigger automatically whenever there's a push to the main branch. 
It includes checkstyle static analysis, executes all tests, and reports coverage using JaCoCo. 
Checkstlye and JaCoCo reports are viewable as artifacts at the github actions page. 

Part 3 - Amazon Integration and Unit Tests

Unit Tests (AmazonUnitTest.java)
I create specification based tests for the Cost package classes 

These included: 
[1] Delivery Price - Tested boundary values (0, 1, 3, 4, 10, 11) for item quantity to verify correct delivery fee tiers
[2] ExtraCostForElectronics - Tested with and without electronic items in the cart to verify the $7.50 fee
[3] RegularCost - Tested price calculations with multiple items and quantities per item

Unit tests achieved 87% branch coverage on the Cost package. No structural tests were included. 

Integration Tests (AmazonIntegartionTest.java)
I created integration tests using the real Database. 
These were some simple tests that checks situations with empty carts, simple orders, and orders containing electronics. 
Verified that $0 total cost occurs for an empty cart. 
Tested a cart containing multiple items (simple order; no electronics), and verified the correct calculation with all fees included
Tested a cart containing an electronic item, and verified that the electronics fee is applied.

I added one structural-based test for the database close method since it didn't appear to have coverage in the JaCoCo report. 
This improved coverage. 

These integration tests used @BeforeAll to initialize the databse and used @AfterEach to reset the database between tests. 
This isolated each test without needing to create a new database each time. 
 
