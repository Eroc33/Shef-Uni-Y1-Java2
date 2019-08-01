A java swing app that queries historical data from weather underground. Made for a first year module in 2016.

=== Original README content follows===

Some java8 features have been used in this project so please make sure
you are running it with java8!

To run either:
 * Import iml file into intellij and run the "GUIDriver" main
 OR
 * cd to "out/production/Java2" and run `java uk.ac.sheffield.aca15er.GUIDriver`
 OR (if you wish to compile this yourself but don't have intellij)
 * compile all source files in "src/main/" with javac to an output folder
 * copy "src/resources" contents to that output folder also
If you recompile, but don't copy the resources properly there *will* be null pointer errors!

Note that some of the locations don't have any historical weather data on weather underground, but
there is no definitive list of what airfields do and do not have data so I just used a large list of
ICAO codes I found online without trying to remove ones with no data.
Most british and american airfields do have data, so scroll down to those (EG.. and K...)
to see some graphs.