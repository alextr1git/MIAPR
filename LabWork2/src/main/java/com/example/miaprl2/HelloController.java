package com.example.miaprl2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.floor;

public class HelloController {
    private static final double dotSize = 5;
    private static final int objectsAmount = 20000;
    private static final int high = 300;
    private static final int width = 500;
    private int existedClassesCount = 0;
    private Point[] kaKernells;

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

    @FXML
    private void kAHandler(){
        kAverageMethod();
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
    private void KAdrawAllObjects(){
        for (int i = 0; i < points.length; i++){
            switch (points[i].pointClass){
                case 0:
                    gc.setFill(Color.PINK);
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
                    gc.setFill(Color.CHOCOLATE);
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
            gc.fillRect(kaKernells[j].X, kaKernells[j].Y, dotSize + 3, dotSize + 3);
        }

    }

    private void initializeObjects(){
        Random rand = new Random();
        points = new Point[objectsAmount];

        for (int i = 0; i < points.length; i++) {
            double x = rand.nextDouble(width - 20); // Random number between 10 and 100 (inclusive)
            double y = rand.nextDouble(high - 20); // Random number between 10 and 100 (inclusive)
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
    private void tienewObjectsToClasses(){
        for(int i = 0; i < points.length; i++){
            double minDistanceToKernell = 10000;
            for (int j = 1; j <= existedClassesCount; j++){
                double newDist = calculateDistance(points[i].X, points[i].Y,kaKernells[j].X,kaKernells[j].Y);
                if (newDist <= minDistanceToKernell){
                    minDistanceToKernell = newDist;
                    points[i].pointClass = j;
                }

            }
        }

    }

    private void kAverageMethod(){
        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        newKernellsKA();
        tienewObjectsToClasses();
        while (reOrderKernells()) {
            tienewObjectsToClasses();
        }
        KAdrawAllObjects();
    }
    private void newKernellsKA(){
        int large = existedClassesCount + 1;
        kaKernells = new Point[large];
        int[] amountsOfObjectsInClasses = new int[large];
        int[] XSumOfObjectsInClasses = new int[large];
        int[] YSumOfObjectsInClasses = new int[large];
        for (int i = 0; i <points.length; i++){
            switch(points[i].pointClass)
            {
                case 1:
                    amountsOfObjectsInClasses[1]++;
                    XSumOfObjectsInClasses[1] += points[i].X;
                    YSumOfObjectsInClasses[1] += points[i].Y;
                    break;
                case 2:
                    amountsOfObjectsInClasses[2]++;
                    XSumOfObjectsInClasses[2] += points[i].X;
                    YSumOfObjectsInClasses[2] += points[i].Y;
                    break;
                case 3:
                    amountsOfObjectsInClasses[3]++;
                    XSumOfObjectsInClasses[3] += points[i].X;
                    YSumOfObjectsInClasses[3] += points[i].Y;
                    break;
                case 4:
                    amountsOfObjectsInClasses[4]++;
                    XSumOfObjectsInClasses[4] += points[i].X;
                    YSumOfObjectsInClasses[4] += points[i].Y;
                    break;
                case 5:
                    amountsOfObjectsInClasses[5]++;
                    XSumOfObjectsInClasses[5] += points[i].X;
                    YSumOfObjectsInClasses[5] += points[i].Y;
                    break;
                case 6:
                    amountsOfObjectsInClasses[6]++;
                    XSumOfObjectsInClasses[6] += points[i].X;
                    YSumOfObjectsInClasses[6] += points[i].Y;
                    break;
                case 7:
                    amountsOfObjectsInClasses[7]++;
                    XSumOfObjectsInClasses[7] += points[i].X;
                    YSumOfObjectsInClasses[7] += points[i].Y;
                    break;
                case 8:
                    amountsOfObjectsInClasses[8]++;
                    XSumOfObjectsInClasses[8] += points[i].X;
                    YSumOfObjectsInClasses[8] += points[i].Y;
                    break;
                case 9:
                    amountsOfObjectsInClasses[9]++;
                    XSumOfObjectsInClasses[9] += points[i].X;
                    YSumOfObjectsInClasses[9] += points[i].Y;
                    break;
                case 10:
                    amountsOfObjectsInClasses[10]++;
                    XSumOfObjectsInClasses[10] += points[i].X;
                    YSumOfObjectsInClasses[10] += points[i].Y;
                    break;
                default:

                    break;


            }
        }

        for (int j = 1; j <= existedClassesCount; j++){
            kaKernells[j] = new Point(XSumOfObjectsInClasses[j]/amountsOfObjectsInClasses[j], YSumOfObjectsInClasses[j]/amountsOfObjectsInClasses[j]);
           kaKernells[j].isKernell = true;
        }
    }
    private boolean reOrderKernells(){
        int large = existedClassesCount + 1;
        int[] amountsOfObjectsInClasses = new int[large];
        int[] XSumOfObjectsInClasses = new int[large];
        int[] YSumOfObjectsInClasses = new int[large];
        for (int i = 0; i <points.length; i++){
            switch(points[i].pointClass)
            {
                case 1:
                    amountsOfObjectsInClasses[1]++;
                    XSumOfObjectsInClasses[1] += points[i].X;
                    YSumOfObjectsInClasses[1] += points[i].Y;
                    break;
                case 2:
                    amountsOfObjectsInClasses[2]++;
                    XSumOfObjectsInClasses[2] += points[i].X;
                    YSumOfObjectsInClasses[2] += points[i].Y;
                    break;
                case 3:
                    amountsOfObjectsInClasses[3]++;
                    XSumOfObjectsInClasses[3] += points[i].X;
                    YSumOfObjectsInClasses[3] += points[i].Y;
                    break;
                case 4:
                    amountsOfObjectsInClasses[4]++;
                    XSumOfObjectsInClasses[4] += points[i].X;
                    YSumOfObjectsInClasses[4] += points[i].Y;
                    break;
                case 5:
                    amountsOfObjectsInClasses[5]++;
                    XSumOfObjectsInClasses[5] += points[i].X;
                    YSumOfObjectsInClasses[5] += points[i].Y;
                    break;
                case 6:
                    amountsOfObjectsInClasses[6]++;
                    XSumOfObjectsInClasses[6] += points[i].X;
                    YSumOfObjectsInClasses[6] += points[i].Y;
                    break;
                case 7:
                    amountsOfObjectsInClasses[7]++;
                    XSumOfObjectsInClasses[7] += points[i].X;
                    YSumOfObjectsInClasses[7] += points[i].Y;
                    break;
                case 8:
                    amountsOfObjectsInClasses[8]++;
                    XSumOfObjectsInClasses[8] += points[i].X;
                    YSumOfObjectsInClasses[8] += points[i].Y;
                    break;
                case 9:
                    amountsOfObjectsInClasses[9]++;
                    XSumOfObjectsInClasses[9] += points[i].X;
                    YSumOfObjectsInClasses[9] += points[i].Y;
                    break;
                case 10:
                    amountsOfObjectsInClasses[10]++;
                    XSumOfObjectsInClasses[10] += points[i].X;
                    YSumOfObjectsInClasses[10] += points[i].Y;
                    break;
                default:

                    break;

            }

        }

        int oldCheckSum = 0;
        int newCheckSum = 0;
        for (int j = 1; j <= existedClassesCount; j++){
            oldCheckSum += (kaKernells[j].X +kaKernells[j].Y);
            kaKernells[j].X = floor(XSumOfObjectsInClasses[j]/amountsOfObjectsInClasses[j]);
            kaKernells[j].Y = floor(YSumOfObjectsInClasses[j]/amountsOfObjectsInClasses[j]);
            newCheckSum += (kaKernells[j].X +kaKernells[j].Y);
        }
        if (oldCheckSum != newCheckSum){
            return true;

        }
        else{
        return false;}
    }
}