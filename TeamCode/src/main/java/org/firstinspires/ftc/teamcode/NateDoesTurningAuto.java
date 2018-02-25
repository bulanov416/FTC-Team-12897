package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

import static java.lang.Math.abs;

/**
 * Created by Alex Bulanov on 2/24/2018.
 */

@Autonomous(name="Nate's good turn algorithms")
public class NateDoesTurningAuto extends LinearOpMode {

    Robot robot;
    BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);

        robot.init();

        waitForStart();

        pTurn(90, 0.002);
        sleep(500);
        pTurn(0, 0.002);
        sleep(500);
        pTurn(30, 0.002);
        sleep(500);
        pTurn(60, 0.002);
        sleep(500);
        pTurn(120, 0.002);
        sleep(500);
        pTurn(-60, 0.002);
        sleep(500);
        pTurn(0, 0.002);
        sleep(500);
    }

    /**
     * Nate's Proportional turn loop.
     * @param target target heading (-180 to 180)
     * @param kP this should be 0.02, just don't question it.
     */
    public void pTurn(double target, double kP) {
        double error = getOrientaion() - target;
        while (abs(error) > 4 && opModeIsActive()) {
            robot.rotateLeft(error * kP);
            error = getOrientaion() - target;
        }
    }

    /**
     * @return the z-axis orientation of the robot
     */
    public float getOrientaion() {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }
}
