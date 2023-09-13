package ru.sidey383.cube.cube;

import ru.sidey383.cube.matrix.Matrix;
import ru.sidey383.cube.matrix.RotateMatrix;

import java.util.ArrayList;
import java.util.List;

public class NCube implements Cube {

    private final List<Vertex> vertices;

    private final List<Cube.Edge> edges;

    private final int dimension;

    public NCube(int dimension) {
        this.dimension = dimension;
        this.edges = new ArrayList<>();
        Vertex[] vertices = new Vertex[1<<dimension];
        for(int i = 0; i < (1L<<dimension); i++) {
            Vertex ver = new Vertex(new double[dimension]);
            for(int j = 0; j < dimension; j++) {
                ver.coordinate()[j] = ((1&(i>>j)) == 1) ? 1 : -1;
            }
            vertices[i] = ver;
            //System.out.println(vertices.get(vertices.size()-1));
        }
        for (int i = 0; i < vertices.length; i++) {
            for (int num = 0; num < dimension; num++) {
                if (((i >> num) & 1) == 0) {
                    int pair = i + (1 << num);
                    Vertex v1 = vertices[i];
                    Vertex v2 = vertices[pair];
                    edges.add(new Edge(v1, v2));
                }
            }
        }
        this.vertices = List.of(vertices);

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
