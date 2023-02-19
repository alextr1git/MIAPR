package com.example.miaprl2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class HelloController {
    private static final double dotSize = 5;
    private static final int objectsAmount = 20000;
    private static final int high = 300;
    private static final int width = 500;
    private int existedClassesCount = 0;

    private Point points[];
   private int Kernells[] = new int[100];
    @FXML
    private Label welcomeText;
    @FXML
    private Canvas myCanvas;
    private GraphicsContext gc;
    @FXML
    private Button calcButton;



    public void initialize() {
        gc = myCanvas.getGraphicsContext2D();
    }
    @FXML
    private void calcButtonHandler(){
        initializeObjects();
        FindSecondKernell();
        tieObjectsToClasses();
        doCalculations();
        drawAllObjects();
    }
private void doCalculations(){
        while(FindOthersKernell() == 1){
            tieObjectsToClasses();
        }

}
    private void drawAllObjects(){
        for (int i = 0; i < points.length; i++){
            switch (points[i].pointClass){
                case 1:
                    gc.setFill(Color.BLUE);
                    break;
                case 2:
                    gc.setFill(Color.GREEN);
                    break;
                case 3:
                    gc.setFill(Color.YELLOW);
                    break;
                case 4:
                    gc.setFill(Color.PURPLE);
                    break;
                case 5:
                    gc.setFill(Color.GOLD);
                    break;
                case 6:
                    gc.setFill(Color.PINK);
                    break;
                case 7:
                    gc.setFill(Color.LIGHTBLUE);
                    break;
                case 8:
                    gc.setFill(Color.BLACK);
                    break;
                case 9:
                    gc.setFill(Color.DEEPSKYBLUE);
                    break;
                case 10:
                    gc.setFill(Color.SANDYBROWN);
                    break;
                default:
                    gc.setFill(Color.GRAY);
                    break;
            }
            gc.fillRect(points[i].X, points[i].Y, dotSize, dotSize);
        }
        for (int j = 1; j <= existedClassesCount; j++){
            gc.setFill(Color.RED);
            gc.fillRect(points[Kernells[j]].X, points[Kernells[j]].Y, dotSize + 3, dotSize + 3);
        }

    }

    private void initializeObjects(){
        Random rand = new Random();
        points = new Point[objectsAmount];

        for (int i = 0; i < points.length; i++) {
            double x = rand.nextDouble(width - 5); // Random number between 10 and 100 (inclusive)
            double y = rand.nextDouble(high - 5); // Random number between 10 and 100 (inclusive)
         //   int pc = rand.nextInt(3);
            points[i] = new Point(x, y);
        }
        int firstKernell = rand.nextInt(objectsAmount + 1);
        Kernells[existedClassesCount] = firstKernell;
        points[firstKernell].pointClass = ++existedClassesCount;
        points[firstKernell].isKernell = true;

    }

    private void FindSecondKernell(){
        double distanceToKernell = 0;
        double maxDTK = 0;
        int newkernell = -1;
        for (int i = 0; i < points.length; i++){
            distanceToKernell = calculateDistance(points[i].X, points[i].Y,points[Kernells[existedClassesCount-1 ]].X,points[Kernells[existedClassesCount-1 ]].Y);
            if (distanceToKernell >= maxDTK) {
                maxDTK = distanceToKernell;
                newkernell = i;
            }
        }
        if (newkernell >= 0){
        points[newkernell].pointClass = ++existedClassesCount;
        Kernells[existedClassesCount] = newkernell;
        points[newkernell].isKernell = true;
        }
    }
    private int FindOthersKernell() {
        double distanceToKernell = 0;
        double maxDTK = 0;
        int newkernell = -1;
        for (int i = 0; i < points.length; i++) {
            if (points[i].isKernell == false) {
                for (int j = 1; j <= existedClassesCount; j++) {
                    if(points[i].pointClass == j){
                    distanceToKernell = calculateDistance(points[i].X, points[i].Y, points[Kernells[j]].X, points[Kernells[j]].Y);
                        if (distanceToKernell >= maxDTK) {
                            maxDTK = distanceToKernell;
                            newkernell = i;
                        }
                    }
                }
            }
        }
if(existedClassesCount == 50){
    return 0;
}
        if (maxDTK >= CalculateDistanceAverage()/2) {
            points[newkernell].pointClass = ++existedClassesCount;
            Kernells[existedClassesCount] = newkernell;
            points[newkernell].isKernell = true;
            return 1;
        }
       return 0;

    }
    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        double xDiff = x2 - x1;
        double yDiff = y2 - y1;
        double distance = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
        return distance;
    }
    private double CalculateDistanceAverage(){

            double totalDistance = 0;
            int numDistances = 0;

            for (int i = 1; i <= existedClassesCount; i++) {
                Point p1 = points[Kernells[i]];
                for (int j = i + 1; j <= existedClassesCount; j++) {
                    Point p2 = points[Kernells[j]];
                    double distance = Math.sqrt((p2.X - p1.X) * (p2.X - p1.X) + (p2.Y - p1.Y) * (p2.Y - p1.Y));
                    totalDistance += distance;
                    numDistances++;
                }
            }

            if (numDistances == 0) {
                return 0;
            }

            double averageDistance = totalDistance / numDistances;
            return averageDistance;


}
    private void tieObjectsToClasses(){
        for(int i = 0; i < points.length; i++){
            double minDistanceToKernell = 10000;
            for (int j = 1; j <= existedClassesCount; j++){
                double newDist = calculateDistance(points[i].X, points[i].Y,points[Kernells[j]].X,points[Kernells[j]].Y);
                if (newDist <= minDistanceToKernell){
                    minDistanceToKernell = newDist;
                    points[i].pointClass = j;
                }

            }
        }

    }
}