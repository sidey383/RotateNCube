package ru.sidey383.cube.paint;

import ru.sidey383.cube.cube.Cube;

import java.awt.image.BufferedImage;

public interface Painter {

    BufferedImage getBufferedImage(Cube cube, double[] angels);

    int getWidth();

    int getHeight() ;

}
