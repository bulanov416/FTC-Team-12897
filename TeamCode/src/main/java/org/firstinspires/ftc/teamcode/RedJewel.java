package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Alex Bulanov on 1/28/2018.
 */

@Autonomous(name="RedJewel")
public class RedJewel extends LinearOpMode {

    public Robot robot;

    public void runOpMode() throws InterruptedException {
        //Scan Jewel
        if (robot.jewelColor.blue() > robot.jewelColor.red()) {
            robot.rotateRight(0.23); // was 0.25
            sleep(250);
            robot.stopDrive();
            robot.wing.setPosition(robot.RIGHT_WING_UP);
            sleep(250);
            robot.rotateLeft(0.23); // was  0.25
            sleep(250);
            robot.stopDrive();
        }
        else if (robot.jewelColor.blue() < robot.jewelColor.red()) {
            robot.rotateLeft(0.23);
            sleep(250);
            robot.stopDrive();
            robot.wing.setPosition(robot.RIGHT_WING_UP);
            sleep(250);
            robot.rotateRight(0.23);
            sleep(250);
            robot.stopDrive();
        }
        robot.wing.setPosition(robot.RIGHT_WING_UP);
        sleep(500);
    }
}

