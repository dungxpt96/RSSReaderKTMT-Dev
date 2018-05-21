package ktmt.rssreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

import java.util.Arrays;

import ktmt.rssreader.Data.LocalData;

public class FacebookActivity extends AppCompatActivity {
    Button btLogOut;
    LoginButton loginButton;
    EditText edtTitle;
    EditText edtDes;
    EditText edtUrl;
    TextView tvFTitle;
    TextView tvFDes;
    TextView tvFUrl;
    TextView tvTitle;
    Button btOKShare;
    CallbackManager callbackManager;
    ProfilePictureView profilePictureView;
    ShareDialog shareDialog;
    ShareLinkContent shareLinkContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_facebook);
        map();
        shareDialog = new ShareDialog(FacebookActivity.this);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        tvTitle.setText("Chia sẻ lên Facebook");
        logoutActive();
        login_facebook();
        logout_facebook();
        setDefaultContentToShare();
        buttonShareOnClickListener();
    }

    private void setDefaultContentToShare() {
        edtDes.setText("Được chia sẻ từ ứng dụng đọc tin RSS");
        edtTitle.setText("Nhóm 27 - Android 20172");
        edtUrl.setText(LocalData.currentLink);
    }

    private void buttonShareOnClickListener() {
        btOKShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    shareLinkContent = new ShareLinkContent.Builder()
                            .setContentTitle(edtTitle.getText().toString())
                            .setContentDescription(edtDes.getText().toString())
                            .setContentUrl(Uri.parse(edtUrl.getText().toString()))
                            .build();
                }

                shareDialog.show(shareLinkContent);
            }
        });
    }

    private void logout_facebook() {
        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                profilePictureView.setProfileId(null);
                logoutActive();
            }
        });
    }

    private void map() {
        btLogOut = (Button) findViewById(R.id.btLogOut);
        loginButton = (LoginButton) findViewById(R.id.login_button);;
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtDes = (EditText) findViewById(R.id.edtDes);
        edtUrl = (EditText) findViewById(R.id.edtUrl);
        tvFDes = (TextView) findViewById(R.id.tvFDes);
        tvFTitle = (TextView) findViewById(R.id.tvFTitle);
        tvFUrl = (TextView) findViewById(R.id.tvFUrl);
        btOKShare = (Button) findViewById(R.id.btOKShare);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        profilePictureView = (ProfilePictureView) findViewById(R.id.imageProfilePicture);
    }

    private void login_facebook() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("Login Success", "-----");
                loginActive();
                getAvatarFacebook();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Login Error", "-----");
            }
        });
    }

    private void getAvatarFacebook() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("JSON", response.getJSONObject().toString());
                        profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                    }
                });
        graphRequest.executeAsync();
    }

    private void loginActive() {
        loginButton.setVisibility(View.INVISIBLE);
        btLogOut.setVisibility(View.VISIBLE);
        edtTitle.setVisibility(View.VISIBLE);
        edtDes.setVisibility(View.VISIBLE);
        edtUrl.setVisibility(View.VISIBLE);
        tvFDes.setVisibility(View.VISIBLE);
        tvFTitle.setVisibility(View.VISIBLE);
        tvFUrl.setVisibility(View.VISIBLE);
        btOKShare.setVisibility(View.VISIBLE);
    }

    private void logoutActive() {
        loginButton.setVisibility(View.VISIBLE);
        btLogOut.setVisibility(View.INVISIBLE);
        edtTitle.setVisibility(View.INVISIBLE);
        edtDes.setVisibility(View.INVISIBLE);
        edtUrl.setVisibility(View.INVISIBLE);
        tvFDes.setVisibility(View.INVISIBLE);
        tvFTitle.setVisibility(View.INVISIBLE);
        tvFUrl.setVisibility(View.INVISIBLE);
        btOKShare.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
}
