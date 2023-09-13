package ru.sidey383.cube;

import ru.sidey383.cube.cube.Cube;
import ru.sidey383.cube.cube.NCube;
import ru.sidey383.cube.file.CubeFramesCreator;
import ru.sidey383.cube.file.CubeGifCreator;
import ru.sidey383.cube.file.CubeImageGenerator;
import ru.sidey383.cube.file.FileProducer;
import ru.sidey383.cube.paint.DimensionalPainter;
import ru.sidey383.cube.paint.FlatPainter;
import ru.sidey383.cube.paint.Painter;
import ru.sidey383.cube.rotate.AngelProducer;
import ru.sidey383.cube.rotate.PeriodAngelProducer;
import ru.sidey383.cube.rotate.SimpleAngelProducer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int dimensions = 5;
        String painterName = "simple";
        String[] painterArgs = new String[0];
        String angelProducerNames = "simple";
        String[] angelProducerArgs = new String[0];
        String fileProducerName = "gif";
        String[] fileProducerParams = new String[] { "100", "2" };
        int width = 500;
        int height = 500;
        File out = new File("out");
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-angle" -> {
                    i++;
                    switch (args[i]) {
                        case "period" -> {
                            angelProducerNames = "period";
                            i++;
                            angelProducerArgs = new String[] { args[i] };
                        }
                        case "simple" -> {
                            angelProducerNames = "simple";
                        }
                        default -> {
                            System.out.println("Wrong angle producer");
                        }
                    }
                }
                case "-painter" -> {
                    i++;
                    switch (args[i]) {
                        case "dimensional" -> {
                            painterName = "dimensional";
                        }
                        case "simple" -> {
                            painterName = "simple";
                        }
                    }
                }
                case "-dimensions" -> {
                    i++;
                    dimensions = Integer.parseInt(args[i]);
                }
                case "-size" -> {
                    i++;
                    width = Integer.parseInt(args[i]);
                    i++;
                    height = Integer.parseInt(args[i]);
                }
                case "-out" -> {
                    i++;
                    out = new File(args[i]);
                }
                case "-producer" -> {
                    i++;
                    switch (args[i]) {
                        case  "image" -> {
                            fileProducerName = "image";
                        }
                        case "frames" -> {
                            fileProducerName = "frames";
                            i++;
                            fileProducerParams = new String[] { args[i] };
                        }
                        case "gif" -> {
                            fileProducerName = "gif";
                            fileProducerParams = new String[] {args[++i], args[++i]};
                        }
                    }
                }
                default -> System.out.println("Unknown argument " + args[i]);
            }
        }
//        double[] pose = new double[cube.getDimension()];
//        Arrays.fill(pose, 0);
//        pose[0] = 4;
//        Painter painter = new DimensionalPainter(
//                new Color(255, 255 ,255),
//                new Color(0, 0 ,0),
//                new Color(255, 0 ,0),
//                DimensionalPainter.CameraVector.create(cube.getDimension(), 3, 3),
//                pose,
//                2000,
//                2000
//        );
//        painter = new FlatPainter(
//                new Color(255, 255 ,255),
//                new Color(0, 0 ,0),
//                new Color(255, 0 ,0),
//                7,
//                7,
//                2000,
//                2000
//        );
//        AngelProducer angelProducer = new PeriodAngelProducer(cube.getDimension(), 10);
//        int count = 50*2;
//        File dir = new File("cube.gif");
//        System.out.println("Generate output");
//        FileProducer producer = new CubeGifCreator(cube, angelProducer, painter, 2, count);
//        try {
//            producer.draw(dir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Cube cube = new NCube(dimensions);
        AngelProducer angelProducer = null;
        switch (angelProducerNames) {
            case "period" -> angelProducer = new PeriodAngelProducer(dimensions, Integer.parseInt(angelProducerArgs[0]));
            case "simple" -> angelProducer = new SimpleAngelProducer(dimensions);
            default -> {
                System.out.println("Wrong angel producer");
                return;
            }
        }
        Painter painter = null;
        switch (painterName) {
            case "dimensional" -> {
                double[] pose = new double[cube.getDimension()];
                Arrays.fill(pose, 0);
                pose[0] = 4;
                painter = new DimensionalPainter(
                        new Color(255, 255, 255),
                        new Color(0, 0, 0),
                        new Color(255, 0, 0),
                        DimensionalPainter.CameraVector.create(cube.getDimension(), 3, 3),
                        pose,
                        2000,
                        2000
                );
            }
            case "simple" -> {
                painter = new FlatPainter(
                        new Color(255, 255, 255),
                        new Color(0, 0, 0),
                        new Color(255, 0, 0),
                        7,
                        7,
                        2000,
                        2000
                );
            }
            default -> {
                System.out.println("Wrong painter");
                return;
            }
        }
        FileProducer fileProducer = null;
        switch (fileProducerName) {
            case  "image" -> {
                fileProducer = new CubeImageGenerator(cube ,painter, angelProducer.getAngels(0.5));
            }
            case "frames" -> {
                fileProducer = new CubeFramesCreator(cube, angelProducer, painter, Integer.parseInt(fileProducerParams[0]));
            }
            case "gif" -> {
                fileProducer = new CubeGifCreator(cube, angelProducer, painter,  Integer.parseInt(fileProducerParams[1]), Integer.parseInt(fileProducerParams[0]));
            }
            default -> {
                System.out.println("Wrong image producer");
                return;
            }
        }
        try {
            fileProducer.draw(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
