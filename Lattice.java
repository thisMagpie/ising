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
  double beta, mean, dE;
  int[][] box;

  public Lattice(int size) {
    mean = 0.0;
    this.size = size;
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
  *     Method to call for plotting Glauber dynamics TODO finish
  */
  public void flipGlauber(int size, double beta) {
    int count = 0;
    // pick random spin in lattice
    int m = Spin.pick(size);
    int n = Spin.pick(size);

    while (count < size * size) {
      count ++;
      // check if energy meets threshold
      dE = Dynamics.metropolis(box, m, n);
      if (Dynamics.glauber(dE, beta)) {
        box[m][n] = -box[m][n];
      }
    }
    this.mean = Stats.mean(box);
  }

  public double getMean() {
    return mean;
  }

  public double getDE() {
    return dE;
  }
 /**
  * flipKawazaki:
  *     Method to call for plotting Kawazaki dynamics
  */
  public void flipKawazaki(double beta) {
    int count = 0;
    // pick two random spins in lattice
    int m_0 = Spin.pick(size);
    int n_0 = Spin.pick(size);
    int m_1 = Spin.pick(size);
    int n_1 = Spin.pick(size);

    while (count < size * size) {
      count ++;
      if (Dynamics.nearestNeighbour(box, m_0, n_0, m_1, n_1) &
          Dynamics.nearestNeighbour(box, m_1, n_1, m_0, n_0)) {

        double dE_0 = Dynamics.metropolis(box, m_0, n_0);
        double dE_1 = Dynamics.metropolis(box, m_1, n_1);
              // check if energy meets threshold
        dE = dE_0 + dE_1;

        // check if energy meets threshold
        if (Dynamics.kawazaki(dE, beta)) {
          box[m_1][n_1] = -box[m_1][n_1];
          box[m_0][n_0] = -box[m_0][n_0];
        }
      }
    }
    this.mean = Stats.mean(box);
  }
}

