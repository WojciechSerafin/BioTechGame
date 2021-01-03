package komiks;

import fxglgames.BioTechApp;

public class BioTechAppRunnable implements Runnable{
  
  private String[] args;
  
  public BioTechAppRunnable(String[] args) {
    this.args = args;
  }
  
  public void run() {
    BioTechApp.main(args);
  }
}