package Utilitarios;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
    public static DateFormat dfTime = new SimpleDateFormat("HH:mm");

    public static Date getDataFormatada(String data) {

        Date date = null;

        try {
            date = dataFormatada.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static float getFloatSemVirgulas(String valor) {

        valor = valor.replace("R$","");

        if (valor.contains(",")) {
            valor = valor.replace(',', '.');
        }

        return Float.valueOf(valor);
    }

    public static String formatarFloatDuasCasasDecimais(float numero) {
        String retorno = "";
        DecimalFormat formatter = new DecimalFormat("#.00");
        try {
            retorno = formatter.format(numero);
        } catch (Exception ex) {
            System.err.println("Erro ao formatar numero: " + ex);
        }
        return retorno;
    }

    public static byte[] convertStreamtoByte(InputStream imagem) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            int reads = imagem.read();

            /*Enquanto houver dados na imagem, escrever no output*/
            while (reads != -1) {
                outputStream.write(reads);
                reads = imagem.read();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

}