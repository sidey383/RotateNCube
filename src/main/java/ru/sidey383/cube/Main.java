package ru.sidey383.cube;

import ru.sidey383.cube.cube.Cube;
import ru.sidey383.cube.cube.NCube;
import ru.sidey383.cube.file.CubeGifCreator;
import ru.sidey383.cube.file.FileProducer;
import ru.sidey383.cube.paint.DimensionalPainter;
import ru.sidey383.cube.paint.FlatPainter;
import ru.sidey383.cube.paint.Painter;
import ru.sidey383.cube.rotate.AngelProducer;
import ru.sidey383.cube.rotate.PeriodAngelProducer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static final int dimensions = 4;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Cube cube = new NCube(dimensions);
        System.out.println("Create " + dimensions + " dimensions cube in " + (System.currentTimeMillis() - start) + "ms");
        double[] pose = new double[cube.getDimension()];
        Arrays.fill(pose, 0);
        pose[0] = 4;
        Painter painter = new DimensionalPainter(
                new Color(255, 255 ,255),
                new Color(0, 0 ,0),
                new Color(255, 0 ,0),
                DimensionalPainter.CameraVector.create(cube.getDimension(), 3, 3),
                pose,
                2000,
                2000
        );
        painter = new FlatPainter(
                new Color(255, 255 ,255),
                new Color(0, 0 ,0),
                new Color(255, 0 ,0),
                7,
                7,
                2000,
                2000
        );
        AngelProducer angelProducer = new PeriodAngelProducer(cube.getDimension(), 10);
        int count = 50*2;
        File dir = new File("cube.gif");
        System.out.println("Generate output");
        FileProducer producer = new CubeGifCreator(cube, angelProducer, painter, 2, count);
        try {
            producer.draw(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
