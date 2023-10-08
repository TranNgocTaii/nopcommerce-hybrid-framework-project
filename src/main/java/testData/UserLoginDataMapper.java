package testData;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.GlobalConstants;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class UserLoginDataMapper {

    public static UserLoginDataMapper getLoginUserData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new File(GlobalConstants.PROJECT_PATH + "/src/test/resources/UserLoginData.json"), UserLoginDataMapper.class);
        }   catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @JsonProperty("invalidEmail")
    private String invalidEmail;

    @JsonProperty("emailNotRegister")
    private String emailNotRegister;



    public String getInvalidEmail() {
        return invalidEmail;
    }

    public String getEmailNotRegister() {
        return emailNotRegister;
    }
}
