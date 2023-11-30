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

}