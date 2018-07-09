package org.firstinspires.ftc.teamcode;

import android.graphics.Point;

public class Encoder {

    String encoderName;
    double newAngle;
    double oldAngle;
    static double wheelCircumference = 1.89 * Math.PI; //in inches
    static double robotRadius = 10; //in inches 3==================================================> get the real value. also reminder- this is the space between the encoders

    public Encoder() {
        newAngle = 0;
        oldAngle = 0;
        Odometry.allEncoders.add(this);
    }

    public static double returnDistanceTraveled (Encoder encoder) {

        //encoder.newAngle = fetch;//the current encoder angle

        double newEncoderAngle =  encoder.newAngle +(180-encoder.oldAngle);

        if (newEncoderAngle > 360) {
            newEncoderAngle-=360;
        }
        if (newEncoderAngle < 0) {
            newEncoderAngle += 360;
        }
        double degreesChanged = newEncoderAngle - 180;

        encoder.oldAngle = encoder.newAngle;

        return (degreesChanged /360) * wheelCircumference;
    }

    public static void updatePosition(Encoder xLeft, Encoder xRight, Encoder y) {

       // Odometry.X += returnDistanceTraveled(xLeft);
       // Odometry.Y += returnDistanceTraveled(y);

      /*  compare distancetraveled of xleft and xright

                if xleft traveled farther, the degree count increases (clockwise)
                if xright traveled farther, the degree count decreases (counter-clockwise)
        */

      if (returnDistanceTraveled(xLeft) > returnDistanceTraveled(xRight)) {
          double c = returnDistanceTraveled(xLeft) - returnDistanceTraveled(xRight);
          Odometry.DEG += Math.toDegrees(Math.acos(1-((Math.pow(c, 2))/(2 * Math.pow(robotRadius, 2)))));
      } else if (returnDistanceTraveled(xLeft) < returnDistanceTraveled(xRight)) {
          double c = returnDistanceTraveled(xRight) - returnDistanceTraveled(xLeft);
          Odometry.DEG -= Math.toDegrees(Math.acos(1-((Math.pow(c, 2))/(2 * Math.pow(robotRadius, 2)))));
      }

      //this part clips our degree counter to 0<DEG<360
      if (Odometry.DEG > 360) {
          Odometry.DEG = Odometry.DEG - 360;
      } else if (Odometry.DEG < 0) {
          Odometry.DEG = Odometry.DEG + 360;
      }

      /*
      THIS IS FOR UPDATING OUR X AND Y CORDS


       */

    }

}
