package ifa.devlog.gestparc;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ifa.devlog.gestparc.model.Document;
import ifa.devlog.gestparc.utils.requestmanager.MultipartRequest;
import ifa.devlog.gestparc.utils.requestmanager.RequestManager;

public class ActivityEditionDocument extends AppCompatActivity {
    private Document document;
    private EditText editTextNom;
    private Context context;
    ActivityResultLauncher<Intent> activityTakePicture;
    String currentPhotoPath;
    String apiUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        init();
        initClickValidation();
        initClickVoir();
//        initActivitySelectionImage();
        initActivityTakePicture();
    }

    private void init() {
        setContentView(R.layout.activity_edition_document);
        editTextNom = findViewById(R.id.editText_nomDocument);

        if (getIntent().hasExtra("document")) {
            Serializable serializable = getIntent().getSerializableExtra("document");
            Document document = (Document) serializable;
            this.document = document;
            editTextNom.setText(document.getNom());
        } else {
            this.document = new Document();
        }
        apiUrl = this.getResources().getString(R.string.url_spring) +"admin/document/";
        if (document.getId()==null) apiUrl += "new";
        else apiUrl += "update";

    }

    private void initClickVoir() {
        Button buttonVoirDocument = findViewById(R.id.button_document_contenu);
        buttonVoirDocument.setOnClickListener(v -> {
            Intent intent;
            intent= new Intent(this, ActivityVoirDocument.class);
            intent.putExtra("document", document);
            startActivity(intent);
        });
    }

    private void initClickValidation() {
        Button buttonValiderDocument = findViewById(R.id.button_document_validerDocument);
        buttonValiderDocument.setOnClickListener(v -> {
            document.setNom(editTextNom.getText().toString());

            MultipartRequest volleyMultipartRequest = new MultipartRequest(
                    Request.Method.POST,
                    apiUrl,
                    jsonResult -> {
                         finish();
                    },
                    error -> {
                        System.out.println("nok");
                    }
            ) {

                @Override
                public Map<String, String> getHeaders() {
                    SharedPreferences preference = context.getSharedPreferences(
                            context.getResources().getString(R.string.fichier_preference), 0); // 0 - for private mode
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer " + preference.getString("token",""));
                    return params;
                }
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    if (document.getId()!=null)
                        params.put("id", document.getId().toString());
                    params.put("nom", document.getNom());
                    if (document.getMateriel()!=null)
                        params.put("materiel", document.getMateriel().getId().toString());
                    else params.put("materiel", "");
                    if (document.getReparation()!=null)
                        params.put("reparation", document.getReparation().getId().toString());
                    else params.put("reparation", "");
                    if (document.getRetour()!=null)
                        params.put("retour", document.getRetour().getId().toString());
                    else params.put("retour", "");
                    return params;
                }
            };
            RequestManager.getInstance(this).addToRequestQueue(volleyMultipartRequest);

        });
    }

    /******************************* Photo ******************************************************************/

    private void initActivityTakePicture() {
        initBoutonPhoto();
        activityTakePicture= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    File file = new File(currentPhotoPath);
                    try {
                        byte[] fileContent = new byte[(int) file.length()];
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                        DataInputStream dis = new DataInputStream(bis);
                        dis.readFully(fileContent);

                        MultipartRequest volleyMultipartRequest = new MultipartRequest(
                                Request.Method.POST,
                                apiUrl,
                                jsonResult -> {
                                    finish();
                               //     try {
                                  //      question.updateFromUploadPhoto(new JSONObject(responseParser(jsonResult)));
                                //    } catch (JSONException e) {
                                //        e.printStackTrace();
                               //     }
                                },
                                error -> {
                                    System.out.println("nok");
                                }
                        ) {

                            @Override
                            public Map<String, String> getHeaders() {
                                SharedPreferences preference = context.getSharedPreferences(
                                        context.getResources().getString(R.string.fichier_preference), 0); // 0 - for private mode
                                Map<String, String> params = new HashMap<>();
                                params.put("Authorization", "Bearer " + preference.getString("token",""));
                                return params;
                            }

                            @Override
                            public Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                if (document.getId()!=null)
                                    params.put("id", document.getId().toString());
                                params.put("nom", document.getNom());
                                if (document.getMateriel()!=null)
                                    params.put("materiel", document.getMateriel().getId().toString());
                                else params.put("materiel", "");
                                if (document.getReparation()!=null)
                                    params.put("reparation", document.getReparation().getId().toString());
                                else params.put("reparation", "");
                                if (document.getRetour()!=null)
                                    params.put("retour", document.getRetour().getId().toString());
                                else params.put("retour", "");
                                return params;
                            }

                            @Override
                            protected Map<String, DataPart> getByteData() {
                                Map<String, DataPart> params = new HashMap<>();
                                params.put("file", new MultipartRequest.DataPart(
                                        "photo.jpg", fileContent));
                                return params;
                            }
                        };
                        RequestManager.getInstance(this).addToRequestQueue(volleyMultipartRequest);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "photo";
        File storageDir =getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activityTakePicture.launch(takePictureIntent);
            }
        }
    }

    private void initBoutonPhoto() {
        Button button = findViewById(R.id.button_document_photo);
        button.setOnClickListener(v -> {
            document.setNom(editTextNom.getText().toString());
            dispatchTakePictureIntent();
        });
    }
}