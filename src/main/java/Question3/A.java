package Question3;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class A {

    private final Date date;
    private final String name;
    private final List<Long> numbers;
    private List<String> strings;

    public A(Date time, String name, List<Long> numbers, List<String>
        strings) {
        date = time;
        this.name = name;
        this.numbers = numbers;
        this.strings = strings;
    }

    /*
        Question3.A comparator should examine all fields (and their characteristics - e.g: order in lists),
        to determine whether the objects are equal.
     */
    public boolean equals(Object obj) {
        if (obj instanceof A) {
            A a = (A) obj;
            return name.equals(a.name)
                && numbers.equals(a.numbers)
                && strings.equals(a.strings)
                && (date.compareTo(a.date) == 0);
        }
        else {
            return false;
        }
    }

    /*
        String Builder over string concat:
        In Java, arrays are fixed length. The size is defined when
        you create the array.

        On each concatenation, a new copy of the string is created, and the two strings are copied over, character
        by character. The first iteration requires us to copy x characters. The second iteration requires copying 2x
        characters. T he third iteration requires 3x, and so on. The total time therefore is O( x + 2x + . . . + nx).
        which reduces to O(xn^2).

        StringBuilder simply creates a resizable array of all
        the strings, copying them back to a string only when necessary.
    */
    public String toString() {
        String spacedComma = ", ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder builder = new StringBuilder(simpleDateFormat.format(date));
        builder
            .append('\n')
            .append(name)
            .append(spacedComma)
            .append('\n');
        builder
            .append("Numbers:")
            .append(spacedComma)
            .append('\n');
        numbers.stream()
            .forEach(num -> builder.append(num).append(spacedComma));
        builder
            .append("\nStrings:")
            .append(spacedComma)
            .append('\n');
        strings.stream()
            .forEach(string -> builder.append(string).append(spacedComma));
        return builder.toString();
    }

    /*
        First Issue: list can hold duplicates, therefor removeString can
        cost O(n^2) (list of n items all identical)
        Depending on the DataStructure we decided to use,
        removal of an item may have different behaviors than expected.
        e.g. ArrayList.Iterator removes the element from collection and
        then shifts the following data to the left.
        LinkedList.Iterator on the other hand adjusts the pointer to the next element in the list.
        Hence, LinkedList.Iterator's performance is considered more efficient than ArrayList.Iterator
        when it comes to removing an item from the list.

        2nd issue was the the behavior of list.remove(index). Due to the fact
        that the list shifts left after removal, list("a", "a, "b") will
        get the for loop to increment, although the 2nd "a" would be shifted left
        to an index the loop already went through.
        And yet, even if we fix the incrementation issue, we encounter performance issue (as mentioned above)

        next option:
        ForEach function uses Iterator to traverse through the elements.
        However, when we find an element and modify the List,
        inconsistency will occur and Iterator would get into an inconsistent state.
        Hence ConcurrentModificationException will be thrown.

     */
    synchronized public void removeString(String str) {
        strings = strings.stream().filter(string -> !Objects.equals(string, str)).collect(Collectors.toList());
    }


    /*
        List DS will lead to O(n) time for the contains function,
        we can reduce it's time complexity into O(1) by using a Hash Table,
        but then we will loose the option of duplications.
     */
    public boolean containsNumber(long number) {
        return numbers.contains(number);
    }

    /*
        Returned true permanently, now relates to specific date
     */
    public boolean isBefore(Date date) {
        return date.before(date);
    }

    public Date getDate() {
        return date;
    }

    public List<Long> getNumbers() {
        return numbers;
    }

    public List<String> getStrings() {
        return strings;
    }
}
