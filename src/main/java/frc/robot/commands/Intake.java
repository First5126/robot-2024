package frc.robot.commands;

import frc.robot.LEDS_CANdle;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.Robot;


public class Intake extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private boolean isFinished;
    private boolean Slowed = false;
    private final XboxController m_buttonsController;
    private final XboxController m_driverController;
    private LEDS_CANdle m_CANdle;

    public Intake(Shooter subsystem, XboxController controller, XboxController driver) {
        m_subsystem = subsystem;
        m_buttonsController = controller;
        m_driverController = driver;
        m_CANdle = LEDS_CANdle.getInstance();
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        System.out.println("innit");
        isFinished = false;
        Slowed = false; 
    }

    @Override
    public void execute() {
        System.out.println("execute");
        if (m_subsystem.FrontSeesNote()){
            System.out.println("front note sees");
            Slowed = true;
        }
        if (m_subsystem.BackSeesNote()){
            System.out.println("back note sees");
            m_buttonsController.setRumble(RumbleType.kBothRumble, 1);
            m_driverController.setRumble(RumbleType.kBothRumble, 1);
            m_CANdle.clearAnimation();
            m_CANdle.setRGBColor(245, 99, 2);
            Slowed = false;
            isFinished = true;
            m_subsystem.rumbling = true;
            m_subsystem.timer.start();
        }
        else if (Slowed){
            System.out.println("slowed");
            m_subsystem.SetMoveNoteSpeed();
            m_CANdle.clearAnimation();
            m_CANdle.Twinkle(245,0,0);
        }
        else{
            Slowed = false;
            System.out.println("else");
            m_subsystem.SetPickUpSpeed();
            m_CANdle.clearAnimation();
            m_CANdle.Larson(245, 99, 2);
        }
        
    }
        

    @Override
    public void end(boolean interrupted) {
        System.out.println("intake end");
        Slowed = false;
        m_subsystem.zeroIntake();
        //m_CANdle.clearAnimation();
        //m_CANdle.Twinkle(132, 2, 245);
        //m_CANdle.setRGBColor(245, 99, 2);
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
