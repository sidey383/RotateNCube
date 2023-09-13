package ru.sidey383.cube.file;

import ru.sidey383.cube.cube.Cube;
import ru.sidey383.cube.paint.Painter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CubeImageGenerator implements FileProducer {

    private final Cube cube;

    private final Painter painter;

    private final double[] angles;

    public CubeImageGenerator(Cube cube, Painter painter, double[] angles) {
        this.cube = cube;
        this.angles = angles;
        this.painter = painter;
    }

    @Override
    public void draw(File f) throws IOException {
        if (!f.exists())
            f.createNewFile();
        BufferedImage image = painter.getBufferedImage(cube, angles);
        try {
            if (!ImageIO.write(image, "png", f)) {
                System.err.println("no appropriate writer is found.");
            }
        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
            return;
        }
        System.out.println("Complete!");
    }
}
