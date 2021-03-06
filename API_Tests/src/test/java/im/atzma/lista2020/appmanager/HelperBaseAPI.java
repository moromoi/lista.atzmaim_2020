package im.atzma.lista2020.appmanager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelperBaseAPI {

    public String propertiesList(String key) throws IOException {

        Properties properties = new Properties();
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        if (key.equals("web.BaseUrl"))
        { return properties.getProperty("web.BaseUrl");
        } else if (key.equals("web.singupURL")) { return properties.getProperty("web.singupURL");
        } else if (key.equals("web.businessURL")) { return properties.getProperty("web.businessURL");
        } else if (key.equals("web.allsetURL")) { return properties.getProperty("web.allsetURL");
        } else if (key.equals("web.loginURL")) { return properties.getProperty("web.loginURL");
        } else if (key.equals("web.clientURL")) { return properties.getProperty("web.clientURL");
        } else if (key.equals("web.servicesURL")) { return properties.getProperty("web.servicesURL");
        } else if (key.equals("web.calendarURL")) { return properties.getProperty("web.calendarURL");
        } else if (key.equals("web.newClientFormURL")) { return properties.getProperty("web.newClientFormURL");
        } else if (key.equals("web.adminLogin")) { return properties.getProperty("web.adminLogin");
        } else if (key.equals("web.adminPassword")) { return properties.getProperty("web.adminPassword");
        } else if (key.equals("get.clients")) { return properties.getProperty("get.clients");
        } else if (key.equals("web.settings")) { return properties.getProperty("web.settings");
        }

        return null;
    }


}
