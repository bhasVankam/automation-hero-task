package com.automationhero.code;

import java.io.*;
import java.util.*;

public class SortFile {

  static FileOperations fileOp = new FileOperations();

  public static void main(String[] args) {
    System.out.println("Enter the Input File to Sort");
    Scanner scan = new Scanner(System.in);
    String inputFileName = scan.nextLine();
    File inputFile = new File(inputFileName);
    if (!inputFile.exists()) {
      System.out.println("Invalid file");
      System.exit(1);
    }
    createSortedFile(inputFileName);
    scan.close();
  }

  public static void createSortedFile(String inputFileName) {
    if (!fileOp.checkFile(inputFileName)) {
      System.out.println("Invalid input file");
      return;
    }

    List<File> tempFiles = fileOp.splitToTempFiles(inputFileName);
    if (tempFiles.size() > 0) {
      System.out.println("InputFile is Successfully Changed to tempFiles");
      String sortedOutputFileName = fileOp.createSortedFile(tempFiles);
      System.out.println("Sorted File for the given input file is " + sortedOutputFileName);
    }
  }
}
