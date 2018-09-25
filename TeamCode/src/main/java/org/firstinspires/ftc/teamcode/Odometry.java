package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;


@TeleOp(name="Odometry")
public class Odometry extends LinearOpMode {

    public Robot robot;

    public Encoder left;
    public Encoder right;
    public Encoder center;

    public static double X;
    public static double Y;
    public static double DEG;
    public static double DeltaRL;

    public static ArrayList<Encoder> allEncoders = new ArrayList<Encoder>();

    public void runOpMode() throws InterruptedException {
        this.robot = new Robot(hardwareMap);
        this.left = new Encoder();
        this.right = new Encoder();
        this.center = new Encoder();
        StaticLog.clearLog();
        waitForStart();
        robot.left.resetDeviceConfigurationForOpMode();
        robot.right.resetDeviceConfigurationForOpMode();
        robot.center.resetDeviceConfigurationForOpMode();
        Encoder.angleLeftAdjust = Robot.left.getVoltage() / 3.26 * 360;
        Encoder.angleRightAdjust = Robot.right.getVoltage() / 3.26 * 360;
        Encoder.angleCenterAdjust = Robot.center.getVoltage() / 3.26 * 360;
        while (opModeIsActive()) {
            Encoder.updatePosition(left, right, center);

            telemetry.addData("left Distance Traveled: ", Encoder.returnDistanceTraveled(left));
            telemetry.addData("right Distance Traveled: ", Encoder.returnDistanceTraveled(right));
            telemetry.addData("center Distance Traveled: ", Encoder.returnDistanceTraveled(center));
            telemetry.addLine("left Angle: " + left.newAngle);
            telemetry.addLine("right Angle: " + right.newAngle);
            telemetry.addLine("center Angle: " + center.newAngle);
            telemetry.addLine("X: " + X);
            telemetry.addLine("Y: " + Y);
            telemetry.addLine("DEG: " + DEG);
            telemetry.addLine("deltaRL: " + DeltaRL);
            telemetry.update();

        }
    }
}
