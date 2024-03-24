import static org.junit.Assert.assertFalse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class IntroToDALTest {

    private static IntroToDAL dal;


    @BeforeClass
    public static void Setup()
    {
       dal = new IntroToDAL();
    }

    @AfterClass
    public static void Clean()
    {
        System.out.println("Is there anything to be cleaned up? Maybe we should remove test data from database?");
    }

     @Test
    public void testTryExecutingAQuery() 
    {
        assertFalse("Test expects a failure, but no exception/crash", dal.TryExecutingAQuery(null, null, null, null));
    }

    @Test
    public void testTryExecutingAStoredProcedure() 
    {
        assertFalse("Test expects a failure, but no exception/crash", dal.TryExecutingAStoredProcedure(null, null, null));
    }

    @Test
    public void testTryExecutingAStoredProcedureWithParamWithAllNulls() 
    {
        boolean result = dal.TryExecutingAStoredProcedureWithParam(null, null, null, null, null, 0, false, null);
        assertFalse("Test expects a failure, true was returned", result);
    }

    @Test
    public void testTryExecutingAStoredProcedureWithParamTwice() 
    {
        boolean result = dal.TryExecutingAStoredProcedureWithParam(null, null, null, null, null, 0, false, null);
        assertFalse("Test expects success, false was returned", result);
    }
}
