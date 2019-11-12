package com.CK;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        String s = "(a)())()";
//        String s = ")(";
//        String s = "( ) ) ) ( ( ( )";
//        String s = "()())()";
        String s = "((i)";
        Solution solution = new Solution();
        System.out.println(solution.removeInvalidParentheses(s).toString());

    }
}

class Solution2 {
    final char[][] patterns = {{'(', ')'}, {')', '('}};

    public List<String> removeInvalidParentheses(String s) {
        List<String> ret = new ArrayList<>();
        dfs(s, 0, 0, patterns[0], ret);
        return ret;
    }

    private void dfs(String s, int start, int lastRemove, char[] pattern, List<String> ret) {
        int count = 0, n = s.length();
        for (int i = start; i < n; i++) {
            if (s.charAt(i) == pattern[0]) count++;
            if (s.charAt(i) == pattern[1]) count--;
            if (count < 0) {
                for (int j = lastRemove; j <= i; j++) {
                    if (s.charAt(j) == pattern[1] && (j == lastRemove || s.charAt(j) != s.charAt(j - 1)))
                        dfs(s.substring(0, j) + s.substring(j + 1), i, j, pattern, ret);
                }
                return;
            }
        }
        s = new StringBuilder(s).reverse().toString();
        if (pattern[0] == patterns[0][0]) dfs(s, 0, 0, patterns[1], ret);
        else ret.add(s);
    }
}


class Solution {
    public List<String> removeInvalidParentheses(String s) {
        int n = s.length();
        List<String> res = new ArrayList<>();
        if (n == 0){
            res.add("");
            return res;
        }

        int leftExtra = extra(s, true), rightExtra = extra(s, false);
        StringBuilder sb = new StringBuilder();
        dfs(s, 0, sb, leftExtra, rightExtra, 0, res);
        return res;
    }

    private void dfs(String s, int st, StringBuilder sb, int leftExtra, int rightExtra, int counter, List<String> res) {
        if (st >= s.length()){
            if (leftExtra == 0 && rightExtra == 0) {
                String sbString = sb.toString();
                if (res.size() != 0 && res.contains(sbString))
                    return;
                else {
                    res.add(sbString);
                    return;
                }
            } else
                return;
        }

        char c = s.charAt(st);
        if (c == '('){
//            System.out.println("SB is: " + sb.toString());
            if (leftExtra > 0){
                dfs(s, st + 1, sb, leftExtra - 1, rightExtra, counter, res);
            }
            sb.append(c);
            dfs(s, st + 1, sb, leftExtra, rightExtra, counter + 1, res);
            sb.deleteCharAt(sb.length() - 1);
        } else if (c == ')'){
            if (rightExtra > 0){
                dfs(s, st + 1,sb,  leftExtra, rightExtra - 1, counter, res);
            }

            if (counter > 0) {
                sb.append(c);
                dfs(s, st + 1, sb, leftExtra, rightExtra, counter - 1, res);
                sb.deleteCharAt(sb.length() - 1);
            }
        } else {
            sb.append(c);
            dfs(s, st + 1, sb, leftExtra, rightExtra, counter, res);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    private int extra(String s, boolean leftToRight) {
        if (s.length() == 0)
            return 0;

        int counter = 0;

        if (leftToRight){
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(')
                    counter++;
                else if (c == ')')
                    counter = Math.max(0, counter - 1);
            }
            return counter;
        } else {
            for (int i = s.length() - 1; i >=0 ; i--) {
                char c = s.charAt(i);
                if (c == ')')
                    counter++;
                else if (c == '(')
                    counter = Math.max(0, counter - 1);
            }
            return counter;
        }
    }
}