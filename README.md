# Gamezzz
Some game from seminars

#News
## Cross Unit Support API added
IUnit now extends Cross Unit Support API (CUSInteract) by default.
Targets:
* Simplify and unify units coordination
* Simplify interactions

CUSInteract:
```java
public interface CUSInteract {
    Coordinates getXY();
    void setXY(Coordinates position);
}
```
Also unified coordinates system added. New ```java Coordinates ``` class with sharing unit's position added.
Example:
```java
import CrossUnitSupport.CUSInteract;

public class CustomUnit extends CUSInteract implements IUnit {
    @Override
    public void draw(Graphics2D canvas) {

    }

    @Override
    public void step(IWorld world, float dt) {

    }

    //CUSInteract delegated methods
    
    @Override
    public Coordinates getXY() {
        return null;
    }
    
    @Override
    public void setXY(Coordinates position) {
        
    }
    
}
```
##
