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
        RIGHT
    }

    private volatile StartingPosition position = StartingPosition.LEFT;

    Point region1_pointA = new Point(
            0,
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

    int avg1, avg2, avg3;

    @Override
    public Mat processFrame(Mat input)
    {
        region1 = input.submat(new Rect(region1_pointA, region1_pointB));
        region2 = input.submat(new Rect(region2_pointA, region2_pointB));
        region3 = input.submat(new Rect(region3_pointA, region3_pointB));

        avg1 = (int) Core.mean(region1).val[1];
        avg2 = (int) Core.mean(region2).val[1];
        avg3 = (int) Core.mean(region3).val[1];

        int maxOneTwo = Math.max(avg1, avg2);
        int max = Math.max(maxOneTwo, avg3);

        if(max == avg1)
        {
            position = StartingPosition.LEFT;
        } else if(max == avg2)
        {
            position = StartingPosition.CENTER;
        } else if(max == avg3)
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
        }
        return "RIGHT";
    }
}
