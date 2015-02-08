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
      double Tplot = 0.0001;
      String dynamics = args[2];
      double beta = (1.0 /(k * T));

      double magnetism = 0.0;
      double chi = 0.0;

      double[] susceptability = new double[10];
      double[] t = new double[10];

      DrawLattice draw = new DrawLattice(size, beta, dynamics);
      Lattice plot = new Lattice(size);

      switch (dynamics) {
      case "glauber":
        draw.runGlauber();
        plot.flipGlauber(size, 1.0 /(k * Tplot));
        magnetism = plot.getMean();
        for (int i = 0; i < 10; i++) {
          Tplot+= 0.2;
          for (int j = 0; j < 100; j++) {
            plot.flipGlauber(size, 1.0 /(k * Tplot));
          }
          susceptability[i] = 1.0 /(k * Tplot * size * size ) * Stats.standardDeviation(magnetism,
                                                                                        plot.getMean());
          t[i] = Tplot;
        }
        break;
      case "kawazaki":
        draw.runKawazaki();
        plot.flipKawazaki(1.0 /(k * Tplot));
        magnetism = plot.getMean();
        for (int i = 0; i < 10; i++) {
          Tplot+= 0.2;
          for (int j = 0; j < 100; j++) {
            plot.flipGlauber(size, 1.0 /(k * Tplot));
          }
          susceptability[i] = 1.0 /(k * Tplot * size * size ) * Stats.standardDeviation(magnetism,
                                                                                        plot.getMean());
          t[i] = Tplot;
        }
        break;
      }
      try {
        PrintWriter p = IO.writeTo("susceptability.txt");
        ArrayIO.writeDoubles(p, t, susceptability);
      }
      catch (Exception e) {}
      for (int i=0; i < susceptability.length; i++){
        System.out.println(t[i] + " " + susceptability[i]); //TODO print to plotting file
      }
    }
    else {
      System.out.println("\n *** Warning *** Wrong number of Arguments\n\nUsage:\n");
      System.out.println("java Run $size $temperature $dynamics\n");
    }
  }
}

