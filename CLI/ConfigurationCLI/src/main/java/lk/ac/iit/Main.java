package lk.ac.iit;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration();
        Configuration.getConfigInputs(config);

        Configuration.saveConfiguration(config,"Configuration.json");
    }
}