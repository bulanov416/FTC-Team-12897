package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.microsoft.MicrosoftGamepadXbox360;
import com.qualcomm.hardware.motors.NeveRest40Gearmotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;
import com.sun.tools.javac.code.Symbol;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static java.lang.Math.abs;

@TeleOp(name="TeleOp")
public class TeleOpMain extends LinearOpMode {

    float frontLeft = 0;
    float frontRight = 0;
    float backLeft = 0;
    float backRight = 0;
    float straightPower = 0;
    float sidePower = 0;
    double power = 0;

    public Robot robot;

    public void runOpMode() throws InterruptedException {

        this.robot = new Robot(hardwareMap);

        waitForStart();
        robot.init();
        while (opModeIsActive()) {
            robot.rightWing.setPosition(robot.RIGHT_WING_UP);
            robot.leftWing.setPosition(robot.LEFT_WING_UP);

            float gamepad1LeftY = -gamepad1.left_stick_y;
            float gamepad1LeftX = gamepad1.left_stick_x;
            float gamepad1RightX = gamepad1.right_stick_x;

            // holonomic formulas

            float FrontLeft = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            float FrontRight = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            float BackRight = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
            float BackLeft = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

            // clip the right/left values so that the values never exceed +/- 1

            FrontRight = scaleInput(FrontRight);
            FrontLeft = scaleInput(FrontLeft);
            BackRight = scaleInput(BackRight);
            BackLeft = scaleInput(BackLeft);

            FrontRight = Range.clip(FrontRight, -1, 1);
            FrontLeft = Range.clip(FrontLeft, -1, 1);
            BackLeft = Range.clip(BackLeft, -1, 1);
            BackRight = Range.clip(BackRight, -1, 1);

            // write the values to the motors
            robot.frontRightDrive.setPower(FrontRight);
            robot.frontLeftDrive.setPower(FrontLeft);
            robot.backLeftDrive.setPower(BackLeft);
            robot.backRightDrive.setPower(BackRight);


            if (gamepad1.right_bumper) {
                robot.inLeft.setPower(0.95);
                robot.inRight.setPower(0.95);
            } else if (!gamepad1.right_bumper) {
                robot.inLeft.setPower(0);
                robot.inRight.setPower(0);
            }

            if (gamepad1.dpad_left) {
                robot.strafeLeft(0.2);
            }
            if (gamepad1.dpad_right) {
                robot.strafeRight(0.2);
            }

            if (gamepad1.left_bumper) {
                robot.inLeft.setPower(-0.95);
                robot.inRight.setPower(-0.95);
            } else if (!gamepad1.left_bumper) {
                robot.inLeft.setPower(0);
                robot.inRight.setPower(0);
            }

            if (gamepad1.dpad_up) {
                robot.leftLift.setPosition(robot.RAMP_LEFT_UP);
                robot.rightLift.setPosition(robot.RAMP_RIGHT_UP);
            }

            if (gamepad1.dpad_down) {
                robot.leftLift.setPosition(robot.RAMP_LEFT_DOWN);
                robot.rightLift.setPosition(robot.RAMP_RIGHT_DOWN);
            }

            if (gamepad2.right_bumper) {
                robot.inLeft.setPower(0.95);
                robot.inRight.setPower(-0.95);
            }

            if (gamepad2.left_bumper) {
                robot.inLeft.setPower(-0.95);
                robot.inRight.setPower(0.95);
            }

            if (gamepad2.x) {
                robot.backRelic.setPosition(robot.RELIC_BACK_DOWN);
            }
            if (gamepad2.b) {
                robot.backRelic.setPosition(robot.RELIC_BACK_EXTENDED);
            }
            if (gamepad2.a) {
                robot.frontRelic.setPosition(robot.RELIC_FRONT_UP);
            }
            if (gamepad2.y) {
                robot.frontRelic.setPosition(robot.RELIC_FRONT_DOWN);
            }


            if (gamepad1.dpad_left) {
                robot.strafeLeft(0.25);
            }

            if (gamepad1.dpad_right) {
                robot.strafeRight(0.25);
            }

            if (gamepad1.a) {
                robot.relic.setPower(1);
            } else if (!gamepad1.a) {
                robot.relic.setPower(0);
            }

            if (gamepad1.y) {
                robot.relic.setPower(-1);
            } else if (!gamepad1.y) {
                robot.relic.setPower(0);
            }
           /* //float gamepad1LeftY = -gamepad1.left_stick_y;
            float gamepad1LeftX = gamepad1.left_stick_x;
            float gamepad1RightX = gamepad1.right_stick_x;

            // holonomic formulas

           /* frontLeft = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            frontRight = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            backRight = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
            backLeft = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

            // clip the right/left values so that the values never exceed +/- 1
            frontRight = Range.clip(frontRight, -1, 1);
            frontLeft = Range.clip(frontLeft, -1, 1);
            backLeft = Range.clip(backLeft, -1, 1);
            backRight = Range.clip(backRight, -1, 1);

            // write the values to the motors
            robot.frontRightDrive.setPower(frontRight);
            robot.frontLeftDrive.setPower(frontLeft);
            robot.backLeftDrive.setPower(backLeft);
            robot.backRightDrive.setPower(backRight);

           /*
            telemetry.addData("frontRightPower: ", frontRight);
            telemetry.addData("frontLeftPower: ", frontLeft);
            telemetry.addData("backRightPower: ", backRight);
            telemetry.addData("backLeftPower: ", backLeft);
            telemetry.update();
            */
        }
    }

    float scaleInput(float dVal) {
        float[] scaleArray = {0.0f, 0.05f, 0.09f, 0.10f, 0.12f, 0.15f, 0.18f, 0.24f,
                0.30f, 0.36f, 0.43f, 0.50f, 0.60f, 0.72f, 0.85f, 1.00f, 1.00f};

        // get the corresponding index for the scaleInput array.
        float index = (float) (dVal * 16.0);

        // index should be positive.
        if (index < 0f) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16f) {
            index = 16f;
        }

        // get value from the array.
        float dScale = 0.0f;
        if (dVal < 0f) {
            dScale = -scaleArray[(int)index];
        } else {
            dScale = scaleArray[(int)index];
        }

        // return scaled value.
        return dScale;
    }
}
