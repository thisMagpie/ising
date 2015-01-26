/**
 * Copyright (c) 2014 Magdalen Berns <m.berns@ed.ac.uk>
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

class Spin {

 /**
  * isFlipped
  *
  * @return Whether or not the spin was flipped.
  */
  public static boolean isFlipped () {
    boolean flip = false;
    if (Math.random() >= 0.5)
      flip = true;
    
    return flip; 
  }

 /**
  * pick
  *
  * @size    The size of the Lattice
  * @return  The coordinate of the picked spin
  */
  public static int pick (int size) {
    return (int) (Math.random() * size); 
  }

  /**
   * deltaE
   *
   * @m       The mth coordinate of the selected spin
   * @n       The nth coordinate of the selected spin
   * @return  The combined energy of the selected spin and it's
   *          top, bottom, left and right nearest neighbours minus
   *          the combined energy of the selected spin flipped plus
   *          its top, bottom, left and right nearest neighbours
   *          i.e. 2.0*J*sum*box[m][n] with J=1
   */
  public static double deltaE(int[][] box, int m, int n) {
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

  /**
   * threshold
   *
   * @deltaE  Difference in energy
   * @beta    beta = 1/kT as a double value
   * @return  Whether the energy has reached the threshold neccessary to flip
   *          its spin, as a boolean.
   */
  public static boolean threshold(double deltaE, double beta){
    boolean result = false;
    if ((deltaE < 0.0) || Math.random() <= Math.exp(-(deltaE * beta)))
      result = true;
    return result;
  }
}

