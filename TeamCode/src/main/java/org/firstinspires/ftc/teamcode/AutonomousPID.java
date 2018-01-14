package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Locale;

/**
 * Created by alexbulanov on 11/14/17.
 */

public class AutonomousPID extends LinearOpMode {

    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor centerDrive;
    DcMotor inLeft;
    DcMotor inRight;
    Servo leftLift;
    Servo rightLift;
    Servo leftSensor;
    Servo rightSensor;
    Servo leftWing;
    Servo rightWing;
    ColorSensor columnColorLeft;
    ColorSensor columnColorRight;
    ColorSensor frontLeft;
    ColorSensor frontRight;
    ColorSensor juulColor;
    BNO055IMU imu;
    int columnCount = 0;

    //Constants
    double RAMP_LEFT_UP = 0.04;
    double RAMP_LEFT_DOWN = 0.72;
    double RAMP_RIGHT_UP = 0.88;
    double RAMP_RIGHT_DOWN = 0.18;

    double LEFT_WING_UP = 0.6; //lower should bring it up. was 0.5
    double LEFT_WING_DOWN = 0.3; //was 0.5
    double RIGHT_WING_UP = 0.04; //was 0.9
    double RIGHT_WING_DOWN = 0.58; // was 0.5
    double LEFT_SENSOR_UP = 0.93;
    double LEFT_SENSOR_DOWN = 0.04;
    double RIGHT_SENSOR_UP = 0.3;
    double RIGHT_SENSOR_DOWN = 0.86;

    int DESIRED_HEADING = 0;
    double HEADING_LEEWAY = 0.2;

    Orientation angles;
    Acceleration gravity;

    public void runOpMode() throws InterruptedException {

        //Drive
        leftDrive = hardwareMap.dcMotor.get("left");
        rightDrive = hardwareMap.dcMotor.get("right");
        centerDrive = hardwareMap.dcMotor.get("center");

        //Color Sensors
        columnColorRight = hardwareMap.colorSensor.get("ccr");
        columnColorLeft = hardwareMap.colorSensor.get("ccl");
        frontLeft = hardwareMap.colorSensor.get("fl");
        frontRight = hardwareMap.colorSensor.get("fr");

        //Intake
        inLeft = hardwareMap.dcMotor.get("lspin");
        inRight = hardwareMap.dcMotor.get("rspin");

        //Lift
        leftLift = hardwareMap.servo.get("llift");
        rightLift = hardwareMap.servo.get("rlift");

        //Sensor Flippers
        leftSensor = hardwareMap.servo.get("lsensor");
        rightSensor = hardwareMap.servo.get("rsensor");

        //Wings
        leftWing = hardwareMap.servo.get("lwing");
        rightWing = hardwareMap.servo.get("rwing");

        //Config
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        centerDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        //Set all servo positions
        leftSensor.setPosition(LEFT_SENSOR_DOWN);
        rightSensor.setPosition(RIGHT_SENSOR_DOWN);
        leftWing.setPosition(LEFT_WING_UP);
        rightWing.setPosition(RIGHT_WING_UP);
        leftLift.setPosition(RAMP_LEFT_DOWN);
        rightLift.setPosition(RAMP_RIGHT_DOWN);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        gravity  = imu.getGravity();

        waitForStart();

        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        while (opModeIsActive()) {
            composeTelemetry();
            telemetry.addLine("Starting PID Loop");
            telemetry.update();
            PID_DRIVE();
        }

    }
    public void PID_DRIVE() {
        if ((angles.firstAngle > DESIRED_HEADING + HEADING_LEEWAY) && opModeIsActive()) {
            leftDrive.setPower(0.25);
            telemetry.addLine("Twisting Right");
            telemetry.update();
        } else if ((angles.firstAngle < DESIRED_HEADING - HEADING_LEEWAY) && opModeIsActive()) {
            leftDrive.setPower(-0.25);
            telemetry.addLine("Twisting Left");
            telemetry.update();
        } else if (((angles.firstAngle == DESIRED_HEADING + HEADING_LEEWAY) || (angles.firstAngle == DESIRED_HEADING - HEADING_LEEWAY)) && opModeIsActive()) {
            leftDrive.setPower(0);
            telemetry.addLine("Good position!");
            telemetry.update();
        }
    }
    void composeTelemetry() {

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() { @Override public void run()
        {
            // Acquiring the angles is relatively expensive; we don't want
            // to do that in each of the three items that need that info, as that's
            // three times the necessary expense.
            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            gravity  = imu.getGravity();
        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override public String value() {
                        return gravity.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(gravity.xAccel*gravity.xAccel
                                        + gravity.yAccel*gravity.yAccel
                                        + gravity.zAccel*gravity.zAccel));
                    }
                });
    }

    //----------------------------------------------------------------------------------------------
    // Formatting
    //----------------------------------------------------------------------------------------------

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
