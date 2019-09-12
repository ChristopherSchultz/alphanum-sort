package net.christopherschultz.alphanumsort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestAlphanumComparator
{
    @Test
    public void testSimpleSort() {
        // Ensure that "normal" sorting works with strings containing *NO* numbers
        String[] strings = new String[] {
                "When, in the course of human events, ...",
                "A string",
                "", // empty
                "The quick brown fox jumps over the lazy dog"
        };
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList(strings));
        ArrayList<String> test = new ArrayList<String>(Arrays.asList(strings));

        // We expect that "natural" sorting of strings will match our own
        Collections.sort(expected);
        Collections.sort(test, new AlphanumComparator());

        Assert.assertEquals("Sorting is not correct", expected, test);
    }

    @Test
    public void testAlphanumericSort() {
        String[] strings = new String[] {
                "File 7",
                "File 1",
                "File 2",
                "File 17",
                "File 25"
        };
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("File 1");
        expected.add("File 2");
        expected.add("File 7");
        expected.add("File 17");
        expected.add("File 25");

        ArrayList<String> test = new ArrayList<String>(Arrays.asList(strings));

        Collections.sort(test, new AlphanumComparator());

        Assert.assertEquals("Sorting is not correct", expected, test);
    }

    @Test
    public void testAlphanumericSort1() {

        String[] filenames = new String[] {
                "z1.doc",
                "z10.doc",
                "z100.doc",
                "z101.doc",
                "z102.doc",
                "z11.doc",
                "z12.doc",
                "z13.doc",
                "z14.doc",
                "z15.doc",
                "z16.doc",
                "z17.doc",
                "z18.doc",
                "z19.doc",
                "z2.doc",
                "z20.doc",
                "z3.doc",
                "z4.doc",
                "z5.doc",
                "z6.doc",
                "z7.doc",
                "z8.doc",
                "z9.doc"
        };

        String[] expectedOrder = new String[] {
                "z1.doc",
                "z2.doc",
                "z3.doc",
                "z4.doc",
                "z5.doc",
                "z6.doc",
                "z7.doc",
                "z8.doc",
                "z9.doc",
                "z10.doc",
                "z11.doc",
                "z12.doc",
                "z13.doc",
                "z14.doc",
                "z15.doc",
                "z16.doc",
                "z17.doc",
                "z18.doc",
                "z19.doc",
                "z20.doc",
                "z100.doc",
                "z101.doc",
                "z102.doc"
        };

        ArrayList<String> test = new ArrayList<String>(Arrays.asList(filenames));
        List<String> expected = Arrays.asList(expectedOrder);

        Collections.sort(test, new AlphanumComparator());

        Assert.assertEquals("Sorting is not correct", expected, test);
    }

    @Test
    public void testAlphanumericSort2() {

        String[] filenames = new String[] {
                "1000X Radonius Maximus",
                "10X Radonius",
                "200X Radonius",
                "20X Radonius",
                "20X Radonius Prime",
                "30X Radonius",
                "40X Radonius",
                "Allegia 50 Clasteron",
                "Allegia 500 Clasteron",
                "Allegia 50B Clasteron",
                "Allegia 51 Clasteron",
                "Allegia 6R Clasteron",
                "Alpha 100",
                "Alpha 2",
                "Alpha 200",
                "Alpha 2A",
                "Alpha 2A-8000",
                "Alpha 2A-900",
                "Callisto Morphamax",
                "Callisto Morphamax 500",
                "Callisto Morphamax 5000",
                "Callisto Morphamax 600",
                "Callisto Morphamax 6000 SE",
                "Callisto Morphamax 6000 SE2",
                "Callisto Morphamax 700",
                "Callisto Morphamax 7000",
                "Xiph Xlater 10000",
                "Xiph Xlater 2000",
                "Xiph Xlater 300",
                "Xiph Xlater 40",
                "Xiph Xlater 5",
                "Xiph Xlater 50",
                "Xiph Xlater 500",
                "Xiph Xlater 5000",
                "Xiph Xlater 58"
        };

        String[] expectedOrder = new String[] {
                "10X Radonius",
                "20X Radonius",
                "20X Radonius Prime",
                "30X Radonius",
                "40X Radonius",
                "200X Radonius",
                "1000X Radonius Maximus",
                "Allegia 6R Clasteron",
                "Allegia 50B Clasteron", // <---- I disagree about the proper place to sort this
                "Allegia 50 Clasteron",

                //"Allegia 50B Clasteron", // <---- I disagree about the proper place to sort this
                "Allegia 51 Clasteron",
                "Allegia 500 Clasteron",
                "Alpha 2",
                "Alpha 2A",
                "Alpha 2A-900",
                "Alpha 2A-8000",
                "Alpha 100",
                "Alpha 200",
                "Callisto Morphamax",
                "Callisto Morphamax 500",
                "Callisto Morphamax 600",
                "Callisto Morphamax 700",
                "Callisto Morphamax 5000",
                "Callisto Morphamax 6000 SE",
                "Callisto Morphamax 6000 SE2",
                "Callisto Morphamax 7000",
                "Xiph Xlater 5",
                "Xiph Xlater 40",
                "Xiph Xlater 50",
                "Xiph Xlater 58",
                "Xiph Xlater 300",
                "Xiph Xlater 500",
                "Xiph Xlater 2000",
                "Xiph Xlater 5000",
                "Xiph Xlater 10000"
        };

        ArrayList<String> test = new ArrayList<String>(Arrays.asList(filenames));
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList(expectedOrder));

        Collections.sort(test, new AlphanumComparator());

        Assert.assertEquals("Sorting is not correct", expected, test);
    }

    @Test
    public void testAlphanumSort3() {
        String[] strings = new String[] {
                "Example 1",
                "Example 10",
                "Example 100",
                "Example 2",
                "Example 20",
                "Example 3",
                "Example 4"
        };

        String[] expectedOrder = new String[] {
                "Example 1",
                "Example 2",
                "Example 3",
                "Example 4",
                "Example 10",
                "Example 20",
                "Example 100"
        };

        ArrayList<String> test = new ArrayList<String>(Arrays.asList(strings));
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList(expectedOrder));

        Collections.sort(test, new AlphanumComparator());

        Assert.assertEquals("Sorting is not correct", expected, test);

    }
}
