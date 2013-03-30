# Intercalates: A web-service for iCircles #

iCircles is an innovative Euler diagram drawing algorithm.  Intercalates
is a web-service in front of iCircles.

## Using Intercalates ##

Assuming you have play-framweork installed and you can run the intercalates
service, then calling the service is as follows

<code>
$ curl -X POST -d @venn2.json http://localhost:9000 --header "Content-Type:application/json"
</code>

where the "@venn2.json" is a file containing the JSON description of your diagram.  An example,
copied from the iCircles documentation, is:

<code>
{"AbstractDiagram" :
   {
    "Version"     : 0,
    "Contours"    : ["Contour1", "Contour2"],
    "Zones"       : [{"in" : ["Contour1"]}, {"in" : ["Contour2"]}, {"in" : ["Contour1", "Contour2"]}],
    "ShadedZones" : [{"in" : ["Contour1"]}],
    "Spiders"     : [{"name" : "spider1", "habitat" : [{"in" : ["Contour1"]}]}],
   }
 }
</code>