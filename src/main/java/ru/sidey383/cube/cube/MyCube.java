package ru.sidey383.cube.cube;

import ru.sidey383.cube.matrix.Matrix;
import ru.sidey383.cube.matrix.RotateMatrix;

import java.util.ArrayList;
import java.util.List;

public class MyCube implements Cube {

    private final List<Cube.Vertex> vertices;

    private final List<Cube.Edge> edges;

    private static final int dimension = 10;

    public MyCube() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        for(int i = 0; i < 0b10000000000; i++) {
            vertices.add(new Cube.Vertex(new double[]{
                    ((1&(i>>9)) == 1) ? 1 : -1,
                    ((1&(i>>8)) == 1) ? 1 : -1,
                    ((1&(i>>7)) == 1) ? 1 : -1,
                    ((1&(i>>6)) == 1) ? 1 : -1,
                    ((1&(i>>5)) == 1) ? 1 : -1,
                    ((1&(i>>4)) == 1) ? 1 : -1,
                    ((1&(i>>3)) == 1) ? 1 : -1,
                    ((1&(i>>2)) == 1) ? 1 : -1,
                    ((1&(i>>1)) == 1) ? 1 : -1,
                    ((1&i) == 1) ? 1 : -1
                }));
            //System.out.println(vertices.get(vertices.size()-1));
        }
        for(int i = 0; i < vertices.size(); i++) {
            for(int j = 0; j < vertices.size(); j++) {
                int distance = 0;
                for(int k = 0; k < dimension; k++) {
                    distance += vertices.get(i).coordinate()[k] != vertices.get(j).coordinate()[k] ? 1 : 0;
                }
                if(distance == 1) {
                    edges.add(new Cube.Edge(vertices.get(i), vertices.get(j)));
                }
            }
        }
    }

    @Override
    public List<Vertex> getVertexes(double[] rotate) {
        Matrix matrix = new RotateMatrix(rotate);
        return vertices.stream().map((vertex) -> vertex.rotate(matrix)).toList();
    }

    @Override
    public List<Edge> getEdges(double[] rotate) {
        Matrix matrix = new RotateMatrix(rotate);
        return edges.stream().map((edge) -> edge.rotate(matrix)).toList();
    }

    @Override
    public int getDimension() {
        return dimension;
    }
}
