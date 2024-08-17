import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Main class for managing and displaying weather data.
 * This class provides functionalities to initialize a weather data manager from a file,
 * display specific weather readings, statistics for a particular city, and demonstrate
 * the calculation of temperature trend analysis using linear regression.
 */
public class Main {
    /**
     * File path for the weather data CSV file.
     */
    private static final String FILE_PATH = "C:\\Users\\samue\\OneDrive\\Desktop\\Proj02\\city_temperature.csv";

    /**
     * The main method to run the application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        GlobalWeatherManager manager = initializeWeatherManagerFromFile();

        if (manager == null) {
            System.out.println("Initialization failed. Exiting program...");
            return;
        }

        System.out.println("\nTotal Reading Count: " + manager.getReadingCount());
        displayReadingAtIndex(manager);
        displayFirstNReadings(manager);
        displayReadingsOnDate(manager);
        displayCityStats(manager);
        displayTemperatureRegressionSlope(manager);
        demonstrateCalcLinearRegressionSlope(manager);
    }

    /**
     * Initializes the GlobalWeatherManager using data from a specified file path.
     *
     * @return An instance of GlobalWeatherManager or {@code null} if the file was not found.
     */
    private static GlobalWeatherManager initializeWeatherManagerFromFile() {
        try {
            return new GlobalWeatherManager(new File(Main.FILE_PATH));
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Exiting gracefully...");
            return null;
        }
    }

    /**
     * Displays a specific weather reading from the GlobalWeatherManager based on the index.
     *
     * @param manager The GlobalWeatherManager instance.
     */
    private static void displayReadingAtIndex(GlobalWeatherManager manager) {
        System.out.println("Reading at index " + 2885063 + ": " + manager.getReading(2000000));
    }

    /**
     * Displays the first 'N' weather readings from the GlobalWeatherManager.
     *
     * @param manager The GlobalWeatherManager instance.
     */
    private static void displayFirstNReadings(GlobalWeatherManager manager) {
        System.out.println("\nFirst " + 10 + " readings:");
        for (WeatherReading reading : manager.getReadings(0, 10)) {
            System.out.println(reading);
        }
    }

    /**
     * Displays weather readings on a specified date.
     *
     * @param manager The GlobalWeatherManager instance.
     */
    private static void displayReadingsOnDate(GlobalWeatherManager manager) {
        System.out.println("\nReadings on " + 1 + "/" + 2 + " from different years:");
        // Modified to reflect new getReadings method
        WeatherReading[] readings = manager.getReadings(0, manager.getReadingCount(), 1, 2);
        for (WeatherReading reading : readings) {
            System.out.println(reading);
        }
    }

    /**
     * Displays statistical information about a specific city.
     *
     * @param manager The GlobalWeatherManager instance.
     */
    private static void displayCityStats(GlobalWeatherManager manager) {
        CityListStats stats = manager.getCityListStats("US", "Maine", "Caribou");
        System.out.println("\nStats for " + "Caribou" + ", " + "Maine" + ", " + "US" + ":");
        if (stats != null) {
            System.out.println("Starting Index: " + stats.startingIndex());
            System.out.println("Count of Readings: " + stats.count());
            System.out.print("Years: " + String.join(", ", Arrays.stream(stats.years())
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new)));
            System.out.println();
        } else {
            System.out.println("City data is not available.");
        }
    }

    /**
     * Displays the slope of the linear regression line calculated for temperature trends.
     * @param manager The GlobalWeatherManager instance.
     */
    private static void displayTemperatureRegressionSlope(GlobalWeatherManager manager) {
        double slope = manager.getTemperatureLinearRegressionSlope(manager.getReadings(0, manager.getReadingCount()));
        System.out.println("\nTemperature Linear Regression Slope: " + slope);
    }

    /**
     * Demonstrates the calculation of the linear regression slope for a set of given data points.
     * @param manager The GlobalWeatherManager instance.
     */
    private static void demonstrateCalcLinearRegressionSlope(GlobalWeatherManager manager) {
        Integer[] x = {2000, 2001, 2002, 2003};
        Double[] y = {32.5, 33.0, 33.5, 34.0};
        System.out.println("Linear Regression Slope for provided data: " + manager.calcLinearRegressionSlope(x, y));
    }
}
