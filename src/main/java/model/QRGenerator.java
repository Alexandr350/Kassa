package model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class QRGenerator {
    public static ImageIcon generateQRCode(String text, int width, int height) throws Exception {

        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Пустой текст");
        }

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Недопустимые значения");
        }

        String targetDir = "src\\main\\resources\\images";
        File dir = new File(targetDir);

        if (!dir.exists()) {
            Files.createDirectories(dir.toPath());
            System.out.println("Создана папка: " + targetDir);
        }

        String fileName = "qrcode.png";
        Path path = Paths.get(targetDir, fileName);

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                text, BarcodeFormat.QR_CODE, width, height, hints);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println("QR-код сохранён: " + path.toAbsolutePath());

        BufferedImage img = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return new ImageIcon(img);
    }

}

