package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;
import java.util.Date;


@TeleOp(name="RefreshRateTest")
public class RefreshRateTest extends LinearOpMode {

    public Robot robot;

    double temp = 0.00;
    int refreshCounterThreeSeconds = 0;
    int refreshCounterFiveSeconds = 0;
    int refreshCounterTenSeconds = 0;

    long startTime = System.currentTimeMillis();
    long timeElapsed = 0L;

    public void runOpMode() throws InterruptedException {
       this.robot = new Robot(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            if ((temp != (robot.left.getVoltage()/3.26*360)) && timeElapsed < 3000) {
                refreshCounterThreeSeconds++;
                temp = (robot.left.getVoltage()/3.26*360);
                timeElapsed = (new Date()).getTime() - startTime;
            }

            if ((temp != (robot.left.getVoltage()/3.26*360)) && timeElapsed < 5000) {
                refreshCounterFiveSeconds++;
                temp = (robot.left.getVoltage()/3.26*360);
                timeElapsed = (new Date()).getTime() - startTime;
            }

            if ((temp != (robot.left.getVoltage()/3.26*360)) && timeElapsed < 10000) {
                refreshCounterTenSeconds++;
                temp = (robot.left.getVoltage()/3.26*360);
                timeElapsed = (new Date()).getTime() - startTime;
            }

            telemetry.addData("Three Seconds: ", refreshCounterThreeSeconds/3);
            telemetry.addData("Five Seconds: ", refreshCounterFiveSeconds/5);
            telemetry.addData("Ten Seconds: ", refreshCounterTenSeconds/10);
            telemetry.addData("xLeft Encoder Data", robot.left.getVoltage()/3.26*360);
            telemetry.update();
        }
    }
}
