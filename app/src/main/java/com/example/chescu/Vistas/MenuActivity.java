package com.example.chescu.Vistas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chescu.Conexion;
import com.example.chescu.Gestion.Gestion_Usuario_Activity;
import com.example.chescu.Gestion.Gestion_platos_Activity;
import com.example.chescu.R;
import com.example.chescu.model.Plato;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MenuActivity extends AppCompatActivity {
    private TextView btnplatos, btnpedidos, btnqr, btnusuario;
    private FirebaseStorage mStorage;

    private ImageView qr;
    private static final String STORAGE_PATH_PDF_CARTILLA = "archivos/cartilla.pdf";
    private static final String STORAGE_EDITED_PDF_NAME = "cartilla_editada.pdf";
    private static final String TOAST_PDF_DOWNLOADED = "PDF descargado correctamente";
    private static final String TOAST_PDF_DOWNLOAD_FAILED = "Error al descargar el PDF";
    final long MAX_SIZE_BYTES = 1024 * 1024;

    Conexion conexionBD = new Conexion();

    String storagePathPdfCartilla = "archivos/cartilla.pdf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mStorage = FirebaseStorage.getInstance();
        qr = findViewById(R.id.qr);
        establecerQR();
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descargarPDFFromStorage();
            }
        });

        btnpedidos = findViewById(R.id.btnpedidos);
        btnpedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), PedidosActivity.class));
            }
        });
        btnqr = findViewById(R.id.btnqr);
        btnqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarPDF();
            }
        });
        btnusuario = findViewById(R.id.btnusuario);
        btnusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Gestion_Usuario_Activity.class));
            }
        });
        btnplatos = findViewById(R.id.btnplatos);
        btnplatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Gestion_platos_Activity.class));
            }
        });
    }

    private void generarQR(StorageReference storageReference) {
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            String storageUrl = uri.toString();
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(storageUrl, BarcodeFormat.QR_CODE, 300, 300);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                StorageReference qrImageRef = mStorage.getReference().child("archivos/qr.jpg");

                UploadTask uploadTask = qrImageRef.putBytes(data);
                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(this, "Se actualizo el qr", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(Throwable::printStackTrace);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).addOnFailureListener(Throwable::printStackTrace);
    }

    private void configurarPDF() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.cartilla_virual);
            File outputFile = new File(this.getFilesDir(), STORAGE_EDITED_PDF_NAME);

            PdfReader reader = new PdfReader(inputStream);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));

            int totalPages = reader.getNumberOfPages();

            for (int i = 1; i <= totalPages; i++) {
                int textoBurgerX = 15;
                int textoBurgerY = 530;
                int textoAlitasX = 300;
                int textoAlitasY = 760;
                int textoBebidaX = 410;
                int textoBebidaY = 325;
                int textoSalchiX = 15;
                int textoSalchiY = 380;

                AtomicReference<PdfContentByte> canvas = new AtomicReference<>(stamper.getOverContent(i));
                AtomicInteger yPosBurgerY = new AtomicInteger(textoBurgerY);
                AtomicInteger yPosAlitasY = new AtomicInteger(textoAlitasY);
                AtomicInteger yPosBebidaY = new AtomicInteger(textoBebidaY);
                AtomicInteger yPosSalchiY = new AtomicInteger(textoSalchiY);

                List<Plato> platos = obtenerPlatoBD();
                platos.forEach(plato -> {
                    Font nombreFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
                    nombreFont.setColor(BaseColor.WHITE);

                    Font descripcionFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
                    descripcionFont.setColor(BaseColor.WHITE);

                    if (plato.getId_tipoplato().equalsIgnoreCase("Burger")) {
                        yPosBurgerY.addAndGet(-30);
                        // Agrega el nombre
                        Phrase nombrePhrase = new Phrase(plato.getDesc(), nombreFont);
                        ColumnText.showTextAligned(canvas.get(), Element.ALIGN_LEFT, nombrePhrase, textoBurgerX, yPosBurgerY.get(), 0);
                        // Agrega la precio
                        Phrase descripcionPhrase = new Phrase("S/. "+plato.getPrecio(), descripcionFont);
                        ColumnText.showTextAligned(canvas.get(), Element.ALIGN_LEFT, descripcionPhrase, textoBurgerX + 180, yPosBurgerY.get(), 0);
                    } else if (plato.getId_tipoplato().equalsIgnoreCase("Alitas")) {
                        yPosAlitasY.addAndGet(-30);
                        // Agrega el nombre
                        Phrase nombrePhrase = new Phrase(plato.getDesc(), nombreFont);
                        ColumnText.showTextAligned(canvas.get(), Element.ALIGN_LEFT, nombrePhrase, textoAlitasX, yPosAlitasY.get(), 0);
                        // Agrega la precio
                        Phrase descripcionPhrase = new Phrase("S/. "+plato.getPrecio(), descripcionFont);
                        ColumnText.showTextAligned(canvas.get(), Element.ALIGN_LEFT, descripcionPhrase, textoAlitasX + 160, yPosAlitasY.get(), 0);
                    } else if (plato.getId_tipoplato().equalsIgnoreCase("Bebida")) {
                        yPosBebidaY.addAndGet(-30);
                        // Agrega el nombre
                        Phrase nombrePhrase = new Phrase(plato.getDesc(), nombreFont);
                        ColumnText.showTextAligned(canvas.get(), Element.ALIGN_LEFT, nombrePhrase, textoBebidaX, yPosBebidaY.get(), 0);
                        // Agrega la precio
                        Phrase descripcionPhrase = new Phrase("S/. "+plato.getPrecio(), descripcionFont);
                        ColumnText.showTextAligned(canvas.get(), Element.ALIGN_LEFT, descripcionPhrase, textoBebidaX + 130, yPosBebidaY.get(), 0);
                    } else if (plato.getId_tipoplato().equalsIgnoreCase("Salchi")) {
                        yPosSalchiY.addAndGet(-30);
                        // Agrega el nombre
                        Phrase nombrePhrase = new Phrase(plato.getDesc(), nombreFont);
                        ColumnText.showTextAligned(canvas.get(), Element.ALIGN_LEFT, nombrePhrase, textoSalchiX, yPosSalchiY.get(), 0);
                        // Agrega la precio
                        Phrase descripcionPhrase = new Phrase("S/. "+plato.getPrecio(), descripcionFont);
                        ColumnText.showTextAligned(canvas.get(), Element.ALIGN_LEFT, descripcionPhrase, textoSalchiX + 180, yPosSalchiY.get(), 0);
                    }
                });
                try {
                    stamper.close();
                } catch (DocumentException | IOException e) {
                    throw new RuntimeException(e);
                }
                reader.close();

                Toast.makeText(this, "Se editó el PDF", Toast.LENGTH_SHORT).show();

                StorageReference storageRef = mStorage.getReference().child(STORAGE_PATH_PDF_CARTILLA);
                Uri fileUri = Uri.fromFile(outputFile);
                UploadTask uploadTask = storageRef.putFile(fileUri);

                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    generarQR(storageRef);
                }).addOnFailureListener(Throwable::printStackTrace);
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public List<Plato> obtenerPlatoBD() {
        List<Plato> Plato = new ArrayList<>();
        try {
            Statement st = conexionBD.conexionBD().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Plato WHERE Stado = 'TRUE'");
            while (rs.next()) {
                Plato.add(new Plato(rs.getInt("Id_plato"), rs.getString("Id_tipoplato"), rs.getDouble("Prec_plato"), rs.getString("Desc_plato"), rs.getBoolean("Stado")));
            }
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return Plato;
    }

    private void establecerQR() {
        StorageReference qrImageRef = mStorage.getReference().child("archivos").child("qr.jpg");

        // Error al obtener la imagen del código QR desde Firebase Storage
        qrImageRef.getBytes(MAX_SIZE_BYTES).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            qr.setImageBitmap(bitmap);
        }).addOnFailureListener(Throwable::printStackTrace);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null && result.getContents() != null) {
            String qrContent = result.getContents();

            StorageReference storageRef = mStorage.getReference().child("archivos/cartilla.pdf");
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String storageUrl = uri.toString();
                    if (qrContent.equals(storageUrl)) {
                        descargarPDFFromStorage();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Ocurrió un error al obtener la URL del archivo
                    e.printStackTrace();
                }
            });
        }
    }

    private void descargarPDFFromStorage() {
        StorageReference storageRef = mStorage.getReference().child(storagePathPdfCartilla);
        File localFile = new File(this.getExternalFilesDir(null), "cartilla.pdf");

        storageRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(this, TOAST_PDF_DOWNLOADED, Toast.LENGTH_SHORT).show();
            openPDF(localFile);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, TOAST_PDF_DOWNLOAD_FAILED, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        });
    }

    private void openPDF(File file) {
        Uri fileUri = FileProvider.getUriForFile(this, "com.example.chescu.fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}