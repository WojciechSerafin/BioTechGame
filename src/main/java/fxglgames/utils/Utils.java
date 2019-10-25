package fxglgames.utils;

public class Utils {
  public static float clamp (float val, float min, float max) {
    if (val > max) val = max;
    else if (val < min) val = min;
    
    return val;
  }
}
