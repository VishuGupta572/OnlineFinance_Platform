package com.finance.test;

public class TestPasswordPolicy {
    public static void main(String[] args) {
        // Regex used in AuthServlet and register.jsp
        String regex = "(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,}";

        System.out.println("Testing Password Policy Regex: " + regex);

        test("weak", regex, false);
        test("noSpecial1", regex, false);
        test("NoNumber!", regex, false);
        test("nocapital1!", regex, false);
        test("Short1!", regex, false); // 7 chars
        test("ValidPa$$1", regex, true);
        test("Strong1!", regex, true);
        test("Another@1", regex, true);
    }

    private static void test(String password, String regex, boolean expected) {
        boolean valid = password.matches(regex);
        String result = (valid == expected) ? "PASS" : "FAIL";
        System.out.println(String.format("[%s] Input: '%s' -> Valid: %s", result, password, valid));
    }
}
