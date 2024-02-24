package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{
    private TalonFX FalconLeftShooter;
    private TalonFX FalconRightShooter;
    private CANSparkMax IntakeSpark;
    public DigitalInput StopSensor;
    public DigitalInput SlowSensor;
    public Shooter(){
        StopSensor = new DigitalInput(0);
        SlowSensor = new DigitalInput(0);
        FalconLeftShooter = new TalonFX(0);
        FalconRightShooter = new TalonFX(0);
        IntakeSpark = new CANSparkMax(0, MotorType.kBrushless);
        FalconRightShooter.setInverted(true);
        IntakeSpark.setInverted(true);
    }
    @Override
    public void periodic(){}
    public void OuttakeOn(){
        FalconLeftShooter.set(0);
        FalconRightShooter.set(0);
        IntakeSpark.set(0);
    }
    public void OuttakeOff(){
        FalconLeftShooter.set(0);
        FalconRightShooter.set(0);
        IntakeSpark.set(0);
    }
    public void IntakeOn(){
        IntakeSpark.set(0);
    }
    public void IntakeOff(){
        IntakeSpark.set(0);
    }
    public void SlowIntake(){
        IntakeSpark.set(0);
    }
    public void UnJamOn(){
        IntakeSpark.set(0);
        IntakeSpark.setInverted(false);
    }
    public void UnJamOff(){
        IntakeSpark.set(0);
        IntakeSpark.setInverted(true);
    }
}
