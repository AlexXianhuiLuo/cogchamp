package org.firstinspires.ftc.teamcode.opmodes.auto;

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

    DcMotor intakeMotor, outtakeMotor, carouselMotor, intakeLifter;

    Servo outtakeGate;

    Character startingPosition;

    @Override
    public void runOpMode() throws InterruptedException
    {
        /*<----- OPENCV ----->*/
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

        int leftCount = 0, centerCount = 0, rightCount = 0;
        for(int i = 0; i < 100; i++)
        {
            switch (pipeline.getPosition()) {
                case "LEFT":
                    leftCount++;
                    break;
                case "CENTER":
                    centerCount++;
                    break;
                case "RIGHT":
                    rightCount++;
                    break;
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

    private void initialize()
    {
        /*<----- MOTORS ----->*/
        fr = hardwareMap.get(DcMotor.class, "fr");
        fl = hardwareMap.get(DcMotor.class, "fl");
        br = hardwareMap.get(DcMotor.class, "br");
        bl = hardwareMap.get(DcMotor.class, "bl");
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        /*<----- INTAKE ----->*/
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeLifter = hardwareMap.get(DcMotor.class, "intake lifter");

        /*<----- OUTTAKE ----->*/
        outtakeMotor = hardwareMap.get(DcMotor.class,"outtake");
        outtakeGate = hardwareMap.get(Servo.class, "outtake gate");
        //outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /*<----- CAROUSEL ----->*/
        carouselMotor = hardwareMap.get(DcMotor.class,"carousel");
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();
    }


    /*<------- MOVEMENT ------->*/
    public void forwards(boolean forward, int amount, int time_ms)
    {
        if(!forward)
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

    public void strafe(boolean left, int amount, int time_ms)
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

    public void turn(boolean left, int amount, int time_ms)
    {
        if(!left)
        {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(amount);
            br.setTargetPosition(amount);
            bl.setTargetPosition(-amount);
        } else
        {
            fr.setTargetPosition(amount);
            fl.setTargetPosition(-amount);
            br.setTargetPosition(-amount);
            bl.setTargetPosition(amount);
        }
        go(time_ms);
    }

    public void setMotorPower(double power)
    {
        fl.setPower(power);
        fr.setPower(power);
        br.setPower(power);
        bl.setPower(power);
    }

    private void go(int time_ms)
    {
        runtime.reset();
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setMotorPower(GLOBAL_SPEED_MODIFIER);

        while(opModeIsActive() && runtime.milliseconds() < time_ms )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        setMotorPower(0);

        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addLine("STOPPED");
        telemetry.update();
    }
    /*<------- MOVEMENT ------->*/

    /*<------- INTAKE ------->*/
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
    /*<------- INTAKE ------->*/

    /*<------- OUTTAKE ------->*/
    public void raiseLvl1()
    {
        outtakeMotor.setTargetPosition(50);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 100 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        drop();
        sleep(100);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setTargetPosition(-50);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 100 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void raiseLvl2()
    {
        outtakeMotor.setTargetPosition(250);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 500 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        drop();
        sleep(100);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setTargetPosition(-250);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 500 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void raiseLvl3()
    {
        outtakeMotor.setTargetPosition(450);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 1000 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        drop();
        sleep(100);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setTargetPosition(-450);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeMotor.setPower(.5);
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < 1000 )
        {
            telemetry.addLine("RUNNING");
            telemetry.update();
        }
        outtakeMotor.setPower(0);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drop()
    {
        outtakeGate.setPosition(.5);
        sleep(500);
        outtakeGate.setPosition(0);
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
    private void resetEncoders()
    {
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    /*<------- HELPER ------->*/
}
