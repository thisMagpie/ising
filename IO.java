/**
 * Copyright (C) 2012-2015 Magdalen Berns
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.Scanner;
import java.io.*;

public class IO {

/**
 * This file provides a library of static methods for handling common
 * IO requirements.
 *
 * @author Magdalen Berns
 * @version 1.0
 * @since 1.0
 */

 /**
  * skipToDouble
  *                   Method to read only numbers in decimal form from a given input file
  *                   This makes it possible to input data with handy human readable labels 
  * @param scanner
  *                   Scanned characters from an input file
  * @return 
  *                   The first decimal number (separated by spaces) found as a double
  */
  public static double skipToDouble(Scanner scanner) {
    while (scanner.hasNext() && !scanner.hasNextDouble()) {
        scanner.next();
    }
    return scanner.hasNextDouble() ? scanner.nextDouble() : Double.NaN;
  }

 /**
  * file
  *                   Method to read only numbers in decimal form from a given input file
  *                   This makes it possible to input data with handy human readable labels 
  * @param name
  *                   name of file
  * @return 
  *                   If exception is not thrown it will return the name of the file as
  *                   a String instance.
  */
  public static FileReader file(String name) throws FileNotFoundException {
    return new FileReader(name);
  }

 /**
  * typedInput
  *                   Method to read only numbers in decimal form from a given input file
  *                   This makes it possible to input data with handy human readable labels 
  * @return
  *                   returns line that has been typed into the terminal prompt by the user
  *                   as a BufferedReader instance object.
  */
  public static String typedInput() throws IOException {
    BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
    return keyIn.readLine();
  }

 /**
  * skipToInt
  *                   Method to read only numbers in integer form from a given input file
  *                   This makes it possible to input data with handy human readable labels 
  * @param scanner
  *                   Scanned characters from an input file
  * @return 
  *                   The first integer number (separated by spaces) found as a int
  */
  public static int skipToInt(Scanner scanner) {
    while (scanner.hasNext() && !scanner.hasNextInt()) {
      scanner.next();
    }
    return scanner.hasNextInt() ? scanner.nextInt() : (int) Double.NaN; //workaround
  }

 /**
  * abuse             Call this every time wrong thing is typed and make an abusive statement
  *                   by printing it to the terminal and then exiting the program
  */
  public static void abuse() {
    System.out.println("Invalid entry.");
    System.out.println("Abusive statement.");
    System.exit(0);
  }

 /**
  * scanFrom
  *                   Method to query whether file is there and if so create a scanner onject
  *                   So that content can be read
  * @param name
  *                   name and relative path of file to be scanned from
  * @return
  *                   Scanner object for reading elements from file with specified name
  */
  public static Scanner scanFrom(String name) throws FileNotFoundException {
    return new Scanner(new BufferedReader(new FileReader(name)));
  }

 /**
  * writeTo
  *                   Method to create filename if not already in relative path
  *                   or overwrite existing file contents if filename is already in existance.
  * @param name
  *                   name and relative path of file to be written to.
  * @return
  *                   Printwriter object for writing elements to file with specified name
  * @throws 
  *                   FileNotFoundException
  */
  public static PrintWriter writeTo(String name) throws FileNotFoundException {
    return new PrintWriter(name);
  }
}
