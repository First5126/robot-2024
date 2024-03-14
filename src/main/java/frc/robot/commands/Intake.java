package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;


public class Intake extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private boolean isFinished;
    private boolean Slowed = false;
    private final CommandGenericHID m_buttonsController;
    private final CommandGenericHID m_driverController;


    public Intake(Shooter subsystem, CommandGenericHID controller, CommandGenericHID driver) {
        m_subsystem = subsystem;
        m_buttonsController = controller;
        m_driverController = driver;
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
            m_buttonsController.getHID().setRumble(RumbleType.kBothRumble, 1);
            m_driverController.getHID().setRumble(RumbleType.kBothRumble, 1);
            Slowed = true;
        }
        if (m_subsystem.BackSeesNote()){
            m_buttonsController.getHID().setRumble(RumbleType.kBothRumble, 1);
            m_driverController.getHID().setRumble(RumbleType.kBothRumble, 1);
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
