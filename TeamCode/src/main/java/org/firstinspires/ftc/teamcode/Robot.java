package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.AnalogInput;

public class Robot {
    public HardwareMap map;
    public static AnalogInput left;
    public static AnalogInput right;
    public static AnalogInput center;
    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public Double wheelDiameter;

    public Robot (HardwareMap hardwareMap) {
        this.map = hardwareMap;
        left = map.analogInput.get("left");
        right = map.analogInput.get("right");
        center = map.analogInput.get("center");
        //fl = map.dcMotor.get("fl");
        //fr = map.dcMotor.get("fr");
        //bl = map.dcMotor.get("bl");
        //br = map.dcMotor.get("br");
    }



}