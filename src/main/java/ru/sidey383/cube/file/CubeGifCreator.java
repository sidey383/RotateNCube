package ru.sidey383.cube.file;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import ru.sidey383.cube.cube.Cube;
import ru.sidey383.cube.paint.Painter;
import ru.sidey383.cube.rotate.AngelProducer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CubeGifCreator implements FileProducer {

    private final Cube cube;

    private final AngelProducer angelProducer;

    private final Painter painter;

    private final int frameTime;

    private final int frameCount;

    public CubeGifCreator(Cube cube, AngelProducer angelProducer, Painter painter, int frameTime, int frameCount) {
        this.cube = cube;
        this.angelProducer = angelProducer;
        this.painter = painter;
        this.frameTime = frameTime;
        this.frameCount = frameCount;
    }

    public void draw(File f) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(f)) {
            GifWriter writer = new GifWriter(frameCount, outputStream, frameTime);
            ThreadPoolExecutor calculatePool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 64, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(frameCount));
            for (int n = 0; n < frameCount; n++) {
                final int i = n;
                calculatePool.execute(() -> {
                    BufferedImage image = painter.getBufferedImage(cube, angelProducer.getAngels(((double) i) / frameCount));
                    writer.addImage(i, image);
                });

            }
            try {
                calculatePool.shutdown();
                if (calculatePool.awaitTermination(10, TimeUnit.MINUTES)) {
                    writer.complete();
                } else {
                    writer.interrupt();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class GifWriter {

        private final BufferedImage[] buffer;

        private int current = 0;

        private final AnimatedGifEncoder encoder;

        private final Thread t;

        private final StatusPrinter statusPrinter;

        public GifWriter(int frameCount, FileOutputStream os, int frameTime) {
            this.buffer = new BufferedImage[frameCount];
            this.encoder = new AnimatedGifEncoder();
            this.encoder.start(os);
            this.encoder.setRepeat(0);
            this.encoder.setDelay(frameTime);
            this.statusPrinter = new ConsoleStatusPrinter();
            t = new Thread(this::task);
            t.start();
        }

        public synchronized void addImage(int num, BufferedImage image) {
            buffer[num] = image;
            if (num == current) {
                this.notify();
            }
        }

        private synchronized void task() {
            try {
                do {
                    while (buffer[current] == null)
                        this.wait();
                    encoder.addFrame(buffer[current]);
                    current++;
                    statusPrinter.printStatus(current, buffer.length);
                } while (!Thread.currentThread().isInterrupted() && buffer.length > current);
                if (buffer.length > current) {
                    statusPrinter.interrupted();
                    encoder.finish();
                } else {
                    statusPrinter.complete();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                statusPrinter.interrupted();
            }
        }

        public synchronized void complete() {
            this.notify();
            t.interrupt();
        }


        public void interrupt() {
            t.interrupt();
        }

    }

}
