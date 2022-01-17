package org.firstinspires.ftc.teamcode.util;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.PipelineRecordingParameters;

public class GreenStartingPositionPipeline extends OpenCvPipeline
{
    public enum StartingPosition
    {
        LEFT,
        CENTER,
        RIGHT,
        NONE
    }

    private StartingPosition position = StartingPosition.NONE;

    Point region1_pointA = new Point(
            1,
            0);
    Point region1_pointB = new Point(
            320,
            480);
    Point region2_pointA = new Point(
            160,
            0);
    Point region2_pointB = new Point(
            480,
            480);
    Point region3_pointA = new Point(
            320,
            0);
    Point region3_pointB = new Point(
            640,
            480);

    Mat region1, region2, region3 = new Mat();

    int total1, total2, total3;

    @Override
    public Mat processFrame(Mat input)
    {
//        region1 = input.submat(new Rect(region1_pointA, region1_pointB));
//        region2 = input.submat(new Rect(region2_pointA, region2_pointB));
//        region3 = input.submat(new Rect(region3_pointA, region3_pointB));
//
//        avg1 = (int) Core.mean(region1).val[1];
//        avg2 = (int) Core.mean(region2).val[1];
//        avg3 = (int) Core.mean(region3).val[1];


        Mat copyMat = input;

        int countL = 0, countC = 0, countR = 0;
        for(int i = 240; i < 480; i++)
        {
            for(int j = 0; j < 640; j++)
            {
                if(copyMat.get(i, j)[1] > copyMat.get(i, j)[0] && copyMat.get(i, j)[1] > copyMat.get(i, j)[2] )
                {
                    if(j < 213)
                    {
                        countL ++;
                    } else if(j < 427)
                    {
                        countC ++;
                    } else {
                        countR ++;
                    }
                }


            }
        }
        total1 = countL;
        total2 = countC;
        total3 = countR;
        int maxOneTwo = Math.max(countL, countC);
        int max = Math.max(maxOneTwo, countR);

        if(max == countL)
        {
            position = StartingPosition.LEFT;
        } else if(max == countC)
        {
            position = StartingPosition.CENTER;
        } else
        {
            position = StartingPosition.RIGHT;
        }

        return input;
    }

    public String getPosition()
    {
        if(position == StartingPosition.LEFT)
        {
            return "LEFT";
        } else if(position == StartingPosition.CENTER)
        {
            return "CENTER";
        } else if(position == StartingPosition.RIGHT) {
            return "RIGHT";
        } else
        {
            return "ERROR";
        }
    }

    public int[] totalGreen()
    {
        return new int[]{total1, total2, total3};
    }
}
