package ru.sidey383.cube.paint;

import ru.sidey383.cube.cube.Cube;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class DimensionalPainter implements Painter {

    private final Color edgeColor;

    private final Color vertexColor;

    private final Color backgroundColor;

    private final double[] cudePose;

    private final int width;
    private final int height;

    private final CameraVector camera;

    public DimensionalPainter(Color background , Color edge, Color vertex, CameraVector camera, double[] cubePose, int width, int height) {
        this.edgeColor = edge;
        this.camera = camera;
        this.vertexColor = vertex;
        this.backgroundColor = background;
        this.cudePose = cubePose;
        this.width = width;
        this.height = height;
    }

    @Override
    public BufferedImage getBufferedImage(Cube cube, double[] angels) {
        if(cube.getDimension() < 3)
            throw new IllegalArgumentException("wrong dimensions");
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        List<Cube.Vertex> vertices = cube.getVertexes(angels);
        List<Cube.Edge> edges = cube.getEdges(angels);
        Graphics2D gr = image.createGraphics();
        gr.setColor(backgroundColor);
        gr.fillRect(0, 0, width, height);
        gr.setColor(edgeColor);
        //System.out.println(edges.size());
        for(Cube.Edge edge : edges) {
            int[] dot1 = getDot(
                    camera,
                    plus(edge.a().coordinate().clone(), cudePose),
                    width,
                    height);
            int[] dot2 = getDot(
                    camera,
                    plus(edge.b().coordinate().clone(), cudePose),
                    width,
                    height);
            gr.drawLine(dot1[0], dot1[1], dot2[0], dot2[1]);
        }
        gr.setColor(vertexColor);
        for(Cube.Vertex vertex : vertices) {
            int[] dot = getDot(
                    camera,
                    plus(vertex.coordinate().clone(), cudePose),
                    width,
                    height);
            gr.drawRect(dot[0] - 1, dot[1] - 1, 3, 3);
        }
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static int[] getDot(CameraVector camera, double[] dot, int width, int height) {
        double mult = camera.camNorm / scalarMultiply(camera.camVector, dot);
        multiply(dot, mult);
        minus(dot, camera.camVector());
        double x = scalarMultiply(dot, camera.xVector()) / camera.xNorm;
        double y = scalarMultiply(dot, camera.yVector()) / camera.yNorm;
        return new int[]{width/2 + (int)(x * (width / 2)), height/2 + (int)(y * (height / 2))};
    }

    private static double[] multiply(double[] vec, double vale) {
        for (int i = 0; i < vec.length; i++) {
            vec[i] = vec[i] * vale;
        }
        return vec;
    }

    private static double[] minus(double[] a, double[] b) {
        if (a.length != b.length)
            throw new IllegalStateException("Wrong vector to multiply");
        for (int i = 0; i < a.length; i++) {
            a[i] -= b[i];
        }
        return a;
    }

    private static double[] plus(double[] a, double[] b) {
        if (a.length != b.length)
            throw new IllegalStateException("Wrong vector to multiply");
        for (int i = 0; i < a.length; i++) {
            a[i] += b[i];
        }
        return a;
    }

    private static double scalarMultiply(double[] a, double[] b) {
        if (a.length != b.length)
            throw new IllegalStateException("Wrong vector to multiply");
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    private static double norm(double[] a) {
        double result = 0;
        for (int i = 0; i < a.length; i++)
            result += a[i] * a[i];
        return Math.sqrt(result);
    }

    public record CameraVector(double[] camVector, double[] xVector, double[] yVector, int dimensions, double camNorm, double xNorm, double yNorm) {

        public CameraVector(double[] camVector, double[] xVector, double[] yVector) {
            this(camVector, xVector, yVector, camVector.length, norm(camVector), norm(xVector), norm(yVector));
            if (camVector.length != xVector.length || camVector.length != yVector.length) {
                throw new IllegalArgumentException("Wrong vector sizes");
            }
        }

        public static CameraVector create(int n, double xSize, double ySize) {
            double[] camVec = new double[n];
            double[] xVec = new double[n];
            double[] yVec = new double[n];
            for (int i = 0; i < n; i++) {
                camVec[i] = i == 0 ? 1 : 0;
                xVec[i] = i == 1 ? 1 / xSize : 0;
                yVec[i] = i == 2 ? 1 / ySize : 0;
            }
            return new CameraVector(camVec, xVec, yVec);
        }

    }

}
