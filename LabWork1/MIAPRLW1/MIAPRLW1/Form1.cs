using System.Drawing;
using System.Globalization;
using System.Windows.Forms;
using System.Windows.Forms.VisualStyles;

namespace MIAPRLW1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        public class MyPoints
        {
            public int X;
            public int Y;

            public bool isCenter;
            public int PointClass;

            public MyPoints()
            {
                X = 0;
                Y = 0;          
            }
        }

        public MyPoints[] points = new MyPoints[1000];

        public int[] classes = new int[3];
        public int[] elementsClassCount = new int[3];

        public Point[] centerClasses = new Point[3];
        public Random rnd = new Random();

        public bool stopFlag = false;



        public void initObjects()
        {
            for (int i = 0; i < points.Length; i++)
            {
                points[i] = new MyPoints();
                points[i].X = rnd.Next(0, 300);
                points[i].Y = rnd.Next(0, 300);
                points[i].PointClass = 0;
            }
        }
        public void initClassCentres(int classamount, MyPoints[] pointsarr)
        {
            int amount = 0;
            while (amount < classamount)
            {
                int chosen = rnd.Next(0, points.Length - 1);
                if (pointsarr[chosen].isCenter == false)
                {
                    centerClasses[amount].X = pointsarr[chosen].X;
                    centerClasses[amount].Y = pointsarr[chosen].Y;
                    amount++;
                }
            }
        }
        public void countClasses(MyPoints[] pointsarr, int classamount)
        {
            for (int j = 0; j < classamount; j++)
                elementsClassCount[j] = 0;

            for (int i = 0; i < pointsarr.Length; i++)
            {
                switch (pointsarr[i].PointClass)
                {
                    case 1:
                        elementsClassCount[0]++;
                        break;
                    case 2:
                        elementsClassCount[1]++;
                        break;
                    case 3:
                        elementsClassCount[2]++;
                        break;
                    default:                       
                        break;

                }
            }
        }
        public int findAnswer(MyPoints[] pointsarr, int classamount, int[] countsClassElem)
        {
            int checkSumX = 0;
            int checkSumY = 0;

            int[] sumsX = new int[classamount];
            int[] sumsY = new int[classamount];

            for (int i = 0; i < classamount; i++)
            {
                for (int j = 0; j < pointsarr.Length; j++)
                {
                    if (pointsarr[j].PointClass == i + 1)
                    {
                        sumsX[i] += pointsarr[j].X;
                        sumsY[i] += pointsarr[j].Y;
                    }

                }
                // new centres points
           

                centerClasses[i].X = sumsX[i] / countsClassElem[i];
                centerClasses[i].Y = sumsY[i] / countsClassElem[i];

                checkSumX += centerClasses[i].X;
                checkSumY += centerClasses[i].Y;




            }
            return checkSumX + checkSumY;
        }
        public void tiePointsToClasses(MyPoints[] pointsarr, int classamount)
        {
            
            for (int i = 0; i < points.Length; i++)
            {
                double minDist = 100000;
                for (int j = 0; j < classamount; j++)
                {
                   double distance = Math.Sqrt(Math.Pow((Math.Abs(centerClasses[j].X - pointsarr[i].X)), 2) + Math.Pow((Math.Abs(centerClasses[j].Y - pointsarr[i].Y)), 2));
                    if (distance < minDist) {
                        pointsarr[i].PointClass = j + 1;
                        minDist = Math.Round(distance);
                    }
                }
            
            }
        }
        
        public void DrawPoints(Graphics graphics, Point[] centres)
        {

            
            for (int i = 0; i < points.Length; i++)
            {
                switch (points[i].PointClass)
                {
                    case 1:
                        graphics.FillRectangle(Brushes.Blue, points[i].X, points[i].Y, 5, 5);
                        break;
                    case 2:
                        graphics.FillRectangle(Brushes.Red, points[i].X, points[i].Y, 5, 5);
                        break;
                    case 3:
                        graphics.FillRectangle(Brushes.Green, points[i].X, points[i].Y, 5, 5);
                        break;
                    default:
                        graphics.FillRectangle(Brushes.Black, points[i].X, points[i].Y, 5, 5);
                        break;

                }

                for (int j = 0; j < centres.Length; j++)
                {
                    graphics.FillRectangle(Brushes.Black, centres[j].X, centres[j].Y, 10, 10);
                }
                
            }
        }

        public int SumCheck(int amountOfClasses, Point [] kernells)
        {
            int SumX = 0;
            int SumY = 0;

            for (int i = 0; i < amountOfClasses; i++)
            {
                SumX += kernells[i].X;
                SumY += kernells[i].Y;
            }
            return SumX + SumY;

        }
    
        private void button1_Click(object sender, EventArgs e)
        {
            initObjects();
            Graphics graphics = pictureBox1.CreateGraphics();
            initClassCentres(classes.Length, points);
            tiePointsToClasses(points, classes.Length);
            countClasses(points, classes.Length);
            DrawPoints(graphics, centerClasses);


        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            Graphics graphics = pictureBox2.CreateGraphics();
            graphics.Clear(Color.White);
            
            while (SumCheck(classes.Length, centerClasses) != findAnswer(points, classes.Length, elementsClassCount))
            {
                tiePointsToClasses(points, classes.Length);
                countClasses(points, classes.Length);
            }
                
            DrawPoints(graphics, centerClasses);
        }
    }
}