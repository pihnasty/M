package math.linear;

import org.apache.commons.math3.linear.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolvingLinearSystems {

    public List<Double>  getSolution(List<List<Double>> listCoefficients, List<Double> listfreeTerm ) {
        ///  http://commons.apache.org/proper/commons-math/userguide/linear.html

        double[][]  arrayCoefficients = convertListToArray2(listCoefficients);

        double[] arrayfreeTerm = convertListToArray(listfreeTerm);

        RealMatrix coefficients =
            new Array2DRowRealMatrix(arrayCoefficients,
                false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();


        RealVector constants = new ArrayRealVector( arrayfreeTerm, false);
        RealVector solution = solver.solve(constants);
        return convertRealVectorToList(solution);
    }

    protected static double[] convertListToArray(List<Double> list) {
        double[] array = new double[list.size()];
        Counter counter = new Counter();
        list.forEach(cell -> {
            array[Math.toIntExact(counter.get())] = cell;
            counter.increment();
        });
        return array;
    }

    protected static double[][] convertListToArray2(List<List<Double>> list) {
        double[][] array = new double[list.size()][list.get(0).size()];
        Counter counterRow = Counter.getCounter();
        list.forEach( row -> {
                Counter counterColumn = Counter.getCounter();
                row.forEach ( column -> {
                    (array[Math.toIntExact(counterRow.get())][Math.toIntExact(counterColumn.get())]) = column;
                    counterColumn.increment();
                    }
                );
                counterRow.increment();
            }
        );
        return array;
    }

    protected static List<Double> convertArrayToList(double[] array) {
        List<Double> list = new ArrayList<>();
        for (int i=0; i<array.length;i++) {
            list.add(array[i]);
        }
        return list;
    }

    protected static List<List<Double>> convertArrayToList2(double[][] array) {
        Double [][] arrayDouble = new Double[array.length][array[0].length];
        for (int row=0; row <array.length; row++) {
            for (int column=0; column<array[row].length; column++) {
                arrayDouble[row][column]=array[row][column];
            }
        }

        List<List<Double>> list = new ArrayList<>();
        for (int row=0; row< array.length; row++)  {
            List<Double> listRow = Arrays.asList(arrayDouble[row]);
            list.add(listRow);
        }
        return list;
    }

    protected static List<Double> convertRealVectorToList(RealVector realVector) {
        List<Double> list = new ArrayList<>();
        for(int i=0; i<realVector.getDimension(); i++) {
            list.add(realVector.getEntry(i));
        }
        return list;
    }

}
