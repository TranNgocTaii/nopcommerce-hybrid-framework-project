package utilities.helpers;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataHelper {
    private Locale local = new Locale("en");
    private Faker faker = new Faker (local);
    public static DataHelper getDataHelper() {
        return new DataHelper();
    }

    public String getFirstName() {
        return faker.address().firstName();
    }

    public String getLastName() {
        return faker.address().lastName();
    }

    public String getEmailAddress() {
        return faker.internet().emailAddress();
    }

    public String getPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }

}
