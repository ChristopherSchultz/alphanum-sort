package net.christopherschultz.alphanumsort;
/*
 * The Alphanum Algorithm is an improved sorting algorithm for strings
 * containing numbers.  Instead of sorting numbers in ASCII order like
 * a standard sort, this algorithm sorts numbers in numeric order.
 *
 * The Alphanum Algorithm is discussed at http://www.DaveKoelle.com
 *
 * Released under the MIT License - https://opensource.org/licenses/MIT
 *
 * Copyright 2019 Christopher Schultz (portions Copyright 2007-2017 David Koelle)
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * NOTE: Modified from David Koelle's original publication in the following ways:
 * - Removed StringBuilder in favor of String.substring
 * - Only calculate chunk lengths a single time
 * - Added caching of chunkified strings
 * - Changed to using Unicode-compatible comparisons
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

/**
 * This is an updated version with enhancements made by Daniel Migowski,
 * Andre Bogus, David Koelle, and Christopher Schultz.
 *
 * NOTE: Modified from David Koelle's original publication in the following ways:
 * - Removed StringBuilder in favor of String.substring
 * - Only calculate chunk lengths a single time
 * - Added caching of chunkified strings
 * - Changed to using Unicode-compatible comparisons
 *
 * @see http://www.davekoelle.com/alphanum.html
 */
public class AlphanumComparator
    implements Comparator<String>, Serializable
{
    private static final long serialVersionUID = -825362512456653634L;

    private final HashMap<String,String[]> _chunks = new HashMap<String,String[]>();
    private transient Collator _collator;
    private Locale _locale;

    public AlphanumComparator()
    {
        this(Locale.getDefault());
    }

    public AlphanumComparator(Locale locale)
    {
        _locale = locale;
        _collator = Collator.getInstance(locale);
        _collator.setStrength(Collator.PRIMARY);
    }

    /** Length of string is passed in for improved efficiency (only need to calculate it once) **/
    private static final String getChunk(final String s, final int slength, int marker)
    {
        final int start = marker;
        char c = s.charAt(marker);
        marker++;
        if (Character.isDigit(c))
        {
            while (marker < slength)
            {
                c = s.charAt(marker);
                if (!Character.isDigit(c))
                    break;
                marker++;
            }
        } else
        {
            while (marker < slength)
            {
                c = s.charAt(marker);
                if (Character.isDigit(c))
                    break;
                marker++;
            }
        }
        return s.substring(start, marker);
    }

    private static String[] getChunks(final String s, final int length)
    {
        final ArrayList<String> chunks = new ArrayList<String>(1);
        int marker=0;
        while(marker < length)
        {
            String chunk = getChunk(s, length, marker);
            chunks.add(chunk.trim());
            marker += chunk.length();
        }

        return chunks.toArray(new String[chunks.size()]);
    }

    // For serialization: restore the Collator from the Locale
    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();

        _collator = Collator.getInstance(_locale);
        _collator.setStrength(Collator.PRIMARY);
    }

    public int compare(final String s1, final String s2)
    {
        if(null == s1)
            if(null == s2)
                return 0;
            else
                return -1;
        else if(null == s2)
            return 1;

        final int s1length = s1.length();
        final int s2length = s2.length();
        String[] s1chunks = _chunks.get(s1);
        if(null == s1chunks)
        {
            s1chunks = getChunks(s1, s1length);
            _chunks.put(s1, s1chunks);
        }
        String[] s2chunks = _chunks.get(s2);
        if(null == s2chunks)
        {
            s2chunks = getChunks(s2, s2length);
            _chunks.put(s2, s2chunks);
        }

        final int s1chunkCount = s1chunks.length;
        final int s2chunkCount = s2chunks.length;
        final int minChunkCount = (s1chunkCount < s2chunkCount ? s1chunkCount : s2chunkCount);

        for(int i=0; i<minChunkCount; ++i)
        {
            final String thisChunk = s1chunks[i];
            final String thatChunk = s2chunks[i];
            final int thisChunkLength = thisChunk.length();
            final int thatChunkLength = thatChunk.length();

            // If both chunks contain numeric characters, sort them numerically
            int result = 0;
            if (Character.isDigit(thisChunk.charAt(0)) && Character.isDigit(thatChunk.charAt(0)))
            {
                // Simple chunk comparison by length.
                result = thisChunkLength - thatChunkLength;
                // If equal, the first different number counts
                if (result == 0)
                {
                    for (int j = 0; j < thisChunkLength; j++)
                    {
                        final int k = j+1;
                        result = _collator.compare(thisChunk.substring(j, k),
                                                   thatChunk.substring(j, k));

                        if (result != 0)
                        {
                            return result;
                        }
                    }
                }
            } else
            {
                result = _collator.compare(thisChunk, thatChunk);
            }

            if (result != 0)
                return result;
        }

        return s1length - s2length;
    }
}
