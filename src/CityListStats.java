/**
 * Represents statistical data for a list of cities. This record holds the
 * starting index of a city in a larger dataset, the count of entries for
 * the city, and an array of years for which data is available.
 * <p>
 * Being a record, it inherently provides implementations for methods like
 * {@code equals()}, {@code hashCode()}, and {@code toString()} based on
 * its state, in addition to the canonical constructor and accessor methods
 * for all its fields.
 */
public record CityListStats(int startingIndex, int count, int[] years) {

}
