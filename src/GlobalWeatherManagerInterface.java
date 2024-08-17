import java.util.Iterator;

/**
 * Specifies the methods required for the GlobalWeatherManager class
 */
public interface GlobalWeatherManagerInterface {

    // Constructor should take a single parameter, a File reference for the file to be read.
    // It is expected that the constructor might throw a FileNotFoundException.

    /**
     * Retrieves a count of readings
     * @return      count of readings
     */
    public int getReadingCount();

    /**
     * Retrieves the weather reading at the specified index.
     * @param index     the index for the desired reading; must be a valid element index.
     * @return          the reading at the specified index.
     */
    public WeatherReading getReading(int index);

    /**
     * Retrieves a set of weather readings.
     * @param index     the index of the first reading; must be a valid index.
     * @param count     the count of readings to potentially include.  Must be at least 1.  Must imply a valid range;
     *                  index + count must be less than the total reading count.
     * @return          an array of readings.
     */
    public WeatherReading[] getReadings(int index, int count);

    /**
     * Retrieves a set of weather readings.
     * @param index     the index of the first reading.
     * @param count     the count of readings to check for potential inclusion.  Must be at least 1.
     *                  Must imply a valid range; index + count must be less than the total reading count.
     * @param month     the month to filter; must be a valid month (1 to 12).
     * @param day       the day to filter; must be a valid day (1 to 31).
     * @return          an array of readings matching the specified criteria.  Length will usually be smaller than
     *                  the count specified as a parameter, as each year will only have one matching day.
     */
    public WeatherReading[] getReadings(int index, int count, int month, int day);

    /**
     * Retrieves key list statistics for the specified country/state/city.
     * Student note:  okay to use an additional ArrayList in this method.
     *
     * @param country   the country of interest; must not be null or blank.
     * @param state     the state of interest; must not be null.
     * @param city      the city of interest; must not be null or blank.
     * @return          the list stats for the specified city, or null if not found.
     */
    public CityListStats getCityListStats(String country, String state, String city);

    /**
     * Retrieves an iterator over all weather readings.
     * @return      strongly typed iterator for.
     */
    public Iterator<WeatherReading> iterator();

    /**
     * Does a linear regression analysis on the data, using x = year and y = temperature.
     * Calculates the slope of a best-fit line using the Least Squares method.   For more information
     * on that method, see <a href="https://www.youtube.com/watch?v=P8hT5nDai6A">...</a>
     * Student note:  okay to use two additional ArrayLists in this method.

     * @param readings      array of readings to analyze.  Should typically be readings for a single day over
     *                      a number of years; larger data sets will likely yield better results.  Ignores
     *                      temperature data of -99.0, a default value indicating no temperature data was present.
     *                      Must not be null and must contain at least two readings.
     * @return              slope of best-fit line; positive slope indicates increasing temperatures.
     */
    public double getTemperatureLinearRegressionSlope(WeatherReading[] readings);

    /**
     * Calculates the slope of the best-fit line calculated using the Least Squares method.  For more information
     * on that method, see <a href="https://www.youtube.com/watch?v=P8hT5nDai6A">...</a>
     *
     * @param x     an array of x values; must not be null and must contain at least two elements.
     * @param y     an array of y values; must be the same length as the x array and must not be null.
     * @return      the slope of the best-fit line
     */
    public double calcLinearRegressionSlope(Integer[] x, Double[] y);


}
