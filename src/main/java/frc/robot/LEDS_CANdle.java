package frc.robot;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.LarsonAnimation;
import com.ctre.phoenix.led.SingleFadeAnimation;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.TwinkleAnimation;

public class LEDS_CANdle {
  private static CANdle candle;
  private static LEDS_CANdle ledinstance;

  public static LEDS_CANdle getInstance(){
      if(ledinstance == null){
        ledinstance = new LEDS_CANdle();
        return ledinstance;
      }
    else {
        return ledinstance;
      }
    }

  public LEDS_CANdle() {
    constructTheCandle();
  }

  private static void constructTheCandle(){
    try{
        if(candle == null){
        candle = new CANdle(0, "frc5126");
        candle.configLEDType(LEDStripType.GRB);
        }

    }
    catch(Exception e1){
        System.out.println("Unable to connect to Candle");
    }
  }

  public void setRGBColor(int r, int g, int b){
    try{
        if(candle != null){
          candle.setLEDs(r, g, b);
        }

    }
    catch(Exception e1){
        System.out.println("Unable to set RGB for Color");
        e1.printStackTrace();
    }
  }
  
  public void Twinkle(int r, int g, int b){
    candle.animate(new TwinkleAnimation(r, g, b));
  }

  public void Larson(int r, int g, int b){
    candle.animate(new LarsonAnimation(r, g, b, 255, .3, 190, LarsonAnimation.BounceMode.Front, 7,0));
  }

    public void Larson(int r, int g, int b, double speed){
    candle.animate(new LarsonAnimation(r, g, b, 255, speed, 190, LarsonAnimation.BounceMode.Front, 7,0));
  }

  public void SingleFade(int r, int g, int b){
    candle.animate(new SingleFadeAnimation(r, g, b, 255, .3, 68, 0));
  }

  public void clearAnimation(){
    candle.clearAnimation(0);
  }
}
