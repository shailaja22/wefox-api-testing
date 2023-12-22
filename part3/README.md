# In Terms of the different testing categories that describe the scope of the test, how would you describe the type of test you have just written?

## Tests I have written includes: 

### Functional Testing: 
- Using different methods API is tested(POST,GET,PUT,DELETE)
- Purposefully make mistakes in your requests to see how the API reacts.
- Check each part of the API and confirm the response is correct.

### Validation Testing
- It includes Request and Response validation.  We validated with the standard json schema.

### Error Handling Testing
- It helps to test Error Code Validation and Error message verification

# Outline the possibilities of automating these specific test cases in different scopes, together with a short summary of the pros and cons of each of them.

###  Functional Testing:
#### Pros:
- Ensures Correct Functionality: Verifies that individual functions or methods of the API work as intended.
- Early Detection of Bugs: Identifies issues at the unit level, allowing for early bug detection and resolution.
#### Cons:
- Limited Coverage
- Inability to detect non functional issues such as performance, scalability, and security.

### Validation Testing:
#### Pros:
- Data Integrity: Ensures that data exchanged between the client and server adheres to specified standards.
- Interoperability: Verifies that the API integrates seamlessly with other components.
#### Cons:
- Validation testing may not cover all possible use cases, especially in complex systems with numerous inputs and scenarios.
- API not handling unexpected situations correctly

### Error Handling Testing:
#### Pros:
- Improved Robustness: Identifies and addresses how the API handles different error scenarios.
- Enhanced User Experience: Ensures that error messages are meaningful and helpful for developers.
#### Cons:
- May Lack Comprehensive Coverage: It might be challenging to cover all possible error scenarios exhaustively.