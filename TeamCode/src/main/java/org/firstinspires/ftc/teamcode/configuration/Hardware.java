package org.firstinspires.ftc.teamcode.configuration;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class Hardware {
    //define all 4 movement motors
    public DcMotorEx frontLeftWheel;
    public DcMotorEx frontRightWheel;
    public DcMotorEx backLeftWheel;
    public DcMotorEx backRightWheel;
    public DcMotorEx gate;
    public DcMotorEx shooter;
    public DcMotorEx intake;

    public double maxSpeed = 1.0;

    public HardwareMap hardwareMap;

    public void initialization(HardwareMap robot) {
        this.hardwareMap = robot;

        frontLeftWheel  = wheelInstantiationHelper("em1", true);
        frontRightWheel = wheelInstantiationHelper("cm2", true);
        backLeftWheel   = wheelInstantiationHelper("em0", true);
        backRightWheel  = wheelInstantiationHelper("cm3", false);
        intake = wheelInstantiationHelper("em2", true);
        gate = wheelInstantiationHelper("cm1", false);
        shooter = wheelInstantiationHelper("cm0", true);




        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        gate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gate.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    public DcMotorEx wheelInstantiationHelper(String configSlot, boolean reverse) {
        DcMotorEx wheel = hardwareMap.get(DcMotorEx.class, configSlot);
        if (reverse)
        {
            wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else
        {
            wheel.setDirection(DcMotorSimple.Direction.FORWARD);
        }

        wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheel.setPower(0);
        return wheel;
    }

    public void setPower(double fr, double br, double bl, double fl) {
        frontRightWheel.setPower(Range.clip(fr, -maxSpeed, maxSpeed));
        backRightWheel.setPower(Range.clip(br, -maxSpeed, maxSpeed));
        backLeftWheel.setPower(Range.clip(bl, -maxSpeed, maxSpeed));
        frontLeftWheel.setPower(Range.clip(fl, -maxSpeed, maxSpeed));
    }
}
