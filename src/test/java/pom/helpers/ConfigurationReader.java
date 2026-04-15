package pom.helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    private final String browser;
    private final String baseURL;
    private final String headless;
    private final String waitInSeconds;

    public ConfigurationReader() {
        String configurationPath = "src/test/java/pom/helpers/configuration.properties";


        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(configurationPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Configuration filed not found at: " + configurationPath);
        }
        Properties properties = new Properties();
        try {
            properties.load(reader);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        browser = properties.getProperty("browser");
        headless = properties.getProperty("headless");
        baseURL = properties.getProperty("baseURL");
        waitInSeconds = properties.getProperty("waitInSeconds");
    }

    public String getBrowser() {
        if (!browser.isEmpty()) return browser;
        else throw new RuntimeException("\"browser\" is not specified in the Configuration.properties file. "
        + "Provided value : " + getBrowser());
    }

    public String getBaseURL() {
        if (!baseURL.isEmpty()) return baseURL;
        else throw new RuntimeException("\"baseURL\" is not specified in the Configuration.properties file. "
                + "Provided value : " + getBaseURL());
    }

    public boolean getHeadless() {
        if (!headless.isEmpty()) return Boolean.parseBoolean(headless);
            else throw new RuntimeException("\"headless\" is not specified in the Configuration.properties file. "
                + "Provided value : " + getHeadless());
    }
    public int getWait() {
        if (!waitInSeconds.isEmpty()) return Integer.parseInt(waitInSeconds);
        else throw new RuntimeException("\"wait\" is not specified in the Configuration.properties file. "
                + "Provided value : " + getWait());
    }
}
