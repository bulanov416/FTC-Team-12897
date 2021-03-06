package org.firstinspires.ftc.teamcode;

import android.graphics.Camera;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.sun.tools.javac.code.Symbol;
import com.vuforia.CameraDevice;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Locale;

import static java.lang.Double.NaN;

/**
 * Created by Alex Bulanov on 12/16/2017.
 */

public class Robot {

    public DcMotor frontLeftDrive;
    public DcMotor backLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor backRightDrive;
    public DcMotor inLeft;
    public DcMotor inRight;
    public Servo leftLift;
    public Servo rightLift;
    public Servo leftSensor;
    public Servo rightSensor;
    public Servo leftWing;
    public Servo rightWing;
    public ColorSensor columnColorLeft;
    public ColorSensor columnColorRight;
    public DistanceSensor columnDistanceLeft;
    public DistanceSensor columnDistanceRight;
    public ColorSensor frontLeft;
    public ColorSensor frontRight;
    public ColorSensor juulColorLeft;
    public ColorSensor juulColorRight;

    IntegratingGyroscope imu;
    NavxMicroNavigationSensor navxMicro;

    public int columnCount = 0;
    public char vuMarkData;

    //Constants
    public static double RAMP_LEFT_UP = 0.04; // was 0.04
    public static double RAMP_LEFT_DOWN = 0.722; // was 0.722
    public static double RAMP_RIGHT_UP = 0.96; // was 0.88
    public static double RAMP_RIGHT_DOWN = 0.278; // was 0.18
    public static double LEFT_WING_UP = 0.64; //was 0.6
    public static double LEFT_WING_DOWN = 0.1; //was 0.3
    public static double RIGHT_WING_UP = 0.04; //was 0.9
    public static double RIGHT_WING_DOWN = 0.57; // was 0.58
    public static double LEFT_SENSOR_UP = 0.88;
    public static double LEFT_SENSOR_DOWN = 0.12;
    public static double RIGHT_SENSOR_UP = 0.12;// was 0.5
    public static double RIGHT_SENSOR_DOWN = 0.98;

    public static double HEADING = 0;
    public static double ERROR = 0.5;

    public HardwareMap map;

    public Robot (HardwareMap hardwareMap) {
        this.map = map;

        //Drive
        frontLeftDrive = hardwareMap.dcMotor.get("fl");
        frontRightDrive = hardwareMap.dcMotor.get("fr");
        backLeftDrive = hardwareMap.dcMotor.get("bl");
        backRightDrive = hardwareMap.dcMotor.get("br");

        //Color Sensors
        columnColorRight = hardwareMap.colorSensor.get("ccr");
        columnColorLeft = hardwareMap.colorSensor.get("ccl");
        frontLeft = hardwareMap.colorSensor.get("fl");
        frontRight = hardwareMap.colorSensor.get("fr");

        //
        columnDistanceRight = hardwareMap.get(DistanceSensor.class, "ccr");
        columnDistanceLeft = hardwareMap.get(DistanceSensor.class, "ccl");

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

        //Juul Sensors
        juulColorLeft = hardwareMap.colorSensor.get("jcl");
        juulColorRight = hardwareMap.colorSensor.get("jcr");

        //IMU
        navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "imu");
        imu = (IntegratingGyroscope)navxMicro;
        //Config
        /*rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        centerDrive.setDirection(DcMotorSimple.Direction.REVERSE);*/
    }

    public void init() {
        //Config
        /*rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        centerDrive.setDirection(DcMotorSimple.Direction.REVERSE);*/
        frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        inRight.setDirection(DcMotorSimple.Direction.REVERSE);
        rightWing.setPosition(RIGHT_WING_UP);
        leftWing.setPosition(LEFT_WING_UP);
        leftLift.setPosition(RAMP_LEFT_DOWN);
        rightLift.setPosition(RAMP_RIGHT_DOWN);
    }
    public void forward(double power){
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower(power);
    }
    public void backward(double power) {
        frontLeftDrive.setPower(-power);
        frontRightDrive.setPower(-power);
        backLeftDrive.setPower(-power);
        backRightDrive.setPower(-power);
    }
    public static void turnleft(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack, double rightStickY, double leftStickY){
        leftFront.setPower(rightStickY);
        rightFront.setPower(leftStickY);
        leftBack.setPower(rightStickY);
        rightBack.setPower(leftStickY);
    }
    public static void turnright(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack, double rightStickY, double leftStickY){
        leftFront.setPower(leftStickY);
        rightFront.setPower(rightStickY);
        leftBack.setPower(leftStickY);
        rightBack.setPower(rightStickY);
    }
    public static void strafeleft(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack, double leftStickX){
        leftFront.setPower(-leftStickX);
        rightFront.setPower(leftStickX);
        leftBack.setPower(leftStickX);
        rightBack.setPower(-leftStickX);
    }
    public static void straferight(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack, double rightStickX){
        leftFront.setPower(rightStickX);
        rightFront.setPower(-rightStickX);
        leftBack.setPower(-rightStickX);
        rightBack.setPower(rightStickX);
    }
    public void stopDrive() {
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
    }
    public void intakeGlyph(double power) {
        inLeft.setPower(-power);
        inRight.setPower(-power);
    }
    public void ejectGlyph(double power) {
        inLeft.setPower(power);
        inRight.setPower(power);
    }
    public void between() throws InterruptedException {
        stopDrive();
        Thread.sleep(500);
    }


    public double getDR() {
        double distanceRight = columnDistanceRight.getDistance(DistanceUnit.CM);
        return distanceRight;
    }

    public double getDL() {
        double distanceLeft = columnDistanceLeft.getDistance(DistanceUnit.CM);
        return distanceLeft;
    }
}
