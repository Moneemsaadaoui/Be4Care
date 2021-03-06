package rrdl.be4care.Views.Activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import rrdl.be4care.Controllers.SignupService;
import rrdl.be4care.R;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button login, validateBtn;
    private EditText mPhone;
    private EditText mEmail,mPassword;
    private JSONObject request;
    private String AccessToken;
    SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mEmailView=findViewById(R.id.email);
        mPasswordView=findViewById(R.id.password);
        mPhone=findViewById(R.id.phonenumber);

        final CircularProgressButton circularProgressButton=findViewById(R.id.signupbtn);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //Change status bar color

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#ffcd7f"));
        }
     /*   Intent intent=new Intent(LoginActivity.this,WelcomeActivity.class);
        startActivity(intent);*/
        // Set up the login form.

        final CheckBox terms=findViewById(R.id.termsbox);
        circularProgressButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!terms.isChecked())
                {
                    Toast.makeText(SignUpActivity.this, "You have to agree first (placeholder text)", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    circularProgressButton.startAnimation();
                    SignupService signupService=new SignupService(mEmailView,mPasswordView,mPhone,circularProgressButton,getBaseContext(),SignUpActivity.this);
                    signupService.signup();
                }
            }
        });


       /* login = findViewById(R.id.login);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SignUpActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.popup_login, null);
                final EditText mEmail=mView.findViewById(R.id.Loginemail);
                final EditText mPassword=mView.findViewById(R.id.Loginpassword);
                Button loginbutton=mView.findViewById(R.id.loginbutton);
                loginbutton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }

        });
    }

*/
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */


    /**
     * Shows the progress UI and hides the login form.
     */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }

        }
    }




}

