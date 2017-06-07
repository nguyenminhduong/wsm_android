Feature: RequestOvertime

  Scenario Outline: Input wrong format
    Given I have a Create request overtime screen
    When I input project name <project>
    And I input time start <from>
    And I input time end <to>
    And I input reason <reason>
    Then I should see error <message>

    Examples:
      | project     | from             | to               | reason   | message                                                      |
      |             | 2017/05/06 08:00 | 2017/05/06 08:00 | Deadline | Project can not be blank                                     |
      | WSM Android |                  | 2017/05/06 08:00 | Deadline | From time not be blank                                       |
      | WSM Android |                  |                  | Deadline | End time not be blank                                        |
      | WSM Android | 2017/05/06 18:00 | 2017/05/06 20:00 |          | Reason not be blank                                          |
      | WSM Android | 2017/05/05 10:00 | 2017/05/05 12:00 | Deadline | Time request ot invalid: it is not in time period request ot |
      | WSM Android | 2017/05/06 18:00 | 2017/05/06 17:00 | Deadline | End time is less than start time                             |
      | WSM Android | 2017/05/06 18:00 | 2017/05/06 18:00 | Deadline | End time is less than start time                             |
      | WSM Android | 2017/05/06 18:00 | 2017/05/07 20:00 | Deadline | End time is less than start time                             |
      | WSM Android | 2017/05/06 17:00 | 2017/05/07 18:00 | Deadline | Time request ot invalid: it is not in time period request ot |

  Scenario: Input right format
    Given I have a Create request overtime screen
    When I input project name <WSM Android>
    And I input time start <2017/05/06 18:00>
    And I input time end <2017/05/06 20:00>
    And I input reason <Deadline>
    And I click button create
    Then I should see screen confirm request overtime

