package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Cogchamp TeleOp", group = "TELECHAMP")
public class TeleOp15118 extends LinearOpMode {
    private final float DRIVETRAIN_SPEED_MODIFIER = 1;

    DcMotor fr, fl, br, bl;
    DcMotor intakeMotor, linearSlideMotor, carouselMotor;
    Servo intakeLifter, outtakeGate;

    private int LINEAR_SLIDE_BOTTOM_LIMIT;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        LINEAR_SLIDE_BOTTOM_LIMIT = linearSlideMotor.getCurrentPosition();
        while(opModeIsActive())
        {
            /**<----- DRIVETRAIN ----->*/
            if(gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0)
            {
                move(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_stick_y);
            }
            if(gamepad1.dpad_up)
            {
                move(0, 0, -.5);
            }
            if(gamepad1.dpad_left)
            {
                move(-.5, 0, 0);
            }
            if(gamepad1.dpad_down)
            {
                move(0, 0, .5);
            }
            if(gamepad1.dpad_right)
            {
                move(.5,0,0);
            }
            if(gamepad1.left_stick_x == 0 || gamepad1.left_stick_y == 0 || gamepad1.right_stick_x == 0)
            {
                move(0, 0, 0);
            }
            /**<----- DRIVETRAIN ----->*/

            /**<----- INTAKE ----->*/
            if(gamepad1.right_trigger != 0 || gamepad1.left_trigger != 0)
            {
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
            }
            //TEMP DON'T KNOW POSITIONS OR IF SUPPOSED TO SET NEGATIVE POSITION OR 0 TO RESET
            if(gamepad1.left_bumper)
            {
                intakeLifter.setPosition(0.59);
            }
            if(gamepad1.right_bumper)
            {
                intakeLifter.setPosition(0);
            }
            /**<----- INTAKE ----->*/

            /**<----- OUTTAKE ----->*/
            if(gamepad2.right_trigger != 0 || gamepad2.left_trigger != 0)
            {
                if(gamepad2.right_trigger != 0)
                {
                    linearSlideMotor.setPower(1);
                } else if(gamepad2.left_trigger != 0 && linearSlideMotor.getCurrentPosition() >= LINEAR_SLIDE_BOTTOM_LIMIT)
                {
                    linearSlideMotor.setPower(-1);
                }
            } else
            {
                linearSlideMotor.setPower(0);
            }
            if(gamepad2.right_bumper)
            {
                linearSlideMotor.setPower(.5);
            }
            if(gamepad2.left_bumper)
            {
                linearSlideMotor.setPower(-.5);
            }
            if(gamepad2.a)
            {
                outtakeGate.setPosition(.5);
                sleep(250);
                outtakeGate.setPosition(0);
            }
//            if(gamepad1.b)
//            {
//                linearSlideMotor.setTargetPosition(250); //TEMPORARY DON'T KNOW HOW LONG TO WAIT OR HOW FAR
//                sleep(250);
//                outtakeGate.setPosition(1);
//                sleep(250);
//                outtakeGate.setPosition(0);
//                linearSlideMotor.setTargetPosition(0);
//            }
            /**<----- OUTTAKE ----->*/

            /**<----- CAROUSEL ----->*/
            if(gamepad1.a || gamepad1.y)
            {
                if (gamepad1.a) {
                    carouselMotor.setPower(.5);
                }
                if (gamepad1.y) {
                    carouselMotor.setPower(-.5);
                }
            }  else
            {
                carouselMotor.setPower(0);
            }
            /**<----- CAROUSEL ----->*/
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
        intakeLifter = hardwareMap.get(Servo.class,"intake lifter");
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /**<----- OUTTAKE ----->*/
        linearSlideMotor = hardwareMap.get(DcMotor.class,"outtake");
        outtakeGate = hardwareMap.get(Servo.class, "outtake gate");
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /**<----- CAROUSEL ----->*/
        carouselMotor = hardwareMap.get(DcMotor.class,"carousel");
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void move(double strafe, double turn, double forward)
    {
        //Following is just there if your robot is moving backwards, if it is then just uncomment it
        forward *= DRIVETRAIN_SPEED_MODIFIER;
        turn *= -DRIVETRAIN_SPEED_MODIFIER;
        strafe *= -DRIVETRAIN_SPEED_MODIFIER;
        fl.setPower(turn + forward + strafe);
        fr.setPower(turn - forward + strafe);
        bl.setPower(turn + forward - strafe);
        br.setPower(turn - forward - strafe);
    }
}
