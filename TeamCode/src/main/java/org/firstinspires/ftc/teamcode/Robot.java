package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.AnalogInput;

public class Robot {
    public HardwareMap map;
    public AnalogInput encoder;
    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    double encoderAngle;

    public Robot (HardwareMap hardwareMap) {
        this.map = hardwareMap;
        encoder = map.analogInput.get("encoder");
        //fl = map.dcMotor.get("fl");
        //fr = map.dcMotor.get("fr");
        //bl = map.dcMotor.get("bl");
        //br = map.dcMotor.get("br");
    }
}