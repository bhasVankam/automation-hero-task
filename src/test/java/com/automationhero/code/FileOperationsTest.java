package com.automationhero.code;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileOperationsTest {

  private static final String TEST_TEMP_FILE1_TXT = "temp-file1.txt";
  private static final String TEST_TEMP_FILE2_TXT = "temp-file2.txt";
  private static final String testFileExpected = "expectedSortFile.txt";
  private static final List<Integer> writeList =
      new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
  private static final String WRITE_EXPECTED_FILE = "writeExpected.txt";
  private static final String WRITE_ACTUAL_FILE = "writeOut.txt";
  FileOperations fileOperations = null;
  List<File> outputs = null;

  @Before
  public void init() {
    fileOperations = new FileOperations();
    outputs = new ArrayList<>();
  }

  @Test
  public void createSortedFileTest() {

    outputs.add(new File(TEST_TEMP_FILE1_TXT));
    outputs.add(new File(TEST_TEMP_FILE2_TXT));
    String outputFile = fileOperations.createSortedFile(outputs);
    try {
      assertReaders(testFileExpected, outputFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void assertReaders(String file, String actualfile) throws Exception {
    FileReader fileReaderExpected = new FileReader(file);
    BufferedReader expected = new BufferedReader(fileReaderExpected);
    FileReader fileReaderActual = new FileReader(actualfile);
    BufferedReader actual = new BufferedReader(fileReaderActual);
    String line;
    while ((line = expected.readLine()) != null) {
      assertEquals(line, actual.readLine());
    }
    actual.close();
    expected.close();
  }

  @Test
  public void writeOutTest() {
    fileOperations.writeOut(writeList, new File(WRITE_ACTUAL_FILE));
    try {
      assertReaders(WRITE_EXPECTED_FILE, WRITE_ACTUAL_FILE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
