Feature: Check-in a booking
  In order to assing rooms to a booking.
  As a front office employee.
  I want to do a check in.

  Scenario: Assign rooms
    Given a booking with the id 1
    When I assign the rooms for the booking with the id 1
    Then I should have rooms matching the booked categories for booking with id 1

  Scenario: Check in
    Given a booking with the id 1 and the room 101
    When I do the check in for the booking with id 1 and with the room 101
    Then I should have a matching stay for the booking with id 1