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
        robot.init();
        waitForStart();

        while (opModeIsActive()) {
            straightPower = gamepad1.left_stick_y;
            sidePower = gamepad1.left_stick_x;

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

            if (gamepad1.dpad_up) {
                robot.leftLift.setPosition(robot.RAMP_LEFT_UP);
                robot.rightLift.setPosition(robot.RAMP_RIGHT_UP);
            }

            if (gamepad1.dpad_down) {
                robot.leftLift.setPosition(robot.RAMP_LEFT_DOWN);
                robot.rightLift.setPosition(robot.RAMP_RIGHT_DOWN);
            }

            if (gamepad1.a) {
               robot.frontRelic.setPosition(robot.RELIC_FRONT_UP);
            }
            if (gamepad1.y) {
                robot.frontRelic.setPosition(robot.RELIC_FRONT_DOWN);
            }
            if (gamepad1.x) {
                robot.backRelic.setPosition(robot.RELIC_BACK_UP);
            }
            if (gamepad1.b) {
                robot.backRelic.setPosition(robot.RELIC_BACK_DOWN);
            }

            if (sidePower > 0.5) {
                robot.strafeRight(sidePower);
            }
            else if (sidePower < -0.5) {
                robot.strafeLeft(-sidePower);
            }
            else if (straightPower > 0) {
                robot.backward(straightPower);

            }
            else if (straightPower < 0) {
                robot.forward(-straightPower);
            }
            else if (gamepad1.right_stick_y > 0) {
                robot.rotateLeft(gamepad1.right_stick_y);
            }
            else if (gamepad1.right_stick_y < 0) {
                robot.rotateRight (-gamepad1.right_stick_y);
            }
            else {
                robot.stopDrive();
            }
        }
    }
}
