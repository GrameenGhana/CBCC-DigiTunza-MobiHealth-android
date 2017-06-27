package org.cbccessence.mobihealth.activity;

import java.util.ArrayList;
import java.util.List;

import org.cbccessence.mobihealth.HttpHandler;
import org.cbccessence.mobihealth.R;
import org.cbccessence.mobihealth.adapter.ProjectsListAdapter;
import org.cbccessence.mobihealth.model.Projects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    private SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    HttpHandler httpHandler;
    private TextInputLayout username_layout;
    private TextInputLayout password_layout;
    String username;
    String password;
    FloatingActionButton fab;
    public static ProgressDialog pDialog;

    static String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.VIBRATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.INTERNET,
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR

    };

    static Boolean hasAllPermissions = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getIntent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_login_new);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        httpHandler = new HttpHandler(LoginActivity.this);

        username_layout = (TextInputLayout) findViewById(R.id.email_layout);
        password_layout = (TextInputLayout) findViewById(R.id.password_layout);


        fab = (FloatingActionButton) findViewById(R.id.login_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onLoginClick();

            }
        });


        password_layout.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.this.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);


                onLoginClick();

                return true;

            }
        });

    }




    public void onLoginClick() {




        if(PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).getBoolean("isFirstSignIn", true)){

            Log.i(TAG, "Is first run is True");
            // check for Internet status
            if (httpHandler.checkInternetConnection()) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    launchMultiplePermissions(LoginActivity.this);
                     else performLoginTask();

            } else {
                // Internet connection is not present
                // Ask user to connect to Internet
                httpHandler.showAlertDialog(LoginActivity.this, "No Internet Connection",
                        "If this is your first time signing in, you need to sign in online!");
            }

        }else{     Log.i(TAG, "Is first run is False. Creds have probably been saved before");

            if (httpHandler.checkInternetConnection()) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                  launchMultiplePermissions(LoginActivity.this);
                     else performLoginTask();

            } else {
                fab.setEnabled(false);

                Log.i(TAG, "No Internet, Checking for creds locally");
                username = username_layout.getEditText().getText().toString().trim();
                password = password_layout.getEditText().getText().toString().trim();

                Log.i(TAG, "Login locally");

                if (!validate()) {
                    Toast.makeText(LoginActivity.this, "Please enter valid username and or password", Toast.LENGTH_SHORT).show();
                } else {

                    if (username.equals(PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).getString("username", "null")) &&
                            password.equals(PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).getString("password", "null"))) {

                        Log.i(TAG, "Local username and password match!");
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putBoolean("isSignedIn", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putBoolean("isFirstSignIn", false).apply();
                        startActivity(intent);
                        finish();
                    } else   Toast.makeText(LoginActivity.this, "Incorrect username and password", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

            return super.onOptionsItemSelected(item);

    }



    public static boolean launchMultiplePermissions(Activity context) {
        Boolean hasPermissions = null;

        for(String permission : PERMISSIONS){
            if(!hasPermission(context, permission)){


                if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                    ActivityCompat.requestPermissions(context, PERMISSIONS, 30);
                } else {
                    ActivityCompat.requestPermissions(context, PERMISSIONS, 30);
                }

                return false;
            }


        }

        return true;
    }


    public static boolean hasPermission(Context context, String PERMISSION) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {

            if (ActivityCompat.checkSelfPermission(context, PERMISSION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, PERMISSION)) {


                }
                return false;
            }

        }
        return true;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);



        if(grantResults[0] != PackageManager.PERMISSION_GRANTED ) {


            //httpHandler.showAlertDialog(LoginActivity.this, "Provide permissions", "Digitunza requires all permissions to work effectively");
             AlertDialog.Builder alertDialog = new  AlertDialog.Builder(this, R.style.AppAlertDialog)
                    .setTitle("Permissions")
                    .setMessage("Digitunza requires all permissions to work effectively")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                            onLoginClick();
                        }
                    }).setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            dialogInterface.dismiss();
                            supportFinishAfterTransition();

                        }
                    });

             AlertDialog alert = alertDialog.create();
            alert.show();
        }else {

           // login
            performLoginTask();


        }
    }


 private void performLoginTask(){

     username = username_layout.getEditText().getText().toString().trim();
     password = password_layout.getEditText().getText().toString().trim();

     Log.d(TAG, "Login");

     if (!validate()) {
         return;
     }

     fab.setEnabled(false);
     LoginTask loginTask = new LoginTask(LoginActivity.this);
     loginTask.execute(username, password);






 }

    public boolean validate() {
        boolean valid = true;

        if (username.isEmpty() ) {
            username_layout.setError("enter a valid username ");
            valid = false;
        } else {
            username_layout.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 ) {
            password_layout.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password_layout.setError(null);
        }

        return valid;
    }









    public class LoginTask extends AsyncTask<String, String, String> {

        public  final String TAG = LoginTask.class.getSimpleName();
        private AppCompatActivity ctx;
        private SharedPreferences prefs;
        ProgressDialog progress;
        HttpHandler post;
        List<Projects> projects = new ArrayList<Projects>();
        android.support.v7.app.AlertDialog dialog;
        Integer uid;

        String email;
        String password;


        public LoginTask(AppCompatActivity c) {
            this.ctx = c;
            prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            progress = new ProgressDialog(ctx);
            progress.setTitle("Logging in");

        }

        @Override
        protected void onPreExecute() {

            progress.setMessage("Authenticating...");
            progress.show();

            post = new HttpHandler(ctx);


        }

        @Override
        protected String doInBackground(String... strings) {


            String response = null;

            String url = "http://188.166.30.140/gfcare/api/users/login";
            JSONObject json = new JSONObject();

            email = strings[0];
            password = strings[1];
            String module = "gfcare-module-3";

            try {


                Log.d(TAG, "logging in user *** " + email);


                json.put("email", email);
                json.put("password", password);
                json.put("module", module);


                String responseJsonString = post.makePostRequest(url, json.toString());

                Log.i("Login Task", responseJsonString);

                switch (responseJsonString) {
                    case "no access": // unauthorised

                        response = "no access";


                        break;
                    case "invalid": // user not found
                        response = "invalid";

                        break;
                    case "null":

                        //Check connection

                        response = "null";
                        Log.d(TAG, responseJsonString);


                        break;
                    default: // logged in



                        JSONObject jsonResp = new JSONObject(responseJsonString);
                        String token = jsonResp.getString("token");
                        JSONObject jsonUserDetails = jsonResp.getJSONObject("user");
                        JSONArray projectsArray = jsonUserDetails.getJSONArray("projects");

                        uid = jsonUserDetails.getInt("id");


                        Log.d(TAG, token);
                        Log.d(TAG, jsonUserDetails.toString());

                        for (int i = 0; i < projectsArray.length(); i++) {
                            JSONObject project = projectsArray.getJSONObject(i);
                            JSONObject pivotObject = project.getJSONObject("pivot");


                            Integer id = project.getInt("id");
                            Integer owner_id = project.getInt("owner_id");
                            String name = project.getString("name");
                            String date_created = project.getString("created_at");
                            String date_updated = project.getString("updated_at");

                            int tid = pivotObject.getInt("team_id");


                            Projects _project = new Projects();
                            _project.setProjectId(id);
                            _project.setProjectOwnerId(owner_id);
                            _project.setProjectName(name);
                            _project.setDateUpdated(date_updated);
                            _project.setgetDateCreated(date_created);
                            _project.setTeamId(tid);

                            projects.add(_project);

                            Log.d(TAG, i + "\t" + _project.getProjectName());


                        }


                        //User's parameters
                        String name[] = jsonUserDetails.getString("name").split("\\s+");

                        //Get current users Projects, Display in a custom dialog with a list of users projects, User makes a selection then app starts
                        //with the current projects data! after making a post with the project id


                        String firstName = name[0].trim();
                        String lastName = name[1].trim();


                        //User has access to This module, Save name in shared prefs/database and Sign him in
                        SharedPreferences.Editor prefsEditor = prefs.edit();
                        prefsEditor.putString("token", token);
                        prefsEditor.putInt("uid", uid);

                        prefsEditor.putString("email", email);
                        prefsEditor.putString("first_name", firstName);
                        prefsEditor.putString("last_name", lastName);

                        prefsEditor.apply();

                        Log.d(TAG, token);
                        Log.d(TAG, firstName);
                        Log.d(TAG, lastName);


                }


            } catch (JSONException e) {

                e.printStackTrace();

            }
            return response;

        }


        @Override
        protected void onPostExecute(String response) {

            if (progress != null) progress.hide();

            if (response != null) {

                switch (response) {
                    case "no access":

                        post.showAlertDialog(ctx, "Login failed!", "You do not have access to this module.");


                        break;

                    case "invalid":

                        post.showAlertDialog(ctx, "Login failed!", "Please check your email and password.");

                        break;

                    case "null":

                        post.showAlertDialog(ctx, "Login failed!", "Server is under maintenance, please try again later");

                        break;

                }
            } else {
                //logged In start activity



                if (projects != null) {
                    Log.i(TAG, "There are " + projects.size() + " project(s) ");

                    Log.i(TAG, "0 " + projects.get(0));



                    if (projects.size() == 0) {
                        post.showAlertDialog(ctx, "No Projects!", "You have no current projects");

                        //If no projects, display a dialog. Close app since there are no projects
                    } else if (projects.size() == 1) { //Else log the user in.


                        sendPostLoginRequest(uid, projects.get(0).getTeamId(), projects.get(0).getProjectId());


                    } else if (projects.size() > 1) {
                        LayoutInflater inflater = LayoutInflater.from(ctx);
                        View view = inflater.inflate(R.layout.select_project_dialog, null, false);
                        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recycler);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
                        recycler.setLayoutManager(layoutManager);
                        recycler.hasFixedSize();
                        ProjectsListAdapter adapter = new ProjectsListAdapter(ctx, projects);
                        recycler.setAdapter(adapter);
                        adapter.setOnItemClickListener(onItemClickListener);


                        //Show a dialog with a list of current projects if the projects obtained are more than one.

                        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(ctx, R.style.AppDialog);
                        alertDialog.setCancelable(false);
                        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                post.showAlertDialog(ctx, null, "You did not make any selection");
                            }
                        }).setView(view);
                        dialog = alertDialog.create();
                        dialog.show();
                    }

                } else {
                    post.showAlertDialog(ctx, "Server error", "Couldn't load app projects! Something went wrong :(");
                }

            }


        }

        ProjectsListAdapter.OnItemClickListener onItemClickListener = new ProjectsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Projects _project = projects.get(position);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("project_name", _project.getProjectName());
                editor.putInt("project_id", _project.getProjectId());
                editor.putInt("project_owner_id", _project.getProjectOwnerId());
                editor.putString("project_date_created", _project.getDateCreated());
                editor.putString("project_date_updated", _project.getDateUpdated());
                editor.apply();

                Log.i(TAG, _project.getProjectName() + " was clicked!");
                sendPostLoginRequest(uid, projects.get(position).getTeamId(), projects.get(position).getProjectId());


            }
        };


        public void sendPostLoginRequest(int uid, int tid, int mid) {

            Intent intent = new Intent(ctx, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ctx.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);




            progress.setTitle("Please wait");
            progress.setMessage("Doing more stuff...");

            String postProjectSelectedUrl = "http://188.166.30.140/gfcare/api/users/context/" + uid + "/" + tid + "/" + mid;
            String responsePostJsonString = post.sendPostProjectSelection(postProjectSelectedUrl);


            Log.i(TAG, "Post url is " + postProjectSelectedUrl);
            Log.i(TAG, "Post url response is " + responsePostJsonString);

            //test
            responsePostJsonString = "OK";
            prefsEditor = prefs.edit();

            prefsEditor.putString("username", email);
            prefsEditor.putString("password", password);

            switch (responsePostJsonString) {

                case "OK":
                    //log the user in

                    prefsEditor.putBoolean("isSignedIn", true);
                    prefsEditor.putBoolean("isFirstSignIn", false);

                    prefsEditor.apply();
                    ctx.startActivity(intent);
                    ctx.finish();
                    break;

                default:
                    //Something happened, check if the user has signed in before

                    if (prefs.getBoolean("isFirstSignIn", true)) {//first time ever user signed in, let user try again later
                        post.showAlertDialog(ctx, "Uh-oh! Something happened!", "Couldn't get modules. Please try again later");

                    } else { //user has signed in before, just sign in anyway :)

                        prefsEditor.putBoolean("isSignedIn", true);
                        prefsEditor.putBoolean("isFirstSignIn", false);
                        prefsEditor.apply();
                        ctx.startActivity(intent);
                        ctx.finish();



                    }
            }
        }
    }
}