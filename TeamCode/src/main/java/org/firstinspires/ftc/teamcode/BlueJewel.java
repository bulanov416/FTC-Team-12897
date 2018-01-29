package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Alex Bulanov on 1/28/2018.
 */
@Autonomous(name="BlueJewel")
public class BlueJewel extends LinearOpMode {

    public Robot robot;

    public void runOpMode() throws InterruptedException {
        //Scan Jewel
        if (robot.jewelColorRight.blue() < robot.jewelColorRight.red()) {
            robot.rotateRight(0.23); // was 0.25
            sleep(250);
            robot.stopDrive();
            robot.rightWing.setPosition(robot.RIGHT_WING_UP);
            sleep(250);
            robot.rotateLeft(0.23); // was  0.25
            sleep(250);
            robot.stopDrive();
        }
        else if (robot.jewelColorRight.blue() > robot.jewelColorRight.red()) {
            robot.rotateLeft(0.23);
            sleep(250);
            robot.stopDrive();
            robot.rightWing.setPosition(robot.RIGHT_WING_UP);
            sleep(250);
            robot.rotateRight(0.23);
            sleep(250);
            robot.stopDrive();
        }
        robot.rightWing.setPosition(robot.RIGHT_WING_UP);
        sleep(500);
    }
}
