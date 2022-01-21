package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Cogchamp TeleOp", group = "TELECHAMP")
public class TeleOp15118 extends LinearOpMode {
    public ElapsedTime runtime = new ElapsedTime();

    public double DRIVETRAIN_SPEED_MODIFIER = 1;

    DcMotor fr, fl, br, bl;
    DcMotor intakeMotor, outtakeMotor, carouselMotor, intakeLifter;
    Servo outtakeGate;

    public ElapsedTime loopTime = new ElapsedTime();

    private boolean intakeLifted = false;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        runtime.reset();

        while(opModeIsActive())
        {
            double loopStart = loopTime.milliseconds();
            telemetry.addData("OUTTAKE ENCODER POSITION >>", outtakeMotor.getCurrentPosition());
            /**<----- DRIVETRAIN ----->*/
            if(gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0)
            {
                move(-gamepad1.left_stick_x, -gamepad1.right_stick_x, -gamepad1.left_stick_y);
            } else if(gamepad1.dpad_up)
            {
                move(0, 0, .5);
            }
            else if(gamepad1.dpad_left)
            {
                move(.5, 0, 0);
            }
            else if(gamepad1.dpad_down)
            {
                move(0, 0, -.5);
            }
            else if(gamepad1.dpad_right)
            {
                move(-.5,0,0);
            }
            if(gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0)
            {
                move(0, 0, 0);
            }
            /**<----- DRIVETRAIN ----->*/

            /**<----- INTAKE ----->*/
            if(gamepad1.right_trigger != 0 || gamepad1.left_trigger != 0)
            {
                DRIVETRAIN_SPEED_MODIFIER = .5;
                if(gamepad1.right_trigger != 0)
                {
                    intakeMotor.setPower(gamepad1.right_trigger);
                } else
                {
                    intakeMotor.setPower(-gamepad1.left_trigger);
                }
            } else
            {
                intakeMotor.setPower(0);
                DRIVETRAIN_SPEED_MODIFIER = 1;
            }
            if(gamepad2.x)
            {
                if(!intakeLifter.isBusy())
                {
                    intakeLifted = (intakeLifter.getTargetPosition() > 0);
                    if(intakeLifted)
                    {
                        lowerIntake();
                    } else
                    {
                        liftIntake();
                    }
                }
            }
            /**<----- INTAKE ----->*/

            /**<----- OUTTAKE ----->*/
            if(gamepad2.right_trigger != 0 || gamepad2.left_trigger != 0)
            {
                if(gamepad2.right_trigger != 0)
                {
                    outtakeMotor.setPower(1);
                }
                else if(gamepad2.left_trigger != 0 && outtakeMotor.getCurrentPosition() >= 0)
                {
                    outtakeMotor.setPower(-1);
                }
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
            if(gamepad2.a)
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
        /**<----- MOTORS ----->*/
        fr = hardwareMap.get(DcMotor.class, "fr");
        fl = hardwareMap.get(DcMotor.class, "fl");
        br = hardwareMap.get(DcMotor.class, "br");
        bl = hardwareMap.get(DcMotor.class, "bl");

        /**<----- INTAKE ----->*/
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeLifter = hardwareMap.get(DcMotor.class,"intake lifter");
        intakeLifter.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /**<----- OUTTAKE ----->*/
        outtakeMotor = hardwareMap.get(DcMotor.class,"outtake");
        outtakeGate = hardwareMap.get(Servo.class, "outtake gate");

        /**<----- CAROUSEL ----->*/
        carouselMotor = hardwareMap.get(DcMotor.class,"carousel");
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();
    }

    private void liftIntake()
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
        intakeLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void lowerIntake()
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

    private void resetEncoders()
    {
        outtakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void move(double strafe, double forward, double turn)
    {
        //Following is just there if your robot is moving backwards, if it is then just uncomment it
        turn *= -DRIVETRAIN_SPEED_MODIFIER;
        forward *= DRIVETRAIN_SPEED_MODIFIER;
        strafe *= DRIVETRAIN_SPEED_MODIFIER;
        fl.setPower(forward + turn + strafe);
        fr.setPower(forward - turn + strafe);
        bl.setPower(forward - turn - strafe);
        br.setPower(forward + turn - strafe);
    }

    public void drop()
    {
        outtakeGate.setPosition(.5);
        sleep(500);
        outtakeGate.setPosition(0);
    }
}
