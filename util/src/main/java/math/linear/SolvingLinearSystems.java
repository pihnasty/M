package math.linear;

import org.apache.commons.math3.linear.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Double> multiply (List<List<Double>> wS, List<Double> xS) {
        List<List<Double>> x2S = fillColumn(xS);

        double[][]  wAs = convertListToArray2(wS);
        double[][]  xAs = convertListToArray2(x2S);
        RealMatrix wmAs = MatrixUtils.createRealMatrix(wAs);
        RealMatrix xmAs = MatrixUtils.createRealMatrix(xAs);
        RealMatrix result_wmAs_xmWs = wmAs.multiply(xmAs);


        List<Double> result = new ArrayList<>();
        convertArrayToList2(result_wmAs_xmWs.getData()).forEach(
            row->result.add(row.get(0))
        );
        return result;
    }

    public static List<List<Double>> multiplyColumnToRow (List<Double> yS, List<Double> xS) {
        List<List<Double>> y2S = fillColumn(yS);
        List<List<Double>> x2S = fillRow(xS);


        double[][]  yAs = convertListToArray2(y2S);
        double[][]  xAs = convertListToArray2(x2S);
        RealMatrix ymAs = MatrixUtils.createRealMatrix(yAs);
        RealMatrix xmAs = MatrixUtils.createRealMatrix(xAs);
        RealMatrix result_ymAs_xmWs = ymAs.multiply(xmAs);
        return convertArrayToList2(result_ymAs_xmWs.getData());
    }

    private static List<List<Double>> fillColumn(List<Double> xS) {
        List<List<Double>> x2S = new ArrayList<>();
        xS.forEach(value -> {
            List<Double> element = new ArrayList<>();
            element.add(value);
            x2S.add(element);
        });
        return x2S;
    }

    private static List<List<Double>> fillRow(List<Double> xS) {
        List<List<Double>> x2S = new ArrayList<>();
        x2S.add(xS);
        return x2S;
    }

    public static List<List<Double>> proportionalChangeMatrix (List<List<Double>> wS, List<Double> proportionalChangeColumn) {
        int i1Max = wS.size();
        int i2Max = wS.get(0).size();

        List<List<Double>>  changeWs = new ArrayList<>();

        for(int i1=0; i1<i1Max; i1++) {
            List<Double> row = new ArrayList<>();
            for(int i2=0; i2<i2Max; i2++) {
                row.add(wS.get(i1).get(i2)*proportionalChangeColumn.get(i1));
            }
            changeWs.add(row);
        }
        return changeWs;
    }


    public static List<List<Double>> inverseMatrix(List<List<Double>> wS) {
        double[][] matrixData = convertListToArray2(wS);
        RealMatrix realMatrix = MatrixUtils.createRealMatrix(matrixData);
        RealMatrix inverseRealMatrix = new LUDecomposition(realMatrix).getSolver().getInverse();
        return convertArrayToList2(inverseRealMatrix.getData());
    }

    public static List<List<Double>> transponeMatrix(List<List<Double>> wS) {
        int i1Max = wS.size();
        int i2Max = wS.get(0).size();

        List<List<Double>>  transponeWs = new ArrayList<>();

        for (int i2 = 0; i2 < i2Max; i2++) {
            List<Double> row = new ArrayList<>();
            for (int i1 = 0; i1 < i1Max; i1++) {
                row.add(wS.get(i1).get(i2));
            }
            transponeWs.add(row);
        }
        return transponeWs;
    }

    public static List<Double> summaryValuesRow(List<List<Double>> listWs) {
        return listWs.stream().map(row->(row.stream().mapToDouble(value->value).sum())).collect(Collectors.toList());
    }

    /**
     * result = m1 -m2
     */
    public static List<List<Double>> subtract(List<List<Double>> m1,List<List<Double>> m2) {
        double[][]  m1s = convertListToArray2(m1);
        double[][]  m2s = convertListToArray2(m2);
        RealMatrix m1As = MatrixUtils.createRealMatrix(m1s);
        RealMatrix m2As = MatrixUtils.createRealMatrix(m2s);
        RealMatrix result = m1As.subtract(m2As);
        return convertArrayToList2(result.getData());
    }


}
