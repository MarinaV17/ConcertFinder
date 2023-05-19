package main.config;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Getter
@Log4j2
public class Configuration {
    private static Configuration instance;
    private String adminUser;//root
    private String adminPass;//root
    private String pathFile;

    private Configuration() {
        try {
            Properties p = new Properties();
            p.load(Configuration.class.getClassLoader().getResourceAsStream("configuration/paths.properties"));
            this.adminUser = p.getProperty("adminUserProperty");
            this.adminPass = p.getProperty("adminPassProperty");
            this.pathFile = p.getProperty("filesPath");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public List<String> getPremiumNames() {
        ArrayList<String> premiumNames = new ArrayList<>();
        premiumNames.add("Gema");
        premiumNames.add("Marina");
        return premiumNames;
    }
}