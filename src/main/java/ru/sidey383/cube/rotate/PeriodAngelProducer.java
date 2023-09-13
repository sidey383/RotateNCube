package ru.sidey383.cube.rotate;

import java.util.Arrays;

public class PeriodAngelProducer implements AngelProducer {

    private final int period;

    private final int dimensions;

    public PeriodAngelProducer(int dimensions, int period) {
        this.period = period;
        this.dimensions = dimensions;
    }

    @Override
    public double[] getAngels(double pose) {
        double[] angels = new double[dimensions*(dimensions-1)/2];
        for (int i = 0; i < dimensions*(dimensions-1)/2; i++) {
            angels[i] = pose * (1 + (i % period)) * Math.PI / 2;
        }
        return angels;
    }
}
