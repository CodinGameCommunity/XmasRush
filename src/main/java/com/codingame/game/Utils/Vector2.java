package com.codingame.game.Utils;

public class Vector2 {
    public int x, y;
    public static final Vector2 ZERO = new Vector2(0, 0);

    public Vector2() {
        this.x = this.y = 0;
    }

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2 add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2 sub(Vector2 other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2 mult(double val) {
        this.x *= val;
        this.y *= val;
        return this;
    }

    public Vector2 div(double val) {
        this.x /= val;
        this.y /= val;
        return this;
    }

    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a).add(b);
    }

    public static Vector2 sub(Vector2 a, Vector2 b) {
        return new Vector2(a).sub(b);
    }

    public static Vector2 mult(Vector2 a, int b) {
        return new Vector2(a).mult(b);
    }

    public static Vector2 div(Vector2 a, int b) {
        return new Vector2(a).div(b);
    }

    @Override
    public String toString() {
        return "[x=" + this.x + ", y=" + this.y + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Vector2 other = (Vector2) obj;
        return this.x == other.x && this.y == other.y;
    }

    public int manhattanDist(Vector2 other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }
}