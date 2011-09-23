package ch.mbae.jaxjs.model;

/**
 * 
 * @author marcbaechinger
 */
public class PathVariable {
    private final String name;
    private final String regExpr;

    PathVariable(String pathExpression) {
        System.out.println("create path variable: " + pathExpression);
        if (pathExpression.contains(":")) {
            String[] tokens = pathExpression.split(":");
            this.name = tokens[0];
            this.regExpr = tokens[1].trim();
        } else {
            this.name = pathExpression;
            this.regExpr = null;
        }
        System.out.println("name: " + name);
        System.out.println("regExpr: " + regExpr);
    }

    public String getName() {
        return name;
    }

    public String getRegExpr() {
        return regExpr;
    }
}
