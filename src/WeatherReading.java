import java.util.Objects;


/**
 * Represents a single weather reading, containing details like region, country,
 * state, city, and the average temperature on a specific date.
 * <p>
 * This record also implements the {@link Comparable} interface, allowing it to be
 * compared and sorted based on various attributes such as country, state, city, and
 * date (year, month, day).
 */
public record WeatherReading(String region, String country, String state, String city, int month, int day, int year, double avgTemperature) implements Comparable<WeatherReading> {

    /**
     * Returns the month of this WeatherReading.
     *
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the day of this WeatherReading.
     *
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Returns the year of this WeatherReading.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }
    /**
     * Compares this WeatherReading to another for ordering.
     * The comparison is primarily based on the country, then state, city,
     * and finally the date (year, month, and day).
     *
     * @param o The WeatherReading object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(WeatherReading o) {
        int countryComp = this.country.compareTo(o.country);
        if (countryComp != 0) return countryComp;

        int stateComp = this.state.compareTo(o.state);
        if (stateComp != 0) return stateComp;

        int cityComp = this.city.compareTo(o.city);
        if (cityComp != 0) return cityComp;

        if (this.year != o.year) return Integer.compare(this.year, o.year);
        if (this.month != o.month) return Integer.compare(this.month, o.month);
        return Integer.compare(this.day, o.day);
    }
    /**
     * Indicates whether some other object is "equal to" this one.
     * The {@code equals} method implements an equivalence relation on non-null
     * object references. Two objects are considered equal if they are of the
     * same type and their country, state, city, year, month, and day are equal.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument;
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        WeatherReading that = (WeatherReading) obj;
        return country.equals(that.country) &&
                state.equals(that.state) &&
                city.equals(that.city) &&
                year == that.year &&
                month == that.month &&
                day == that.day;
    }
    /**
     * Returns a hash code value for the object.
     * This method is supported for the benefit of hash tables such as those
     * provided by {@link java.util.HashMap}.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(country, state, city, year, month, day);
    }
}
