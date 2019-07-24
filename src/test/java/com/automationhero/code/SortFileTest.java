package com.automationhero.code;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SortFileTest {

  private static final String TEST_FILE_TXT = "test-file.txt";
  private static final String TEST_OUTPUT_TXT = "test-sortedOutput.txt";

  @Test
  public void noInputFileProvided() {
    System.out.println("Test for invalid file");
    SortFile.createSortedFile(null);
  }

  @Test
  public void testFileSort() throws Exception {
    SortFile.createSortedFile(TEST_FILE_TXT);
    try {
      assertReaders(TEST_OUTPUT_TXT, "SortedOutput.txt");
    } catch (IOException e) {
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
}
