// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;
import com.revrobotics.Rev2mDistanceSensor.RangeProfile;
import com.revrobotics.Rev2mDistanceSensor.Unit;

public class Shooter extends SubsystemBase {

    private TalonFX LeftShoot;
    private TalonFX RightShoot;
    private CANSparkMax Intake;
    private double ShooterRotationsPerSecond;
    private double ReverseNoteSpeed;
    private double PickUpSpeed;
    private double MoveNoteSpeed;
    private DigitalInput BackSensor;
    private DigitalInput FrontSensor;
    private double AverageShooterVelocity;
    private Slot0Configs slot0Configs;
    private VelocityVoltage velocityVoltage;
    private double goalRPS;
    private Pigeon2 Pigeon;
    private Rev2mDistanceSensor DistanceSensor;



    private Encoder revThroughBore;
    public Shooter() {
        
        slot0Configs = new Slot0Configs();
        slot0Configs.kP = Constants.ShooterConstants.kP;
        slot0Configs.kI = Constants.ShooterConstants.kI;
        slot0Configs.kD = Constants.ShooterConstants.kD;
        slot0Configs.kA = Constants.ShooterConstants.kA;
        slot0Configs.kS = Constants.ShooterConstants.kS;
        slot0Configs.kV = Constants.ShooterConstants.kV;
        slot0Configs.kG = Constants.ShooterConstants.kG;

        velocityVoltage = new VelocityVoltage(0).withSlot(0);
        LeftShoot = new TalonFX(5);
            LeftShoot.setInverted(true);
            LeftShoot.getConfigurator().apply(slot0Configs);

        RightShoot = new TalonFX(16);
            RightShoot.setInverted(true);
            RightShoot.getConfigurator().apply(slot0Configs);

        Intake = new CANSparkMax(15, MotorType.kBrushless);
            Intake.setInverted(true);

        BackSensor = new DigitalInput(3);
        FrontSensor = new DigitalInput(4);

        DistanceSensor = new Rev2mDistanceSensor(Port.kMXP);


        SmartDashboard.putNumber("Reverse note speed", 0.0);
        //SmartDashboard.putNumber("Move Note Speed", 0.0);
        //SmartDashboard.putNumber("Goal RPS", 0);

        revThroughBore = new Encoder(1, 2);

        Pigeon = new Pigeon2(0);
    }

    @Override
    public void periodic() {
        //SmartDashboard.putNumber("Desired Rotations Per Second", 0);
        //SmartDashboard.putNumber("Intake Speed", 0);
        //ShooterRotationsPerSecond = SmartDashboard.getNumber("Desired Speed", 0.5);
        PickUpSpeed = SmartDashboard.getNumber("Pick Up Speed", 0.6);
        MoveNoteSpeed = SmartDashboard.getNumber("Move Note Speed", 0.15);
        goalRPS = SmartDashboard.getNumber("Goal RPS", 29);
        ReverseNoteSpeed = SmartDashboard.getNumber("Reverse note speed", 0.0);
        SmartDashboard.putBoolean("Back Sensor", BackSensor.get());
        SmartDashboard.putBoolean("Front Sensor", FrontSensor.get());
        SmartDashboard.putNumber("Arm Encoder", revThroughBore.getDistance());
        SmartDashboard.putNumber("Angle", Pigeon.getAngle());
        SmartDashboard.putNumber("Rate of Rotation", Pigeon.getRate());
        SmartDashboard.putNumber("Roll", Pigeon.getRoll().getValueAsDouble());
        SmartDashboard.putNumber("Yaw", Pigeon.getYaw().getValueAsDouble());
        SmartDashboard.putNumber("Pitch", Pigeon.getPitch().getValueAsDouble());
        SmartDashboard.putBoolean("Is in range", DistanceSensor.isRangeValid());
        SmartDashboard.putNumber("Distance Sensor Range in inches", DistanceSensor.getRange(Unit.kInches));

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
            return true;
        }
        else{
            return false;
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
