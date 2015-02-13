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

class Lattice {

  String dynamics;
  int size;
  double beta, mean, sum, dE;
  double[] totalE;
  int[][] box;
  int[] mn;

  public Lattice(int size) {
    mean = 0.0;
    this.size = size;
    totalE = new double[size *size];
    box = new int[size][size];
    dE = 0.0;

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        box[i][j] = 1;
        if (Spin.isFlipped()) box[i][j] = -1;
      }
    }
  }

 /**
  * plotGlauber:
  *     Method to call for plotting Glauber dynamics
  */
  public void flipGlauber(int size, double beta) {
    int count = 0;
    mn = new int[2];

    // pick random spin in lattice
    mn = Spin.picks(size, 2);

    while (count < size * size) {

      // check if energy meets threshold
      dE = Dynamics.metropolis(box, mn[0], mn[1]);
      //totalE[count] = dE;
      if (Dynamics.glauber(dE, beta)) {
        box[mn[0]][mn[1]] = -box[mn[0]][mn[1]];
      }
      count ++;
    }

    mean = Stats.mean(box);
    sum = Stats.sum(box);
  }

  public double getMean() {
    return mean;
  }

  public double getSum() {
    return sum;
  }

  public double getAveE() {
    double sum = 0.0;
    for (int i=0; i < size; i++){
      for(int j=0; j < size; j++){
        int iup=i+1;
        int jup=j+1;
        if(i==size-1) iup=0;
        if(j==size-1) jup=0;
          sum -= box[i][j]*(box[iup][j]+box[i][jup]);
      } 
    }
    return sum;
  }

  public int[] getSelectedSpin() {
    return mn;
  }

 /**
  * flipKawazaki:
  *               Method to call for plotting Kawazaki dynamics
  */
  public void flipKawazaki(int size, double beta) {
    int count = 0;
    mn = new int[4];
    // pick two random spins in lattice
    mn = Spin.picks(size, mn.length);
    while (count < size * size) {
      if (Dynamics.nearestNeighbour(box, mn[0], mn[1], mn[2], mn[3]) &
          Dynamics.nearestNeighbour(box, mn[2], mn[3], mn[0], mn[1])) {

        double dE_0 = Dynamics.metropolis(box, mn[0], mn[1]);
        double dE_1 = Dynamics.metropolis(box, mn[2], mn[3]);
        // check if energy meets threshold
        dE = dE_0 + dE_1;
        totalE[count] = dE;

        // check if energy meets threshold
        if (Dynamics.kawazaki(dE, beta)) {
          box = Spin.swap(box, mn);
        }
      }
      count ++;
    }
    this.mean = Stats.mean(box);
  }
}

