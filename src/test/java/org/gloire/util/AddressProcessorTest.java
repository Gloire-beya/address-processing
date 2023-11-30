package org.gloire.util;

import org.gloire.model.Address;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.gloire.util.AddressProcessor.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddressProcessorTest {

    private static final Logger logger = LoggerFactory.getLogger(AddressProcessorTest.class);
    private static final String filePath = "src/test/resources/addresses.json";

    /**
     * Unit test for loading addresses from a file using the {@link AddressProcessor} class.
     * It checks if the addresses are loaded successfully and their count is as expected.
     *
     * @throws IOException If an error occurs while loading addresses.
     */
    @Test
    public void loadAddressesFromFile() {
        List<Address> addresses = null;
        try {
            addresses = loadAddresses(filePath);
        } catch (IOException e) {
            fail("IOException occurred while loading addresses");
        }
        assertNotNull(addresses);
        assertEquals(3, addresses.size());
    }

    /**
     * Unit test for pretty printing a single address using the {@link AddressProcessor} class.
     * It loads an address, checks its validity, and verifies the pretty printed output.
     *
     * @throws IOException If an error occurs while loading addresses.
     */
    @Test
    public void prettyPrintSingleAddress() {
        Address address = null;
        try {
            address = loadAddresses(filePath).get(2);
        } catch (IOException e) {
            fail("IOException occurred while loading addresses");
        }
        assertNotNull(address);

        if (!isValidAddress(address)) {
            Address finalAddress = address;
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> prettyPrintAddress(finalAddress));
            assertEquals(getValidationErrorMessage(address), exception.getMessage());
        } else {
            String prettyPrinted = prettyPrintAddress(address);
            assertEquals("Type: Address 1 - Line 2 - City 1 - Eastern Cape - 1234 - South Africa", prettyPrinted);
        }
    }

    /**
     * Unit test for pretty printing all addresses using the {@link AddressProcessor} class.
     * It loads addresses, checks their validity, and verifies the pretty printed output for each address.
     *
     * @throws IOException If an error occurs while loading addresses.
     */
    @Test
    public void prettyPrintAllAddresses() {
        try {
            List<Address> addresses = loadAddresses(filePath);
            assertNotNull(addresses);
            assertEquals(3, addresses.size());

            for (Address address : addresses) {
                if (!isValidAddress(address)) {
                    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> prettyPrintAddress(address));
                    assertEquals(getValidationErrorMessage(address), exception.getMessage());
                } else {
                    String prettyPrinted = prettyPrintAddress(address);
                    assertEquals("Type: Address 1 - Line 2 - City 1 - Eastern Cape - 1234 - South Africa", prettyPrinted);
                }
            }

        } catch (IOException e) {
            fail("IOException occurred while loading addresses");
        }
    }

    /**
     * Unit test for printing addresses of a specific type using the {@link AddressProcessor} class.
     * It loads addresses and prints addresses of the specified type for manual verification.
     *
     * @throws IOException If an error occurs while loading addresses.
     */
    @Test
    public void printAddressesByType() {
        String type = "Physical Address";
        try {
            List<Address> addresses = AddressProcessor.loadAddresses(filePath);
            AddressProcessor.printAddressesByType(addresses, type);  // For manual verification
        } catch (IOException e) {
            fail("IOException occurred while loading addresses");
        }
    }


    /**
     * Unit test for validating an invalid address using the {@link AddressProcessor} class.
     * It creates an invalid address and checks if it is considered invalid.
     *
     * @throws IOException If an error occurs while loading addresses.
     */
    @Test
    public void validateInvalidAddress() throws IOException {
        Address invalidAddress1 = new Address();
        invalidAddress1.setType(new Address.AddressType("2", "Postal Address"));
        invalidAddress1.setCityOrTown("City 2");
        invalidAddress1.setCountry(new Address.Country("LB", "Lebanon"));
        invalidAddress1.setPostalCode("invalid");
        invalidAddress1.setLastUpdated("2017-06-21T00:00:00.000Z");

        assertFalse(isValidAddress(invalidAddress1));
        try {
            assertFalse(isValidAddress(loadAddresses(filePath).get(1)));
            assertFalse(isValidAddress(loadAddresses(filePath).get(2)));

        } catch (IOException e) {
            fail("IOException occurred while loading addresses");
        }


    }

    /**
     * Unit test for validating an address with a missing province using the {@link AddressProcessor} class.
     * It creates an address without a province and checks if it is considered invalid.
     */
    @Test
    public void validateAddressWithMissingProvince() {
        Address addressWithoutProvince = new Address();
        addressWithoutProvince.setType(new Address.AddressType("5", "Business Address"));
        addressWithoutProvince.setAddressLineDetail(new Address.AddressLineDetail("Address 3", ""));
        addressWithoutProvince.setCityOrTown("City 3");
        addressWithoutProvince.setCountry(new Address.Country("ZA", "South Africa"));
        addressWithoutProvince.setPostalCode("3456");
        addressWithoutProvince.setLastUpdated("2018-06-13T00:00:00.000Z");

        assertFalse(isValidAddress(addressWithoutProvince));
    }

    /**
     * Unit test for validating an address with a missing address line using the {@link AddressProcessor} class.
     * It creates an address without an address line and checks if it is considered invalid.
     */
    @Test
    public void validateAddressWithMissingLine() {
        Address addressWithoutLine = new Address();
        addressWithoutLine.setType(new Address.AddressType("2", "Postal Address"));
        addressWithoutLine.setCityOrTown("City 2");
        addressWithoutLine.setCountry(new Address.Country("LB", "Lebanon"));
        addressWithoutLine.setPostalCode("2345");
        addressWithoutLine.setLastUpdated("2017-06-21T00:00:00.000Z");

        assertFalse(isValidAddress(addressWithoutLine));
    }

    /**
     * Unit test for validating an address with a missing country using the {@link AddressProcessor} class.
     * It creates an address without a country and checks if it is considered invalid.
     */
    @Test
    public void validateAddressWithMissingCountry() {
        Address addressWithoutCountry = new Address();
        addressWithoutCountry.setType(new Address.AddressType("1", "Physical Address"));
        addressWithoutCountry.setAddressLineDetail(new Address.AddressLineDetail("Address 1", "Line 2"));
        addressWithoutCountry.setProvinceOrState(new Address.ProvinceOrState("5", "Eastern Cape"));
        addressWithoutCountry.setCityOrTown("City 1");
        addressWithoutCountry.setPostalCode("1234");
        addressWithoutCountry.setLastUpdated("2015-06-21T00:00:00.000Z");

        assertFalse(isValidAddress(addressWithoutCountry));
    }

    /**
     * UUnit test validating addresses using the {@link AddressProcessor} class.
     * It loads addresses from a file and checks their validity.
     *
     * @throws IOException If an error occurs while loading addresses.
     */
    @Test
    public void validateAddresses() {
        try {
            List<Address> addresses = loadAddresses(filePath);
            AddressProcessor.validateAddresses(addresses); // Manual testing
        } catch (IOException e) {
            fail("IOException occurred while loading addresses");
        }
    }

    /**
     * Unit test for validating a valid address using the {@link AddressProcessor} class.
     * It loads an address and checks if it is considered valid.
     *
     * @throws IOException If an error occurs while loading addresses.
     */
    // Done
    @Test
    public void validateValidAddress() {
        try {
            assertTrue(isValidAddress(loadAddresses(filePath).get(0)));
        } catch (IOException e) {
            fail("IOException occurred while loading addresses");
        }
    }
}