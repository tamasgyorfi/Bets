package hu.bets.util;

public class EnvironmentVarResolver {

    public static String getEnvVar(String name, String defaultValue) {
        String envVar = System.getenv(name);

        if (envVar == null) {
            envVar = System.getProperty(name);
        }

        return envVar == null ? defaultValue : envVar;
    }
}
