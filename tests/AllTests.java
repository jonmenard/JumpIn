package tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BoardTest.class, MoveableTest.class, TileTest.class, ViewTest.class, PastMovesTest.class, SerializerTest.class })
public class AllTests {

}
