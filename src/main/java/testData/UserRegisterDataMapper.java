package testData;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.GlobalConstants;

import java.io.File;
import java.util.Random;

public class UserRegisterDataMapper {
    public static UserRegisterDataMapper getUserRegisterData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new File(GlobalConstants.PROJECT_PATH + "/src/test/resources/UserRegisterData.json"), UserRegisterDataMapper.class);
        }   catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int generateFakeNumber() {
        Random rand = new Random();
        return rand.nextInt(99999);
    }

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("invalidEmail")
    private String invalidEmail;

    @JsonProperty("validEmail")
    private String validEmail;
    @JsonProperty("validPassword")
    private String validPassword;

    @JsonProperty("invalidPassword")
    private String invalidPassword;

    @JsonProperty("invalidConfirmPassword")
    private String invalidConfirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInvalidEmail() {
        return invalidEmail;
    }

    public String getValidEmail() {
        return validEmail + generateFakeNumber() + "@gmail.com";
    }

    public String getValidPassword() {
        return validPassword;
    }

    public String getInvalidPassword() {
        return invalidPassword;
    }

    public String getInvalidConfirmPassword() {
        return invalidConfirmPassword;
    }


}
