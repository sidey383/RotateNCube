package ru.sidey383.cube.paint;

import ru.sidey383.cube.cube.Cube;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class FlatPainter implements Painter {

    private final Color edgeColor;

    private final Color vertexColor;

    private final Color backgroundColor;

    private final double xSize;
    private final double ySize;
    private final int width;
    private final int height;

    public FlatPainter(Color background , Color edge, Color vertex, double xSize, double ySize, int width, int height) {
        this.backgroundColor = background;
        this.edgeColor = edge;
        this.vertexColor = vertex;
        this.xSize = xSize;
        this.ySize = ySize;
        this.width = width;
        this.height = height;
    }

    @Override
    public BufferedImage getBufferedImage(Cube cube, double[] angels) {
        if(cube.getDimension() < 3)
            throw new IllegalArgumentException("wrong dimensions");
        List<Cube.Vertex> vertices = cube.getVertexes(angels);
        List<Cube.Edge> edges = cube.getEdges(angels);
        BufferedImage image = new BufferedImage(width, height, Image.SCALE_FAST);
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(edgeColor);
        //System.out.println(edges.size());
        for(Cube.Edge edge : edges) {
            int[] dot1 = getDot(edge.a().coordinate(), xSize, ySize, width, height);
            int[] dot2 = getDot(edge.b().coordinate(), xSize, ySize, width, height);
            graphics2D.drawLine(dot1[0], dot1[1], dot2[0], dot2[1]);
        }
        graphics2D.setColor(vertexColor);
        for(Cube.Vertex vertex : vertices) {
            int[] dot = getDot(vertex.coordinate(), xSize, ySize, width, height);
            graphics2D.fillRect(dot[0]-1, dot[1]-1, 3, 3);
        }
        return image;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private double[] getDot(Cube.Edge edge) {
        double a = 0;
        if (((edge.a().coordinate()[2] - edge.b().coordinate()[2])) == 0) {
            return null;
        } else {
            a = edge.b().coordinate()[2] / (edge.a().coordinate()[2] - edge.b().coordinate()[2]);
        }
        for (int i = 3; i < edge.a().coordinate().length; i++) {
            if (a != edge.b().coordinate()[2] / (edge.a().coordinate()[2]- edge.b().coordinate()[2])) {
                return null;
            }
        }
        return new double[]{a*(edge.a().coordinate()[0]- edge.b().coordinate()[0]), a*(edge.a().coordinate()[1]- edge.b().coordinate()[1])};
    }

    private int[] getDot(double[] dot, double xSize, double ySize, int width, int height) {
        return new int[]{width/2 + (int)(dot[0]*width*2/xSize), height/2 + (int)(dot[1]*height*2/ySize)};
    }

}
