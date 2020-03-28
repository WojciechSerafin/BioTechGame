package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
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

@Required(PlayerComponent.class)
public class ShadowAndLightComponent extends Component {
  Line l = new Line(0,0, 0, 0);
  PlayerComponent playerComponent;
  
  AnchorPane light = new AnchorPane();
  AnchorPane shadow = new AnchorPane();
  
  List<Shape> rays = new ArrayList<Shape>();
  private Stream<Entity> scianyWOkolicy;
  private Set<Point> points = new HashSet<Point>();
  private Set<Segment> segments = new HashSet<Segment>();
  
  @Override
  public void onAdded() {
    super.onAdded();
    
    l.setStroke(Color.RED);
    l.setFill(Color.RED);
    l.setStartX(0 + entity.getHeight()/2);
    l.setStartY(0 + entity.getWidth()/2);
    //entity.getViewComponent().addChild(l);
    
    shadow.getChildren().add( new Rectangle( 0 + entity.getWidth()/2- playerComponent.getPlayerViewRadius()/2,
                                             0 + entity.getHeight()/2 - playerComponent.getPlayerViewRadius()/2,
                                             playerComponent.getPlayerViewRadius(),
                                             playerComponent.getPlayerViewRadius() ) {{
      setFill( Color.valueOf( "#0d0d0dee" ) );
      setBlendMode( BlendMode.MULTIPLY );
    }} ) ;
    
    entity.getViewComponent().addChild(shadow);
    entity.getViewComponent().addChild(light);
  }
  
  @Override
  public void onUpdate(double tpf) {
    super.onUpdate(tpf);
    l.setEndX((getInput().getMouseXWorld() - entity.getX()) * 2 - entity.getWidth()/2);
    l.setEndY((getInput().getMouseYWorld() - entity.getY()) * 2 - entity.getHeight()/2);
  
  
    scianyWOkolicy = getGameWorld().getEntitiesByType(EntityType.WALL, EntityType.FAKE_WALL)
                                                .stream()
                                                .filter(w -> w.isWithin(new Rectangle2D(entity.getX() + entity.getWidth()/2 - playerComponent.getPlayerViewRadius()/2,
                                                  entity.getY() + entity.getHeight()/2 - playerComponent.getPlayerViewRadius()/2,
                                                  playerComponent.getPlayerViewRadius(),
                                                  playerComponent.getPlayerViewRadius())));
    if (BioTechApp.DEBUG) {
      segments.forEach(segment -> entity.getViewComponent().removeChild(segment.getAsLine()));
    }
    segments.clear();
    points.clear();
    /*points =*/ scianyWOkolicy.forEach( ( w ) -> {
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
    points.add(new Point(entity.getX() - playerComponent.getPlayerViewRadius()/2,
                         entity.getY() - playerComponent.getPlayerViewRadius()/2));
    points.add(new Point(entity.getX() + playerComponent.getPlayerViewRadius()/2,
                         entity.getY() - playerComponent.getPlayerViewRadius()/2));
    points.add(new Point(entity.getX() - playerComponent.getPlayerViewRadius()/2,
                         entity.getY() + playerComponent.getPlayerViewRadius()/2));
    points.add(new Point(entity.getX() + playerComponent.getPlayerViewRadius()/2,
                         entity.getY() + playerComponent.getPlayerViewRadius()/2));
    //usunięcie poprzednich raysów
    if (BioTechApp.DEBUG) {
      //rays.forEach(r -> entity.getViewComponent().removeChild(r));
    }
    //rays.clear();
    //generowanie nowych raysów
    //points.forEach(p -> rays.add(new Line(0 + entity.getWidth()/2, 0 + entity.getHeight()/2,
    //  p.getX() - entity.getX(),
    // p.getY() - entity.getY())));
    //dodawanie wygenerowanych raysów
    if (BioTechApp.DEBUG) {
      //rays.forEach(r -> entity.getViewComponent().addChild(r));
      //segments.forEach(segment -> entity.getViewComponent().addChild(segment.getAsLine()));
    }
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
        Intersection i = segmentSet.stream()
          .map( ss -> Intersection.intersect( s, ss ) )
          .min( ( o1, o2 ) -> Double.compare( o1.getDistance(), o2.getDistance() ) ).get();
        return i;
      } )
      .filter( i -> i != Intersection.NONE )
      .map( Intersection::getPoint )
      .collect( Collectors.toList() );
    lightPoly.getPoints()
      .addAll( beams.stream()
        .flatMap( p -> Stream.of( p.getX(), p.getY() ) )
        .collect( Collectors.toList() ) );
    lightPoly.setFill( new RadialGradient( 0, 0/*.5*/, mp.getX(), mp.getY(),
      800, false, CycleMethod.NO_CYCLE, new Stop( 0, Color.valueOf( "#FFFFFF55" ) ),
      new Stop( 1, Color.valueOf( "#33333300" ) ) ) );
    lightPoly.setBlendMode( BlendMode.OVERLAY );
    lightPoly.setOpacity(1);
    return lightPoly ;
  }
  
  private void renderRays() {
    light.getChildren().removeAll( rays );
    rays.clear();
    double mx =0 + entity.getWidth()/2;
    double my =0 + entity.getHeight()/2;
    
    for( int i = 0 ; i < 8 ; i++ ) {
      Polygon beam = renderRay( new Point( mx + Math.cos( ( i / 8.0 ) * ( Math.PI * 2.0 ) ) * 7.0,
        my + Math.sin( ( i / 8.0 ) * ( Math.PI * 2.0 ) ) * 7.0  ), points, segments ) ;
      rays.add( beam ) ;
    }
    
    //chest.setClip( rays.stream().reduce( new Rectangle( 0, 0, 0, 0 ), (r,n) -> Shape.union( r, n ) ) );
    
    rays.clear();
    
    Set<Point> combinedPoints = new HashSet<>( points ) ;
    //combinedPoints.addAll( spritePoints ) ;
    Set<Segment> combinedSegments = new HashSet<>( segments ) ;
    //combinedSegments.addAll( spriteSegments ) ;
    for( int i = 0 ; i < 8 ; i++ ) {
      Polygon beam = renderRay( new Point( mx + Math.cos( ( i / 8.0 ) * ( Math.PI * 2.0 ) ) * 7.0,
        my + Math.sin( ( i / 8.0 ) * ( Math.PI * 2.0 ) ) * 7.0  ), combinedPoints, combinedSegments ) ;
      rays.add( beam ) ;
    }
    
    light.getChildren().addAll( rays );
  }
}
