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
 */
import java.lang.Math.*;

class Dynamics {

  /**
   * metropolis
   *
   * @box     The box of size, containing the spins as 2D integer array
   * @m       The mth coordinate of the selected spin
   * @n       The nth coordinate of the selected spin
   * @return  The combined energy of the selected spin and it's
   *          top, bottom, left and right nearest neighbours minus
   *          the combined energy of the selected spin flipped plus
   *          its top, bottom, left and right nearest neighbours
   *          i.e. 2.0*J*sum*box[m][n] with J=1
   */
  public static double metropolis(int[][] box, int m, int n) {
    int sum = 0;
    int max = box.length-1;
    // spacial symmetry
    if (m == 0)
      sum += box[max][n]; // no more room to decrease, check on other side of box
    else
      sum += box[m - 1][n]; // look left

    if (m == max)
      sum += box[0][n]; // no more room to increase, check on other side of box
    else
      sum += box[m + 1][n]; // look right

    if (n == 0)
      sum += box[m][max];  // no more room to decrease, check on other side of box
    else
      sum += box[m][n - 1]; // look upwards

    if (n == max)
      sum += box[m][0]; // no more room to increase, check on other side of box
    else
      sum += box[m][n + 1]; // look downwards

    //spin flipped state - original state i.e. 2.0*J*sum*box[m][n] with J=1
    return 2.0 * sum *  box[m][n];
  }

  public static boolean nearestNeighbour(int[][] box, int m_0, int n_0, int m_1, int n_1) {
    boolean result = false;
    int sum = 0;
    int max = box.length-1;
    // spacial symmetry
    if (m_0 == 0) {
      if (box[max][n_0] == box[m_1][n_1])
        result = true;
    } else if (box[m_0 - 1][n_0] == box[m_1][n_1]) {
      result = true;
    }

    if (n_0 == 0) {
      if (box[m_0][max] == box[m_1][n_1])
      result = true;
    } else if (box[m_0][n_0 - 1] == box[m_1][n_1]) {
      result = true;
    }

    if (m_0 == max) {
      if (box[0][n_0] == box[m_1][n_1])
        result = true;
    } else if (box[m_0 + 1][n_0] == box[m_1][n_1]) {
      result = true;
    }


    if (n_0 == max) {
      if (box[m_0][0] == box[m_1][n_1])
        result = true;
    } else if (box[m_0][n_0 + 1] == box[m_1][n_1]) {
        result = true;
    }

    return result;
  }

  /**
   * glauber
   *
   * @deltaE  Difference in energy
   * @beta    beta = 1/kT as a double value
   * @return  Whether the energy has reached the threshold neccessary to flip
   *          its spin, as a boolean.
   */
  public static boolean glauber(double deltaE, double beta){
    boolean result = false;

    if ((deltaE <= 0.0 && beta == 0) ||
    Math.random() < Math.exp(-(deltaE * beta)))
      result = true;
    return result;
  }

  /**
   * kawazaki
   *
   * @deltaE  Difference in energy
   * @beta    beta = 1/kT as a double value
   * @return  Whether the energy has reached the threshold neccessary to flip
   *          its spin, as a boolean.
   */
  public static boolean kawazaki(double deltaE, double beta) {
    boolean result = false;
    if ((deltaE <= 0 && beta == 0) ||
        (Math.random() < Math.exp(-(deltaE * beta))/(1 + Math.exp(-(deltaE * beta)))))
      result = true;

    return result;
  }
}

