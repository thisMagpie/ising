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
import java.io.*;

class Run {

  public static void main(String[] args) {
    double k = 1.0;
    if(args.length == 3) {
      int size = Integer.parseInt(args[0]);
      double T = Double.parseDouble(args[1]);
      double Tplot = 10.0;
      String dynamics = args[2];
      double beta = (1.0 /(k * T));
      double alpha = 1.0 /(k * size * size );

      double M1 = 0.0;
      double M2 = 0.0;
      double chi = 0.0;
      int noSweeps = 100;
      int noMeasurements = 1000;
      double dE = 0.0;

      double[] susceptability = new double[noMeasurements];
      double[] t = new double[noSweeps];
      double[] heatCapacity = new double[noMeasurements];
      double[] errorM = new double[noMeasurements];
      double[] errorE = new double[noMeasurements];

      int noEquilibration = 100;

      DrawLattice draw = new DrawLattice(size, beta, dynamics);
      Lattice plot = new Lattice(size);

      switch (dynamics) {
        case "glauber":
          draw.runGlauber();
          break;
        case "kawazaki":
          draw.runKawazaki();
          break;
      }

      // Plotting stuff
      flipDynamics(plot, dynamics, Tplot, size, k);
      for (int i = 0; i < noSweeps; i++) {
        Tplot-= 0.1;
        t[i] = Tplot;
        int nMeasurement = 0;
        for (int j = 0; j < noEquilibration; j++) {
          flipDynamics(plot, dynamics, Tplot, size, k);
        }
      }
      int nMeasurement = 0;
      for (int j = 1; j <= noMeasurements + 1; j++) {
        flipDynamics(plot, dynamics, Tplot, size, k);
        if(j%10==0) {
          M1 = plot.getSum() / (size * size);
          M2 = (plot.getSum() * plot.getSum())/ (size * size);
          dE = Math.abs(plot.getAveE());
          nMeasurement ++;
          errorM[nMeasurement] = Math.abs(M2 - (M1 * M1));
          susceptability[nMeasurement] =  (alpha / Tplot ) * errorM[nMeasurement];
          errorM[nMeasurement] = Math.sqrt(errorM[nMeasurement]);
          heatCapacity[nMeasurement] = (alpha / (Tplot * Tplot)) * dE;
          errorE[nMeasurement] = Math.sqrt(dE);
        }
      }
      try {
         System.out.println("\nWriting "+ dynamics + " to file... ");
         PrintWriter susc = IO.writeTo("susceptability.dat");
         PrintWriter hc = IO.writeTo("heat_capacity.dat");
         ArrayIO.writeDoubles(susc, t, susceptability, errorM);
         ArrayIO.writeDoubles(hc, t, heatCapacity, errorE);
         System.out.println("\nFile written. ");
         System.exit(0);
      }
      catch (Exception e) {}
    }
    else {
      System.out.println("\n *** Warning *** Wrong number of Arguments\n\nUsage:\n");
      System.out.println("java Run $size $temperature $dynamics\n");
    }
  }

  // Throwaway method just to do this specific job.
  public static void flipDynamics(Lattice plot,
                                  String dynamics,
                                  double T,
                                  int size,
                                  double k) {
    switch (dynamics) {
      case "glauber":
        plot.flipGlauber(size, 1.0 /(k * T));
        break;
      case "kawazaki":
        plot.flipKawazaki(size, 1.0 /(k * T));
        break;
     }
  }
}

