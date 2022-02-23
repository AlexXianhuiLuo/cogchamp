package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.Drivetrain;
import org.firstinspires.ftc.teamcode.util.GreenStartingPositionPipeline;
import org.firstinspires.ftc.teamcode.util.Outtake;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class Auto extends LinearOpMode
{
    private int
        LVL_1_TIME =  500,
        LVL_2_TIME = 1000,
        LVL_3_TIME = 2000;

    private int
        DROP_TIME = 500;

    public ElapsedTime runtime = new ElapsedTime();

    public Drivetrain drivetrain;

    public Outtake outtake;

    public OpenCvCamera camera;

    public DcMotor intakeMotor, carouselMotor;

    public TouchSensor touch;

    public ColorSensor colorSensor;

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

        /*<----- INTAKE ----->*/
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        touch = hardwareMap.get(TouchSensor.class, "touch");
        colorSensor = hardwareMap.get(ColorSensor.class, "colour");

        /*<----- OUTTAKE ----->*/
        outtake = new Outtake(hardwareMap);

        /*<----- CAROUSEL ----->*/
        carouselMotor = hardwareMap.get(DcMotor.class,"carousel");
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /*<------- INTAKE ------->*/
    //TODO: IMPLEMENT THIS WITH NEW INTAKE
    public void intake()
    {
        int[] ticks = drivetrain.getEncoderTicks();
        drivetrain.setMotorPower(0.25);
        intakeMotor.setPower(1);
        while(touch.isPressed())
        {
            telemetry.addData("MODE >>", "PICKING UP");
            telemetry.update();
        }
        drivetrain.setMotorPower(0);
        intakeMotor.setPower(0.75);
        while(colorSensor.red() < 125 && colorSensor.blue() < 125 && colorSensor.green() < 125)
        {
            telemetry.addData("MODE >>", "INTAKING");
            telemetry.update();
        }
        sleep(500);
        intakeMotor.setPower(-1);
        sleep(500);
        intakeMotor.setPower(0);
        drivetrain.goToPosition(ticks[0], ticks[1], ticks[2], ticks[3], 5000);
        telemetry.addData("MODE >>","STOPPED");
        telemetry.update();
    }

    public boolean buttonPressed()
    {
        return touch.isPressed();
    }
    /*<------- INTAKE ------->*/

    /*<------- OUTTAKE ------->*/
    //TODO: Increase speed once we are sure it won't break

    //TODO: Fix, doesn't stop at starting position

    public void raiseLvl1() throws InterruptedException {
        outtake.raise1Auto();
        sleep(LVL_1_TIME);
        outtake.flatArm();
        sleep(250);
        outtake.sleepDrop(500);
        sleep(250);
        outtake.angleArm();
        sleep(500);
        outtake.resetLift(LVL_1_TIME);
    }

    public void raiseLvl2() throws InterruptedException {
        outtake.raise2Auto();
        sleep(LVL_2_TIME);
        outtake.autoDrop();
        sleep(DROP_TIME);
        outtake.resetLift(LVL_2_TIME);
    }

    public void raiseLvl3() throws InterruptedException {
        outtake.raise3Auto();
        sleep(LVL_3_TIME);
        outtake.autoDrop();
        sleep(DROP_TIME);
        sleep(500);
        outtake.resetLift(LVL_3_TIME);
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
