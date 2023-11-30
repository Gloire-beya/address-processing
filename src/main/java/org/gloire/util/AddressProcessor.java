package org.gloire.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gloire.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Utility class for processing and working with addresses.
 */
public class AddressProcessor {
    private static final Logger logger = LoggerFactory.getLogger(AddressProcessor.class);

    /**
     * Loads a list of addresses from a JSON file.
     *
     * @param fileName The name of the JSON file containing addresses.
     * @return A list of addresses read from the file.
     * @throws IOException If an error occurs while reading the file.
     */
    public static List<Address> loadAddresses(String fileName) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(fileName);
            return objectMapper.readValue(file, new TypeReference<List<Address>>() {
            });
        } catch (IOException e) {
            logger.error("Error reading addresses from file: {}", e.getMessage(), e);
            return List.of();
        }
    }

    /**
     * Generates a pretty printed string representation of an address.
     *
     * @param address The address to be pretty printed.
     * @return A formatted string representing the address.
     * @throws IllegalArgumentException If the address is not valid.
     */
    public static String prettyPrintAddress(Address address) {
        if (!isValidAddress(address)) {
            throw new IllegalArgumentException(getValidationErrorMessage(address));
        }

        return String.format("Type: %s - %s - %s - %s - %s",
                address.getAddressLineDetail().getFormattedLineDetail(),
                address.getCityOrTown(),
                address.getProvinceOrState() != null ? address.getProvinceOrState().getName() : "",
                address.getPostalCode(),
                address.getCountry().getName());
    }

    /**
     * Prints addresses of a specific type to the logger.
     *
     * @param addresses The list of addresses to filter and print.
     * @param type      The type of addresses to print.
     */
    public static void printAddressesByType(List<Address> addresses, String type) {
        for (Address address : addresses) {
            if (address.getType() != null && address.getType().getName().equals(type)) {
                logger.info(prettyPrintAddress(address));
            }
        }
    }

    /**
     * Pretty prints all addresses to the logger.
     *
     * @param addresses The list of addresses to be pretty printed.
     */
    public static void prettyPrintAllAddresses(List<Address> addresses) {
        for (Address address : addresses) {
            logger.info(prettyPrintAddress(address));
        }
    }

    /**
     * Checks if an address is valid based on certain criteria.
     *
     * @param address The address to be validated.
     * @return true if the address is valid, false otherwise.
     */
    public static boolean isValidAddress(Address address) {
        if (address.getPostalCode().matches("\\d+") && address.getCountry() != null && !address.getCountry().getName().isEmpty()
                && address.getAddressLineDetail() != null && !address.getAddressLineDetail().getLine1().isEmpty()) {
            if (address.getCountry().getCode().equals("ZA")) {
                return address.getProvinceOrState() != null && !address.getProvinceOrState().getName().isEmpty();
            }
            return true;
        }
        return false;
    }

    /**
     * Gets a validation error message for an invalid address.
     *
     * @param address The address for which to generate the error message.
     * @return The validation error message.
     */
    public static String getValidationErrorMessage(Address address) {
        if ((address.getCountry().getCode().equals("ZA") && address.getProvinceOrState() == null)) {
            return "Province is required for South Africa";
        }
        if (!address.getPostalCode().matches("\\d+")) {
            return "Postal code must consist of numeric characters";
        }
        if (address.getAddressLineDetail() == null) {
            return "At least one address line is required";
        }
        return "Unknown validation error";
    }
}
