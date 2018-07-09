package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Teleop")
public class Teleop extends LinearOpMode {

    public Robot robot;

    public void runOpMode() throws InterruptedException {
        this.robot = new Robot(hardwareMap);

        waitForStart();
        double driveAngle = 0;
        robot.fl.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.bl.setDirection(DcMotorSimple.Direction.REVERSE);
        //robot.encoder.resetDeviceConfigurationForOpMode();
        while (opModeIsActive()) {
            driveAngle = Math.toDegrees(Math.atan(gamepad1.left_stick_x/gamepad1.left_stick_y)) + 90;
            if(gamepad1.left_stick_y >= 0) {
                driveAngle += 180;
            }
            if (gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0) {
                robot.fl.setPower(0);
                robot.fr.setPower(0);
                robot.bl.setPower(0);
                robot.br.setPower(0);
            }
            if(driveAngle > 315 || driveAngle < 135) {
                robot.fl.setPower(1);
                robot.br.setPower(1);
            }
            else if (driveAngle < 315 && driveAngle > 135){
                robot.fl.setPower(-1);
                robot.br.setPower(-1);
            }
            else {
                robot.fr.setPower(0);
                robot.bl.setPower(0);
            }
            if(driveAngle > 45 && driveAngle < 225) {
                robot.fr.setPower(1);
                robot.bl.setPower(1);
            }
            else if(driveAngle < 45 || driveAngle > 225) {
                robot.fr.setPower(-1);
                robot.bl.setPower(-1);
            }
            else {
                robot.fl.setPower(0);
                robot.br.setPower(0);
            }
            telemetry.addLine("Angle: " + driveAngle);
            telemetry.update();
        }
    }
}
