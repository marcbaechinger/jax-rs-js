package ch.mbae.jaxjs.model;

import java.util.List;

/**
 * 
 * @author marcbaechinger
 */
public class PathVariableParser {

    public static final int INSIDE_EXPR = 1;
    public static final int INSIDE_REGEXPR = 3;
    public static final int OUTSIDE_EXPR = 0;

    public String parse(String pathExpression, List<PathVariable> vars) {
        StringBuilder cleanPath = new StringBuilder();
        StringBuilder expr = new StringBuilder();
        short parseState = OUTSIDE_EXPR;
        for (int i = 0; i < pathExpression.length(); i++) {
            final char currChar = pathExpression.charAt(i);
            if (parseState == OUTSIDE_EXPR) {
                cleanPath.append(currChar);
                if (currChar == '{') {
                    parseState = INSIDE_EXPR;
                }
            } else if (parseState == INSIDE_EXPR) {
                if (currChar == ' ') {
                    continue;
                } else if (currChar == '}') {
                    cleanPath.append(currChar);
                    vars.add(new PathVariable(expr.toString()));
                    // ready for next expression
                    expr = new StringBuilder();
                    parseState = OUTSIDE_EXPR;
                } else if (currChar == ':') {
                    parseState = INSIDE_REGEXPR;
                    expr.append(currChar);
                } else {
                    cleanPath.append(currChar);
                    expr.append(currChar);
                }
            } else if (parseState == INSIDE_REGEXPR) {
                if (currChar == '}') {
                    cleanPath.append(currChar);
                    vars.add(new PathVariable(expr.toString()));
                    expr = new StringBuilder();
                    parseState = OUTSIDE_EXPR;
                } else {
                    expr.append(currChar);
                }
            }
        }

        return cleanPath.toString();
    }
}
