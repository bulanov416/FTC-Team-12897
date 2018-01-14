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

    float leftPower;
    float rightPower;
    float centerPower;

    public Robot robot;

    public void runOpMode() throws InterruptedException {
        telemetry.addLine("1");
        telemetry.update();
        this.robot = new Robot(hardwareMap);
        telemetry.addLine("2");
        telemetry.update();
        robot.init();
        waitForStart();
        while (opModeIsActive()) {
            leftPower = gamepad1.left_stick_y;
            rightPower = gamepad1.right_stick_y;
            centerPower = gamepad1.left_stick_x;


            if (gamepad1.right_bumper) {
                robot.inLeft.setPower(0.75);
                robot.inRight.setPower(0.75);
            } else if (!gamepad1.right_bumper) {
                robot.inLeft.setPower(0);
                robot.inRight.setPower(0);
            }

            if (gamepad1.left_bumper) {
                robot.inLeft.setPower(-0.75);
                robot.inRight.setPower(-0.75);
            } else if (!gamepad1.left_bumper) {
                robot.inLeft.setPower(0);
                robot.inRight.setPower(0);
            }

            if (gamepad1.y) {
                robot.leftDrive.setPower(0.5);
                robot.rightDrive.setPower(0.5);
            }

            if (gamepad1.a) {
                robot.leftDrive.setPower(-0.5);
                robot.rightDrive.setPower(-0.5);
            }

            if (gamepad1.dpad_up) {
                robot.leftLift.setPosition(robot.RAMP_LEFT_UP);
                robot.rightLift.setPosition(robot.RAMP_RIGHT_UP);
            }

            if (gamepad1.dpad_down) {
                robot.leftLift.setPosition(robot.RAMP_LEFT_DOWN);
                robot.rightLift.setPosition(robot.RAMP_RIGHT_DOWN);
            }

            if (gamepad2.dpad_left) {
                robot.leftWing.setPosition(robot.LEFT_WING_DOWN);
                robot.rightWing.setPosition(robot.RIGHT_WING_DOWN);
            }

            if (gamepad2.dpad_right) {
                robot.leftWing.setPosition(robot.LEFT_WING_UP);
                robot.rightWing.setPosition(robot.RIGHT_WING_UP);
            }

            if (gamepad2.x) {
                robot.leftSensor.setPosition(robot.LEFT_SENSOR_DOWN);
                robot.rightSensor.setPosition(robot.RIGHT_SENSOR_DOWN);
            }

            if (gamepad2.b) {
                robot.leftSensor.setPosition(robot.LEFT_SENSOR_UP);
                robot.rightSensor.setPosition(robot.RIGHT_SENSOR_UP);
            }

            if (gamepad2.dpad_up) {
                robot.forward(0.5);
            }
            if (gamepad2.dpad_down) {
                robot.backward(0.5);
            }
           /* robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);
            robot.centerDrive.setPower(centerPower);*/
        }
    }
}
