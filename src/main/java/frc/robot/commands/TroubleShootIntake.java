package frc.robot.commands;

import frc.robot.LEDS_CANdle;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.Robot;


public class TroubleShootIntake extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private boolean isFinished;
    private boolean Slowed = false;
    private final XboxController m_buttonsController;
    private final XboxController m_driverController;
    private LEDS_CANdle m_CANdle;


    public TroubleShootIntake(Shooter subsystem, XboxController controller, XboxController driver) {
        m_subsystem = subsystem;
        m_buttonsController = controller;
        m_driverController = driver;
        m_CANdle = new LEDS_CANdle();
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        isFinished = false;
        Slowed = false; 
    }

    @Override
    public void execute() {
        if (m_subsystem.BackSeesNote()){
            m_buttonsController.setRumble(RumbleType.kBothRumble, 1);
            m_driverController.setRumble(RumbleType.kBothRumble, 1);
            m_CANdle.setRGBColor(245, 99, 2);

            isFinished = true;
            m_subsystem.rumbling = true;
            m_subsystem.timer.start();
        }
        else{
            m_subsystem.SetPickUpSpeed();
            m_CANdle.Larson(245, 99, 2);
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
