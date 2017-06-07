Feature: Create Request leave
  # Link PR: https://github.com/framgia/wsm_android/pull/46

  Scenario Outline : Input wrong format In Late(M),In Late(A),Leave early(M),Leave early(A),Leave out
    Given I have a Create request leave screen
    When I input project name <project>
    And I select leave type <type>
    And I input register time from<register_from>
    And I input register time to <register_to>
    And I input compensation time from <compensation_from>
    And I input compensation time to <compensation_to>
    And I input reason <reason>
    Then I should see message <error>

    Examples:
      | project | type           | register_from    | register_to      | compensation_from | compensation_to  | reason | error                                                   |
      | WSM     | In Late(M)     | 2017/05/06 07:00 |                  |                   |                  |        | This is form request late, time in is incorrect!        |
      | WSM     | In Late(M)     | 2017/05/06 10:00 |                  |                   |                  |        | Your amount time can't greater than max alllow time     |
      | WSM     | In Late(M)     | 2017/05/06 13:00 |                  |                   |                  |        | Check in time must be in morning shift                  |
      | WSM     | In Late(M)     | 2017/04/06 08:15 |                  |                   |                  |        | Form overdue                                            |
      | WSM     | In Late(M)     | 2017/05/06 08:15 |                  | 2017/05/06 08:15  |                  |        | Your compensation time can't in working time of company |
      | WSM     | In Late(M)     | 2017/05/06 08:15 |                  | 2017/05/06 12:00  |                  |        | Your time can't in lunch break time of company          |
      | WSM     | In Late(M)     | 2017/05/06 08:45 |                  | 2017/05/06 23:15  |                  |        | Time must be is in a day                                |
      | WSM     | In Late(M)     | 2017/08/06 08:15 |                  | 2017/05/06 17:00  | 2017/05/06 17:30 |        | Compensation time isn't in the past                     |
      | WSM     | In Late(M)     | 2017/05/06 08:15 |                  | 2017/05/06 17:00  | 2017/05/06 17:30 |        | Please fill in reason box                               |

      | WSM     | In Late(A)     | 2017/05/06 07:00 |                  |                   |                  |        | Check in time must be in afternoon shift                |
      | WSM     | In Late(A)     | 2017/05/06 15:00 |                  |                   |                  |        | Your amount time can't greater than max alllow time     |
      | WSM     | In Late(A)     | 2017/05/06 17:00 |                  |                   |                  |        | Check in time must be in afternoon shift                |
      | WSM     | In Late(A)     | 2017/04/06 13:15 |                  |                   |                  |        | Form overdue                                            |
      | WSM     | In Late(A)     | 2017/05/06 13:15 |                  | 2017/05/06 08:15  |                  |        | Your compensation time can't in working time of company |
      | WSM     | In Late(A)     | 2017/05/06 13:15 |                  | 2017/05/06 12:00  |                  |        | Your time can't in lunch break time of company          |
      | WSM     | In Late(A)     | 2017/05/06 13:45 |                  | 2017/05/06 23:15  |                  |        | Time must be is in a day                                |
      | WSM     | In Late(A)     | 2017/08/06 13:15 |                  | 2017/05/06 17:00  | 2017/05/06 17:30 |        | Compensation time isn't in the past                     |
      | WSM     | In Late(A)     | 2017/05/06 13:15 |                  | 2017/05/06 17:00  | 2017/05/06 17:30 |        | Please fill in reason box                               |

      | WSM     | Leave early(M) | 2017/05/06 07:00 |                  |                   |                  |        | Your time can't be sooner than time in of company       |
      | WSM     | Leave early(M) | 2017/05/06 09:00 |                  |                   |                  |        | Your amount time can't greater than max alllow time     |
      | WSM     | Leave early(M) | 2017/05/06 12:00 |                  |                   |                  |        | Your time can't in lunch break time of company          |
      | WSM     | Leave early(M) | 2017/05/06 14:00 |                  |                   |                  |        | Check out time must be in morning shift                 |
      | WSM     | Leave early(M) | 2017/05/06 17:00 |                  |                   |                  |        | Your time can't be later than time out company          |
      | WSM     | Leave early(M) | 2017/04/06 10:45 |                  |                   |                  |        | Form overdue                                            |
      | WSM     | Leave early(M) | 2017/05/06 10:45 |                  | 2017/05/06 08:15  |                  |        | Your compensation time can't in working time of company |
      | WSM     | Leave early(M) | 2017/05/06 10:45 |                  | 2017/05/06 12:00  |                  |        | Your time can't in lunch break time of company          |
      | WSM     | Leave early(M) | 2017/05/06 10:45 |                  | 2017/05/06 23:15  |                  |        | Time must be is in a day                                |
      | WSM     | Leave early(M) | 2017/08/06 10:45 |                  | 2017/05/06 17:00  | 2017/05/06 18:00 |        | Compensation time isn't in the past                     |
      | WSM     | Leave early(M) | 2017/05/06 10:45 |                  | 2017/05/06 17:00  | 2017/05/06 18:00 |        | Please fill in reason box                               |

      | WSM     | Leave early(A) | 2017/05/06 07:00 |                  |                   |                  |        | Your time can't be sooner than time in of company       |
      | WSM     | Leave early(A) | 2017/05/06 09:00 |                  |                   |                  |        | Check out time must be in afternoon shift               |
      | WSM     | Leave early(A) | 2017/05/06 12:00 |                  |                   |                  |        | Your time can't in lunch break time of company          |
      | WSM     | Leave early(A) | 2017/05/06 14:00 |                  |                   |                  |        | Your amount time can't greater than max alllow time     |
      | WSM     | Leave early(A) | 2017/05/06 17:00 |                  |                   |                  |        | Your time can't be later than time out company          |
      | WSM     | Leave early(A) | 2017/04/06 15:45 |                  |                   |                  |        | Form overdue                                            |
      | WSM     | Leave early(A) | 2017/05/06 15:45 |                  | 2017/05/06 08:15  |                  |        | Your compensation time can't in working time of company |
      | WSM     | Leave early(A) | 2017/05/06 15:45 |                  | 2017/05/06 12:00  |                  |        | Your time can't in lunch break time of company          |
      | WSM     | Leave early(A) | 2017/05/06 15:45 |                  | 2017/05/06 23:15  |                  |        | Time must be is in a day                                |
      | WSM     | Leave early(A) | 2017/08/06 15:45 |                  | 2017/05/06 17:00  | 2017/05/06 18:00 |        | Compensation time isn't in the past                     |
      | WSM     | Leave early(A) | 2017/05/06 15:45 |                  | 2017/05/06 17:00  | 2017/05/06 18:00 |        | Please fill in reason box                               |

      | WSM     | Leave out      | 2017/05/06 07:00 |                  |                   |                  |        | Your time can't be sooner than time in of company       |
      | WSM     | Leave out      | 2017/05/06 12:00 |                  |                   |                  |        | Your time can't in lunch break time of company          |
      | WSM     | Leave out      | 2017/05/06 17:00 |                  |                   |                  |        | Your time can't be later than time out company          |
      | WSM     | Leave out      | 2017/04/06 08:15 |                  |                   |                  |        | Form overdue                                            |
      | WSM     | Leave out      | 2017/05/06 08:15 | 2017/05/06 07:00 |                   |                  |        | Request time to cant greater than request time from     |
      | WSM     | Leave out      | 2017/05/06 08:15 | 2017/05/06 12:00 |                   |                  |        | Your time can't in lunch break time of company          |
      | WSM     | Leave out      | 2017/05/06 16:00 | 2017/05/06 17:30 |                   |                  |        | Your time can't be later than time out company          |
      | WSM     | Leave out      | 2017/05/06 08:15 | 2017/05/06 10:30 |                   |                  |        | Your amount time can't greater than max alllow time     |
      | WSM     | Leave out      | 2017/05/06 09:15 | 2017/05/06 08:15 |                   |                  |        | Request time to cant greater than request time from     |
      | WSM     | Leave out      | 2017/05/06 13:45 | 2017/05/06 14:45 | 2017/05/06 08:15  |                  |        | Your compensation time can't in working time of company |
      | WSM     | Leave out      | 2017/05/06 13:45 | 2017/05/06 14:45 | 2017/05/06 12:00  |                  |        | Your time can't in lunch break time of company          |
      | WSM     | Leave out      | 2017/05/06 13:45 | 2017/05/06 14:45 | 2017/05/06 23:30  |                  |        | Time must be is in a day                                |
      | WSM     | Leave out      | 2017/08/06 13:45 | 2017/05/06 14:45 | 2017/05/06 17:00  | 2017/05/06 18:00 |        | Compensation time isn't in the past                     |
      | WSM     | Leave out      | 2017/05/06 13:45 | 2017/05/06 14:45 | 2017/05/06 17:00  | 2017/05/06 18:00 |        | Please fill in reason box                               |

  Scenario Outline: Input right format In Late(M),In Late(A),Leave early(M),Leave early(A),Leave out
    Given I have a Create request leave screen
    When I input project name <project>
    And I select leave type <type>
    And I input register time from<register_from>
    And I input register time to <register_to>
    And I input compensation time from <compensation_from>
    And I input compensation time to <compensation_to>
    And I input reason <reason>
    Then I should see screen confirm request leave

    Examples:
      | project | type           | register_from    | register_to      | compensation_from | compensation_to  | reason   |
      | WSM     | In Late(M)     | 2017/05/06 08:15 |                  | 2017/05/06 17:00  | 2017/05/06 17:30 | Deadline |
      | WSM     | In Late(A)     | 2017/05/06 13:15 |                  | 2017/05/06 17:00  | 2017/05/06 17:30 | Deadline |
      | WSM     | Leave early(M) | 2017/05/06 10:45 |                  | 2017/05/06 17:00  | 2017/05/06 18:00 | Deadline |
      | WSM     | Leave early(A) | 2017/05/06 15:45 |                  | 2017/05/06 17:00  | 2017/05/06 18:00 | Deadline |
      | WSM     | Leave out      | 2017/05/06 13:45 | 2017/05/06 14:45 | 2017/05/06 17:00  | 2017/05/06 18:00 | Deadline |

  Scenario Outline : Input wrong format Forgot card(all day), Forgot to check in/check out(all day)
    Given I have a Create request leave screen
    When I input project name <project>
    And I select leave type <type>
    And I input register time from<register_from>
    And I input register time to <register_to>
    Then I should see message <error>

    Examples:
      | project | type                                  | register_from    | register_to      | error                                                               |
      | WSM     | Forgot card(all day)                  | 2017/06/06 13:00 |                  | The working time dose not fit to the request                        |
      | WSM     | Forgot card(all day)                  | 2017/06/06 09:00 | 2017/06/06 10:00 | The working time dose not fit to the request                        |
      | WSM     | Forgot card(all day)                  | 2017/06/10 09:00 |                  | Registration time is not greater than the current date (2017/06/06) |
      | WSM     | Forgot card(all day)                  | 2017/06/06 09:00 | 2017/06/09 14:00 | Time must be is in a day (2017/06/06)                               |
      | WSM     | Forgot card(all day)                  | 2017/06/06 09:00 | 2017/06/06 08:00 | Request time to cant greater than request time from                 |
      | WSM     | Forgot card(all day)                  | 2017/06/06 09:00 | 2017/06/04 14:00 | Request time to cant greater than request time from                 |

      | WSM     | Forgot to check in/check out(all day) | 2017/06/06 13:00 |                  | The working time dose not fit to the request                        |
      | WSM     | Forgot to check in/check out(all day) | 2017/05/06 09:00 |                  | Form overdue                                                        |
      | WSM     | Forgot to check in/check out(all day) | 2017/06/06 09:00 | 2017/06/06 10:00 | The working time dose not fit to the request                        |
      | WSM     | Forgot to check in/check out(all day) | 2017/06/07 09:00 |                  | Registration time is not greater than the current date (2017/06/06) |
      | WSM     | Forgot to check in/check out(all day) | 2017/06/06 09:00 | 2017/06/07 14:00 | Registration time is not greater than the current date (2017/06/06) |
      | WSM     | Forgot to check in/check out(all day) | 2017/06/06 09:00 | 2017/06/06 08:00 | Request time to cant greater than request time from                 |

  Scenario Outline : Input right format Forgot card(all day), Forgot to check in/check out(all day)
    Given I have a Create request leave screen
    When I input project name <project>
    And I select leave type <type>
    And I input register time from<register_from>
    And I input register time to <register_to>
    Then I should see screen confirm request leave

    Examples:
      | project | type                                  | register_from    | register_to      |
      | WSM     | Forgot card(all day)                  | 2017/06/06 09:00 | 2017/06/06 14:00 |
      | WSM     | Forgot to check in/check out(all day) | 2017/06/06 09:00 | 2017/06/06 14:00 |
      | WSM     | Forgot to check in/check out(all day) | 2017/06/03 09:00 | 2017/06/03 14:00 |

  Scenario Outline :Input wrong format Forgot card (in),Forgot card (out),Forgot to check in,Forgot to check out,
  In late(Wo man only) (M),In late(Wo man only) (A),Leave early(woman only)(M), Leave early(woman only)(A)
    Given I have a Create request leave screen
    When I input project name <project>
    And I select leave type <type>
    And I input register time from<register_from>
    Then I should see message <error>

    Examples:
      | project | type                         | register_from    | error                                                                                     |
      | WSM     | Forgot card (in)             | 2017/06/09 09:00 | Registration time is not greater than the current date (2017/06/06)                       |
      | WSM     | Forgot card (in)             | 2017/06/04 09:00 | Form overdue                                                                              |

      | WSM     | Forgot card (out)            | 2017/06/09 09:00 | Registration time is not greater than the current date (2017/06/06)                       |
      | WSM     | Forgot card (out)            | 2017/06/04 14:00 | Form overdue                                                                              |

      | WSM     | Forgot to check in           | 2017/06/09 09:00 | Registration time is not greater than the current date (2017/06/06)                       |
      | WSM     | Forgot to check in           | 2017/05/06 09:00 | Form overdue                                                                              |

      | WSM     | Forgot to check out          | 2017/06/09 09:00 | Registration time is not greater than the current date (2017/06/06)                       |
      | WSM     | Forgot to check out          | 2017/05/06 09:00 | Form overdue                                                                              |

      | WSM     | In late(Wo man only) (M)     | 2017/06/06 07:00 | Time into company Illegal                                                                 |
      | WSM     | In late(Wo man only) (M)     | 2017/06/06 08:00 | The request for IL (woman only) (A) can not be greater than 0.5 hours from the work shift |
      | WSM     | In late(Wo man only) (M)     | 2017/06/06 10:30 | Your amount time can't greater than max alllow time                                       |
      | WSM     | In late(Wo man only) (M)     | 2017/06/06 12:00 | Check in time must be in morning shift                                                    |

      | WSM     | In late(Wo man only) (A)     | 2017/06/06 10:00 | Check in time must be in afternoon shift                                                  |
      | WSM     | In late(Wo man only) (A)     | 2017/06/06 13:00 | The request for IL (woman only) (A) can not be greater than 0.5 hours from the work shift |
      | WSM     | In late(Wo man only) (A)     | 2017/06/06 15:30 | Your amount time can't greater than max alllow time                                       |
      | WSM     | In late(Wo man only) (A)     | 2017/06/06 17:00 | Time into company Illegal                                                                 |

      | WSM     | Leave early(Wo man only) (M) | 2017/06/06 07:00 | Your time can't be sooner than time in of company                                         |
      | WSM     | Leave early(Wo man only) (M) | 2017/06/06 08:00 | Your amount time can't greater than max alllow time                                       |
      | WSM     | Leave early(Wo man only) (M) | 2017/06/06 12:00 | Your time can't in lunch break time of company                                            |
      | WSM     | Leave early(Wo man only) (M) | 2017/06/06 14:00 | Check out time must be in morning shift                                                   |
      | WSM     | Leave early(Wo man only) (M) | 2017/06/06 17:00 | Your time can't be later than time out company                                            |

      | WSM     | Leave early(Wo man only) (A) | 2017/06/06 07:00 | Your time can't be sooner than time in of company                                         |
      | WSM     | Leave early(Wo man only) (A) | 2017/06/06 14:00 | Your amount time can't greater than max alllow time                                       |
      | WSM     | Leave early(Wo man only) (A) | 2017/06/06 12:00 | Your time can't in lunch break time of company                                            |
      | WSM     | Leave early(Wo man only) (A) | 2017/06/06 09:00 | Check out time must be in afternoon shift                                                 |
      | WSM     | Leave early(Wo man only) (A) | 2017/06/06 17:00 | Your time can't be later than time out company

  Scenario Outline : Input right format Forgot card (in),Forgot card (out),Forgot to check in,Forgot to check out,
  In late(Wo man only) (M),In late(Wo man only) (A),Leave early(woman only)(M), Leave early(woman only)(A))
    Given I have a Create request leave screen
    When I input project name <project>
    And I select leave type <type>
    And I input register time from<register_from>
    Then I should see screen confirm request leave

    Examples:
      | project | type                     | register_from    |
      | WSM     | Forgot card (in)         | 2017/06/06 09:00 |
      | WSM     | Forgot card (out)        | 2017/06/06 14:00 |

      | WSM     | Forgot to check in       | 2017/06/06 09:00 |
      | WSM     | Forgot to check in       | 2017/06/03 09:00 |

      | WSM     | Forgot to check out      | 2017/06/06 14:00 |
      | WSM     | Forgot to check out      | 2017/06/03 14:00 |

      | WSM     | In late(Wo man only) (M) | 2017/06/06 09:00 |
      | WSM     | In late(Wo man only) (M) | 2017/06/03 09:00 |
      | WSM     | In late(Wo man only) (M) | 2017/06/09 09:00 |

      | WSM     | In late(Wo man only) (A) | 2017/06/06 14:00 |
      | WSM     | In late(Wo man only) (A) | 2017/06/03 14:00 |
      | WSM     | In late(Wo man only) (A) | 2017/06/09 14:00 |

      | WSM     | In late(Wo man only) (M) | 2017/06/06 10:00 |
      | WSM     | In late(Wo man only) (M) | 2017/06/03 10:00 |
      | WSM     | In late(Wo man only) (M) | 2017/06/09 10:00 |

      | WSM     | In late(Wo man only) (A) | 2017/06/06 15:00 |
      | WSM     | In late(Wo man only) (A) | 2017/06/03 15:00 |
      | WSM     | In late(Wo man only) (A) | 2017/06/09 15:00 |
