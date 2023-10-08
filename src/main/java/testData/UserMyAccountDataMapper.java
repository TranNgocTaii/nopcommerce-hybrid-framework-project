package testData;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.GlobalConstants;

import java.io.File;
import java.util.Random;

public class UserMyAccountDataMapper {
    public static UserMyAccountDataMapper getMyAccountUserData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new File(GlobalConstants.PROJECT_PATH + "/src/test/resources/UserMyAccountData.json"), UserMyAccountDataMapper.class);
        }   catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int generateFakeNumber(){
        Random rand = new Random();
        return rand.nextInt(99999);
    }

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("addressFirstName")
    private String addressFirstName;

    @JsonProperty("addressLastName")
    private String addressLastName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("faxNumber")
    private String faxNumber;

    @JsonProperty("addressCompany")
    private String addressCompany;

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("addressCity")
    private String addressCity;

    @JsonProperty("addressZipPostalCode")
    private String addressZipPostalCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("oldPassword")
    private String oldPassword;

    @JsonProperty("newPassword")
    private String newPassword;

    @JsonProperty("confirmPassword")
    private String confirmPassword;

    @JsonProperty("day")
    private String day;

    @JsonProperty("month")
    private String month;

    @JsonProperty("year")
    private String year;

    @JsonProperty("email")
    private String email;

    @JsonProperty("emailAddress")
    private String emailAddress;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddressFirstName() {
        return addressFirstName;
    }

    public String getAddressLastName() {
        return addressLastName;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public String getAddressCompany() {
        return addressCompany;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public String getAddressZipPostalCode() {
        return addressZipPostalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getEmail() {
        return email + generateFakeNumber() + "@gmail.com";
    }

    public String getEmailAddress() {
        return emailAddress + generateFakeNumber() + "@gmail.com";
    }
}
