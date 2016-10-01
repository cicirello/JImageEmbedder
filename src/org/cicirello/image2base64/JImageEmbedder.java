package org.cicirello.image2base64;

import java.util.*;
import java.nio.file.*;
import java.io.*;

/**
 * JImageEmbedder:
 * Command line utility for converting small image files to base64 encoding for embedding small
 * images directly in a webpage.
 *
 * Copyright (C) 2016 Vincent A. Cicirello.
 * http://www.cicirello.org/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *
 * JImageEmbedder:
 *
 * Command line utility for generating Base 64 representation of small image files for embedding small
 * images directly in a webpage. Implemented as a simple command line wrapper of Java's Base64.Encoder class.
 * Utility will either simply generate base64 encoding from an image file, or can also be used to generate
 * the img tag needed for embedding in a webpage.
 * 
 *
 * @author Vincent A. Cicirello
 * @version 10.01.2016
 *
 */
public class JImageEmbedder {
	
	public static void main(String[] args) {
		
		String inputFile = null;
		String outputFile = null;
		boolean imgTag = false;
		String format = null;
		int w = -1;
		int h = -1;
		
		outputCopyrightNotice();
		
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-help")) {
				outputUsage();
				System.exit(0);
			} else if (args[i].equals("-i")) {
				i++;
				if (inputFile != null) processInvalidArgs("Too many input files specified.");
				inputFile = args[i];
			} else if (args[i].equals("-o")) {
				i++;
				if (outputFile != null) processInvalidArgs("Too many output files specified.");
				outputFile = args[i];
			} else if (args[i].equals("-imgTag")) {
				imgTag = true;
			} else if (args[i].equals("-format")) {
				i++;
				if (format != null) processInvalidArgs("Too many image formats specified.");
				format = args[i];
			} else if (args[i].equals("-w")) {
				i++;
				w = Integer.parseInt(args[i]);
			} else if (args[i].equals("-h")) {
				i++;
				h = Integer.parseInt(args[i]);
			} else {
				processInvalidArgs("Unexpected command line argument found."); 
			}
		}
		
		if (inputFile==null) {
			processInvalidArgs("Missing input filename."); 
		}
		if (outputFile==null) {
			int dot = inputFile.lastIndexOf('.');
			if (dot < 0) outputFile = inputFile + ".txt";
			else outputFile = inputFile.substring(0,dot) + ".txt";
		}
		if (format==null) {
			int dot = inputFile.lastIndexOf('.');
			format = inputFile.substring(dot+1);
		}
		String tag = null;
		if (imgTag) {
			tag = "<img src=\"data:image/" + format + ";base64,";
		}
		
		Base64.Encoder enc = Base64.getEncoder();
		try {
			Path p = FileSystems.getDefault().getPath("", inputFile);
			byte[] data = Files.readAllBytes(p);
			String s = enc.encodeToString(data);
			
			PrintWriter out = null;
			try {
				out = new PrintWriter(outputFile);
			} catch (FileNotFoundException ex) {
				System.out.println("I/O error generating output file: " + ex);
				System.exit(0);
			}
			
			if (imgTag) out.print(tag); 
			out.print(s);
			if (imgTag) {
				out.print("\"");
				if (w > 0) out.print(" width=\"" + w + "\"");
				if (h > 0) out.print(" height=\"" + h + "\"");
				out.print(">");
			}
			
			out.flush();
			out.close();
			
			System.out.println("STATUS:\n\tConversion of your image to base64 encoding is complete.");
		} catch (Exception ex) {
			System.out.println("Error while processing input file: " + ex);
			System.exit(0);
		}
	}
	
	private static void outputCopyrightNotice() {
		System.out.println("JImageEmbedder Copyright (C) 2016 Vincent A. Cicirello");
		System.out.println("The source code is available within the jar file or also from");
		System.out.println("http://www.cicirello.org/\n");
		System.out.println("This program comes with ABSOLUTELY NO WARRANTY; This is free software,");
		System.out.println("and you are welcome to redistribute it under certain conditions.");
		System.out.println("This program is distributed in the hope that it will be useful,");
		System.out.println("but WITHOUT ANY WARRANTY; without even the implied warranty of");
		System.out.println("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the");
		System.out.println("GNU General Public License for more details.");
		System.out.println("https://www.gnu.org/licenses/gpl-3.0.html\n");
	}
	
	private static void outputUsage() {
		System.out.println("JImageEmbedder [options]");
		System.out.println("   -help: outputs this usage menu");
		System.out.println("   -i inputImageFile: specifies input image");
		System.out.println("   -o outputFile: optional, if not specified changes extension of input filename to txt");
		System.out.println("   -imgTag: optional, if present, output will be embedded in an html img tag, ready for embedding in webpage");
		System.out.println("   -format imageFormat: optional, such as png, gif, etc, if not specified is based on input image filename extension");
		System.out.println("   -w width: specifies the width for the img tag");
		System.out.println("   -h height: specifies the height for the img tag");
	}
	
	private static void processInvalidArgs(String message) {
		System.out.println(message);
		outputUsage();
		System.exit(0);
	}
}