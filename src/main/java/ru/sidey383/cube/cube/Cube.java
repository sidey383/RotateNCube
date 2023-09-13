package ru.sidey383.cube.cube;

import ru.sidey383.cube.matrix.Matrix;

import java.util.List;

public interface Cube {

    List<Vertex> getVertexes(double[] rotate);

    List<Edge> getEdges(double[] rotate);

    int getDimension();

    record Vertex(double[] coordinate) {

        public Vertex rotate(Matrix matrix) {
            return new Vertex(matrix.multiple(coordinate));
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[ ");
            for(int i = 0; i < coordinate.length; i++) {
                builder.append(coordinate[i]);
                if(i != coordinate.length -1)
                    builder.append(", ");
            }
            builder.append(" ]");
            return builder.toString();
        }

    }

    record Edge(Vertex a, Vertex b) {

        public Edge rotate(Matrix matrix) {
            return new Edge(a.rotate(matrix), b.rotate(matrix));
        }

        public String toString() {
            return "[ " + a.toString() + " , " + b.toString()+ " ]";
        }

    }

}
