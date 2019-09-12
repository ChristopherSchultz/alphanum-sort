# Alphanum Sort

This is a Java implementation of the "alphanum" sort" as described by David Koelle
at http://www.davekoelle.com/alphanum.html

Briefly, the "alphanum sort" is a sorting algorithm for `String` values that sorts them in a natural order when numbers are mixed-in with non-numbers. For example, a standard alphabetic sort would sort some strings like this:

    Example 1
    Example 10
    Example 100
    Example 2
    Example 20
    Example 3
    Example 4

The alphanum sort yields the following order:

    Example 1
    Example 2
    Example 3
    Example 4
    Example 10
    Example 20
    Example 100

I have made some improvements over the sample Java implementation provided on David's web site. Specifically:

 * Removed `StringBuilder` in favor of `String.substring`
 * Only calculate chunk lengths a single time
 * Added caching of chunkified strings
 * Changed to using Unicode-compatible comparisons


This library is a single class implementing the `java.util.Comparator` interface, so you can easily use it to sort lists of `String` values like this:

    ArrayList<String> strings = ...;
    Collections.sort(strings, new AlphanumComparator());

The `AlphanumComparator` caches the chunks of the strings it is sorting as the sorting algorithm works its way through the various combinations of values to compare, so an `AlphanumComparator` instance should not be re-used to sort multiple lists of strings.

## Building

    $ mvn package

## Tests

I have included a unit test for this code in the source. It disagrees with the original examples on David's web site on where the string "Allegia 50B Clasteron" should be located in the sorted list. I'm happy to accept arguments for why my ordering is incorrect, and pull-requests are welcome.

