Feature: Login Test

  @desktop
  Scenario: Login with valid credentials
    Given Navigate to "https://getir.com/"
    Then Enter "5555555555" to phone number input on website
    Then Click "Continue" button on website

  @mobile
  Scenario: Login with valid credentials
    Given Click "Get Started" button when app is opened
    When popup is displayed dismiss it
    Then Click "Continue with phone" button
    Then Enter "5555555555" to phone number input on app
    Then Click "Continue" button on app

