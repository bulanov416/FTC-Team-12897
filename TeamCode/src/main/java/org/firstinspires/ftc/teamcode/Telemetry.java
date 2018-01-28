package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Telemetry")
public class Telemetry extends LinearOpMode {

    public Robot robot;

    public void runOpMode() throws InterruptedException {

        this.robot = new Robot(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
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
        }}

}