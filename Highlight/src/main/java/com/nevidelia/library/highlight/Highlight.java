package com.nevidelia.library.highlight;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Highlight {

    private final String C_STYLE_FUNCTION_DECLARATION = "([\\w])+([(])(.*?)([)])([{]|[\\s])";
    private final String C_STYLE_FUNCTION_CALL = "([\\w])+([(])(.*?)([)])[;]";
    private final String C_STYLE_STRING = "\\\"(.*?)\\\"";
    private final String C_STYLE_CHAR = "'(.*?)'";
    private final String C_STYLE_NUMBER = "[^\\w]([0-9]+([.][0-9]*)?|[.][0-9]+)";
    private final String C_STYLE_STATEMENTS = "\\bint\\b|\\bfloat\\b|\\blong\\b|\\bchar\\b|\\bdouble\\b|\\bif\\b|\\belse\\b|\\bswitch\\b|\\bcase\\b|\\bdefault\\b|\\bbreak\\b|\\bdo\\b|\\bwhile\\b|\\bfor\\b|\\bgoto\\b|\\breturn\\b|\\bshort\\b|\\bsigned\\b|\\bunsigned\\b|\\bstatic\\b|\\bcontinue\\b|\\bstruct\\b|\\bvoid\\b|\\bauto\\b|\\bconst\\b|\\benum\\b|\\bextern\\b|\\binline\\b|\\bregister\\b|\\brestrict\\b|\\b_Bool\\b|\\b_Complex\\b|\\b_Imaginary\\b|\\bNULL\\b|#define|#include|;";
    private final String C_STYLE_VARIABLE = "((?=_[a-z_0-9]|[a-z])[a-z_0-9]+(?=\\s*\\/=))|((?=_[a-z_0-9]|[a-z])[a-z_0-9]+(?=\\s*\\*=))|((?=_[a-z_0-9]|[a-z])[a-z_0-9]+(?=\\s*\\+=))|((?=_[a-z_0-9]|[a-z])[a-z_0-9]+(?=\\s*\\-=))|((?=_[a-z_0-9]|[a-z])[a-z_0-9]+(?=\\s*=))|([&][\\w]+)";
    private final String C_STYLE_COMMENT = "[/]{2}(.*)";
    private final String C_STYLE_COMMENT_LONG = "[/][*]([\\s\\S]*?)[*][/]";
    private final String JAVA_STYLE_STATEMENTS = "\\bint\\b|\\bfloat\\b|\\blong\\b|\\bchar\\b|\\bdouble\\b|\\bif\\b|\\belse\\b|\\bswitch\\b|\\bcase\\b|\\bdefault\\b|\\bbreak\\b|\\bdo\\b|\\bwhile\\b|\\bfor\\b|\\bgoto\\b|\\breturn\\b|\\bshort\\b|\\bstatic\\b|\\bcontinue\\b|\\bvoid\\b|\\bconst\\b|\\benum\\b|\\btry\\b|\\bcatch\\b|\\bfinally\\b|\\bboolean\\b|\\btrue\\b|\\bfalse\\b|\\bthrow\\b|\\bnew\\b|\\bpublic\\b|\\bprivate\\b|\\bsynchronised\\b|\\binterface\\b|\\bnull\\b|\\bassert\\b|\\bprotected\\b|\\bsuper\\b|\\bimport\\b|\\bpackage\\b|\\bimplements\\b|\\bextends\\b|\\binstanceof\\b|\\bclass\\b|\\bthis\\b|;";
    private final String ERROR_MESSAGE = "/* Syntax highlighting unavailable for #. */\n\n@";
    public static final String C = "c", JAVA = "java";

    private int variables, statements, numbers, strings, comments, functions, primary = -1;

    public Highlight() {
        this.variables = Color.MAGENTA;
        this.statements = Color.RED;
        this.numbers = Color.BLUE;
        this.strings = Color.GREEN;
        this.comments = Color.GRAY;
        this.functions = Color.YELLOW;
    }

    public void setColors(int variables, int statements, int numbers, int strings, int comments, int functions) {
        this.variables = variables;
        this.statements = statements;
        this.numbers = numbers;
        this.strings = strings;
        this.comments = comments;
        this.functions = functions;
    }

    public void setColors(Context context, int variables, int statements, int numbers, int strings, int comments, int functions) {
        this.variables = ContextCompat.getColor(context, variables);
        this.statements = ContextCompat.getColor(context, statements);
        this.numbers = ContextCompat.getColor(context, numbers);
        this.strings = ContextCompat.getColor(context, strings);
        this.comments = ContextCompat.getColor(context, comments);
        this.functions = ContextCompat.getColor(context, functions);
    }

    public void setColors(String variables, String statements, String numbers, String strings, String comments, String functions) {
        this.variables = Color.parseColor(variables);
        this.statements = Color.parseColor(statements);
        this.numbers = Color.parseColor(numbers);
        this.strings = Color.parseColor(strings);
        this.comments = Color.parseColor(comments);
        this.functions = Color.parseColor(functions);
    }

    public void setPrimaryColor(int primary) {
        this.primary = primary;
    }

    public void setPrimaryColor(Context context, int primary) {
        this.primary = ContextCompat.getColor(context, primary);
    }

    public void setPrimaryColor(String primary) {
        this.primary = Color.parseColor(primary);
    }

    public SpannableString c(String code) {
        String[] regexes = new String[]{C_STYLE_VARIABLE, C_STYLE_FUNCTION_DECLARATION, C_STYLE_FUNCTION_CALL, C_STYLE_STATEMENTS, C_STYLE_NUMBER, C_STYLE_STRING, C_STYLE_CHAR, C_STYLE_COMMENT, C_STYLE_COMMENT_LONG};
        return setHighlighter(regexes, code);
    }

    public SpannableString java(String code) {
        String[] regexes = new String[]{C_STYLE_VARIABLE, C_STYLE_FUNCTION_DECLARATION, C_STYLE_FUNCTION_CALL, JAVA_STYLE_STATEMENTS, C_STYLE_NUMBER, C_STYLE_STRING, C_STYLE_CHAR, C_STYLE_COMMENT, C_STYLE_COMMENT_LONG};
        return setHighlighter(regexes, code);
    }

    public SpannableString code(String language, String code) {
        String[] regexes;
        switch (language) {
            case C:
                regexes = new String[]{C_STYLE_VARIABLE, C_STYLE_FUNCTION_DECLARATION, C_STYLE_FUNCTION_CALL, C_STYLE_STATEMENTS, C_STYLE_NUMBER, C_STYLE_STRING, C_STYLE_CHAR, C_STYLE_COMMENT, C_STYLE_COMMENT_LONG};
                break;
            case JAVA:
                regexes = new String[]{C_STYLE_VARIABLE, C_STYLE_FUNCTION_DECLARATION, C_STYLE_FUNCTION_CALL, JAVA_STYLE_STATEMENTS, C_STYLE_NUMBER, C_STYLE_STRING, C_STYLE_CHAR, C_STYLE_COMMENT, C_STYLE_COMMENT_LONG};
                break;
            default:
                return new SpannableString(ERROR_MESSAGE.replace("#", language).replace("@", code));
        }
        return setHighlighter(regexes, code);
    }

    private SpannableString setHighlighter(String[] regexs, String text) {
        SpannableString spannableString = new SpannableString(text);
        if (this.primary != -1) spannableString.setSpan(new ForegroundColorSpan(this.primary),0, spannableString.length(),0);
        ArrayList<String> variables = new ArrayList<>();
        for (String regex : regexs) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                switch (regex) {
                    case C_STYLE_VARIABLE:
                        if (!variables.contains(matcher.group(0))) {
                            variables.add(Objects.requireNonNull(matcher.group(0)).replaceAll("&",""));
                            Pattern pattern2 = Pattern.compile("\\b" + Objects.requireNonNull(matcher.group(0)).replaceAll("&","") + "\\b");
                            Matcher matcher2 = pattern2.matcher(text);
                            while (matcher2.find()) spannableString.setSpan(new ForegroundColorSpan(this.variables), matcher2.start(), matcher2.end(),0);
                        }
                        break;
                    case C_STYLE_FUNCTION_DECLARATION:
                    case C_STYLE_FUNCTION_CALL:
                        spannableString.setSpan(new ForegroundColorSpan(this.functions), matcher.start(), matcher.end(1),0);
                        break;
                    case C_STYLE_STATEMENTS:
                    case JAVA_STYLE_STATEMENTS:
                        spannableString.setSpan(new ForegroundColorSpan(this.statements), matcher.start(), matcher.end(),0);
                        break;
                    case C_STYLE_NUMBER:
                        spannableString.setSpan(new ForegroundColorSpan(this.numbers), matcher.start(1), matcher.end(1),0);
                        break;
                    case C_STYLE_STRING:
                    case C_STYLE_CHAR:
                        spannableString.setSpan(new ForegroundColorSpan(this.strings), matcher.start(), matcher.end(),0);
                        break;
                    case C_STYLE_COMMENT:
                    case C_STYLE_COMMENT_LONG:
                        spannableString.setSpan(new ForegroundColorSpan(this.comments), matcher.start(), matcher.end(),0);
                        break;
                }
            }
        }
        return spannableString;
    }
}
