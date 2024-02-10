package frc.robot.subsystems;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NoteStuckIntake extends SubsystemBase {
    private TalonFX talonFXone;
    private TalonFX talonFXtwo;
    private double Armspeedin;
    private double Armspeedout;
    private DigitalInput digitalinputin;
  
    public NoteStuckIntake() {
        digitalinputin = new DigitalInput(0);
        talonFXone = new TalonFX(5);
        talonFXtwo = new TalonFX(6);
        Armspeedin = SmartDashboard.getNumber("intakespeed",0);
        Armspeedout = SmartDashboard.getNumber("outtake speed",0);
    }   

    public boolean enableIntakeRev(){
        talonFXone.set(Armspeedout);
        talonFXtwo.set(Armspeedout);
        talonFXone.setInverted(true);
        return !digitalinputin.get();
    }

    public void disableIntakeRev(){
        talonFXone.set(0);
        talonFXtwo.set(0);
    }
}