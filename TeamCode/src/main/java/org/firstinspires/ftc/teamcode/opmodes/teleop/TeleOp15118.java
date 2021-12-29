package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Cogchamp TeleOp", group = "TELECHAMP")
public class TeleOp15118 extends LinearOpMode {
    public double DRIVETRAIN_SPEED_MODIFIER = 1;

    DcMotor fr, fl, br, bl;
    DcMotor intakeMotor, outtakeMotor, carouselMotor, intakeLifter;
    Servo outtakeGate;

    private int LINEAR_SLIDE_BOTTOM_LIMIT;

    private boolean intakeLifted = false;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        LINEAR_SLIDE_BOTTOM_LIMIT = outtakeMotor.getCurrentPosition();
        while(opModeIsActive())
        {
            telemetry.addData("OUTTAKE ENCODER POSITION >>", outtakeMotor.getCurrentPosition());
            /**<----- DRIVETRAIN ----->*/
            if(gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0)
            {
                move(-gamepad1.left_stick_x, -gamepad1.right_stick_x, -gamepad1.left_stick_y);
            }
            if(gamepad1.dpad_up)
            {
                move(0, 0, .5);
            }
            if(gamepad1.dpad_left)
            {
                move(.5, 0, 0);
            }
            if(gamepad1.dpad_down)
            {
                move(0, 0, -.5);
            }
            if(gamepad1.dpad_right)
            {
                move(-.5,0,0);
            }
            if(gamepad1.left_stick_x == 0 || gamepad1.left_stick_y == 0 || gamepad1.right_stick_x == 0)
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
                if(intakeLifted)
                {

                } else
                {

                }
                sleep(500);
                intakeLifted = !intakeLifted;
            }
            /**<----- INTAKE ----->*/

            /**<----- OUTTAKE ----->*/
            if(gamepad2.right_trigger != 0 || gamepad2.left_trigger != 0)
            {
                if(gamepad2.right_trigger != 0)
                {
                    outtakeMotor.setPower(1);
                } else if(gamepad2.left_trigger != 0 && outtakeMotor.getCurrentPosition() >= LINEAR_SLIDE_BOTTOM_LIMIT)
                {
                    outtakeMotor.setPower(-1);
                }
            } else
            {
                outtakeMotor.setPower(0);
            }
            if(gamepad2.right_bumper)
            {
                outtakeMotor.setPower(.5);
            }
            if(gamepad2.left_bumper)
            {
                outtakeMotor.setPower(-.5);
            }
            if(gamepad2.a)
            {
                outtakeGate.setPosition(.5);
                sleep(250);
                outtakeGate.setPosition(0);
            }
            if(gamepad2.b)
            {
                outtakeGate.setPosition(.5);
                sleep(500);
                outtakeGate.setPosition(0);
            }
            if(gamepad2.y)
            {
                outtakeMotor.setTargetPosition(0);
                outtakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outtakeMotor.setPower(1);
                while(outtakeMotor.isBusy())
                {
                    sleep(10);
                }
                outtakeMotor.setPower(0);
                outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            //if(outtakeMotor.getCurrentPosition() >= 1000)
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
        fl.setDirection(DcMotorSimple.Direction.REVERSE);

        /**<----- INTAKE ----->*/
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeLifter = hardwareMap.get(DcMotor.class,"intake lifter");

        /**<----- OUTTAKE ----->*/
        outtakeMotor = hardwareMap.get(DcMotor.class,"outtake");
        outtakeGate = hardwareMap.get(Servo.class, "outtake gate");

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

        LINEAR_SLIDE_BOTTOM_LIMIT = outtakeMotor.getCurrentPosition();
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
}
