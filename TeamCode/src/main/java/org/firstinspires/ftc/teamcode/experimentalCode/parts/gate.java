package org.firstinspires.ftc.teamcode.experimentalCode.parts;

import org.firstinspires.ftc.teamcode.configuration.Hardware;

public class gate {

    private Hardware hardware;
    private double defaultVelocity = 430;

    public gate(Hardware hardware)
    {
        this.hardware = hardware;

    }

    public void setDefaultGateVelocity(double velo)
    {
        defaultVelocity = velo;
    }
    public void halt()
    {
        hardware.gate.setVelocity(100);
    }

    public void stop()
    {
        hardware.gate.setVelocity(0);
    }

    public void setVelocity(double velocity)
    {
        hardware.gate.setVelocity(velocity);
    }


}


