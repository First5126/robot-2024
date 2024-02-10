package frc.robot.subsystems;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NoteStuckOutake extends SubsystemBase {
    private CANSparkMax sparkyone;
    private double Armspeedin;
    private double Armspeedout;
    private DigitalInput digitalinputin;
  
    public NoteStuckOutake() {
        digitalinputin = new DigitalInput(0);
        sparkyone = new CANSparkMax(5, MotorType.kBrushless);
        Armspeedin = SmartDashboard.getNumber("intakespeed",.15);
        Armspeedout = SmartDashboard.getNumber("outtake speed",.15);
    }   
    
    public void enableOutakeRev(){
        sparkyone.set(Armspeedin);
        sparkyone.setInverted(true);
    }

    public void disableOutakeRev(){
        sparkyone.set(0);
    }
}