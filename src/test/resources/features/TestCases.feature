Feature: Tests

# Tests Part 1
  @PartOne @TAT-62
  Scenario: Submit the form with valid user credentials
    Given I open the Test App
    When I tap on "PART 1" button to open Part 1 screen
    And I input valid user credentials in the form
    And I tap on SUBMIT button
    Then I see a pop-up window with a message "Success"

  @PartOne @TAT-63
  Scenario: Submit the form with invalid username
    Given I open the Test App
    When I tap on "PART 1" button to open Part 1 screen
    And I input invalid username in the form
    And I tap on SUBMIT button
    Then I see a pop-up window with a error message "Incorrect username!"

# Tests Part 2
  @PartTwo @TAT-64
  Scenario:	Validate that list is in an alphabetical order
    Given	I open the Test App
    When	I tap on "PART 2" button to open Part 2 screen
    And	I save list items while scrolling through the list
    Then	I validate the saved items are in an alphabetical order

  @PartTwo @TAT-65
  Scenario:	Validate * symbol added to specific car manufacturer names
    Given	I open the Test App
    When	I tap on "PART 2" button to open Part 2 screen
    And	I save list items while scrolling through the list
    Then	I validate the "Mercury, Pontiac, Saturn, Scion, Suzuki" has a * symbol added

  @PartTwo @TAT-66
  Scenario:	Validate empty categories
    Given	I open the Test App
    When	I tap on "PART 2" button to open Part 2 screen
    And	I save list items while scrolling through the list
    Then	I validate the "E, O, Q, U, W, X, Y" categories are empty

  @PartTwo @TAT-67
  Scenario:	Validate categories with 3 or more items
    Given	I open the Test App
    When	I tap on "PART 2" button to open Part 2 screen
    And	I save list items while scrolling through the list
    Then	I validate the "(name categories)" categories have 3 or more items

# Tests Part 3
  @PartThree @TAT-68
  Scenario:	Validate total sum
    Given	I open the Test App
    When	I tap on "PART 3" button to open Part 3 screen
    And	I save all item price sum while scrolling through the item list
    Then	I validate the total sum matches all item price sum

  @PartThree @TAT-69
  Scenario:	Validate specific brand sum
    Given	I open the Test App
    When	I tap on "PART 3" button to open Part 3 screen
    And	I save brand item price sum while scrolling through the item list
    Then	I validate a brand total sum matches that brand item price sum

  @PartThree @TAT-70
  Scenario:	Validate all unique FAKEIMAGE names
    Given	I open the Test App
    When	I tap on "PART 3" button to open Part 3 screen
    And	I save all item image names while scrolling through the item list
    Then	I validate image names matches from the image name pool

# Tests Part 4
  @PartFour @TAT-71
  Scenario:	Enable all Section One checkboxes
    Given	I open the Test App
    When	I tap on "PART 4" button to open Part 4 screen
    And	I enable all section one checkboxes
    And	I tap on SUBMIT button
    Then	I see a pop-up window with a message "Success" for section 1

  @PartFour @TAT-72
  Scenario:	Enable all required section two checkboxes
    Given	I open the Test App
    When	I tap on "PART 4" button to open Part 4 screen
    And	I enable all required section two checkboxes
    And	I tap on SUBMIT button
    Then	I see a pop-up window with a message "Success" for section 2

  @PartFour @TAT-73
  Scenario:	Enable all section three checkboxes that have 'yes' under them
    Given	I open the Test App
    When	I tap on "PART 4" button to open Part 4 screen
    And	I enable all section three checkboxes that have 'yes' under them
    And	I tap on SUBMIT button
    Then	I see a pop-up window with a message "Success" for section 3
