Feature: Login
#     Perform error on email and password are inputted

  Scenario Outline: Not input anything and click login
    Given I have a Login Screen
    When  I click login
    Then I should see error when missing field <view>

    Examples:
      | view     |
      | email    |
      | password |

  Scenario: Only input email in wrong format
    Given I have a Login Screen
    When I input email vinh
    Then I should see error on the email

  Scenario: Input email in wrong format and input password
    Given I have a Login Screen
    When I input email vinh
    When I input password 123
    Then I should see error on the email

  Scenario Outline: Input email in wrong format and click login
    Given I have a Login Screen
    When I input email <email>
    And I click login
    Then I should see error on the <view>

    Examples:
      | email | view     |
      | vinh  | email    |
      | vinh  | password |

  Scenario: Input email in correct format
    Given I have a Login Screen
    When I input email 123@gmail.com
    Then I can't see any error on the correct email email

  Scenario: Only input email in correct format and click login
    Given I have a Login Screen
    When I input email 123@gmail.com
    And I click login
    Then I should see error on the password

  Scenario: Only input password and click login
    Given I have a Login Screen
    When I input password 123
    And I click login
    Then I should see error when missing field email
