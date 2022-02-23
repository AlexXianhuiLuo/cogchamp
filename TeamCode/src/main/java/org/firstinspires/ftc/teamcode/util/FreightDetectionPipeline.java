package org.firstinspires.ftc.teamcode.util;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

public class FreightDetectionPipeline extends OpenCvPipeline
{
    final int length = 0;
    final int width = 0;

    @Override
    public Mat processFrame(Mat input) {
        return null;
    }

    public Mat removeBlue(Mat input)
    {
        input.clone();
        return input;
    }
}