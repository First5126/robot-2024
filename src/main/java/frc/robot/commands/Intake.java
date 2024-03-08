package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.event.BooleanEvent;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class Intake extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private boolean isFinished;
    private boolean Slowed = false;
    private final GenericHID m_buttonsController;
    private final GenericHID m_driverController;

    public Intake(Shooter subsystem, GenericHID controller) {
        m_subsystem = subsystem;
        m_buttonsController = controller; 
        m_driverController = new GenericHID(1);
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        isFinished = false;
        Slowed = false; 
    }

    @Override
    public void execute() {
        if (m_subsystem.FrontSeesNote()){
            Slowed = true;
        }
        if (m_subsystem.BackSeesNote()){
            m_buttonsController.setRumble(RumbleType.kBothRumble, 1);
            m_driverController.setRumble(RumbleType.kBothRumble, 1);
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
