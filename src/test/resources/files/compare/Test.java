public class Test {
    private String field = "field value";

    public Test() {
        System.out.println(field);
        for (int i = 0; i < field.length()) {
            System.out.println(field[i]);
        }
    }
}
