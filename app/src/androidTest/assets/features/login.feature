Feature: Search User GitHub
     Perform search on keyword and limit number are inputted

     Scenario Outline: Input email and password in wrong format
        Given I have a Login Screen
        When I input keyword <keyword>
        And I input limit number <limit>
        Then I should see error on the <view>

     Examples:
        | keyword | limit | view    |
        | shit    | 123   | keyword |
