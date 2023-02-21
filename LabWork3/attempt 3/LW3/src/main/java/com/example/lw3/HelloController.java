package com.example.lw3;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.floor;

public class HelloController {

    private GraphicsContext gc;

    @FXML
    final NumberAxis naX = new NumberAxis(0, 700, 100);
    @FXML
    final NumberAxis naY = new NumberAxis(0, 0.002, 0.001);
    @FXML
    public LineChart<Number,Number> chartL = new LineChart(naX, naY);
    @FXML
    public TextField tbFA;
    @FXML
    public TextField tbPO;

    @FXML
    public TextField tbSUM;
 //   public TextField textBox2PO;
    private static final int COUNT_OF_POINTS = 500;
    private static final int GRAPHICS_OFFSET = 200;
    private static final double dotSize = 5;
    private static final double Pc1 = 0.3;
    private static final double Pc2 = 0.7;
    private static final int length = 700;


    public void initialize() {

        naX.setLabel("Xm");
        naY.setLabel("P(Ck)*p(Xm/Ck");

        chartL.setTitle("Probability Density Functions");

        // Create data series for each distribution
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();

        // Set names for the data series
        series1.setName("Class 2");
        series2.setName("Class 1");
        series3.setName("Delta");


        Random rand = new Random();
        var points1 = new double[COUNT_OF_POINTS];
        var points2 = new double[COUNT_OF_POINTS];
        double mx1 = 0;
        double mx2 = 0;


        for (int i = 0; i < COUNT_OF_POINTS; i++) {
            points1[i] = rand.nextDouble(length - GRAPHICS_OFFSET) + GRAPHICS_OFFSET;
            points2[i] = rand.nextDouble(length - GRAPHICS_OFFSET);
            mx1 += points1[i];
            mx2 += points2[i];
        }
        mx1 /= COUNT_OF_POINTS;
        mx2 /= COUNT_OF_POINTS;
        Arrays.sort(points1);
        Arrays.sort(points2);

        double sigma1 = 0;
        double sigma2 = 0;

        for (var i = 0; i < COUNT_OF_POINTS; i++) {
            sigma1 += Math.pow(points1[i] - mx1, 2);
            sigma2 += Math.pow(points2[i] - mx2, 2);
        }

        sigma1 = Math.sqrt(sigma1 / COUNT_OF_POINTS);
        sigma2 = Math.sqrt(sigma2 / COUNT_OF_POINTS);

        double[] yPoints1 = new double[COUNT_OF_POINTS];
        double[] yPoints2 = new double[COUNT_OF_POINTS];

        for (int i = 0; i < COUNT_OF_POINTS; i++) {
            yPoints1[i] = gaussian(points1[i], mx1, sigma1) * Pc1;
            yPoints2[i] = gaussian(points2[i], mx2, sigma2) * Pc2;
        }

        for (int i = 0; i < COUNT_OF_POINTS; i++) {
            series1.getData().add(new XYChart.Data(points1[i], yPoints1[i]));
            series2.getData().add(new XYChart.Data(points2[i], yPoints2[i]));
        }

        double Xdelta = 0;
/*
        for (int i = 0; i < COUNT_OF_POINTS; i++) {
            if (points2[i] >= GRAPHICS_OFFSET){
                if (floor(points1[j]) >= floor(points2[i] + 2 || (floor(points1[j]) >= floor(points2[i] + 2))
                        &&
                        ((floor(yPoints1[i]) == floor(yPoints2[j])) || (floor()))

                ){

                    Xdelta = points2[i];
                }
                j++;
            }
*/
//Center search
        int getTenthanswer = 0;
        int XPosition1 = 0;
        int XPosition2 = 0;
        for (int i = 0; i < COUNT_OF_POINTS; i++) {
            if (points2[i] >= GRAPHICS_OFFSET){
                int j = 0;
                while (floor(points1[j]) <= floor(points2[i])){
                    if (floor(points1[j]) == floor(points2[i])) {
                        if(floor(yPoints1[j]*10000) == floor(yPoints2[i]*10000)){
                            if (getTenthanswer == 10)
                                break;
                            getTenthanswer++;
                            Xdelta = points1[j];
                            XPosition1 = j;
                            XPosition2 = i;
                            System.out.printf("x1:%f x2:%f FOUND \n y1:%f y2:%f\n\n\n", points1[j], points2[i],yPoints1[j]*10000,yPoints2[i]*10000);
                        }

                    }

                    j++;
                    }

            }
        }
        double [] sortedY;
        sortedY = yPoints1;
        Arrays.sort(sortedY);
        for (double i = 0; i < 0.002; i += 0.0005){
            series3.getData().add(new XYChart.Data(Xdelta, i));
        }


        // Add the data series to the chart
        chartL.getData().addAll(series1,series2,series3);
        double sum = falseAlarm(XPosition1, yPoints1) + propuskObnaruzh(XPosition2, yPoints2);
        tbFA.setText(String.valueOf(falseAlarm(XPosition1, yPoints1)));
        tbPO.setText(String.valueOf(propuskObnaruzh(XPosition2, yPoints2)));
        tbSUM.setText(String.valueOf(sum));
    }
    private double gaussian ( double x, double mu, double sigma){
        double factor1 = 1.0 / (sigma * Math.sqrt(2 * Math.PI));
        double factor2 = -0.5 * Math.pow((x - mu) / sigma, 2);
        return factor1 * Math.exp(factor2);
    }

    private double falseAlarm(int posX, double[] ys) {
        double integral = 0;
        for (int i = 0; i < posX; i++){
            integral += ys[i];
        }
        return integral;
    }
    private double propuskObnaruzh(int posX, double[] ys) {
        double integral = 0;
        for (int i = posX; i < ys.length; i++){
            integral += ys[i];
        }
        return integral;
    }


/*
        for(int i = 0; i < COUNT_OF_POINTS; i++){
            gc.setFill(Color.BLUE);
            gc.fillRect(points1[i], yPoints1[i]*MULTCONST, dotSize, dotSize);
            System.out.printf("m1:%f s1:%f x:%f y:%f \n",mx1, sigma1, points1[i],yPoints1[i]*MULTCONST);

            gc.setFill(Color.RED);
            gc.fillRect(points2[i], yPoints2[i]*MULTCONST, dotSize, dotSize);
        }

    }
*/

}
