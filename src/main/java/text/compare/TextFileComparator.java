package text.compare;




import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.Scanner;
//
//public class TextFileComparator {
//
//    private static Workbook workbook;
//
//    public static void main(String[] args) throws IOException {
//        String folderPath1 = "/Users/pmahajan/Downloads/folder1"; // Replace with the path to the first folder
//        String folderPath2 = "/Users/pmahajan/Downloads/folder2"; // Replace with the path to the second folder
//
//        workbook = new XSSFWorkbook();
//
//        File folder1 = new File(folderPath1);
//        File folder2 = new File(folderPath2);
//
//        compareFolders(folder1, folder2);
//
//        // Save the differences to an Excel file
//        try (FileOutputStream outputStream = new FileOutputStream("differences.xlsx")) {
//            workbook.write(outputStream);
//        }
//    }
//
//    private static void compareFolders(File folder1, File folder2) throws IOException {
//        File[] files1 = folder1.listFiles((dir, name) -> !name.equals(".DS_Store"));
//        File[] files2 = folder2.listFiles((dir, name) -> !name.equals(".DS_Store"));
//
//        if (files1 != null && files2 != null) {
//            for (File file1 : files1) {
//                File file2 = new File(folder2, file1.getName());
//
//                if (file2.exists()) {
//                    if (file1.isDirectory() && file2.isDirectory()) {
//                        compareFolders(file1, file2); // Recursively compare subfolders
//                    } else if (file1.isFile() && file2.isFile()) {
//                        compareAndWriteToExcel(file1, file2);
//                    }
//                }
//            }
//        }
//    }
//
//    private static void compareAndWriteToExcel(File file1, File file2) throws IOException {
//        Scanner scanner1 = new Scanner(file1);
//        Scanner scanner2 = new Scanner(file2);
//        
//System.out.println(file1.getAbsolutePath() + "\n" + file2.getAbsolutePath());
//
//        String fileName = file1.getAbsolutePath().replace("/", "_");
//          int leng =  fileName.length();
//          fileName = fileName.substring(leng/2, leng);
//
//        Sheet sheet = workbook.createSheet(fileName);
//        int rowIndex = 0;
//
//        while (scanner1.hasNextLine() || scanner2.hasNextLine()) {
//            Row row = sheet.createRow(rowIndex++);
//            
//            if (scanner1.hasNextLine()) {
//                String line1 = scanner1.nextLine();
//                Cell cell1 = row.createCell(0);
//                cell1.setCellValue(line1);
//            }
//
//            if (scanner2.hasNextLine()) {
//                String line2 = scanner2.nextLine();
//                Cell cell2 = row.createCell(1);
//                cell2.setCellValue(line2);
//            }
//        }
//
//        scanner1.close();
//        scanner2.close();
//    }
//}
//
//
//
//import org.apache.commons.io.FileUtils;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;

public class TextFileComparator {

    private static Workbook workbook;
    private static int sheetIndex = 0;

    public static void main(String[] args) throws IOException {
        String folderPath1 = "/Users/pmahajan/Downloads/folder1"; // Replace with the path to the first folder
        String folderPath2 = "/Users/pmahajan/Downloads/folder2"; // Replace with the path to the second folder

        workbook = new XSSFWorkbook();

        File folder1 = new File(folderPath1);
        File folder2 = new File(folderPath2);

        compareFolders(folder1, folder2);

        // Save the differences to an Excel file
        try (FileOutputStream outputStream = new FileOutputStream("differences.xlsx")) {
            workbook.write(outputStream);
        }
    }

    private static void compareFolders(File folder1, File folder2) throws IOException {
        File[] files1 = folder1.listFiles((dir, name) -> !name.equals(".DS_Store"));
        File[] files2 = folder2.listFiles((dir, name) -> !name.equals(".DS_Store"));

        if (files1 != null && files2 != null) {
            for (File file1 : files1) {
                File file2 = new File(folder2, file1.getName());

                if (file2.exists()) {
                    if (file1.isDirectory() && file2.isDirectory()) {
                        compareFolders(file1, file2); // Recursively compare subfolders
                    } else if (file1.isFile() && file2.isFile()) {
                        List<String> differences = compareAndFindDifferences(file1, file2);
                        if (!differences.isEmpty()) {
                            writeDifferencesToExcel(file1, differences);
                        }
                    }
                }
            }
        }
    }

    private static List<String> compareAndFindDifferences(File file1, File file2) throws IOException {
        List<String> differences = new ArrayList<>();

        Scanner scanner1 = new Scanner(file1);
        Scanner scanner2 = new Scanner(file2);

        while (scanner1.hasNextLine() && scanner2.hasNextLine()) {
            String line1 = scanner1.nextLine();
            String line2 = scanner2.nextLine();

            if (!line1.equals(line2)) {
                differences.add("Line " + (differences.size() + 1) + ": " + line1 + " vs " + line2);
            }
        }

        scanner1.close();
        scanner2.close();

        return differences;
    }

    private static void writeDifferencesToExcel(File file, List<String> differences) {
    	
      String fileName = file.getAbsolutePath().replace("/", "_");
      int leng =  fileName.length();
      String sheetName = fileName.substring(leng-40, leng);
    	
        Sheet sheet = workbook.createSheet(sheetName);
        int rowIndex = 0;

        for (String difference : differences) {
            Row row = sheet.createRow(rowIndex++);
            Cell cell = row.createCell(0);
            cell.setCellValue(file.getName());
            cell = row.createCell(1);
            cell.setCellValue(difference);
        }
    }
}




