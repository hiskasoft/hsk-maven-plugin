package com.github.yracnet.example;

/**
 * @author yracnet
 */
public class BuildRun {
   public static void main(String[] args) {     BuildRun.create()            .append1("1")
            .append1("1")
.append1("1")
.append1("1")
.append1("1")
.append1("1").append1("1").append1("1").append1("1")                                                                .append1("1")
.append1("1")
            .append2("ss");
   }
   public static BuildRun create() {      return new BuildRun();   }    public BuildRun append() {return this;}    public BuildRun append1(String a) {return this;}

   public BuildRun append2(String b) {return this;} }
