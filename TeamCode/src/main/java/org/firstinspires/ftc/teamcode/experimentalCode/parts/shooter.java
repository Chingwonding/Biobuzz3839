package org.firstinspires.ftc.teamcode.experimentalCode.parts;

import org.firstinspires.ftc.teamcode.configuration.Hardware;

public class shooter {
    private Hardware hardware;
    private double defaultPower = 1.0;
    private double defaultVelocity = 2000.0; // ticks per second

    public shooter() {}

    public shooter(Hardware hardware) {
        this.hardware = hardware;
    }

    public void initialize(Hardware hardware) {
        this.hardware = hardware;
    }

    // --- Speed Adjustment Methods ---

    public void setDefaultPower(double power) {
        this.defaultPower = power;
    }

    public double getDefaultPower() {
        return defaultPower;
    }

    public void setDefaultVelocity(double velocity) {
        this.defaultVelocity = velocity;
    }

    public double getDefaultVelocity() {
        return defaultVelocity;
    }

    // --- Shooter Actions ---

    /**
     * Preps shooter motor at the default power level.
     */
    public void prepShooters() {
        prepShooters(defaultPower);
    }

    /**
     * Preps shooter motor at a specified power level.
     */
    public void prepShooters(double power) {
        if (hardware != null && hardware.shooter != null) {
            hardware.shooter.setPower(power);
        }
    }

    /**
     * Sets shooter motor velocity using the default velocity setting.
     */
    public void setVelocity() {
        setVelocity(defaultVelocity);
    }

    /**
     * Sets shooter motor velocity in encoder ticks per second.
     */
    public void setVelocity(double velocity) {
        if (hardware != null && hardware.shooter != null) {
            hardware.shooter.setVelocity(velocity);
        }
    }

    /**
     * Operates the gate motor to feed elements into the shooter.
     */
    public void setGatePower(double power) {
        if (hardware != null && hardware.gate != null) {
            hardware.gate.setPower(power);
        }
    }

    /**
     * Stops both shooter and gate motors.
     */
    public void stop() {
        if (hardware != null) {
            if (hardware.shooter != null) hardware.shooter.setPower(0);
            if (hardware.gate != null) hardware.gate.setPower(0);
        }
    }
}
