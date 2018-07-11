package org.firstinspires.ftc.teamcode;


public class Encoder {

    String encoderName;
    double newAngle;
    double oldAngle;
    static double wheelCircumference = 1.89 * Math.PI; //Inches
    static double robotRadius = 14.6; //Inches
    static double deltaLeft;
    static double deltaRight;
    static double deltaCenter;
    static double deltaTheta;

    public Encoder() {
        newAngle = 0;
        oldAngle = 0;
        Odometry.allEncoders.add(this);
    }

    public static double returnDistanceTraveled(Encoder encoder) {

        double newEncoderAngle = encoder.newAngle + (180 - encoder.oldAngle);


        if (newEncoderAngle > 360) {
            newEncoderAngle -= 360;
        }

        if (newEncoderAngle < 0) {
            newEncoderAngle += 360;
        }

        double degreesChanged = newEncoderAngle - 180;

        encoder.oldAngle = encoder.newAngle;

        return (degreesChanged / 360) * wheelCircumference;
    }

    public static void updatePosition(Encoder xLeft, Encoder xRight, Encoder y) {

        xLeft.newAngle = Robot.left.getVoltage() / 3.26 * 360;
        xRight.newAngle = Robot.right.getVoltage() / 3.26 * 360;
        y.newAngle = Robot.center.getVoltage() / 3.26 * 360;

        deltaLeft = returnDistanceTraveled(xLeft);
        deltaRight = -returnDistanceTraveled(xRight);
        deltaCenter = returnDistanceTraveled(y);
        deltaTheta = Math.toDegrees(Math.acos(1 - ((Math.pow(deltaRight - deltaLeft, 2)) / (2 * Math.pow(robotRadius, 2)))));

        // Degree
        if (deltaRight > deltaLeft) {
            Odometry.DEG -= deltaTheta;
        }
        if (deltaLeft > deltaRight) {
            Odometry.DEG += deltaTheta;
        }
        if (Odometry.DEG > 360) {
            Odometry.DEG = Odometry.DEG - 360;
        } else if (Odometry.DEG < 0) {
            Odometry.DEG = Odometry.DEG + 360;
        }

        //Distance traveled calculations
        //Data from left and right sensors
        Odometry.X += ((deltaLeft+deltaRight)/2)*Math.cos(90-(Odometry.DEG-deltaTheta));
        Odometry.Y += ((deltaLeft+deltaRight)/2)*Math.sin(90-(Odometry.DEG-deltaTheta));
        //Data from center sensor
        Odometry.X += ((deltaCenter)/2)*Math.cos(90-(Odometry.DEG-deltaTheta));
        Odometry.Y += ((deltaCenter)/2)*Math.sin(90-(Odometry.DEG-deltaTheta));
    }
}