After exploring the PHPTravels hotel booking service, here's a concise overview of the key scenarios for booking a hotel on the platform :

# Exploration Target: 
## Scenario 1:  Selecting Room

## Functionality: 
Users can make reservations for different room types by providing details such as the number of rooms, adults, and children. When they select the "Select Now" option through the PHP platform, the system conducts availability checks and presents a list of rooms along with their respective costs.
## Bugs Found:
On the initial screen, when choosing 2 Adults and 2 Children, if the "Select Now" option is selected, the rooms are not displayed in accordance with the number of travelers chosen.

## Improvements:
The clarity of customer ratings is lacking for obtaining information before booking the hotel
The map is not accessible to determine the actual location of the hotel. 
Details regarding room types, room selection, and pricing are not explicitly provided.
The UI can be improved by clearly indicating the progress and steps involved in booking


# Exploration Target : 
## Scenario 2:  Booking Hotel

## Functionality: 
Upon selecting the "Book Now" option on the PHP platform, users can proceed to reserve a hotel. A form is displayed, and users are prompted to provide all the required details.
## Bugs Found:
- The fields for First Name and Last Name should reject entries containing numerical characters; if entered, an error message should be displayed.
- Email submissions without the '@' symbol should be flagged as invalid.
- In the Phone Number field:
 a) Entries with an incorrect number of digits based on the selected country are not permissible.
 b) Entries containing alphabetic characters are not accepted.
- Nationality must be selected from a dropdown menu.
- The policies regarding cancellation and terms and conditions lack clarity.
## Improvements:
- When completing the form, it should be evident which fields are mandatory and optional.
- If any field is left blank or filled incorrectly, a clear error message should be displayed to guide the user.
- Clearly communicate the hotel's cancellation policy during the booking process.
Use tooltips or expandable sections for additional details.



## Exploration Target
## Scenario 3:  Payment Gateway Integration

## Functionality: 
PHPTravels supports various e-payment gateways including PayPal, Credit Card,Payment without card etc
## Bugs Found:
A confirmation email should be dispatched promptly upon the successful completion of the payment.
## Improvements:
- Summary of charges are not clearly shown
- Implement a confirmation step to review payment details before finalizing the transaction.
- Show users their progress in the payment process with step indicators.
- Clearly display relevant terms and conditions related to the payment.

## Notes from Exploration Phase:
Making the payment process clearer is crucial. Our main goal is to make booking hotels more user-friendly and provide better details about the hotels, so customers are encouraged to visit PHP again.

## Risks Need to mitigate for this web application:

# Risk Priority
1
# Risk
User Experience-Poor usability
# Risk Mitigation
Conduct usability testing with real users.
Gather and analyze user feedback to continuously improve the user interface.
Ensure a responsive and intuitive design for a positive user experience.

# Risk Priority
1
# Risk
Lack of complete Testing-Incomplete test coverage
# Risk Mitigation
Implement comprehensive testing strategies, including unit, integration, and end-to-end testing.

# Risk Priority
2
# Risk
Legal and Compliance
# Risk Mitigation
update privacy policies and terms of service.




