Feature: RequestOff

#  Link PR: https://github.com/framgia/wsm_android/pull/44
#  Off have salary - Company pay

  Scenario Outline: Input in wrong format Off have salary - Company pay
    Given I have a Create request off Screen
    When I input project name <project>
    And I input position name <position>
    And I choose Off have salary - Company pay <off type>
    And I input number day off annual leave <annual leave>
    And I input number day off leave for child marriage <leave for child marriage>
    And I input number day off funeral leave <funeral leave>
    And I input time start <from>
    And I input time end <to>
    And I input reason <reason>
    And I click save button
    Then I should see error on the <view>

    Examples:
      | project     | position | off type                      | annual leave | leave for child marriage | funeral leave | from          | to            | reason | view         |
      | WSM Android | Leader   | Off have salary - Company pay | 1            |                          |               | 07/06/2017 am | 07/06/2017 am | Rest   | to           |
      | WSM Android | Leader   | Off have salary - Company pay | 1            |                          |               |               | 07/06/2017 am | Rest   | from         |
      | WSM Android | Leader   | Off have salary - Company pay | 1            |                          |               | 07/06/2017 am |               | Rest   | to           |
      | WSM Android | Leader   | Off have salary - Company pay | 1            |                          |               | 07/06/2017 am | 07/06/2017 pm |        | reason       |
      | WSM Android | Leader   | Off have salary - Company pay | 1            | 1                        | 1             | 05/06/2017 am | 07/06/2017 pm | Rest   | from         |
      | WSM Android | Leader   | Off have salary - Company pay | 1            | 1                        | 1             | 07/06/2017 am | 04/06/2017 pm | Rest   | to           |
      | WSM Android | Leader   | Off have salary - Company pay |              |                          |               | 07/06/2017 am | 07/06/2017 am | Rest   | annual leave |

  Scenario Outline: Input in right format Off have salary - Company pay
    Given I have a Create request off Screen
    When I input project name <WSM Android>
    And I input position name <Leader>
    And I choose Off have salary - Company pay
    And I input number day off annual leave <1>
    And I input number day off leave for child marriage <1>
    And I input number day off funeral leave <1>
    And I input time start <07/06/2017 am>
    And I input time end <09/06/2017 pm>
    And I input reason <Rest>
    And I click save button
    Then I should see confirm request off screen
    Examples:
      | WSM Android | Leader | 1 | 07/06/2017 am | 09/06/2017 pm | Rest |
      | WSM Android | Leader | 1 | 07/06/2017 am | 09/06/2017 pm | Rest |

#  Off have salary - Insurance coverage

  Scenario Outline: Input in wrong format Off have salary - Insurance coverage
    Given I have a Create request off Screen
    When I input project name <project>
    And I input position name <position>
    And I choose Off have salary - Insurance coverage
    And I input number day off Leave for care of sick child <leave for care of sick child>
    And I input number day off Pregnancy examination leave <pregnancy examination>
    And I input number day off Sick leave <sick>
    And I input number day off Miscarriage leave <miscarriage>
    And I input number day off Maternity leave <maternity>
    And I input number day off Wife’s labor leave <wife’s labor>
    And I input time start <from>
    And I input time end <to>
    And I input reason <reason>
    And I click save button
    Then I should see error on the <view>

    Examples:
      | project     | position | leave for care of sick child | pregnancy examination | sick | miscarriage | maternity | wife’s labor | from          | to            | reason | view                         |
      | WSM Android | Leader   | 1                            |                       |      |             |           |              | 07/06/2017 am | 07/06/2017 pm |        | reason                       |
      | WSM Android | Leader   | 1                            |                       |      |             |           |              |               | 07/06/2017 am | Rest   | from                         |
      | WSM Android | Leader   | 1                            |                       |      |             |           |              | 07/06/2017 am |               | Rest   | to                           |
      | WSM Android | Leader   | 1                            |                       |      |             |           |              | 06/06/2017 am | 06/06/2017 pm | Rest   | from                         |
      | WSM Android | Leader   | 1                            | 1                     | 1    |             |           |              | 07/06/2017 am | 09/06/2017 am | Rest   | to                           |
      | WSM Android | Leader   |                              |                       |      |             |           |              | 07/06/2017 am | 07/06/2017 am | Rest   | leave for care of sick child |
      | WSM Android | Leader   | 1                            |                       |      |             |           |              | 07/06/2017 am | 05/06/2017 am | Rest   | to                           |

  Scenario Outline: Input in right format Off have salary - Insurance coverage
    Given I have a Create request off Screen
    When I input project name <WSM Android>
    And I input position name <Leader>
    And I choose Off have salary - Insurance coverage
    And I input number day off Leave for care of sick child <1>
    And I input number day off Pregnancy examination leave <0>
    And I input number day off Sick leave <0>
    And I input number day off Miscarriage leave <0>
    And I input number day off Maternity leave <0>
    And I input number day off Wife’s labor leave <0>
    And I input time start <07/06/2017 am>
    And I input time end <07/06/2017 pm>
    And I input reason <Rest>
    And I click save button
    Then I should see confirm request off screen
    Examples:
      | WSM Android | Leader | 1 | 0 | 07/06/2017 am | 07/06/2017 pm | Rest |
      | WSM Android | Leader | 1 | 0 | 07/06/2017 am | 07/06/2017 pm | Rest |

    #  Off no salary

  Scenario Outline: Input in wrong format Off have salary - Insurance coverage
    Given I have a Create request off Screen
    When I input project name <project>
    And I input position name <position>
    And I choose Off no salary
    And I input time start <from>
    And I input time end <to>
    And I input reason <reason>
    And I click save button
    Then I should see error on the <view>

    Examples:
      | project     | position | from          | to            | reason | view   |
      | WSM Android | Leader   | 07/06/2017 am | 07/06/2017 pm |        | reason |
      | WSM Android | Leader   |               | 07/06/2017 pm | Rest   | from   |
      | WSM Android | Leader   | 07/06/2017 am |               | Rest   | to     |
      | WSM Android | Leader   |               |               | Rest   | from   |
      | WSM Android | Leader   | 07/06/2017 am | 05/06/2017 pm | Rest   | to     |


  Scenario Outline: Input in right format Off no salary
    Given I have a Create request off Screen
    When I input project name <WSM Android>
    And I input position name <Leader>
    And I choose Off no salary
    And I input time start <07/06/2017 am>
    And I input time end <07/06/2017 pm>
    And I input reason <Rest>
    And I click save button
    Then I should see confirm request off screen
    Examples:
      | WSM Android | Leader | 07/06/2017 am | 07/06/2017 pm | Rest |
      | WSM Android | Leader | 07/06/2017 am | 07/06/2017 pm | Rest |
