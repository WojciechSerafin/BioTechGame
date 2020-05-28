package fxglgames.utils;

public class Utils {
  public static Double clamp (Double val, Double min, Double max) {
    if (val > max) val = max;
    else if (val < min) val = min;
    
    return val;
  }
}
