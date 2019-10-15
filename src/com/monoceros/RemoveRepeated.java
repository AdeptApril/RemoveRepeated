package com.monoceros;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
    Program to remove a repeated segment of text, so long as the text is one character away from the next iteration.

    Back story:
    I (April Monoceros) was using https://manytools.org/hacker-tools/convert-image-to-ansi-art/ to convert an image
    into ANSI-looking art, where the output was HTML/CSS with lots of <span> tags to set the color for each space.

    I had an image that I had cartoonified in GIMP, and cut everything out until there was a plain-white background.

    This made the image look pretty good, but there were a lot of spans that could have covered way more than one
    character -- at least with the white background.

    So I removed a few by hand, before realizing that coding a solution would be way faster, and way more reproducible.

    And this is the end result, which, with my first test, reduced a file from about 800k to about 400k.

    Caveats, possible enhancements:
    This is still a very specific solution. It could, instead, take in a span, check to see if the next span
    is the same, and cut out duplicates.

    And, even so, it's still very much a solution to this specific instance of excess items in an HTML file. And,
    roughly when I typed that sentence, it occurred to me that there very well might be a solution out there that does
    this. I did one search, found that people have at least attempted similar things, but don't know beyond that.
 */

public class RemoveRepeated {

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        String fileNameIn = args[0];
        String fileNameOut = args[1];
        String searchStr = args[2];
        System.out.println(args.length);
        System.out.println(fileNameIn);
        System.out.println(fileNameOut);
        System.out.println(searchStr);
        if (args.length != 3 || fileNameIn == null || fileNameOut == null || searchStr == null) {
            System.out.println("Error getting started. Please use this syntax:");
            System.out.println("removeRepeated filenameIN filenameOUT searchString");
            System.out.println("Make sure to use backslashes to escape special characters and have the entire thing in quotes");
            System.out.println("Your string was read in as: " + searchStr);
            return;
        }
        BufferedReader br = null;
        Path fileOut = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileNameIn)));
            fileOut = Paths.get(fileNameOut);
        } catch (Exception ex) {
            System.out.println("Error opening file: " + ex.toString());
            return;
        }

        String line;
        System.out.println("Processing file");
        try {
            while ((line = br.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                int beginIndex = 0;
                int endIndex = line.length();
                int currIndex = -1;
                int nextIndex = -1;
                //Looking for: "</span><span style="color:rgb(255 , 255 , 255);">"
                //String searchStr=  "</span><span style=\"color:rgb(255 , 255 , 255);\">";
                //searchStr set on file input
                while(beginIndex < endIndex) {
                    //System.out.println("beginIndex: " + beginIndex);
                    //System.out.println("currIndex: " + currIndex);
                    //System.out.println("nextIndex: " + nextIndex);
                    //System.out.println("endIndex: " + endIndex);
                    //Write out the part up to where the searchStr is first found
                    currIndex = line.indexOf(searchStr, beginIndex);
                    if (currIndex != -1)
                    {
                        sb.append(line.substring(beginIndex, currIndex));
                        //See if there's another occurrence, and if not, break out and add the rest of the line.
                        //If there's no occurrence at all, this'll still trigger.
                        nextIndex = line.indexOf(searchStr, currIndex + searchStr.length());
                    }
                    if(nextIndex == -1){
                        if(currIndex == -1)
                            sb.append(line.substring(beginIndex, endIndex));
                        else
                            sb.append(line.substring(currIndex, endIndex));
                        break;
                    }
                    //If we get here, there were two instances of the string
                    //We have to determine if they're next to each other.
                    //If not, append everything up to nextIndex.
                    //If so, append only the character between.
                    //In either case, advance beginIndex
                    if(nextIndex - 1 - searchStr.length() - currIndex != 0)
                        sb.append(line.substring(currIndex, nextIndex));
                    else
                        sb.append(line.charAt(nextIndex-1));
                    beginIndex = nextIndex;
                }
                lines.add(sb.toString());
            }
        }
        catch(Exception ex)
        {
            System.out.println("BufferedReaderProcessing error: " + ex.toString());
            return;
        }
        if (br != null) {
            try {
                Files.write(fileOut, lines, StandardCharsets.UTF_8);
                br.close();
            } catch (Exception ex) {
                System.out.println("Error closing file: " + ex.toString());
                return;
            }
        }
        System.out.println(fileNameOut + " is now ready.");
        return;
    }
}