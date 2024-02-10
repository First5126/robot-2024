// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;

public class Shooter extends SubsystemBase {
    private TalonFX LeftShoot;
    private TalonFX RightShoot;
    private CANSparkMax Intake;
    private double ShooterRotationsPerSecond;
    private double PickUpSpeed;
    private double MoveNoteSpeed;
    private DigitalInput BackSensor;
    private DigitalInput FrontSensor;
    private double AverageShooterSpeed;
    public Shooter() {
        LeftShoot = new TalonFX(5);
            LeftShoot.setInverted(false);
        RightShoot = new TalonFX(6);
            RightShoot.setInverted(false);
        Intake = new CANSparkMax(5, MotorType.kBrushless);
            Intake.setInverted(false);
        BackSensor = new DigitalInput(3);
        FrontSensor = new DigitalInput(1);
        ShooterRotationsPerSecond = 0;
        
        
        SmartDashboard.putNumber("Desired Speed", 0);
        SmartDashboard.putNumber("Intake Speed", 0);
    }

    @Override
    public void periodic() {
        //SmartDashboard.putNumber("Desired Rotations Per Second", 0);
        //SmartDashboard.putNumber("Intake Speed", 0);
        ShooterRotationsPerSecond = SmartDashboard.getNumber("Desired Speed", 0.5);
        PickUpSpeed = SmartDashboard.getNumber("Pick Up Speed", 0.6);
        MoveNoteSpeed = SmartDashboard.getNumber("Move Note Speed", 0.15);
        SmartDashboard.putBoolean("Back Sensor", BackSensor.get());
        SmartDashboard.putBoolean("Front Sensor", FrontSensor.get());
    }

    @Override
    public void simulationPeriodic() {}

    public void setShooterRPS(){
        RightShoot.set(ShooterRotationsPerSecond);
        LeftShoot.set(ShooterRotationsPerSecond);
    }

    public void SetPickUpSpeed(){
        Intake.set(-PickUpSpeed);
    }

    public void zeroIntake(){
        Intake.stopMotor();
    }

    public void zeroShooter(){
        LeftShoot.stopMotor();
        RightShoot.stopMotor();
    }

    public double getShooterValue(){
        AverageShooterSpeed = Math.abs(LeftShoot.get()) + Math.abs(RightShoot.get()) / 2;
        return AverageShooterSpeed;
    }

    public double getDesiredShooterSpeed(){
        return ShooterRotationsPerSecond;
    }

    public boolean BackSeesNote(){
        if (BackSensor.get() == false){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean FrontSeesNote(){
        if (FrontSensor.get() == false){
            return true;
        }
        else{
            return false;
        }
    }

   public void ManualIntakeSpeed(double Speed){
    Intake.set(-Speed);
   }
   public void SetMoveNoteSpeed(){
        Intake.set(-MoveNoteSpeed);
    }
}
