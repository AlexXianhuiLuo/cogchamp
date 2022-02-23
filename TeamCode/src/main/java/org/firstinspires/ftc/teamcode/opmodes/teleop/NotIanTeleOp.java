package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Drivetrain;
import org.firstinspires.ftc.teamcode.util.Outtake;

@TeleOp(name = "Not Ian TeleOp", group = "TELECHAMP")
public class NotIanTeleOp extends LinearOpMode {
    public ElapsedTime runtime = new ElapsedTime();

    DcMotor intakeMotor, carouselMotor;

    Drivetrain drivetrain;

    Outtake outtake;

    public ElapsedTime loopTime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        runtime.reset();

        while(opModeIsActive())
        {
            double loopStart = loopTime.milliseconds();

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
                drivetrain.move(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_stick_y);
            } else if(gamepad1.dpad_right)
            {
                drivetrain.move(0, 0.25, 0);
            } else if(gamepad1.dpad_left)
            {
                drivetrain.move(0, -0.25, 0);
            }
            else
            {
                drivetrain.move(0, 0, 0);
            }
            /**<----- DRIVETRAIN ----->*/

            /**<----- INTAKE ----->*/
            if((gamepad1.right_trigger != 0 || gamepad1.left_trigger != 0) && outtake.isLowered)
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
            /**<----- INTAKE ----->*/

            /**<----- OUTTAKE ----->*/
            if(gamepad2.a)
            {
                outtake.setSpeed(0.1);
            } else
            {
                outtake.setSpeed(1);
            }
            if(!outtake.isBusy())
            {
                if(gamepad2.right_trigger != 0)
                {
                    outtake.setPower(gamepad2.right_trigger);
                }
                else if(gamepad2.left_trigger != 0)
                {
                    outtake.setPower(-gamepad2.left_trigger);
                }
                else if(gamepad2.dpad_left)
                {
                    outtake.raise1();
                }
                else if(gamepad2.dpad_up)
                {
                    outtake.raise2();
                } else if(gamepad2.dpad_right)
                {
                    outtake.raise3();
                }
                else if(gamepad2.dpad_down && !outtake.isLowered)
                {
                    outtake.resetLift(2000);
                }
                else
                {
                    outtake.setPower(0);
                }
                if(gamepad1.right_bumper)
                {
                    outtake.drop();
                }
                if(gamepad2.right_bumper)
                {
                    outtake.flatArm();
                }
                if(gamepad2.left_bumper)
                {
                    outtake.angleArm();
                }
                if(gamepad2.b)
                {
                    outtake.drop();
                } else if(gamepad2.x)
                {
                    outtake.smallDrop();
                } else if(gamepad2.y)
                {
                    outtake.pushDrop();
                }
            }
            /**<----- OUTTAKE ----->*/

            /**<----- CAROUSEL ----->*/
            if(gamepad1.dpad_up || gamepad1.dpad_down)
            {
                if (gamepad1.dpad_up) {
                    carouselMotor.setPower(.45);
                }
                if (gamepad1.dpad_down) {
                    carouselMotor.setPower(-.45);
                }
            }  else
            {
                carouselMotor.setPower(0);
            }
            /**<----- CAROUSEL ----->*/
            int encoderPos = outtake.update();

            telemetry.addData("LOOP TIME >>",loopTime.milliseconds() - loopStart);
            telemetry.addData("LIFT ENCODER >>", encoderPos);

            telemetry.update();
        }
    }

    public void initialize()
    {
        /**<----- DRIVETRAIN ----->*/
        drivetrain = new Drivetrain(hardwareMap);
        drivetrain.setMode("TELEOP");

        /**<----- INTAKE ----->*/
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");

        /**<----- OUTTAKE ----->*/
        outtake = new Outtake(hardwareMap);

        /**<----- CAROUSEL ----->*/
        carouselMotor = hardwareMap.get(DcMotor.class,"carousel");
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
