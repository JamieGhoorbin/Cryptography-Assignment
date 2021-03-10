package com.jamieghoorbin;

/*
 A simplified Pair class.
 Credit:https://www.techiedelight.com/implement-pair-class-java/
 */

class Pair<U, V>
{
    private U left;
    private V right;

    // Constructs a new Pair with specified values
    public Pair(U left, V right)
    {
        this.left = left;
        this.right = right;
    }

    public U getLeft() {
        return this.left;
    }

    public V getRight() {
        return this.right;
    }

    public void setLeft(U left) {
        this.left = left;
    }

    public void setRight(V right) {
        this.right = right;
    }

    public String toString() {
        return String.valueOf(left) + right;
    }
}
