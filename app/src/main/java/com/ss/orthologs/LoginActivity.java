package com.ss.orthologs;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class LoginActivity extends AppCompatActivity {
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermissions(Arrays.asList(permissions), 001)) {
                //do nothing
            }
        }

        Button login_btn = findViewById(R.id.login_button);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    /**
     * responsible for checking if permissions are granted. In case permissions are not granted, the user will be requested and the method returns false. In case we have all permissions, the method return true.
     * The response of the request for the permissions is going to be handled in the onRequestPermissionsResult() method
     *
     * @param permissions list of permissions to be checked if are granted onRequestPermissionsResult().
     * @param requestCode request code to identify this request in
     * @return true case we already have all permissions. false in case we had to prompt the user for it.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkPermissions(List<String> permissions, int requestCode) {
        List<String> permissionsNotGranted = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                permissionsNotGranted.add(permission);
        }

        //If there is any permission we don't have (it's going to be in permissionsNotGranted List) , we need to request.
        if (!permissionsNotGranted.isEmpty()) {
            requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]), requestCode);
            return false;
        }
        return true;
    }

    /**
     * called after permissions are requested to the user. This is called always, either
     * has granted or not the permissions.
     *
     * @param requestCode  int code used to identify the request made. Was passed as parameter in the
     *                     requestPermissions() call.
     * @param permissions  Array containing the permissions asked to the user.
     * @param grantResults Array containing the results of the permissions requested to the user.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 001: {
                boolean anyPermissionDenied = false;
                boolean neverAskAgainSelected = false;
                // Check if any permission asked has been denied
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        anyPermissionDenied = true;
                        //check if user select "never ask again" when denying any permission
                        if (!shouldShowRequestPermissionRationale(permissions[i])) {
                            neverAskAgainSelected = true;
                        }
                    }
                }
                if (!anyPermissionDenied) {
                    // All Permissions asked were granted! Yey!
                    // DO YOUR STUFF
                } else {
                    // the user has just denied one or all of the permissions
                    // use this message to explain why he needs to grant these permissions in order to proceed
                    String message = "";
                    DialogInterface.OnClickListener listener = null;
                    if (neverAskAgainSelected) {
                        //TODO we have to change the text contents
                        //This message is displayed after the user has checked never ask again checkbox.
                        message = getString(R.string.permission_denied_never_ask_again_dialog_message);
                    } else {
                        //TODO we have to change the text contents
                        //This message is displayed while the user hasn't checked never ask again checkbox.
                        message = getString(R.string.permission_denied_dialog_message);
                    }
                    listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //this will be executed if User clicks OK button. This is gonna take the user to the App Settings
                                    startAppSettingsConfigActivity();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    LoginActivity.this.finish();
                                    break;
                            }
                        }
                    };

                    Dialog dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
                            .setMessage(message)
                            .setPositiveButton(getString(R.string.label_Ok), listener)
                            .setNegativeButton(getString(R.string.label_cancel), listener)
                            .create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * start the App Settings Activity so that the user can change
     * settings related to the application such as permissions.
     */
    private void startAppSettingsConfigActivity() {
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + this.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        this.startActivity(i);
        finish();
    }
}
