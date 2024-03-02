package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj2.command.Command;


public class Intake extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private boolean isFinished; 

    public Intake(Shooter subsystem) {
        m_subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        isFinished = false;
    }

    @Override
    public void execute() {
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
        }

        
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
