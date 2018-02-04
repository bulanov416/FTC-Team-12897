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
           if (gamepad1.dpad_up) {
               robot.forward(0.8);
           } else {
               robot.stopDrive();
           }

           if (gamepad1.dpad_down) {
               robot.backward(0.8);
           } else {
               robot.stopDrive();
           }

           if (gamepad1.dpad_left) {
               robot.strafeLeft(0.8);
           } else {
               robot.stopDrive();
           }

           if (gamepad1.dpad_right) {
               robot.strafeRight(0.8);
           } else {
               robot.stopDrive();
           }

           if (gamepad1.x) {
               robot.rotateLeft(0.8);
           } else {
               robot.stopDrive();
           }

           if (gamepad1.b) {
               robot.rotateRight(0.8);
           } else {
               robot.stopDrive();
           }

        }
    }
}
