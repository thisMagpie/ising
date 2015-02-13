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
      double Tplot = 3.0;
      String dynamics = args[2];
      double beta = (1.0 /(k * T));
      double alpha = 1.0 /(k * size * size );

      double M1 = 0.0;
      double M2 = 0.0;
      double E1 = 0.0;
      double E2 = 0.0;
      int noSweeps = 20;
      int noMeasurements = 10000;
      double dE = 0.0;

      double[] susceptability = new double[noSweeps];
      double[] t = new double[noSweeps];
      double[] heatCapacity = new double[noSweeps];
      double[] errorM = new double[noSweeps];
      double[] errorE = new double[noSweeps];

      int noEquilibration = 1000;

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

      for (int i = 0; i < noSweeps; i++) {
        flipDynamics(plot, dynamics, Tplot, size, k);
        // initialise measurement variables
        int nMeasurement = 0;
        M1 = 0.0;
        M2 = 0.0;
        E1 = 0.0;
        E2 = 0.0;
        Tplot-= 0.1;
        t[i] = Tplot;

        // Equilibration for loop
        for (int j = 0; j < noEquilibration; j++) {
          flipDynamics(plot, dynamics, Tplot, size, k);
        }

        // Start taking measurements
        for (int j = 0; j <= noMeasurements; j++) {
          flipDynamics(plot, dynamics, Tplot, size, k);
         if(j%10==0) {
            M1 += plot.getSum();
            M2 += (plot.getSum() * plot.getSum());
            E1 += plot.getAveE();
            E2 += plot.getAveE()*plot.getAveE();
            nMeasurement ++;
          }
        }

        // divide average by number of measurements
        E1 /= (double)(nMeasurement);
        E2 /= (double)(nMeasurement);
        M1 /= (double)(nMeasurement);
        M2 /= (double)(nMeasurement);

        //for a given temperature specific heat is (E2-E1*E1)/(t[i]*t[i])
        heatCapacity[i] = (E2 - E1*E1)/(Tplot*Tplot);
        susceptability[i] =  (M2 - (M1 * M1))/Tplot;
        errorM[i] = Math.sqrt(M2 - M1*M1);
        errorE[i] = Math.sqrt(E2 - E1*E1);
      }

      try {
         System.out.println("\nWriting "+ dynamics + " to file... ");
         PrintWriter susc = IO.writeTo(dynamics+"_susceptability.dat");
         PrintWriter hc = IO.writeTo(dynamics+"_heat_capacity.dat");
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

