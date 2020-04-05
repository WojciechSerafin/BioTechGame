package fxglgames.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import fxglgames.BioTechApp;
import fxglgames.EntityType;
import fxglgames.utils.Intersection;
import fxglgames.utils.Point;
import fxglgames.utils.Segment;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.almasb.fxgl.dsl.FXGL.*;

public class ShadowAndLightComponent extends Component {
  
  private static final String SHADOW_COLOR = "#0d0d0dee";
  private static final BlendMode SHADOW_BLEND = BlendMode.MULTIPLY;
  private static final double SHADOW_OPACITY = 1.0D;
  private static final String CLOSE_LIGHT_COLOR = "#FFFFFF55";
  private static final String FAR_LIGHT_COLOR = "#33333300";
  private static final BlendMode LIGHT_BLEND = BlendMode.OVERLAY;
  private static final double LIGHT_OPACITY = 1.0D;
  private static final Long LIGHT_RANGE = 600L;
  
  
  
  Line l = new Line(0,0, 0, 0);
  PlayerComponent playerComponent;
  
  AnchorPane light = new AnchorPane();
  AnchorPane shadow = new AnchorPane();
  
  List<Shape> rays = new ArrayList<>();
  private Set<Point> points = new HashSet<>();
  private Set<Segment> segments = new HashSet<>();
  
  @Override
  public void onAdded() {
    super.onAdded();
    
    shadow.getChildren().add( new Rectangle( 0 + entity.getWidth()/2- playerComponent.getPlayerViewRadius()/2.0D,
                                             0 + entity.getHeight()/2 - playerComponent.getPlayerViewRadius()/2.0D,
                                             playerComponent.getPlayerViewRadius(),
                                             playerComponent.getPlayerViewRadius() ) {{
      setFill( Color.valueOf( SHADOW_COLOR ) );
      setOpacity(SHADOW_OPACITY);
      setBlendMode( SHADOW_BLEND );
    }} );
    entity.getViewComponent().addChild(shadow);
    entity.getViewComponent().addChild(light);
  }
  
  @Override
  public void onUpdate(double tpf) {
    super.onUpdate(tpf);
    l.setEndX((getInput().getMouseXWorld() - entity.getX()) * 2 - entity.getWidth()/2);
    l.setEndY((getInput().getMouseYWorld() - entity.getY()) * 2 - entity.getHeight()/2);
  
  
    Stream<Entity> scianyWOkolicy = getGameWorld().getEntitiesByType(EntityType.WALL, EntityType.FAKE_WALL)
      .stream()
      .filter(w -> w.isWithin(new Rectangle2D(entity.getX() + entity.getWidth() / 2 - playerComponent.getPlayerViewRadius() / 2.0D,
        entity.getY() + entity.getHeight() / 2 - playerComponent.getPlayerViewRadius() / 2.0D,
        playerComponent.getPlayerViewRadius(),
        playerComponent.getPlayerViewRadius())));
    if (BioTechApp.DEBUG) {
      segments.forEach(segment -> entity.getViewComponent().removeChild(segment.getAsLine()));
    }
    segments.clear();
    points.clear();
    scianyWOkolicy.forEach( (w ) -> {
      //góra poziomo
      segments.add(new Segment(new Point(w.getX() - entity.getX(),
                                         w.getY() - entity.getY()),
                               new Point(w.getX() - entity.getX() + w.getWidth(),
                                         w.getY() - entity.getY())));
      //prawy pion
      segments.add(new Segment(new Point(w.getX() - entity.getX() + w.getWidth(),
                                         w.getY() - entity.getY()),
                               new Point(w.getX() - entity.getX() + w.getWidth(),
                                         w.getY() - entity.getY() + w.getHeight())));
      //dół poziomo
      segments.add(new Segment(new Point(w.getX() - entity.getX() + w.getWidth(),
                                         w.getY() - entity.getY() + w.getHeight()),
                               new Point(w.getX() - entity.getX(),
                                         w.getY() - entity.getY() + w.getHeight())));
      //lewy pion
      segments.add(new Segment(new Point(w.getX() - entity.getX(),
                                         w.getY() - entity.getY() + w.getHeight()),
                               new Point(w.getX() - entity.getX(),
                                         w.getY() - entity.getY())));
      points.addAll(Arrays.asList(new Point(w.getX() - entity.getX(), w.getY() - entity.getY()),
                                  new Point(w.getX() - entity.getX() + w.getWidth(), w.getY() - entity.getY()),
                                  new Point(w.getX() - entity.getX(), w.getY() - entity.getY() + w.getHeight()),
                                  new Point(w.getX() - entity.getX() + w.getWidth(),w.getY() - entity.getY() + w.getHeight())));
    });
    //Dodanie rogów zasięgu widoku
    points.add(new Point(entity.getX() - playerComponent.getPlayerViewRadius()/2.0D,
                         entity.getY() - playerComponent.getPlayerViewRadius()/2.0D));
    points.add(new Point(entity.getX() + playerComponent.getPlayerViewRadius()/2.0D,
                         entity.getY() - playerComponent.getPlayerViewRadius()/2.0D));
    points.add(new Point(entity.getX() - playerComponent.getPlayerViewRadius()/2.0D,
                         entity.getY() + playerComponent.getPlayerViewRadius()/2.0D));
    points.add(new Point(entity.getX() + playerComponent.getPlayerViewRadius()/2.0D,
                         entity.getY() + playerComponent.getPlayerViewRadius()/2.0D));
    
    renderRays();
  }
  
  private Polygon renderRay(Point mp, Set<Point> points, Set<Segment> segmentSet ) {
    Polygon lightPoly = new Polygon();
    List<Point> beams = points.stream()
      .map( p -> Math.atan2( p.getY() - mp.getY(), p.getX() - mp.getX() ) )
      .flatMap( a -> Stream.of( a - 0.0001, a, a + 0.0001 ) )
      .sorted()
      .map( a -> {
        Segment s = new Segment( mp, new Point( mp.getX() + Math.cos( a ), mp.getY() + Math.sin( a ) ) );
        boolean seen = false;
        Intersection best = null;
        Comparator<Intersection> comparator = Comparator.comparingDouble(Intersection::getDistance);
        for (Segment ss : segmentSet) {
          Intersection intersect = Intersection.intersect(s, ss);
          if (!seen || comparator.compare(intersect, best) < 0) {
            seen = true;
            best = intersect;
          }
        }
        return (!seen ? Optional.<Intersection>empty() : Optional.of(best)).get();
        
      } )
      .filter( i -> i != Intersection.NONE )
      .map( Intersection::getPoint )
      .collect( Collectors.toList() );
    lightPoly.getPoints()
      .addAll( beams.stream()
        .flatMap( p -> Stream.of( p.getX(), p.getY() ) )
        .collect( Collectors.toList() ) );
    lightPoly.setFill( new RadialGradient( 0, 0/*.5*/, mp.getX(), mp.getY(),
      LIGHT_RANGE, false, CycleMethod.NO_CYCLE, new Stop( 0, Color.valueOf( CLOSE_LIGHT_COLOR ) ),
      new Stop( 1, Color.valueOf( FAR_LIGHT_COLOR ) ) ) );
    lightPoly.setBlendMode( LIGHT_BLEND );
    lightPoly.setOpacity(LIGHT_OPACITY);
    return lightPoly ;
  }
  
  private void renderRays() {
    light.getChildren().removeAll( rays );
    rays.clear();
    double mx =0 + entity.getWidth()/2;
    double my =0 + entity.getHeight()/2;

    Set<Point> combinedPoints = new HashSet<>( points ) ;
    Set<Segment> combinedSegments = new HashSet<>( segments ) ;
    for( int i = 0 ; i < 8 ; i++ ) {
      Polygon beam = renderRay( new Point( mx + Math.cos( ( i / 8.0 ) * ( Math.PI * 2.0 ) ) * 7.0,
        my + Math.sin( ( i / 8.0 ) * ( Math.PI * 2.0 ) ) * 7.0  ), combinedPoints, combinedSegments ) ;
      rays.add( beam ) ;
    }

    //rays.add( renderRay( new Point( mx + Math.cos( ( 0 / 8.0 ) * ( Math.PI * 2.0 ) ) * 7.0,
      //my + Math.sin( ( 0 / 8.0 ) * ( Math.PI * 2.0 ) ) * 7.0  ), combinedPoints, combinedSegments ) ) ;
    light.getChildren().addAll( rays );
  }
}
