/**
 * Copyright (c) 2015 Magdalen Berns <m.berns@ed.ac.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>
 *
 */

import java.lang.Math.*;

class Stats{
  double mean;

  public static double mean(int[][] box) {
    int mean = 0;
    for (int i = 0; i < box.length - 1; i++) {
      for (int j = 0; j < box.length - 1; j++) {
        mean += box[i][j];
      }
    }
    return mean / (box.length - 1);
  }

  public static double standardDeviation(double mean, double ave) {
    return (mean * mean) - (ave * ave);
  }
}
