package org.gloire.model;


import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents an address with various properties.
 */
public class Address {
    private String id;
    private AddressType type;
    private AddressLineDetail addressLineDetail;
    private ProvinceOrState provinceOrState;
    private String cityOrTown;
    private Country country;
    private String postalCode;
    private String suburbOrDistrict;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private String lastUpdated;

    public Address() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    public AddressLineDetail getAddressLineDetail() {
        return addressLineDetail;
    }

    public void setAddressLineDetail(AddressLineDetail addressLineDetail) {
        this.addressLineDetail = addressLineDetail;
    }

    public ProvinceOrState getProvinceOrState() {
        return provinceOrState;
    }

    public void setProvinceOrState(ProvinceOrState provinceOrState) {
        this.provinceOrState = provinceOrState;
    }

    public String getCityOrTown() {
        return cityOrTown;
    }

    public void setCityOrTown(String cityOrTown) {
        this.cityOrTown = cityOrTown;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getSuburbOrDistrict() {
        return suburbOrDistrict;
    }

    public void setSuburbOrDistrict(String suburbOrDistrict) {
        this.suburbOrDistrict = suburbOrDistrict;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Represents the type of an address, such as Physical Address, Postal Address, etc.
     */
    public static class AddressType {
        private String code;
        private String name;

        public AddressType() {
        }

        public AddressType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * Represents the details of address lines, including line 1 and line 2.
     */
    public static class AddressLineDetail {
        private String line1;
        private String line2;

        public AddressLineDetail() {
        }

        public AddressLineDetail(String line1, String line2) {
            this.line1 = line1;
            this.line2 = line2;
        }

        public String getLine1() {
            return line1;
        }

        public void setLine1(String line1) {
            this.line1 = line1;
        }

        public String getLine2() {
            return line2;
        }

        public void setLine2(String line2) {
            this.line2 = line2;
        }

        public String getFormattedLineDetail() {
            return line2 == null || line2.isEmpty() ? line1 : line1 + " - " + line2;
        }

        public boolean isBlankOrNull() {
            return line1 == null || line1.trim().isEmpty();
        }

    }

    /**
     * Represents a ProvinceOrState with a code and a name.
     */
    public static class ProvinceOrState {
        private String code;
        private String name;

        public ProvinceOrState() {
        }

        public ProvinceOrState(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isNotNullNorBlank() {
            return name != null && !name.isEmpty();
        }

    }

    /**
     * Represents a country with a code and a name.
     */
    public static class Country {
        private String code;
        private String name;

        public Country() {
        }

        public Country(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
