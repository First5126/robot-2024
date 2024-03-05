package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj2.command.Command;


public class Intake extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private boolean isFinished;
    private boolean Slowed = false;
    private final GenericHID m_buttonsController;


    public Intake(Shooter subsystem, GenericHID controller) {
        m_subsystem = subsystem;
        m_buttonsController = controller; 
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        isFinished = false;
        Slowed = false; 
        System.out.println("intake innit");
    }

    @Override
    public void execute() {
        System.out.println("intake execute");
        if (m_subsystem.FrontSeesNote() == false){
            Slowed = true;
        }
        if (m_subsystem.BackSeesNote() == true){
            m_buttonsController.setRumble(RumbleType.kBothRumble, 1);
            isFinished = true;
            m_subsystem.rumbling = true;
            m_subsystem.timer.start();
        }
        else if (Slowed){
            m_subsystem.SetMoveNoteSpeed();
        }
        else{
            m_subsystem.SetPickUpSpeed();
        }
        /* 
        if (m_subsystem.FrontSeesNote() == false && (m_subsystem.BackSeesNote() == false)) {
            m_subsystem.SetPickUpSpeed();
        }
        else if((m_subsystem.FrontSeesNote() == false) && (m_subsystem.BackSeesNote() == true)){
            isFinished = true;
        }
        else if((m_subsystem.FrontSeesNote() == true) && (m_subsystem.BackSeesNote() == false)){
            m_subsystem.SetMoveNoteSpeed();
        }
        else if(m_subsystem.BackSeesNote() == true){
            isFinished = true;
        }*/
    }
        

    @Override
    public void end(boolean interrupted) {
        m_subsystem.zeroIntake();
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
