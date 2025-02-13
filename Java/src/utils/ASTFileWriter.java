package utils;

import syntactic_analysis.ASTNode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ASTFileWriter {
    public static void writeToFile(ASTNode root, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writeNodeToFile(root, writer, 0);

            writer.close();
        } catch (IOException e) {
           throw new RuntimeException("Error writing to file: " + e.getMessage());
        }
    }

    private static void writeNodeToFile(ASTNode node, BufferedWriter writer, int level) {
        try {
            for (int i = 0; i < level; i++) {
                writer.write("  ");
            }
            writer.write(node.toString());
            writer.newLine();

            for (int i = node.getChildren().size() - 1; i >= 0; i--) {
                writeNodeToFile(node.getChildren().get(i), writer, level + 1);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage());
        }
    }
}
