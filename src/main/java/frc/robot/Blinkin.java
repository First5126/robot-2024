// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class Blinkin {
  private Spark blinkinController;

  public Blinkin() {
    blinkinController = new Spark(1);
  }

  public void DoubleSinelon(){
    blinkinController.set(0.55);
  }

  public void PurpleHeartbeat(){
    blinkinController.set(0.07);
  }

  public void DoubleColorWave(){
    
  }
}
