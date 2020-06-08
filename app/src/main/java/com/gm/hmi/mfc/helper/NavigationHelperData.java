package com.gm.hmi.mfc.helper;

/**
 * This helps the current button to understand what its navigation directions and rotate left or right.
 */
public class NavigationHelperData {
    private String left;
    private String right;
    private String up;
    private String down;
    private String rotateLeft;
    private String rotateRight;

    public NavigationHelperData(String l, String r, String t, String b, String rl, String rr) {
        left = l;
        right = r;
        up = t;
        down = b;
        rotateLeft = rl;
        rotateRight = rr;
    }

    public String getLeft() { return left; }

    public String getRight() {
        return right;
    }

    public String getUp() {
        return up;
    }

    public String getDown() {
        return down;
    }

    public String getRotateLeft() {
        return rotateLeft;
    }

    public String getRotateRight() {
        return rotateRight;
    }

}
