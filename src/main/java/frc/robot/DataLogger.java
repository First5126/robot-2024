package frc.robot;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DataLogger {
    
    public DataLogger(){
        try{
            this.StartLogger();
            this.logJoystick();
            this.logPh();
        }
        catch(Exception e1){
         System.err.println("Unable to start the Datalogger ... Hey yo should check out the Space on the SD card");
        }

        this.startAsCameraServer();
    }

     private void StartLogger(){
        DataLogManager.start();
        DataLogManager.logNetworkTables(true);
     }
     
     private void logJoystick(){
        DriverStation.startDataLog(DataLogManager.getLog());
     }

    private void logPh(){
      PowerDistribution ph = new PowerDistribution(1, PowerDistribution.ModuleType.kRev); //I CHANGED THE CAN ID DANNNNNNNNNN
      SmartDashboard.putData("PH",ph);
     }

     private void startAsCameraServer(){
      try{
      CameraServer.startAutomaticCapture();
      }

      catch(Exception e1){
      System.out.println("Unable to connect to Camera");
      }
     }
}