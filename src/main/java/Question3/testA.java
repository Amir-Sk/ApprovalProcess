package Question3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class testA {

    private List<Long> numbers;
    private List<String> strings;
    private A a, b, c;

    @BeforeAll
    public void setUp(){
        numbers = Arrays.asList(1L,2L);
        strings = Arrays.asList("a", "ab", "a", "c");
        Date date = new Date();
        a = new A(date, "randomName", numbers, strings);
        b = new A(date, "randomName", numbers, strings);
        c = new A(date, "randomName", numbers, strings);
    }

    @Test
    public void testGivenTwoEqualAObjectsTestComparator(){
        assert(a.equals(b));
    }

    @Test
    public void testGivenTwoObjectsTestIsBefore(){
        A c = new A(new Date(), "randomName", numbers, strings);
        assertFalse(a.isBefore(c.getDate()));
    }

    @Test
    public void givenNumbersListTestContainsNumber(){
        assertTrue(a.containsNumber(1L));
    }

    @Test
    public void givenStringsListTestRemoveString(){
        c.removeString("ab");
        assertEquals(Arrays.asList("a", "a", "c"), c.getStrings() );
    }

}
