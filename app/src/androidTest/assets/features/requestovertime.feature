Feature: RequestOvertime

  Scenario Outline: Input wrong format
    Given I have a Create request overtime screen
    When I input project name <project>
    And I input time start <from>
    And I input time end <to>
    And I input reason <reason>
    Then I should see error on the <view>

    Examples:
      | project     | from             | to               | reason   | view    |
      |             | 2017/05/06 08:00 | 2017/05/06 08:00 | Deadline | project |
      | WSM Android |                  | 2017/05/06 08:00 | Deadline | from    |
      | WSM Android | 2017/05/06 18:00 | 2017/05/06 20:00 |          | reason  |
      | WSM Android | 2017/05/05 10:00 | 2017/05/05 12:00 | Deadline | from    |
      | WSM Android | 2017/05/06 18:00 | 2017/05/06 17:00 | Deadline | to      |
      | WSM Android | 2017/05/06 18:00 | 2017/05/06 18:00 | Deadline | to      |
      | WSM Android | 2017/05/06 18:00 | 2017/05/07 18:00 | Deadline | to      |
      | WSM Android | 2017/05/06 17:00 | 2017/05/07 18:00 | Deadline | from    |

  Scenario: Input right format
    Given I have a Create request overtime screen
    When I input project name <WSM Android>
    And I input time start <2017/05/06 18:00>
    And I input time end <2017/05/06 20:00>
    And I input reason <Deadline>
    And I click button create
    Then I should see screen confirm request overtime

