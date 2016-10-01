# JImageEmbedder
Command line utility for generating Base 64 representation of small image files for embedding small images directly in a webpage. Implemented as a simple command line wrapper of Java's Base64.Encoder class. Utility will either simply generate base64 encoding from an image file, or can also be used to generate the img tag needed for embedding in a webpage.

Copyright (C) 2016 Vincent A. Cicirello.<br>
http://www.cicirello.org/
 
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 
 
USAGE:

Usage instructions and examples assume you are running from an executable jar file.
To run the program, from a command prompt, enter the following:

java -jar JImageEmbedder.jar [options]

OPTIONS:

-help: outputs this usage menu<br>
-i inputImageFile: specifies input image<br>
-o outputFile: optional, if not specified changes extension of input filename to txt<br>
-imgTag: optional, if present, output will be embedded in an html img tag, ready for embedding in webpage<br>
-format imageFormat: optional, such as png, gif, etc, if not specified is based on input image filename extension<br>
-w width: specifies the width for the img tag<br>
-h height: specifies the height for the img tag<br>

EXAMPLES:

Example 1: Encodes image.png in base64 storing result in image.txt <br>
java -jar JImageEmbedder.jar -i image.png

Example 2: Encodes image.png in base64 storing result in file.txt <br>
java -jar JImageEmbedder.jar -i image.png -o file.txt

Example 3: Encodes image.png in base64, and formats an html img tag, storing result in image.txt <br>
java -jar JImageEmbedder.jar -i image.png -imgTag

Example 4: Encodes image.png in base64, and formats an html img tag including specifying the width and height, storing result in image.txt <br>
java -jar JImageEmbedder.jar -i image.png -imgTag -w 200 -h 100

