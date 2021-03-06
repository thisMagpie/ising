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
import java.awt.*;
import java.awt.event.*;

class DrawLattice extends Canvas {
  int size;
  int[][] box;
  int pixelDims;
  Graphics graphics;
  Image image;
  int dims;
  double totalM_0, totalM_1;
  double beta;
  boolean timerOn;
  String dynamics;

 /**
  * DrawLattice:
  *              Class constructor
  *
  * @size        The size of the length and width of the bounding box as an int
  * @beta        The value of betaerature for the simulation as a double
  * @nn          A NearestNeighbour object
  */
  DrawLattice (int size, double beta, String dynamics) {
    Frame frame = new Frame("Ising Simulation");
    Button button = new Button("Stop Simulation");

    Panel panel = new Panel();
    this.size = size;
    this.beta = beta;
    this.dynamics = dynamics;

    pixelDims = 4;
    dims = size * pixelDims;

    frame.addWindowListener(new WindowAdapter() {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    frame.add(panel);
    panel.add(this);
    setSize(dims,dims);
    Panel controlPanel = new Panel();
    frame.add(controlPanel,BorderLayout.SOUTH);

    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        timerOn = !timerOn;
      }
    });

    controlPanel.add(button);
    frame.pack();
    image = createImage(dims,dims);
    graphics = image.getGraphics();

    // Initialise box
    box = new int[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        box[i][j] = 1;
        if (Spin.isFlipped()) box[i][j] = -1;
          paintPixels(i,j);
      }
    }
    frame.setVisible(true);
  }

 /**
  * runGlauber:
  *     Method to call for running initialised simulation
  *     CTRL+C to exit.
  */
  public void runGlauber() {
    timerOn = true;

    while (timerOn) {
      Lattice lattice = new Lattice(size); 
      int[] spins = Spin.picks(size, 2);
      // check if energy meets threshold
      if (Dynamics.glauber(Dynamics.metropolis(box, spins[0], spins[1]), beta)) {
        box[spins[0]][spins[1]] = -box[spins[0]][spins[1]];
        paintPixels(spins[0], spins[1]);
        repaint();
      }
    }
  }

 /**
  * runKawazaki:
  *     Method to call for running initialised simulation
  *     CTRL+C to exit.
  */
  public void runKawazaki() {
    timerOn = true;
    int count = 0;
    double aveM = 0;
    double susceptibility = 0;

    while (timerOn) {
      count++;
      // pick random spin in lattice
      int[] spins = Spin.picks(size, 4);


      if (Dynamics.nearestNeighbour(box, spins[0], spins[1], spins[2], spins[3]) &
          Dynamics.nearestNeighbour(box, spins[2], spins[3], spins[0], spins[1])) {
        double dE_0 = Dynamics.metropolis(box, spins[0], spins[1]);
        double dE_1 = Dynamics.metropolis(box, spins[2], spins[3]);

        // check if energy meets threshold
        if (Dynamics.kawazaki((dE_0 + dE_1), beta)) {
          box = Spin.swap(box, spins);
          paintPixels(spins[0], spins[1]);
          paintPixels(spins[2], spins[3]);
          repaint();
        }
      }
    }
  }

 /**
  * @overide in order to paint pixels
  */
  public void paint(Graphics g) {
    g.drawImage(image, 0, 0, dims, dims, this);
  }

 /**
  *  @overide in order to prevent pixel painting lock ups
  */
  public void update(Graphics g) {
    paint(g);
  }

 /**
  * paintPixels:
  *              Method to paint pixel spins onto the screen
  *
  * @i          The ith dims of the box array as an int
  * @j          The jth dims of the box array as an int
  */
  public void paintPixels(int i, int j) {
    if (box[i][j] == 1) graphics.setColor(new Color(240,240,255));
    else graphics.setColor(new Color(30,124,25));
      graphics.fillRect(i * pixelDims,
                        j * pixelDims,
                        pixelDims,
                        pixelDims);
  }
}

