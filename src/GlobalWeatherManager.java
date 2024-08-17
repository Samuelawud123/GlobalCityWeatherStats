import java.util.*;
import java.io.*;

/**
 * Manages weather data from various locations.
 * Allows reading weather data from a file, searching for specific weather readings,
 * and performing analysis on the data such as calculating linear regression slopes
 * for temperature trends.
 */
public class GlobalWeatherManager implements GlobalWeatherManagerInterface, Iterable<WeatherReading>  {
    private final List<WeatherReading> weatherReadings;

    /**
     * Constructs a GlobalWeatherManager and initializes it with weather data
     * read from the provided file.
     *
     * @param file A {@link File} object pointing to the file containing weather data.
     *             The file is expected to have a specific format for correct parsing.
     * @throws FileNotFoundException if the provided file does not exist.
     */
    public GlobalWeatherManager(File file) throws FileNotFoundException {
        weatherReadings = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNext()) {
                scanner.nextLine();
            }
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                try {
                    WeatherReading reading = new WeatherReading(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            Integer.parseInt(parts[4]),
                            Integer.parseInt(parts[5]),
                            Integer.parseInt(parts[6]),
                            Double.parseDouble(parts[7])
                    );
                    weatherReadings.add(reading);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing line: " + line);
                }
            }
        }
    }

    /**
     * Returns the number of weather readings managed by this instance.
     *
     * @return The size of weather readings list.
     */
    @Override
    public int getReadingCount() {
        return weatherReadings.size();
    }

    /**
     * Retrieves a specific weather reading by index.
     *
     * @param index The index of the weather reading in the list.
     * @return The {@link WeatherReading} at the specified index.
     */
    @Override
    public WeatherReading getReading(int index) {
        return weatherReadings.get(index);
    }

    /**
     * Retrieves a subset of weather readings from a specified index with a specified count.
     *
     * @param index The starting index for retrieving readings.
     * @param count The number of readings to retrieve from the starting index.
     * @return An array of {@link WeatherReading} objects.
     */
    @Override
    public WeatherReading[] getReadings(int index, int count) {
        return weatherReadings.subList(index, index + count).toArray(new WeatherReading[0]);
    }

    /**
     * Retrieves a subset of weather readings that match the specified month and day,
     * starting from a given index with a specified count.
     *
     * @param index The starting index for retrieving readings.
     * @param count The number of readings to evaluate for matching month and day.
     * @param month The month to match in the readings.
     * @param day   The day to match in the readings.
     * @return An array of {@link WeatherReading} objects that match the specified month and day.
     */

    public WeatherReading[] getReadings(int index, int count, int month, int day) {
        if (index < 0 || count < 1 || index + count > weatherReadings.size()) {
            throw new IllegalArgumentException("Invalid index and/or count.");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month value. Month must be between 1 and 12.");
        }
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Invalid day value. Day must be between 1 and 31.");
        }

        List<WeatherReading> filteredReadings = new ArrayList<>();
        Set<Integer> includedYears = new HashSet<>();

        for (int i = index; i < index + count && i < weatherReadings.size(); i++) {
            WeatherReading reading = weatherReadings.get(i);
            if (reading.getMonth() == month && reading.getDay() == day && includedYears.add(reading.getYear())) {
                filteredReadings.add(reading);
            }
        }

        return filteredReadings.toArray(new WeatherReading[0]);
    }

    /**
     * Retrieves statistics about weather readings for a specified city within a state and country.
     *
     * @param country The country of the city to search for.
     * @param state   The state of the city to search for.
     * @param city    The city to search for.
     * @return A {@link CityListStats} object containing statistics of weather readings
     *         for the specified city, or null if no readings are found.
     */
    @Override
    public CityListStats getCityListStats(String country, String state, String city) {
        final String searchCountry = country != null ? country : "";
        final String searchState = state != null ? state.trim() : "";
        final String searchCity = city != null ? city.trim() : "";


        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < weatherReadings.size(); i++) {
            WeatherReading current = weatherReadings.get(i);
            if (current.country().equals(searchCountry) &&
                    (searchState.isEmpty() || current.state().equals(searchState)) &&
                    current.city().equals(searchCity)) {
                if (startIndex == -1) {
                    startIndex = i;
                }
                endIndex = i;
            }
        }

        if (startIndex == -1) {
            return null;
        }

        int count = endIndex - startIndex + 1;
        Set<Integer> uniqueYears = new HashSet<>();
        for (int i = startIndex; i <= endIndex; i++) {
            uniqueYears.add(weatherReadings.get(i).year());
        }

        return new CityListStats(startIndex, count, uniqueYears.stream().mapToInt(Integer::intValue).toArray());
    }


    /**
     * Provides an iterator over the weather readings managed by this instance.
     *
     * @return An {@link Iterator} of {@link WeatherReading} objects.
     */
    @Override
    public Iterator<WeatherReading> iterator() {
        return weatherReadings.iterator();
    }

    /**
     * Calculates the slope of the linear regression line for average temperatures
     * across the provided array of weather readings.
     *
     * @param readings An array of {@link WeatherReading} objects.
     * @return The slope of the linear regression line for average temperatures.
     */
    @Override
    public double getTemperatureLinearRegressionSlope(WeatherReading[] readings) {
        List<Integer> years = new ArrayList<>();
        List<Double> temperatures = new ArrayList<>();
        for (WeatherReading reading : readings) {
            if (reading.avgTemperature() != -99.0) { // Assuming -99.0 is used to indicate missing data.
                years.add(reading.year());
                temperatures.add(reading.avgTemperature());
            }
        }
        Integer[] x = years.toArray(new Integer[0]);
        Double[] y = temperatures.toArray(new Double[0]);
        return calcLinearRegressionSlope(x, y);
    }

    /**
     * Calculates the slope of the linear regression line given arrays of x and y values.
     * This method is used internally for computing the linear regression slope of temperature data.
     *
     * @param x The x values in the linear regression, typically representing years.
     * @param y The y values in the linear regression, typically representing average temperatures.
     * @return The slope of the linear regression line for the given data points.
     */
    @Override
    public double calcLinearRegressionSlope(Integer[] x, Double[] y) {
        int n = x.length;
        double sumX = 0, sumY = 0, sumXY = 0, sumXSquare = 0;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumXSquare += x[i] * x[i];
        }

        return (n * sumXY - sumX * sumY) / (n * sumXSquare - sumX * sumX);
    }
}
