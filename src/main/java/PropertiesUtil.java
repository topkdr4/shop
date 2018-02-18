import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class PropertiesUtil {

    private static final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();


    public static Properties loadProperties(String propertiesLocation) throws IOException {
        Properties result = new Properties();
        try (InputStream stream = classLoader.getResourceAsStream(propertiesLocation)) {
            result.load(stream);
        }

        return result;
    }

}
