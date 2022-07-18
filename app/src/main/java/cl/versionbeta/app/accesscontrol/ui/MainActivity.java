package cl.versionbeta.app.accesscontrol.ui;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cl.versionbeta.app.accesscontrol.R;
import cl.versionbeta.app.accesscontrol.ui.fragment.AreaFragment;
import cl.versionbeta.app.accesscontrol.ui.fragment.ConfigAppFragment;
import cl.versionbeta.app.accesscontrol.ui.fragment.InitFragment;
import cl.versionbeta.app.accesscontrol.ui.fragment.PersonListFragment;
import cl.versionbeta.app.accesscontrol.ui.fragment.RegisterFragment;
import cl.versionbeta.app.accesscontrol.ui.fragment.RegisterListFragment;
import cl.versionbeta.app.accesscontrol.ui.fragment.StatisticsFragment;

import static android.Manifest.permission_group.CAMERA;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static SharedPreferences preferences;

    //public static final int REQUEST_CODE = 100;
    //public static final int PERMISSION_REQUEST = 200;

    //public boolean PERMISSIONS_GRANTED = false;
    public static final int CAMERA_PERMISSION = 110;
    private int cameraPermission;
    //public static final int CAMERA_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        checkPermissions();

        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("configuracion", Context.MODE_PRIVATE);

        /*
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        */

        //this.checkPermissions();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set first fragment ..
        Fragment fragment = new InitFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }


    private void checkPermissions() {

        cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {

            Log.e("TEST-PERMISO", "SOLICITAR PERMISO !!! ");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);

        } else {
            Log.e("TEST-PERMISO", "PERMISO CAMARA OK!!! ");
        }

    }



    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case CAMERA_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("TEST-PERMISO", "PERMISO CAMARA OK -> " + requestCode);
                } else {
                    Log.e("TEST-PERMISO", "PERMISO CAMARA DENEGADO -> " + requestCode);
                    finish();
                }
                break;
            }
        }

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_register) {

            fragment = new RegisterFragment();

        } else if (id == R.id.nav_register_list) {

            fragment = new RegisterListFragment();

        } else if (id == R.id.nav_persons_registers) {

            fragment = new PersonListFragment();

        } else if (id == R.id.nav_statistics) {

            fragment = new StatisticsFragment();

        } else if (id == R.id.nav_config_app) {

            fragment = new ConfigAppFragment();

        } else if (id == R.id.nav_send) {

        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
