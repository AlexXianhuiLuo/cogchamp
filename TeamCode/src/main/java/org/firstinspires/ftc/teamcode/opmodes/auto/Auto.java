package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.Drivetrain;
import org.firstinspires.ftc.teamcode.util.GreenStartingPositionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class Auto extends LinearOpMode
{
    public ElapsedTime runtime = new ElapsedTime();

    public Drivetrain drivetrain;

    OpenCvCamera camera;

    DcMotor intakeMotor, outtakeMotor, carouselMotor, intakeLifter;

    Servo outtakeGate;

    public String startingPosition;

    @Override
    public void runOpMode() throws InterruptedException
    {
        /*<----- OPENCV ----->*/
        final boolean[] opened = {false};
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        GreenStartingPositionPipeline pipeline = new GreenStartingPositionPipeline();
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

        initialize();

        int leftCount = 0, centerCount = 0, rightCount = 0, errorCount = 0;
        while(!isStarted())
        {
            startingPosition = pipeline.getPosition();
            telemetry.addData("STARTING POSITION >>", startingPosition);
            telemetry.update();
        }
        waitForStart();
        runtime.reset();
    }

    private void initialize()
    {
        /*<----- MOTORS ----->*/
        drivetrain = new Drivetrain(hardwareMap);
        drivetrain.setMode("AUTO");

        //TODO:REDO THIS WITH NEW INTAKE
//        /*<----- INTAKE ----->*/
//        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
//        intakeLifter = hardwareMap.get(DcMotor.class, "intake lifter");
//        intakeLifter.setDirection(DcMotorSimple.Direction.REVERSE);
//        intakeLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*<----- OUTTAKE ----->*/
        outtakeMotor = hardwareMap.get(DcMotor.class,"outtake");
        outtakeGate = hardwareMap.get(Servo.class, "outtake gate");
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /*<----- CAROUSEL ----->*/
        carouselMotor = hardwareMap.get(DcMotor.class,"carousel");
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /*<------- INTAKE ------->*/

    //TODO: IMPLEMENT THIS WITH NEW INTAKE
//    public void intake()
//    {
//
//    }
//    public void liftIntake()
//    {
//        runtime.reset();
//        intakeLifter.setTargetPosition(40);
//        intakeLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        intakeLifter.setPower(1);
//        while(intakeLifter.isBusy() && runtime.milliseconds() < 1000)
//        {
//            telemetry.addLine("RAISING");
//            telemetry.update();
//        }
//        intakeLifter.setPower(0);
//        intakeLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//    }
//
//    public void lowerIntake()
//    {
//        runtime.reset();
//        intakeLifter.setTargetPosition(-40);
//        intakeLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        intakeLifter.setPower(1);
//        while(intakeLifter.isBusy() && runtime.milliseconds() < 500)
//        {
//            telemetry.addLine("LOWERING");
//            telemetry.update();
//        }
//        intakeLifter.setPower(0);
//        intakeLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        intakeLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//    }
    /*<------- INTAKE ------->*/

    /*<------- OUTTAKE ------->*/
    //TODO: Increase speed once we are sure it won't break

    //TODO: Fix, doesn't stop at starting position

    public void raiseLvl1()
    {
        outtakeMotor.setTargetPosition(600);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(1);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 5000 && outtakeMotor.isBusy())
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        drop();
        sleep(200);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setTargetPosition(-600);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(1);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 5000 && outtakeMotor.isBusy())
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void raiseLvl2()
    {
        outtakeMotor.setTargetPosition(1200);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(1);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 5000 && outtakeMotor.isBusy())
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        drop();
        sleep(200);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setTargetPosition(-1200);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(1);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 5000 && outtakeMotor.isBusy())
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void raiseLvl3()
    {
        outtakeMotor.setTargetPosition(1900);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(1);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 5000 && outtakeMotor.isBusy())
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        drop();
        sleep(100);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setTargetPosition(-1900);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(1);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 5000 && outtakeMotor.isBusy())
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drop()
    {
        outtakeGate.setPosition(.5);
        sleep(500);
        outtakeGate.setPosition(0);
        sleep(100);
    }
    /*<------- OUTTAKE ------->**/

    /*<------- HELPER ------->*/
    public int ticksToMM(int ticks)
    {
        //Math: ticks/TPR = mm/(pi*wheelDiameter) --> pi*wheelDiameter*ticks/TPR = mm
        double ticksPerRoto = 384.5;
        double wheelDiameter = 96;
        return (int) ((ticks*wheelDiameter*Math.PI)/ticksPerRoto);
    }
    public int mmToTick(int millimeters)
    {
        //Math: ticks/TPR = mm/(pi*wheelDiameter) --> ticks = mm*TPR/pi*wheelDiameter
        double ticksPerRoto = 384.5;
        double wheelDiameter = 96;
        return (int) ((millimeters*ticksPerRoto)/(Math.PI*wheelDiameter));
    }
    /*<------- HELPER ------->*/
}
