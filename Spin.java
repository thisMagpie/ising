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
}

