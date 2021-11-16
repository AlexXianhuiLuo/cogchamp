package org.firstinspires.ftc.teamcode.tests;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.*;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.internal.collections.EvictingBlockingQueue;
import org.firstinspires.ftc.robotcore.internal.network.CallbackLooper;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.ContinuationSynchronizer;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@Autonomous(name = "Webcam Tests", group = "TESTCHAMP")
public class ObjectDetectionTests extends LinearOpMode
{
    // TODO: fill in
    public static final String VUFORIA_LICENSE_KEY = "AV6Yl4H/////AAABmaUh5D87g0+rvSueaz9tICxIYE40NPaYAyqFNJ9KWNkyQKlbex/zHvfXUHYpF0hyLDMQyv5YoPPFvvlXmQ8LAo4M/qiK1FV9JrzoWR2cbSMSsI8/+gkDWMwAo2aJVt4h7PWueyGFL3hhkRcu2wK4wzTq7oy/hviFeVYvwhkPdrK0hShhhjc+t5AJCa5hgREKVqssKHRa/H3Wz6ngnET1ht/pqPg8m5SEgFdSdOJW4P7f6NZHBbjM6iGidQfCgnpihS7OXM6Q2pXgzIIzDbfVQnQ4mByLE6xMGsLu1ikaSXWLAyCzCN6Wjv2tPogB7dD0JDOsd11xMp4aJgC9MLxDaCzDvPYoTID0CADoref7pSxN";

    @Override
    public void runOpMode() throws InterruptedException {
        // gives Vuforia more time to exit before the watchdog notices
        msStuckDetectStop = 2500;

        VuforiaLocalizer.Parameters vuforiaParams = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        vuforiaParams.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;
        vuforiaParams.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(vuforiaParams);

        FtcDashboard.getInstance().startCameraStream(vuforia, 0);

        waitForStart();

        while (opModeIsActive());
    }
}
