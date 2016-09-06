/**
 * javadoc is not a comment
 */
public class Test {
    private String field = "field value";

    public Test() {
        /*
        * multiline
        * comment
        */
        System.out.println(field);
        // single line comment
        for (int i = 0; i < field.length()) {
            System.out.println(field[i]);
        }
    }
}
