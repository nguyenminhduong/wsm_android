Feature: Click
  Perform click icon menu, icon notification,icon logout,icon avatar and item on navigation

  Scenario: Click icon menu
    Given I have a MainScreen
    When I click icon menu
    Then I should see all menu item on drawer

  Scenario: Click item on navigation
    Given  have a MainScreen
    When I click icon menu
    And I click item on navigation
    And That item is selected
    Then I should see fragment of that item

  Scenario: Click icon notification
    Given I have a MainScreen
    When I click icon notification
    Then I should see a list notifications

  Scenario: Click icon logout
    Given I have a MainScreen
    When I click icon menu
    And I click logout
    Then I should see screen Login

  Scenario: Click icon avatar
    Given I have a MainScreen
    When I click icon menu
    And I click avatar
    Then I should see screen profile of user
