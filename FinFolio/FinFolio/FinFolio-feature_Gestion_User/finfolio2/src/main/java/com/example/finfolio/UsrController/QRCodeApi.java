package com.example.finfolio.UsrController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Paths;

public class QRCodeApi {
private static final  String path="C:\\Users\\PC\\Desktop\\PI\\finfolio2\\src\\main\\resources\\com\\example\\finfolio\\Pics\\QRCode.jpg";
    private static final  String path1="C:\\Users\\melek\\Desktop\\final\\FinFolio\\FinFolio\\FinFolio\\FinFolio-feature_Gestion_User\\finfolio2\\src\\main\\resources\\com\\example\\finfolio\\Pics\\QRCode.jpg";


    public static String getPath() {
        return path;
    }

    public static String getPathEvent() {
        return path1;
    }

    public void GenereQrCode(String data) throws WriterException, IOException {

        BitMatrix matrix=new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE ,500 ,500);

        MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path));
        System.out.println("QR code successfully created");}



    public void GenereQrCodeEvent(String data) throws WriterException, IOException {

        BitMatrix matrix=new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE ,200 ,200);

        MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path1));
        System.out.println("QR code successfully created");}


}
