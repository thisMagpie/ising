/**
 * Copyright (C) 2014-2015 Magdalen Berns <m.berns@sms.ed.ac.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.*;
import java.util.Scanner;

public class ArrayIO extends IO {

/**
 * This file is a part of a program which serves as a utility for prediction
 * and data analysis of experimental and simulated data
 *
 * @author Magdalen Berns
 * @email <m.berns@sms.ed.ac.uk>
 * @version 1.0
 *
 */

   /**
    * printDoubles      A static method which loops over array elements and spits
    *                   the output to terminal.
    *                   Static method to write data from file with
    * @para,data        The 2D array of doubles to read
    */
    public static void printDoubles(double[] data) {
         for (int i=1; i < data.length + 1; i++) {
            System.out.println(i + " " + data[i - 1]);
        }
    }

   /**
    * writeDoubles
    *                   Static method to write data from file with
    * @param toFile     The PrintWriter instance object to write files with.
    * @para,data        The 2D array of doubles to read
    */
    public static void writeDoubles(PrintWriter toFile, double[] data){
        for (int i = 1; i< data.length + 1; i++) {
            toFile.write(i + " " + data[i - 1]+"\r\n");
        }
        toFile.close();
    }

   /**
    * writeDoubles
    *                   Static method to write data from file with
    * @param toFile     The PrintWriter instance object to write files with.
    * @para,data        The 2D array of doubles to read
    */
    public static void writeDoubles(PrintWriter toFile, double[][] data){
        for(int i=0; i<data.length;i++) {
            toFile.printf("%2.5f %2.5f", data[i][0], data[i][1]);
            toFile.println();
        }
        toFile.close();
    }

   /**
    * writeDoubles
    *                   Static method to write data from file with
    * @param toFile     The PrintWriter instance object to write files with.
    * @para,data        The 2D array of doubles to read
    */
    public static void writeInt(PrintWriter toFile, int[][] data){
        for(int i=0; i < data.length; i++) {
            toFile.printf("%2.5f %2.5f", data[i][0], data[i][1]);
            toFile.println();
        }
        toFile.close();
    }

   /**
    * readDoubles
    *                   Static method to read data from file and return elements as an array.
    *                   storing double instance elements
    * @param scanned
    *                   Scanner to read element from file.
    * @param length
    *                   Length of double array contained in file.
    * @return
    *                   Array of double element instances.
    */
    public static double[] readDoubles(Scanner scanned, int length) {
        double[] data = new double[length];

        for (int i = 0; i < length; i++) {
            data[i] = 0; // init each element in array
            data[i] = IO.skipToDouble(scanned); //read only doubles
        }
        return data;
    }
}
