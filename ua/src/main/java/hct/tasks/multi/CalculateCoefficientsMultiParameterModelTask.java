package hct.tasks.multi;

import hct.tasks.base.BaseTask;
import logging.LoggerP;
import main.ProjectManager;
import math.Combinatorics;
import math.MathP;
import models.MultiParameterModel;
import settings.Settings;

import java.util.*;
import java.util.logging.Level;

public class CalculateCoefficientsMultiParameterModelTask extends BaseTask {

    public CalculateCoefficientsMultiParameterModelTask (ProjectManager projectManager) {
        super(projectManager);
        LoggerP.write(Level.INFO, "CalculateCoefficientsMultiParameterModelTask started...");
    }

    @Override
    protected Void call() {
        calculateCoefficientsMultiParameterModel(
            projectManager.getPlanExperiment().getOutputFactors(),
            projectManager.getPlanExperiment().getInputFactors(),
            projectManager.getPlanExperiment().getParametersOfMultipleRregressionModel()) ;
        return null;
    }

    public void calculateCoefficientsMultiParameterModel(List<String> outputFactors, List<String> inputFactors, Map<String,String> parametersOfMultipleRregressionModel ) {

        List<List<String>> covarianceCoefficients = projectManager.getProject().getCovarianceCoefficients();
        List<List<String>> characteristicsSeparatedRawDataTable = projectManager.getProject().getCharacteristicsSeparatedRawDataTable();
        List<List<String>> separatedRawDataTable = projectManager.getProject().getSeparatedRawDataTable();
        List<List<String>> multiModelDimensionlessKoefficients = new ArrayList<>();
        List<List<String>> multiModelDimensionKoefficients= new ArrayList<>();
        Map<String, List<List<String>>> residualsTables = new TreeMap<>();

        MultiParameterModel model = new  MultiParameterModel(
            covarianceCoefficients
            , characteristicsSeparatedRawDataTable
            , separatedRawDataTable
            , inputFactors
        );

        List<String> headerMultiModelDimensionlessParameters = Arrays.asList(
            Settings.Values.MODEL_NUMBER_FACTOR,
            Settings.Values.OUTPUT_FACTOR,
            Settings.Values.NUMBER_OBSERVATIONS,
            Settings.Values.NUMBER_CONSTRAINTS,
            Settings.Values.RESIDUAL_MEAN,
            Settings.Values.SSE,
            Settings.Values.MSE,
            Settings.Values.stDeviationMSE
        );

        final List<String> headerResidualsTable = Arrays.asList(
            Settings.Values.NUMBER_SAMPLE,
            Settings.Values.SORTED_RESIDUAL,
            Settings.Values.RESIDUAL_MEAN,
            Settings.Values.STANDARD_DEVIATION,
            Settings.Values.SEQUENCE_NUMBER,
            Settings.Values.CUMULATIVE_PROBABILITY,
            Settings.Values.Z_VALUE,
            Settings.Values.EXPECTED_RESIDUAL,
            Settings.Values.ACTUAL_SAMPLE_ELEMENT_VALUE,
            Settings.Values.EXPECTED_SAMPLE_ELEMENT_VALUE
        );


        multiModelDimensionlessKoefficients.add(
            model.fillDimensionLessHeader(headerMultiModelDimensionlessParameters,inputFactors)
        );


        List<String> headerMultiModelDimensionParameters = new ArrayList<>(headerMultiModelDimensionlessParameters);
        headerMultiModelDimensionParameters.add(Settings.Values.COEFFICIENT_A);
        multiModelDimensionKoefficients.add(
            model.fillDimensionHeader(headerMultiModelDimensionParameters,inputFactors)
        );


        List<List<Long>> variantsOfNumber = Combinatorics.getVariants(
            Integer.parseInt(parametersOfMultipleRregressionModel.get(Settings.Keys.NUMBER_REGRESSORS))
            , inputFactors.size());

        MathP.Counter titleCounter = MathP.getCounter(1,1);

        LoggerP.write(Level.INFO, "Number of the model "+variantsOfNumber.size());

        final int delta;
        if (variantsOfNumber.size()<1000) {
            if (variantsOfNumber.size()<100) {
                delta = 1;
            } else {
                delta = 10;
            }
        } else {
            delta = variantsOfNumber.size()/1000;
        }



        outputFactors.forEach(
            outputFactorCategoryIdAndName -> {
                variantsOfNumber.forEach(
                    rowOfNumber -> {
                        boolean isCalculated = model.calculateKoefficients(outputFactorCategoryIdAndName, combinatoricsSetFactors(rowOfNumber,inputFactors));

                        if(isCalculated) {
                            Integer count = titleCounter.get();
                            String numberOfModel =count.toString();

                            List<String> rowLessKoefficients = new ArrayList<>();
                            model.fillDefaultDimensionLess(outputFactorCategoryIdAndName, numberOfModel, rowLessKoefficients);
                            model.fillDimensionLess(rowOfNumber, rowLessKoefficients);
                            multiModelDimensionlessKoefficients.add(rowLessKoefficients);

                            List<String> rowKoefficients = new ArrayList<>();
                            List<List<String>> residualsTable = new ArrayList<>();
                            residualsTable.add(model.fillHeaderCommon(headerResidualsTable, new ArrayList<>()));
                            model.fillDefaultDimension(outputFactorCategoryIdAndName, numberOfModel, rowKoefficients);
                            model.fillDimension(rowOfNumber, rowKoefficients, residualsTable);
                            multiModelDimensionKoefficients.add(rowKoefficients);
                            residualsTables.put(
                                rowKoefficients.get(0)
                                , residualsTable);

                            if (count / delta * delta == count) {
                                LoggerP.write(Level.INFO, "Calculated the model with number " + numberOfModel);
                            }

                        }
                    }
                );
            });

        projectManager.getProject().setMultiModelDimensionlessKoefficients(multiModelDimensionlessKoefficients);
        projectManager.getProject().setMultiModelDimensionKoefficients(multiModelDimensionKoefficients);
        projectManager.getProject().setResidualsTables(residualsTables);
        LoggerP.write(Level.INFO, "Number of the model "+variantsOfNumber.size());


    }

    private List<String> combinatoricsSetFactors (List<Long> rowOfNumber, List<String> inputFactors) {
        List<String> ccmbinatoricsSetFactors = new ArrayList<>();

        rowOfNumber.forEach(
            number -> ccmbinatoricsSetFactors.add(inputFactors.get(Math.toIntExact(number)))
        );
        return  ccmbinatoricsSetFactors;
    }

}
