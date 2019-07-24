package com.automationhero.code;

import java.io.*;
import java.util.*;

public class FileOperations {

  public List<File> splitToTempFiles(String inputFile) {
    List<File> outputs = new ArrayList<>();
    long maxChunkSize = Math.toIntExact(estimateAvailableMemory() / 4);
    List<Integer> lines = new ArrayList<>();
    try {

      Scanner input = new Scanner(new File(inputFile));
      String line;
      int currSize = 0;
      while (input.hasNextLine()) {
        line = input.nextLine();
        lines.add(Integer.parseInt(line));
        currSize += line.length() + 1;
        if (currSize >= maxChunkSize) {
          currSize = 0;
          Collections.sort(lines);
          File file = new File("tempFile" + System.currentTimeMillis() + ".txt");
          outputs.add(file);
          writeOut(lines, file);
          lines.clear();
        }
      }
      Collections.sort(lines);
      File file = new File("tempFile" + System.currentTimeMillis() + ".txt");
      outputs.add(file);
      writeOut(lines, file);
      lines.clear();
      System.gc();
      input.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return outputs;
  }

  private long estimateAvailableMemory() {
    System.gc();
    Runtime runtime = Runtime.getRuntime();
    long allocatedMemory = runtime.totalMemory() - runtime.freeMemory();
    long presFreeMemory = runtime.maxMemory() - allocatedMemory;
    return presFreeMemory;
  }

  void writeOut(List<Integer> lines, File file) {

    PrintWriter writer;
    try {

      writer = new PrintWriter(file, "UTF-8");
      for (Integer value : lines) {
        writer.println(value + "");
      }
      writer.close();
      System.gc();
    } catch (Exception e) {
      System.out.println("Exception occurred while writing to temporary files ");
    }
  }

  public String createSortedFile(List<File> tempFiles) {

    Map<IntegerWrapper, BufferedReader> map = new HashMap<>();
    List<BufferedReader> readers = new ArrayList<>();
    PrintWriter writer = null;
    ComparatorDelegate delegate = new ComparatorDelegate();
    File outputFile = new File("SortedOutput" + ".txt");
    try {

      writer = new PrintWriter(outputFile, "UTF-8");
      for (int i = 0; i < tempFiles.size(); i++) {
        BufferedReader reader = new BufferedReader(new FileReader(tempFiles.get(i)));
        readers.add(reader);
        Integer line = Integer.parseInt(reader.readLine());
        if (line != null) {
          map.put(new IntegerWrapper(line), readers.get(i));
        }
      }
      List<IntegerWrapper> sorted = new LinkedList<>(map.keySet());
      while (map.size() > 0) {
        Collections.sort(sorted, delegate);
        IntegerWrapper line = sorted.remove(0);
        writer.println(line.val + "");
        BufferedReader reader = map.remove(line);
        String nextLine = reader.readLine();
        if (nextLine != null) {
          IntegerWrapper value = new IntegerWrapper(Integer.parseInt(nextLine));
          map.put(value, reader);
          sorted.add(value);
        }
      }

    } catch (IOException ex) {
      System.out.println("IOException occurred ");
    } finally {
      for (int i = 0; i < readers.size(); i++) {
        try {
          readers.get(i).close();
        } catch (Exception e) {
          System.out.println("Exception occurred while closing the buffered reader");
        }
      }
      for (int i = 0; i < tempFiles.size(); i++) {
        tempFiles.get(i).delete();
      }
      try {
        writer.close();
      } catch (Exception e) {
        System.out.println("Exception occurred while closing the print writer");
      }
    }

    return outputFile.getName();
  }

  static class IntegerWrapper implements Comparable<IntegerWrapper> {
    private final Integer val;

    public IntegerWrapper(Integer line) {
      this.val = line;
    }

    @Override
    public int compareTo(IntegerWrapper o) {
      return val.compareTo(o.val);
    }
  }

  static class ComparatorDelegate implements Comparator<IntegerWrapper> {

    @Override
    public int compare(IntegerWrapper o1, IntegerWrapper o2) {
      return o1.val.compareTo(o2.val);
    }
  }

  public boolean checkFile(String inputFile) {
    if (inputFile == null || inputFile.equals("")) return false;

    File f = new File(inputFile);
    return f.exists();
  }
}
