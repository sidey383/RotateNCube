package ru.sidey383.cube.file;

import ru.sidey383.cube.cube.Cube;
import ru.sidey383.cube.paint.Painter;
import ru.sidey383.cube.rotate.AngelProducer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CubeFramesCreator implements FileProducer {

    private final Cube cube;

    private final AngelProducer angelProducer;

    private final Painter painter;

    private final int frameCount;


    public CubeFramesCreator(Cube cube, AngelProducer angelProducer, Painter painter, int frameCount) {
        this.cube = cube;
        this.angelProducer = angelProducer;
        this.painter = painter;
        this.frameCount = frameCount;
    }


    @Override
    public void draw(File dir) throws IOException {
        if (!dir.mkdirs()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                file.delete();
            }
        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 64, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(frameCount));
        for(int n = 0; n < frameCount; n++) {
            final int i = n;
            threadPoolExecutor.execute(() -> {
                BufferedImage image = painter.getBufferedImage(cube, angelProducer.getAngels(((double) i) / frameCount));
                File outputfile = new File(dir, "image" + i + ".png");
                try {
                    outputfile.createNewFile();
                    if (!ImageIO.write(image, "png", outputfile)) {
                        System.err.println("no appropriate writer is found.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }
        Thread t = new Thread(() ->
                FileProducer.printStatus(threadPoolExecutor)
        );
        try {
            t.start();
            threadPoolExecutor.shutdown();
            if (threadPoolExecutor.awaitTermination(10, TimeUnit.MINUTES)) {
                System.out.println("Complete");
            } else {
                System.out.println("Timeout");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        t.interrupt();
    }
}
