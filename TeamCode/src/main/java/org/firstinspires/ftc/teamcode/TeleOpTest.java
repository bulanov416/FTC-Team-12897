package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOpTest")
public class TeleOpTest extends LinearOpMode {

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
           if (gamepad1.x) {
               robot.frontLeftDrive.setPower(0.2);
           } else {
               robot.frontLeftDrive.setPower(0);
           }


           if (gamepad1.y) {
               robot.frontRightDrive.setPower(0.2);
           } else {
               robot.frontRightDrive.setPower(0);
           }

           if (gamepad1.b) {
               robot.backLeftDrive.setPower(0.2);
           } else {
               robot.backLeftDrive.setPower(0);
           }

           if (gamepad1.a) {
               robot.backRightDrive.setPower(0.2);
           } else {
               robot.backLeftDrive.setPower(0);
           }

        }
    }
}
