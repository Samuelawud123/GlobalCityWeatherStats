import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.*;
import java.util.Iterator;

/**
 * Unit tests for the {@code GlobalWeatherManager} class.
 * This class verifies the correct functionality of the GlobalWeatherManager,
 * including its ability to read, process, and analyze weather data from files.
 */
public class GlobalWeatherManagerTest {

    private GlobalWeatherManager manager;

    /**
     * Prepares the testing environment before each test.
     * Creates a file with mock weather data and initializes the GlobalWeatherManager with this file.
     *
     * @throws IOException if an error occurs during file creation.
     */
    @BeforeEach
    void initialize() throws IOException {
        String mockData =
                """
                        Region,Country,State,City,Month,Day,Year,AvgTemperature
                        Region1,Country1,State1,City1,1,15,2020,15.0
                        Region2,Country2,,City2,2,16,2021,-99.0
                        """;

        File sampleFile = Files.createTempFile("sample", ".csv").toFile();
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter(sampleFile))) {
            bufWriter.write(mockData);
        }

        manager = new GlobalWeatherManager(sampleFile);
    }


    /**
     * Validates that the {@code GlobalWeatherManager#getReadingCount()} method returns the correct count of readings.
     */
    @Test
     void testReadingCount()  {
        assertEquals(2, manager.getReadingCount(), "The count of readings should be equal to the number of data rows minus the header.");
    }

    /**
     * Ensures that the {@code GlobalWeatherManager#getReading(int)} method accurately extracts data from the first reading.
     */
    @Test
    void testGetReading() {
        WeatherReading initialReading = manager.getReading(0);
        assertAll("Verification of the first reading data",
                () -> assertEquals("Country1", initialReading.country()),
                () -> assertEquals("City1", initialReading.city()),
                () -> assertEquals(1, initialReading.month()),
                () -> assertEquals(2020, initialReading.year()),
                () -> assertEquals(15.0, initialReading.avgTemperature())
        );
    }

    /**
     * Checks if the {@code GlobalWeatherManager#getReadings(int, int)} method correctly returns a specified range of readings.
     */
    @Test
    void testGetReadings() {
        WeatherReading[] readingArray = manager.getReadings(0, 2);
        assertEquals(2, readingArray.length, "The method should return the correct number of readings within the specified range.");
    }

    /**
     * Tests the {@code GlobalWeatherManager#getReadings(int, int, int, int)} method for correctness in filtering readings by specific month and day.
     */
    @Test
    void testGetReadingsByDate() {
        WeatherReading[] dateFilteredReadings = manager.getReadings(0, 2, 1, 15);
        assertEquals(1, dateFilteredReadings.length, "The method should filter and return readings based on the specified month and day.");
    }
    /**
     * Tests the {@code GlobalWeatherManager#getCityListStats(String, String, String)} method for correctness
     * in calculating statistics for a specified city.
     */
    @Test
    void testCityListStats() {
        CityListStats stats = manager.getCityListStats("Country1", "State1", "City1");

        assertNotNull(stats, "CityListStats should not be null for existing city data.");
        assertEquals(1, stats.count(), "The count of readings for the specified city should be correct.");
        assertArrayEquals(new int[]{2020}, stats.years(), "The years array should correctly reflect the years in the readings for the specified city.");
        assertEquals(0, stats.startingIndex(), "The start index of readings for the specified city should be correct.");
    }

    /**
     * Assesses the {@code GlobalWeatherManager#iterator()} method for its ability to iterate over weather readings correctly.
     */
    @Test
    void testIterator() {
        Iterator<WeatherReading> readingIterator = manager.iterator();
        assertTrue(readingIterator.hasNext(), "Iterator should initially have a next item.");
        assertNotNull(readingIterator.next(), "First item from iterator should not be null.");
        assertTrue(readingIterator.hasNext(), "Iterator should have a second item.");
        assertNotNull(readingIterator.next(), "Second item from iterator should not be null.");
        assertFalse(readingIterator.hasNext(), "Iterator should not have any more items.");
    }

    /**
     * Validates the {@code GlobalWeatherManager#getTemperatureLinearRegressionSlope(WeatherReading[])} method's accuracy in calculating the temperature slope.
     */
    @Test
    void testGetTemperatureLinearRegressionSlope() {
        WeatherReading[] sampleReadings = {
                new WeatherReading("Region", "Country", "State", "City", 1, 1, 2000, 10.0),
                new WeatherReading("Region", "Country", "State", "City", 1, 1, 2001, 20.0)
        };
        double slope = manager.getTemperatureLinearRegressionSlope(sampleReadings);
        assertEquals(10.0, slope, "The calculated slope should be accurate.");
    }

    /**
     * Ensures that the {@code GlobalWeatherManager#calcLinearRegressionSlope(Integer[], Double[])} method calculates the linear regression slope correctly.
     */
    @Test
    void testLinearRegressionSlopeCalculation() {
        Integer[] years = { 2000, 2001 };
        Double[] temperatures = { 10.0, 20.0 };
        double calculatedSlope = manager.calcLinearRegressionSlope(years, temperatures);
        assertEquals(10.0, calculatedSlope, "The slope should be calculated correctly.");
    }

    /**
     * Confirms that the {@code GlobalWeatherManager#GlobalWeatherManager(File)} constructor throws an exception when given a non-existent file.
     */
    @Test
    void testConstructorWithInvalidFile() {
        assertThrows(FileNotFoundException.class, () -> new GlobalWeatherManager(new File("nonexistentfile.csv")), "The constructor should throw a FileNotFoundException for non-existent files.");
    }

}
