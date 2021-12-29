package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.GreenStartingPositionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class Auto extends LinearOpMode
{
    private static final double GLOBAL_SPEED_MODIFIER = -.75;

    public ElapsedTime runtime = new ElapsedTime();

    OpenCvCamera camera;

    DcMotor fr, fl, br, bl;

    DcMotor intakeMotor, outtakeMotor, carouselMotor;

    Servo intakeLifterL, intakeLifterR, outtakeGate;

    Character startingPosition;

    @Override
    public void runOpMode() throws InterruptedException
    {
        /**<----- OPENCV ----->*/
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
        /**<----- OPENCV ----->*/

        initialize();

        int leftCount = 0, centerCount = 0, rightCount = 0;
        for(int i = 0; i < 10; i++)
        {
            if(pipeline.getPosition().equals("LEFT"))
            {
                leftCount++;
            } else if(pipeline.getPosition().equals("CENTER"))
            {
                centerCount++;
            } else if(pipeline.getPosition().equals("RIGHT"))
            {
                rightCount++;
            }
            sleep(100);
        }
        int maxOneTwo = Math.max(leftCount, centerCount);
        int max = Math.max(maxOneTwo, rightCount);
        if(max == leftCount)
        {
            startingPosition = 'L';
        } else if(max == centerCount)
        {
            startingPosition = 'C';
        } else
        {
            startingPosition = 'R';
        }

        telemetry.addData("STARTING POSITION >>", startingPosition);
        telemetry.update();
        waitForStart();
        runtime.reset();
    }

    public void gradualStop()
    {
        for(int i = 0; i < 10; i++)
        {
            fr.setPower(fr.getPower()/2);
            fl.setPower(fl.getPower()/2);
            br.setPower(br.getPower()/2);
            bl.setPower(bl.getPower()/2);
            sleep(5);
        }
        fl.setPower(0);
        fr.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }

    public void strafe(boolean left, int amount, int time_ms)
    {
        if(!left)
        {
            fr.setTargetPosition(amount);
            fl.setTargetPosition(-amount);
            br.setTargetPosition(-amount);
            bl.setTargetPosition(amount);
        } else
        {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(amount);
            br.setTargetPosition(amount);
            bl.setTargetPosition(-amount);
        }
        go(time_ms);
    }

    public void forwards(boolean forward, int amount, int time_ms)
    {
        if(forward)
        {
            fr.setTargetPosition(amount);
            fl.setTargetPosition(amount);
            br.setTargetPosition(amount);
            bl.setTargetPosition(amount);
        } else
        {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(-amount);
            br.setTargetPosition(-amount);
            bl.setTargetPosition(-amount);
        }
        sleep(100);
        go(time_ms);
    }

    public void turn(boolean left, int amount, int time_ms)
    {
        if(!left)
        {
            fr.setTargetPosition(amount);
            fl.setTargetPosition(-amount);
            br.setTargetPosition(amount);
            bl.setTargetPosition(-amount);
        } else
        {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(amount);
            br.setTargetPosition(-amount);
            bl.setTargetPosition(amount);
        }
        go(time_ms);
    }

    public void raiseOuttake(int amount, int time_ms)
    {
        outtakeMotor.setTargetPosition(amount);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(1);
        while(opModeIsActive() && runtime.milliseconds() < time_ms)
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void raiseLvl1()
    {
        raiseOuttake(100, 1); //TODO: Replace amounts
    }
    public void raiseLvl2()
    {
        raiseOuttake(200, 1); //TODO: Replace amounts
    }
    public void raiseLvl3()
    {

        raiseOuttake(300, 1); //TODO: Replace amounts
    }

    public void lowerOuttake()
    {

        raiseOuttake(0, 5000);
    }

    public void drop()
    {
        outtakeGate.setPosition(.5);
        sleep(250);
        outtakeGate.setPosition(0);
        sleep(250);
    }

    public void intake()
    {
        if(intakeMotor.getPower() == 0)
        {
            intakeMotor.setPower(1);
        } else
        {
            intakeMotor.setPower(0);
        }
    }

    private void go(int time_ms)
    {
        runtime.reset();
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setPower(GLOBAL_SPEED_MODIFIER);
        fl.setPower(GLOBAL_SPEED_MODIFIER);
        br.setPower(GLOBAL_SPEED_MODIFIER);
        bl.setPower(GLOBAL_SPEED_MODIFIER);

        while(opModeIsActive() && runtime.milliseconds() < time_ms)
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }

        gradualStop();

        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

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

    private void initialize()
    {
        /**<----- MOTORS ----->*/
        fr = hardwareMap.get(DcMotor.class, "fr");
        fl = hardwareMap.get(DcMotor.class, "fl");
        br = hardwareMap.get(DcMotor.class, "br");
        bl = hardwareMap.get(DcMotor.class, "bl");
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);

        /**<----- INTAKE ----->*/
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeLifterL = hardwareMap.get(Servo.class,"intake lifter left");
        intakeLifterR = hardwareMap.get(Servo.class, "intake lifter right");

        /**<----- OUTTAKE ----->*/
        outtakeMotor = hardwareMap.get(DcMotor.class,"outtake");
        outtakeGate = hardwareMap.get(Servo.class, "outtake gate");
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /**<----- CAROUSEL ----->*/
        carouselMotor = hardwareMap.get(DcMotor.class,"carousel");
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();
    }

    private void resetEncoders()
    {
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
