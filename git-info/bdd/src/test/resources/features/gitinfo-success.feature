@gitinfo-service
Feature: gitinfo success scenarios

  Scenario Outline: For a valid username should get a success response
    Given that a user with "<username>" try to access gitinfo service
    When gitinfo service is invoked with sortBy as "<sortBy>" and order as "<order>"

    Examples:
      | username  | sortBy | order |
      | noushadbm | NAME   | asc   |