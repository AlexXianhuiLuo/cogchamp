package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
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
    public double GLOBAL_SPEED_MODIFIER = .75;

    public ElapsedTime runtime = new ElapsedTime();

    OpenCvCamera camera;

    DcMotor fr, fl, br, bl;

    DcMotor intakeMotor, outtakeMotor, carouselMotor, intakeLifter;

    Servo outtakeGate;

    ColorSensor colorSensor;

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
        intakeLifter.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        colorSensor = hardwareMap.get(ColorSensor.class, "color sensor");

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
        if(left)
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

    public void goToPosition(int frPos, int flPos, int brPos, int blPos, int time_ms)
    {
        fr.setTargetPosition(frPos);
        fl.setTargetPosition(flPos);
        br.setTargetPosition(brPos);
        bl.setTargetPosition(blPos);
        go(time_ms);
    }

    public void curveForward(boolean left, int amount, int time_ms)
    {
        if(left)
        {
            fr.setTargetPosition(0);
            fl.setTargetPosition(-amount);
        } else
        {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(0);
        }
        br.setTargetPosition(-amount);
        bl.setTargetPosition(-amount);
        go(time_ms);
    }
    public void curveBackward(boolean left, int amount, int time_ms)
    {
        if(left)
        {
            br.setTargetPosition(0);
            bl.setTargetPosition(-amount);
        } else
        {
            br.setTargetPosition(-amount);
            bl.setTargetPosition(0);
        }
        fr.setTargetPosition(-amount);
        fl.setTargetPosition(-amount);
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
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setMotorPower(GLOBAL_SPEED_MODIFIER);

        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < time_ms )
        {
            telemetry.addData("TASK >>", "MOVING");
            telemetry.update();
            if(!fr.isBusy() && !fl.isBusy() && !br.isBusy() && !bl.isBusy())
            {
                break;
            }
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

        telemetry.addData("TASK >>", "STOPPED");
        telemetry.update();
    }
    /*<------- MOVEMENT ------->*/

    /*<------- INTAKE ------->*/
    public int[] intake()
    {
        int[] motorValues = {fr.getCurrentPosition(), fl.getCurrentPosition(), br.getCurrentPosition(), bl.getCurrentPosition()};
        if(intakeMotor.getPower() == 0)
        {
            intakeMotor.setPower(.75);
            setMotorPower(.25);
            while(colorSensor.red() < 50)
            {

            }
            setMotorPower(0);
            intakeMotor.setPower(0);
            int[] newValues = {fr.getCurrentPosition(), fl.getCurrentPosition(), br.getCurrentPosition(), bl.getCurrentPosition()};
            return new int[] {motorValues[0] - newValues[0], motorValues[1] - newValues[1], motorValues[2] - newValues[2], motorValues[3] - newValues[3]};
        } else
        {
            intakeMotor.setPower(0);
        }
        return null;
    }
    public void liftIntake()
    {
        runtime.reset();
        intakeLifter.setTargetPosition(40);
        intakeLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeLifter.setPower(1);
        while(intakeLifter.isBusy() && runtime.milliseconds() < 1000)
        {
            telemetry.addLine("RAISING");
            telemetry.update();
        }
        intakeLifter.setPower(0);
        intakeLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void lowerIntake()
    {
        runtime.reset();
        intakeLifter.setTargetPosition(-40);
        intakeLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeLifter.setPower(1);
        while(intakeLifter.isBusy() && runtime.milliseconds() < 500)
        {
            telemetry.addLine("LOWERING");
            telemetry.update();
        }
        intakeLifter.setPower(0);
        intakeLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    /*<------- INTAKE ------->*/

    /*<------- OUTTAKE ------->*/
    //TODO: Increase speed once we are sure it won't break

    //TODO: Fix, doesn't stop at starting position
//    public void resetLift()
//    {
//        outtakeMotor.setTargetPosition(outtakeMotor.getCurrentPosition() * -1);
//        outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        outtakeMotor.setPower(.75);
//        runtime.reset();
//        while(opModeIsActive() && runtime.milliseconds() < 5000 && outtakeMotor.isBusy())
//        {
//            telemetry.addLine("RUNNING");
//            telemetry.update();
//        }
//        outtakeMotor.setPower(0);
//        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//    }

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
        intakeLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    /*<------- HELPER ------->*/
}
