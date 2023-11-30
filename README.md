# Address Processing Java Application

This Java application provides functionality for processing and handling address data. It includes features such as pretty printing, validation, and reading addresses from a JSON file.

## Usage

### Pretty Printing
Use the *prettyPrintAddress* method to print a well-formatted version of an address.

### Address Validation
Use the *validateAddress* method to check if an address is valid based on specified criteria.

### Reading Addresses from File
Use the *loadAddressesFromFile* method to read addresses from a JSON file.

### Pretty printing address by type
Use the *printAddressesByType* method to print an address based on the type provided

### Print all the addresses
Use the *prettyPrintAllAddresses* method to print all valid addresses 

### Running Tests
Ensure you have *JUnit* on your classpath and then run the tests

#### Note:  
      1. A valid address must consist of a numeric postal code, a country, and at least one address line that is not blank or null.  
      2. If the country is ZA, then a province is required as well.


