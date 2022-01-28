package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Drivetrain;

@TeleOp(name = "Cogchamp TeleOp", group = "TELECHAMP")
public class TeleOp15118 extends LinearOpMode {
    public ElapsedTime runtime = new ElapsedTime();

    DcMotor intakeMotor, outtakeMotor, carouselMotor, intakeLifter;
    Servo outtakeGate;

    Drivetrain drivetrain;

    public ElapsedTime loopTime = new ElapsedTime();

    double dropTime = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        runtime.reset();
        double liftTime = 0;
        boolean lifting = false;
        boolean lifted = true;


        while(opModeIsActive())
        {
            double loopStart = loopTime.milliseconds();

            if(runtime.milliseconds() >= dropTime)
            {
                outtakeGate.setPosition(0);
            }

            telemetry.addData("OUTTAKE ENCODER POSITION >>", outtakeMotor.getCurrentPosition());
            /**<----- DRIVETRAIN ----->*/
            if(gamepad1.left_bumper)
            {
                drivetrain.setSpeed(.5);
            } else
            {
                drivetrain.setSpeed(1);
            }

            if(gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0)
            {
                drivetrain.move(-gamepad1.left_stick_x, -gamepad1.right_stick_x, -gamepad1.left_stick_y);
            } else if(gamepad1.dpad_up)
            {
                drivetrain.move(0, 0, .5);
            }
            else if(gamepad1.dpad_left)
            {
                drivetrain.move(.5, 0, 0);
            }
            else if(gamepad1.dpad_down)
            {
                drivetrain.move(0, 0, -.5);
            }
            else if(gamepad1.dpad_right)
            {
                drivetrain.move(-.5,0,0);
            }
            else
            {
                drivetrain.move(0, 0, 0);
            }
            /**<----- DRIVETRAIN ----->*/

            /**<----- INTAKE ----->*/
            if(gamepad1.right_trigger != 0 || gamepad1.left_trigger != 0)
            {
                drivetrain.setSpeed(.5);
                if(gamepad1.right_trigger != 0)
                {
                    intakeMotor.setPower(gamepad1.right_trigger);
                } else
                {
                    intakeMotor.setPower(-gamepad1.left_trigger);
                }
            }
            else
            {
                intakeMotor.setPower(0);
                drivetrain.setSpeed(1);
            }
            if(runtime.milliseconds() >= liftTime)
            {
                lifting = false;
                liftIntake("STOP");
            }
            if(gamepad2.x && !lifting)
            {
                lifting = true;
                if(lifted)
                {
                    liftTime = runtime.milliseconds() + 500;
                    lifted = false;
                    liftIntake("LOWER");
                } else
                {
                    liftTime = runtime.milliseconds() + 1000;
                    lifted = true;
                    liftIntake("RAISE");
                }
            }
            /**<----- INTAKE ----->*/

            /**<----- OUTTAKE ----->*/
            if(gamepad2.right_trigger != 0 && outtakeMotor.getCurrentPosition() <= 2000)
            {
                outtakeMotor.setPower(1);
            }
            else if(gamepad2.left_trigger != 0 && outtakeMotor.getCurrentPosition() >= 0)
            {
                outtakeMotor.setPower(-1);
            }
            else if(gamepad2.right_bumper)
            {
                outtakeMotor.setPower(.5);
            }
            else if(gamepad2.left_bumper)
            {
                outtakeMotor.setPower(-.5);
            }
            else
            {
                outtakeMotor.setPower(0);
            }
            if(gamepad1.right_bumper)
            {
                drop();
            }
            /**<----- OUTTAKE ----->*/

            /**<----- CAROUSEL ----->*/
            if(gamepad1.a || gamepad1.y)
            {
                if (gamepad1.a) {
                    carouselMotor.setPower(.375);
                }
                if (gamepad1.y) {
                    carouselMotor.setPower(-.375);
                }
            }  else
            {
                carouselMotor.setPower(0);
            }
            /**<----- CAROUSEL ----->*/
            telemetry.addData("Loop Time",loopTime.milliseconds() - loopStart);
            telemetry.update();
        }
    }

    public void initialize()
    {
        drivetrain = new Drivetrain(hardwareMap);
        drivetrain.initialize();
        drivetrain.setMode("TELEOP");

        /**<----- INTAKE ----->*/
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeLifter = hardwareMap.get(DcMotor.class,"intake lifter");
        intakeLifter.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /**<----- OUTTAKE ----->*/
        outtakeMotor = hardwareMap.get(DcMotor.class,"outtake");
        outtakeGate = hardwareMap.get(Servo.class, "outtake gate");
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeGate.setPosition(.75);


        /**<----- CAROUSEL ----->*/
        carouselMotor = hardwareMap.get(DcMotor.class,"carousel");
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();
    }

    private void liftIntake(String command)
    {
        if(command.equals("LOWER"))
        {
            intakeLifter.setTargetPosition(-40);
            intakeLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intakeLifter.setPower(1);
        } else if(command.equals("RAISE"))
        {
            intakeLifter.setTargetPosition(40);
            intakeLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intakeLifter.setPower(1);
        } else if(command.equals("STOP"))
        {
            intakeLifter.setPower(0);
            intakeLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            intakeLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    private void resetEncoders()
    {
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void drop()
    {
        outtakeGate.setPosition(.5);
        dropTime = runtime.milliseconds() + 250;
    }
}
