package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.FreightDetectionPipeline;
import org.opencv.core.Point;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Freight/Duck Detection Test")
public class FreightDetectionTest extends LinearOpMode
{
    public ElapsedTime runtime = new ElapsedTime();

    public OpenCvCamera camera;

    Point duck;

    @Override
    public void runOpMode() throws InterruptedException
    {
        /*<----- OPENCV ----->*/
        final boolean[] opened = {false};
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        FreightDetectionPipeline pipeline = new FreightDetectionPipeline();
        camera.setPipeline(pipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode) { }
        });
        /*<----- OPENCV ----->*/

        waitForStart();
        runtime.reset();
        while(!isStopRequested())
        {
            double loopStart = runtime.milliseconds();
            duck = pipeline.getDuckPoint();

            if(duck != null)
            {
                telemetry.addData("Duck", pipeline.getDuckPoint());
            }
            telemetry.addData("Loop Time >>",  runtime.milliseconds() - loopStart);
            telemetry.update();
        }
    }
}
