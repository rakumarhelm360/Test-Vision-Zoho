Feature: DummyFailure
Background: User logged in with valid credentials
		Given Open Browser and navigate to application URL

@DummyFailure
  Scenario: Print title, url Test
    Then Step1 go to
    Then Step2 verify
	  Then Step3 verify test 
	#  And Click on login button