package com.truthTable;

public class Eq {
    char W;
    char X;
    char Y;
    char Z;
    char F;

    @Override
    public String toString() {
        return Character.toString(F);
    }

    public Eq(char W, char X, char Y, char Z){
        this.W = W;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }
    public boolean getBooleanW() {
        if(W=='1')
            return true;
        else
            return false;
    }
    public boolean getBooleanX() {
        if(X=='1')
            return true;
        else
            return false;
    }
    public boolean getBooleanY() {
        if(Y=='1')
            return true;
        else
            return false;
    }
    public boolean getBooleanZ() {
        if(Z=='1')
            return true;
        else
            return false;
    }
    public void setBooleanF(boolean F) {
        if(F)
            this.F='1';
        else
            this.F='0';
    }
    public char getW() {
        return W;
    }
    public char getX() {
        return X;
    }
    public char getY() {
        return Y;
    }
    public char getZ() {
        return Z;
    }
    public char getF() {
        return F;
    }
    public void setW(char w) {
        W = w;
    }
    public void setX(char x) {
        X = x;
    }
    public void setY(char y) {
        Y = y;
    }
    public void setZ(char z) {
        Z = z;
    }
    public void setF(char f) {
        F = f;
    }

}
