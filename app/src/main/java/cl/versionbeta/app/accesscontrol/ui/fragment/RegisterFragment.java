package cl.versionbeta.app.accesscontrol.ui.fragment;


import android.content.DialogInterface;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.vision.barcode.Barcode;
import com.tapadoo.alerter.Alerter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cl.versionbeta.app.accesscontrol.R;
import cl.versionbeta.app.accesscontrol.io.remote.AccessLogService;
import cl.versionbeta.app.accesscontrol.io.remote.ApiUtils;
import cl.versionbeta.app.accesscontrol.io.remote.AreaService;
import cl.versionbeta.app.accesscontrol.io.remote.ContactService;
import cl.versionbeta.app.accesscontrol.io.remote.PersonService;
import cl.versionbeta.app.accesscontrol.model.Area;
import cl.versionbeta.app.accesscontrol.model.Contact;
import cl.versionbeta.app.accesscontrol.model.Person;
import cl.versionbeta.app.accesscontrol.model.Register;
import cl.versionbeta.app.accesscontrol.ui.ScanActivity;
import cl.versionbeta.app.accesscontrol.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import steelkiwi.com.library.DotsLoaderView;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_CODE = 100;
    public static final int CAMERA_REQUEST_CODE = 101;

    private TextInputEditText txDni;
    private TextInputEditText txFirstName;
    private TextInputEditText txLastName;
    private TextInputEditText txCompany;
    private Spinner cbArea;
    private Spinner cbContact;

    private ImageView iconCamera;

    private FloatingActionMenu btMenuRegister;
    private FloatingActionButton btActionRegisterIn;
    private FloatingActionButton btActionRegisterOut;

    private FloatingActionMenu btMenuOptions;
    private FloatingActionButton btActionPhoto;
    private FloatingActionButton btActionBarCode;

    private DotsLoaderView dotsLoaderView;


    private PersonService personService;
    private AreaService areaService;
    private ContactService contactService;
    private AccessLogService accessLogService;

    private List<Area> areaList = new ArrayList<>();
    private List<Contact> contactList = new ArrayList<>();
    ArrayAdapter<Area> areaAdapter;
    ArrayAdapter<Contact> contactAdapter;

    private Person person;
    private int typeRegister = 0;
    private String imageRegister;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        txDni = view.findViewById(R.id.txDni);
        txFirstName = view.findViewById(R.id.txFirstName);
        txLastName = view.findViewById(R.id.txLastName);
        txCompany = view.findViewById(R.id.txCompany);
        cbArea = view.findViewById(R.id.cbArea);
        cbContact = view.findViewById(R.id.cbContact);

        iconCamera = view.findViewById(R.id.iconCamera);

        btMenuRegister = view.findViewById(R.id.btMenuRegister);
        btActionRegisterIn = view.findViewById(R.id.btActionRegisterIn);
        btActionRegisterOut = view.findViewById(R.id.btActionRegisterOut);

        btMenuOptions = view.findViewById(R.id.btMenuOptions);
        btActionPhoto = view.findViewById(R.id.btActionPhoto);
        btActionBarCode = view.findViewById(R.id.btActionBarCode);

        dotsLoaderView = view.findViewById(R.id.dotsLoaderView);

        iconCamera.setVisibility(View.INVISIBLE);

        txDni.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txFirstName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txLastName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txCompany.setFilters(new InputFilter[] {new InputFilter.AllCaps()});


        btActionBarCode.setOnClickListener(this);
        btActionPhoto.setOnClickListener(this);
        btActionRegisterIn.setOnClickListener(this);
        btActionRegisterOut.setOnClickListener(this);


        txDni.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    person = null;
                    //validar dni ..
                    String dni = txDni.getText().toString().trim();
                    Pattern patternDni = Pattern.compile(Util.REGEX_DNI);
                    Pattern patternDni2 = Pattern.compile(Util.REGEX_DNI2);

                    if (patternDni.matcher(dni).matches() || patternDni2.matcher(dni).matches()) {
                        loadPerson(dni);
                    }

                }
            }
        });



        btMenuOptions.setClosedOnTouchOutside(true);
        btMenuRegister.setClosedOnTouchOutside(true);

        areaAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item);
        contactAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item);

        this.loadAreas();
        this.loadContacts();

        cbArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Area a = (Area)parent.getItemAtPosition(position);
                //Log.e("TEST", "destino -> id: " + a.getId() + " - nombre: " + a.getName());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cbContact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Contact c = (Contact) parent.getItemAtPosition(position);
                //Log.e("TEST", "contacto -> id: " + c.getId() + " - nombre: " + c.getFullName());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btActionBarCode:

                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.btActionPhoto:

                //Log.e("test", "btn foto ..");
                this.imageRegister = null;
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }

                break;

            case R.id.btActionRegisterIn:

                //Log.e("test", "btn registrar entrada ..");
                dotsLoaderView.show();
                boolean bln = this.validateInfo();
                if (bln) {

                    this.typeRegister = 0;
                    if (this.person.getStatus() > 0) {
                        AlertDialog dialog = this.createAlertDialog();
                        dialog.show();
                    } else {
                        this.saveAccessLog(typeRegister);
                    }
                    //this.saveAccessLog(0);
                } else {
                    Log.e("VALIDACION","error en formulario de registro ..");
                }

                break;

            case R.id.btActionRegisterOut:

                //Log.e("test", "btn registrar salida ..");

                dotsLoaderView.show();
                boolean b = this.validateInfo();
                if (b) {
                    this.typeRegister = 1;
                    if (this.person.getStatus() > 0) {
                        AlertDialog dialog = this.createAlertDialog();
                        dialog.show();
                    } else {
                        this.saveAccessLog(typeRegister);
                    }
                } else {
                    Log.e("VALIDACION","error en formulario de registro ..");
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            if (data != null) {

                Log.e("RESULT-SCANNER","data OK !! ");
                final Barcode barcode = data.getParcelableExtra("barcode");
                try {

                    String dni = "";

                    // PDF417 ..
                    if (barcode.valueFormat == 7) {

                        String[] arr = barcode.displayValue.toString().substring(0, 12).split(" ");
                        dni = (arr.length > 1) ? arr[0].substring(0, 8) : arr[0].substring(0, 9);
                        String dv = dni.substring(dni.trim().length() - 1,dni.trim().length());
                        dni = dni.substring(0, dni.trim().length() -1) + "-" + dv;
                        Log.e("RESULT-SCANNER","PDF417 -> cedula: " + dni);

                    }

                    // QR ..
                    if (barcode.valueFormat == 8) {

                        String[] arr = barcode.displayValue.toString().trim().split("=");
                        dni = (arr.length > 1) ? arr[1] : "";
                        arr = dni.split("&");
                        dni = (arr.length > 0) ? arr[0] : "";
                        Log.e("RESULT-SCANNER","QR -> cedula: " + dni);

                    }
                    txDni.setText(dni);
                    this.loadPerson(dni);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }


        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            iconCamera.setVisibility(View.VISIBLE);

            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            Log.e("IMAGEN", "FOTO: alto ->" + bitmap.getHeight() + " - ancho -> " + bitmap.getWidth());


            // imágenes grandes -> resolución 800x600 ..
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();
            if (height > 800 || width > 600) {
                Matrix matrix = new Matrix();
                float scaleHeight = bitmap.getHeight()/800;
                float scaleWidth = bitmap.getWidth()/600;
                matrix.postScale(scaleWidth, scaleHeight);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
                Log.i("imagen", "proceso escala imagen 800x600 .. ");
            }

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b); //compresion ..
            byte[] imgByte = b.toByteArray();



            //codificar en cadena base 64 ..
            //String strImg = Base64.encodeToString(imgByte, Base64.NO_WRAP); // ok
            this.imageRegister = Base64.encodeToString(imgByte, Base64.NO_WRAP);

            /*
            Log.e("TEST-BASE64", "imageRegister lengh -> " + imgByte.length + " !! - strImg -> " + strImg.length());
            accessLogService = ApiUtils.getAccessLogService();
            accessLogService.saveImageAccessLog(strImg).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Log.e("TEST WS", "OK -> " + response.toString().substring(0, 10));
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("TEST WS", ":( .." + t.getMessage());
                }
            });
            */


            //decodificar cadena base 64 ..
            //byte[] byteCode = Base64.decode(strImg, Base64.DEFAULT);
            //Bitmap bitmapPerson = BitmapFactory.decodeByteArray(byteCode, 0, byteCode.length);



        }


    }


    private boolean validateInfo() {

        boolean bln = false;
        try {

            String dni = this.txDni.getText().toString().trim();
            Pattern patternDni = Pattern.compile(Util.REGEX_DNI);
            Pattern patternDni2 = Pattern.compile(Util.REGEX_DNI2);
            Pattern patternWords = Pattern.compile(Util.REGEX_WORDS);

            if (TextUtils.isEmpty(dni)) {

                Alerter.create(getActivity()).setText("Campo Cedula Identidad requerido !!").setDuration(1000)
                       .setIcon(R.drawable.ic_error_outline_24dp).setBackgroundColorRes(R.color.colorAlertError).show();

                this.txDni.requestFocus();
                return false;
            }

            if (patternDni.matcher(dni).matches() || patternDni2.matcher(dni).matches()) {
                Log.e("VALIDACION","Campo Cedula Identidad OK :P");
                bln = true;
            } else {
                Alerter.create(getActivity()).setText("Campo Cedula Identidad: formato Invalido !!").setDuration(1000)
                       .setIcon(R.drawable.ic_error_outline_24dp).setBackgroundColorRes(R.color.colorAlertError).show();
                this.txDni.requestFocus();
                return false;
            }

            String firstName = this.txFirstName.getText().toString().trim();
            String lastName = this.txLastName.getText().toString().trim();

            if (TextUtils.isEmpty(firstName)) {
                Alerter.create(getActivity()).setText("Campo Nombre requerido !!").setDuration(1000)
                       .setIcon(R.drawable.ic_error_outline_24dp).setBackgroundColorRes(R.color.colorAlertError).show();

                this.txFirstName.requestFocus();
                return false;
            }

            if (TextUtils.isEmpty(lastName)) {
                Alerter.create(getActivity()).setText("Campo Apellido es requerido !!").setDuration(1000)
                       .setIcon(R.drawable.ic_error_outline_24dp).setBackgroundColorRes(R.color.colorAlertError).show();
                this.txLastName.requestFocus();
                return false;
            }


            if (!patternWords.matcher(firstName).matches()) {
                Alerter.create(getActivity()).setText("Campo Nombre: formato invalido !!").setDuration(1000)
                        .setIcon(R.drawable.ic_error_outline_24dp).setBackgroundColorRes(R.color.colorAlertError).show();
                this.txFirstName.requestFocus();
                return false;
            }

            if (!patternWords.matcher(lastName).matches()) {
                Alerter.create(getActivity()).setText("Campo Apellido: formato invalido !!").setDuration(1000)
                        .setIcon(R.drawable.ic_error_outline_24dp).setBackgroundColorRes(R.color.colorAlertError).show();
                this.txLastName.requestFocus();
                return false;
            }


            Area area = (Area)cbArea.getSelectedItem();
            if (area.getId().equals(0)) {

                Alerter.create(getActivity()).setText("Campo Destino / Área es requerido !!").setDuration(1000)
                        .setIcon(R.drawable.ic_error_outline_24dp).setBackgroundColorRes(R.color.colorAlertError).show();

                this.cbArea.requestFocus();
                return false;

            }

            // set person object ..
            if (this.person == null) {
                this.person = new Person(dni, firstName, lastName);
            } else {
                this.person.setFirstName(firstName);
                this.person.setLastName(lastName);
            }

            Log.e("TEST-PERSON", this.person.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bln;

    }


    private void loadPerson(String dni) {
        try {

            this.person = null;
            if (TextUtils.isEmpty(dni)) return;
            personService = ApiUtils.getPersonService();
            personService.findPersonById(dni).enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    if (response.isSuccessful()) {
                        person = response.body();
                        if (person != null) {
                            Log.e("TEST-WS", person.toString());
                            txFirstName.setText(person.getFirstName().trim());
                            txLastName.setText(person.getLastName().trim());
                        }
                    }
                }
                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    Log.e("TEST-WS", t.getMessage());
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void loadContacts() {

        try {

            contactService = ApiUtils.getContactService();

            contactService.loadContacts().enqueue(new Callback<List<Contact>>() {
                @Override
                public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                    if (response.isSuccessful()) {
                        contactList = response.body();
                        Contact contact = new Contact(0,"N/A");
                        contactAdapter.add(contact);
                        contactAdapter.addAll(contactList);
                        cbContact.setAdapter(contactAdapter);
                        Log.e("WS", "contactList -> " + contactList.size() );
                    }
                }
                @Override
                public void onFailure(Call<List<Contact>> call, Throwable t) {
                    Log.e("ERROR-WS", t.getMessage());
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void loadAreas() {

        areaService = ApiUtils.getAreaService();
        areaService.loadPlaces().enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                if (response.isSuccessful()) {
                    areaList = response.body();
                    Area area = new Area(0,"N/A");
                    areaAdapter.add(area);
                    areaAdapter.addAll(areaList);
                    cbArea.setAdapter(areaAdapter);
                    Log.e("WS", "areaList -> " + areaList.size() );
                }
            }
            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {
                Log.e("ERROR-WS", t.getMessage());
            }
        });


    }


    private void saveAccessLog(int type) {

        try {

            Area area = (Area)cbArea.getSelectedItem();
            Contact contact = (Contact)cbContact.getSelectedItem();

            if (area.getId().equals(0)) area = null;
            if (contact.getId().equals(0)) contact = null;

            Register register = new Register(this.person, type, "", area, contact);

            if (this.imageRegister != null) register.setDocumentImage(this.imageRegister);
            register.setPersonCompany(this.txCompany.getText().toString());


            accessLogService = ApiUtils.getAccessLogService();
            accessLogService.saveAccessLog(register).enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    if (response.isSuccessful()) {

                        Log.e("TEST WS", "register access OK !!..");
                        dotsLoaderView.hide();
                        changeFragment();

                    }
                }
                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    Log.e("TEST WS", ":( .." + t.getMessage());
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private  void changeFragment() {
        try {

            Alerter.create(getActivity())
                    .setTitle("Control de Visitas")
                    .setText("Acceso registrado correctamente!! ")
                    .setDuration(1000)
                    .setIcon(R.drawable.ic_check)
                    .setBackgroundColorRes(R.color.colorTealPrimary)
                    .show();

            Thread.sleep(2200);

            Fragment fragment = new RegisterListFragment();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.container, fragment).commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private AlertDialog createAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        try {

            if (this.person.getStatus() == 1) {
                builder.setTitle("Acceso Denegado");
                builder.setIcon(R.drawable.ic_warning_red_24dp);
            }
            if (this.person.getStatus() == 2) {
                builder.setTitle("Acceso Restringido");
                builder.setIcon(R.drawable.ic_warning_yellow_24dp);
            }

            String msg = "Visita presenta restricción de acceso.";
            msg+= "\n" + this.person.getComment();
            builder.setMessage(msg);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        builder.setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveAccessLog(typeRegister);
            }
        });


        return builder.create();

    }




    /// ////////////////////////////////////////////////////////////////////////////
}
