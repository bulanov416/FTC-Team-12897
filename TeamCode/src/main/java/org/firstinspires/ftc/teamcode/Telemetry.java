package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.Locale;

/*
 * Created by Alex Bulanov on 12/2/2017.
*/
@Autonomous(name="Telemetry")
public class Telemetry extends LinearOpMode {

    public Robot robot;

    Orientation angles;
    Acceleration gravity;

    ElapsedTime timer = new ElapsedTime();

    public void runOpMode() throws InterruptedException {

        this.robot = new Robot(hardwareMap);

        telemetry.log().add("Gyro Calibrating. Do Not Move!");

        timer.reset();
        while (robot.navxMicro.isCalibrating())  {
            telemetry.addData("calibrating", "%s", Math.round(timer.seconds())%2==0 ? "|.." : "..|");
            telemetry.update();
            Thread.sleep(50);
        }
        telemetry.log().clear();
        telemetry.log().add("Gyro Calibrated. Press Start.");
        waitForStart();
        while (opModeIsActive()) {

            telemetry.addData("heading", formatAngle(angles.angleUnit, angles.firstAngle));
            telemetry.addData("roll", formatAngle(angles.angleUnit, angles.secondAngle));
            telemetry.addData("pitch", "%s deg", formatAngle(angles.angleUnit, angles.thirdAngle));
            telemetry.update();
            telemetry.addData("Column Distance Left: ", robot.getDL());
            telemetry.addData("Column Distance Right: ", robot.getDR());
            telemetry.addData("ColumnLeftRed: ", robot.columnColorLeft.red());
            telemetry.addData("ColumnLeftBlue: ", robot.columnColorLeft.blue());
            telemetry.addData("ColumnRightRed: ", robot.columnColorRight.red());
            telemetry.addData("ColumnRightBlue: ", robot.columnColorRight.blue());
            telemetry.addData("JuulLeftRed: ", robot.juulColorLeft.red());
            telemetry.addData("JuulLeftBlue: ", robot.juulColorLeft.blue());
            telemetry.addData("JuulRightRed: ", robot.juulColorRight.red());
            telemetry.addData("JuulRightBlue: ", robot.juulColorRight.blue());
            telemetry.update();
        }
    }
    String formatRate(float rate) {
        return String.format("%.3f", rate);
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format("%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

}
