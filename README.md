# RemoveRepeated
Remove repeated instances of text where the repeated text is exactly one character away.

    ##Back story:
    I (April Monoceros) was using https://manytools.org/hacker-tools/convert-image-to-ansi-art/ to convert an image
    into ANSI-looking art, where the output was HTML/CSS with lots of <span> tags to set the color for each space.

    I had an image that I had cartoonified in GIMP, and cut everything out until there was a plain-white background.

    This made the image look pretty good, but there were a lot of spans that could have covered way more than one
    character -- at least with the white background.

    So I removed a few by hand, before realizing that coding a solution would be way faster, and way more reproducible.

    And this is the end result, which, with my first test, reduced a file from about 800k to about 400k.

    ##Caveats, possible enhancements:
    This is still a very specific solution. It could, instead, take in a span, check to see if the next span
    is the same, and cut out duplicates.

    And, even so, it's still very much a solution to this specific instance of excess items in an HTML file. And,
    roughly when I typed that sentence, it occurred to me that there very well might be a solution out there that does
    this. I did one search, found that people have at least attempted similar things, but don't know beyond that.
