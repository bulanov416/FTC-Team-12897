package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp")
public class TeleOpMain extends LinearOpMode {

    public Robot robot;
    public void runOpMode() throws InterruptedException {
        this.robot = new Robot(hardwareMap);
        robot.init();
        waitForStart();
        while (opModeIsActive()) {

            float gamepad1LeftY = -gamepad1.left_stick_y;
            float gamepad1LeftX = gamepad1.left_stick_x;
            float gamepad1RightX = gamepad1.right_stick_x;

            float FrontLeft = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            float FrontRight = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            float BackRight = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
            float BackLeft = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

            FrontRight = -scaleInput(FrontRight);
            FrontLeft = -scaleInput(FrontLeft);
            BackRight = -scaleInput(BackRight);
            BackLeft = -scaleInput(BackLeft);

            FrontRight = Range.clip(FrontRight, -1, 1);
            FrontLeft = Range.clip(FrontLeft, -1, 1);
            BackLeft = Range.clip(BackLeft, -1, 1);
            BackRight = Range.clip(BackRight, -1, 1);

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

            if (gamepad2.dpad_down) {
                robot.wing.setPosition(robot.RIGHT_WING_DOWN);
            }

            if (gamepad2.dpad_up) {
                robot.wing.setPosition(robot.RIGHT_WING_UP);
            }

            if (gamepad2.dpad_left) {
                robot.jewelServo.setPosition(robot.JEWEL_SERVO_AWAY);
            }
            if (gamepad2.dpad_right) {
                robot.jewelServo.setPosition(robot.JEWEL_SERVO_MIDDLE);
            }

            if (gamepad2.right_bumper) {
                robot.inLeft.setPower(0.95);
                robot.inRight.setPower(-0.95);
            }

            if (gamepad2.left_bumper) {
                robot.inLeft.setPower(-0.95);
                robot.inRight.setPower(0.95);
            }

            if (gamepad1.a) {
                robot.relicWinch.setPosition(robot.RELIC_WINCH_MIDDLE);
            }
            if (gamepad1.y) {
                robot.relicWinch.setPosition(robot.RELIC_WINCH_EXTENDED);
            }
            if (gamepad1.right_bumper) {
                robot.relicWinch.setPosition(robot.RELIC_WINCH_DOWN);
            }
            if (gamepad1.x) {
                robot.frontRelic.setPosition(robot.RELIC_FRONT_OPEN);
            }
            if (gamepad1.b) {
                robot.frontRelic.setPosition(robot.RELIC_FRONT_CLOSED);
            }


            if (gamepad1.dpad_left) {
                robot.strafeLeft(0.25);
            }

            if (gamepad1.dpad_right) {
                robot.strafeRight(0.25);
            }

            if (gamepad2.x) {
                robot.relic.setPower(1);
            } else if (!gamepad2.x) {
                robot.relic.setPower(0);
            }

            if (gamepad2.y) {
                robot.relic.setPower(-1);
            } else if (!gamepad2.y) {
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
