package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.AnalogInput;

public class Robot {
    public HardwareMap map;
    public AnalogInput xLeft;
    public AnalogInput xRight;
    public AnalogInput y;
    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public Double wheelDiameter;

    public Robot (HardwareMap hardwareMap) {
        this.map = hardwareMap;
        xLeft = map.analogInput.get("xLeft");
        xRight = map.analogInput.get("xRight");
        y = map.analogInput.get("y");
        //fl = map.dcMotor.get("fl");
        //fr = map.dcMotor.get("fr");
        //bl = map.dcMotor.get("bl");
        //br = map.dcMotor.get("br");
    }



}