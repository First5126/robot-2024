// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

    private TalonFX LeftShoot;
    private TalonFX RightShoot;
    private CANSparkMax Intake;
    private double ReverseNoteSpeed;
    private double PickUpSpeed;
    private double MoveNoteSpeed;
    private DigitalInput BackSensor;
    private DigitalInput FrontSensor;
    private double AverageShooterVelocity;
    private Slot0Configs slot0Configs;
    private VelocityVoltage velocityVoltage;
    private double goalRPS;
    private CommandGenericHID m_buttonsController;
    private CommandGenericHID m_driverController;
    //private Pigeon2 Pigeon;
    public boolean rumbling;
    public Timer timer = new Timer();


    public Shooter(CommandGenericHID controller, CommandGenericHID m_driverController2) {
        
        slot0Configs = new Slot0Configs();
        slot0Configs.kP = Constants.ShooterConstants.kP;
        slot0Configs.kI = Constants.ShooterConstants.kI;
        slot0Configs.kD = Constants.ShooterConstants.kD;
        slot0Configs.kA = Constants.ShooterConstants.kA;
        slot0Configs.kS = Constants.ShooterConstants.kS;
        slot0Configs.kV = Constants.ShooterConstants.kV;
        slot0Configs.kG = Constants.ShooterConstants.kG;

        velocityVoltage = new VelocityVoltage(0).withSlot(0);
        LeftShoot = new TalonFX(15);
            LeftShoot.setInverted(true);
            LeftShoot.getConfigurator().apply(slot0Configs);

        RightShoot = new TalonFX(16);
            RightShoot.setInverted(true);
            RightShoot.getConfigurator().apply(slot0Configs);

        Intake = new CANSparkMax(15, MotorType.kBrushless);
            Intake.setInverted(true);
            ReverseNoteSpeed = 0.3;
            PickUpSpeed = 0.6;
            MoveNoteSpeed = 0.15;                                  

        BackSensor = new DigitalInput(3);
        FrontSensor = new DigitalInput(4);

        m_buttonsController = controller;
        m_driverController = m_driverController2;
    }

    @Override
    public void periodic() {

        SmartDashboard.putBoolean("Back Sensor", BackSensor.get());
        SmartDashboard.putBoolean("Front Sensor", FrontSensor.get());

        if(rumbling){
            if (timer.hasElapsed(2)){
                timer.stop();
                timer.reset();
                m_buttonsController.getHID().setRumble(RumbleType.kBothRumble, 0);
                m_driverController.getHID().setRumble(RumbleType.kBothRumble, 0);
            }
        
    }
    }
    @Override
    public void simulationPeriodic() {}

    public void setShooterRPS(double rps){
        LeftShoot.setControl(velocityVoltage.withVelocity(rps));
        RightShoot.setControl(velocityVoltage.withVelocity(rps));
    }

    public void SetPickUpSpeed(){
        Intake.set(PickUpSpeed);
    }

    public void zeroIntake(){
        Intake.stopMotor();
    }

    public void zeroShooter(){
        LeftShoot.stopMotor();
        RightShoot.stopMotor();
    }

    public double getCurrentShooterVelocity(){
        double leftVelo = Math.abs(LeftShoot.getVelocity().getValueAsDouble());
        double rightVelo = Math.abs(RightShoot.getVelocity().getValueAsDouble());
        AverageShooterVelocity = (leftVelo + rightVelo) / 2;
        return AverageShooterVelocity;
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
            return false;
        }
        else{
            return true;
        }
    }

    public void ManualIntakeSpeed(double Speed){
        Intake.set(Speed);
    }

    public void SetMoveNoteSpeed(){
        Intake.set(MoveNoteSpeed);
    }

    public double getGoalRPS(){
        return goalRPS;
    }

    public void ReverseTheNote(){
        Intake.set(-ReverseNoteSpeed);
    }

}
