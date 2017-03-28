/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.generales;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.Rutas;
import com.avbravo.wizardjmoordb.utilidades.Utilidades;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class CryptoConverterGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(CryptoConverterGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    Rutas rutas;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            procesar("CryptoConverter.java", rutas.getPathUtil() + "CryptoConverter.java");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
            } else {
                generarImport(ruta);

                Utilidades.searchAdd(ruta, "private static final String ALGORITHM = \"AES/ECB/PKCS5Padding\";", "public class CryptoConverter", false);
                Utilidades.searchAdd(ruta, "private static final byte[] KEY = \"MySuperSecretKey\".getBytes();", "public class CryptoConverter", false);
                /**
                 * generar los metodos
                 */

                Utilidades.addNotFoundMethod(ruta, "public String convertToDatabaseColumn", convertToDatabaseColumn(), "private static final byte[]", false);
                Utilidades.addNotFoundMethod(ruta, "public String convertToEntityAttribute", convertToEntityAttribute(), "private static final byte[]", false);

            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("procesar() " + e.getLocalizedMessage());
        }
        return true;

    }

    private void generarImport(String ruta) {
        try {
            /**
             * agregar los imports
             */
            Utilidades.searchAdd(ruta, "import com.lowagie.text.pdf.codec.Base64;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.regex.Matcher;", "package", false);
            Utilidades.searchAdd(ruta, "import java.security.InvalidKeyException;", "package", false);
            Utilidades.searchAdd(ruta, "import java.security.InvalidKeyException;", "package", false);
            Utilidades.searchAdd(ruta, "import java.security.Key;", "package", false);
            Utilidades.searchAdd(ruta, "import java.security.NoSuchAlgorithmException;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.crypto.BadPaddingException;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.crypto.Cipher;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.crypto.IllegalBlockSizeException;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.crypto.IllegalBlockSizeException;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.crypto.NoSuchPaddingException;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.crypto.spec.SecretKeySpec;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.persistence.AttributeConverter;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.persistence.Converter;", "package", false);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("CryptoConverterGenerador.generarImport() " + e.getLocalizedMessage());
        }
    }

    /**
     * deleteAll
     *
     * @param entidad
     * @param archivo
     * @return
     */
    private Boolean crearFile(String path, String archivo) {
        try {

            String ruta = path;
            File file = new File(ruta);
            BufferedWriter bw;
            if (file.exists()) {
                // El fichero ya existe
            } else {
                // El fichero no existe y hay que crearlo
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.close();
//      bw.write("Acabo de crear el fichero de texto.");

                File file2 = new File(ruta);
                //Creamos un objeto para escribir caracteres en el archivo de prueba
                try (FileWriter fw = new FileWriter(file)) {
                    fw.write("/*" + "\r\n");
                    fw.write("* To change this license header, choose License Headers in Project Properties." + "\r\n");
                    fw.write("* To change this template file, choose Tools | Templates" + "\r\n");
                    fw.write(" * and open the template in the editor." + "\r\n");
                    fw.write("*/" + "\r\n");
                    fw.write("package " + mySesion.getPaquete() + ".generales;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import com.lowagie.text.pdf.codec.Base64;" + "\r\n");
                    fw.write("import java.security.InvalidKeyException;" + "\r\n");
                    fw.write("import java.security.Key;" + "\r\n");
                    fw.write("import java.security.NoSuchAlgorithmException;" + "\r\n");
                    fw.write("import javax.crypto.BadPaddingException;" + "\r\n");
                    fw.write("import javax.crypto.Cipher;" + "\r\n");
                    fw.write("import javax.crypto.IllegalBlockSizeException;" + "\r\n");
                    fw.write("import javax.crypto.NoSuchPaddingException;" + "\r\n");
                    fw.write("import javax.crypto.spec.SecretKeySpec;" + "\r\n");
                    fw.write("import javax.persistence.AttributeConverter;" + "\r\n");
                    fw.write("import javax.persistence.Converter;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Converter" + "\r\n");
                    fw.write("public class CryptoConverter implements AttributeConverter<String, String> {" + "\r\n");
                     fw.write("" + "\r\n");
                    fw.write("    private static final String ALGORITHM = \"AES/ECB/PKCS5Padding\";" + "\r\n");
                    fw.write("    private static final byte[] KEY = \"MySuperSecretKey\".getBytes();" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String convertToDatabaseColumn(String ccNumber) {" + "\r\n");
                    fw.write("      // do some encryption" + "\r\n");
                    fw.write("      Key key = new SecretKeySpec(KEY, \"AES\");" + "\r\n");
                    fw.write("      try {" + "\r\n");
                    fw.write("         Cipher c = Cipher.getInstance(ALGORITHM);" + "\r\n");
                    fw.write("         c.init(Cipher.ENCRYPT_MODE, key);" + "\r\n");
                    fw.write("         return Base64.encodeBytes(c.doFinal(ccNumber.getBytes()));" + "\r\n");
                    fw.write("      } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {" + "\r\n");
                    fw.write("         throw new RuntimeException(e);" + "\r\n");
                    fw.write("      }" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String convertToEntityAttribute(String dbData) {" + "\r\n");
                    fw.write("      // do some decryption" + "\r\n");
                    fw.write("      Key key = new SecretKeySpec(KEY, \"AES\");" + "\r\n");
                    fw.write("      try {" + "\r\n");
                    fw.write("        Cipher c = Cipher.getInstance(ALGORITHM);" + "\r\n");
                    fw.write("        c.init(Cipher.DECRYPT_MODE, key);" + "\r\n");
                    fw.write("        return new String(c.doFinal(Base64.decode(dbData)));" + "\r\n");
                    fw.write("      } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {" + "\r\n");
                    fw.write("        throw new RuntimeException(e);" + "\r\n");
                    fw.write("      }" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("}" + "\r\n");
                    fw.close();

                } catch (IOException ex) {
                    JSFUtil.addErrorMessage("crearFile() " + ex.getLocalizedMessage());
                }

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("crearFile() " + e.getLocalizedMessage());
        }
        return false;
    }

    private String convertToDatabaseColumn() {
        try {

            String texto = "";

            texto += "@Override" + "\r\n";
            texto += "    public String convertToDatabaseColumn(String ccNumber) {" + "\r\n";
            texto += "      // do some encryption" + "\r\n";
            texto += "      Key key = new SecretKeySpec(KEY, \"AES\");" + "\r\n";
            texto += "      try {" + "\r\n";
            texto += "         Cipher c = Cipher.getInstance(ALGORITHM);" + "\r\n";
            texto += "         c.init(Cipher.ENCRYPT_MODE, key);" + "\r\n";
            texto += "         return Base64.encodeBytes(c.doFinal(ccNumber.getBytes()));" + "\r\n";
            texto += "      } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {" + "\r\n";
            texto += "         throw new RuntimeException(e);" + "\r\n";
            texto += "      }" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("convertToDatabaseColumn()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String convertToEntityAttribute() {
        try {

            String texto = "";

            texto += "    @Override" + "\r\n";
            texto += "    public String convertToEntityAttribute(String dbData) {" + "\r\n";
            texto += "      // do some decryption" + "\r\n";
            texto += "      Key key = new SecretKeySpec(KEY, \"AES\");" + "\r\n";
            texto += "      try {" + "\r\n";
            texto += "        Cipher c = Cipher.getInstance(ALGORITHM);" + "\r\n";
            texto += "        c.init(Cipher.DECRYPT_MODE, key);" + "\r\n";
            texto += "        return new String(c.doFinal(Base64.decode(dbData)));" + "\r\n";
            texto += "      } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {" + "\r\n";
            texto += "        throw new RuntimeException(e);" + "\r\n";
            texto += "      }" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("convertToEntityAttribute()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
