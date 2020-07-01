package cn.booktable.note.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONObject;

import cn.booktable.note.R;
import cn.booktable.note.myApplication;
import cn.booktable.note.network.HttpConstants;
import cn.booktable.note.network.HttpJsonResult;
import cn.booktable.note.network.HttpRequest;
import cn.booktable.note.viewmodel.user.User;
import cn.booktable.note.viewmodel.user.UserProfileViewModel;
import cn.booktable.note.viewmodel.user.UserRepository;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_RES_USERNAME = "androidx.appcompat.app.AppCompatActivity.userName";
    public static final String EXTRA_RES_PASSWORD = "androidx.appcompat.app.AppCompatActivity.password";

    private EditText mEditUIDView;
    private EditText mEditPWDView;
    private EditText mEditCaptchaView;
    private ConstraintLayout mLoginFormView;
    private ProgressBar mProgressBar;
    private ImageView mImageCaptcha;
    private Button mLoginButton;
    private Context mContext;

    private UserProfileViewModel mUserProfileViewModel;

    private UserLoginTask mAuthTask = null;
    private String errorMsg=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEditUIDView = findViewById(R.id.edit_uid);
        mEditPWDView=findViewById(R.id.edit_pwd);
        mImageCaptcha=findViewById(R.id.img_captcha);
        mEditCaptchaView=findViewById(R.id.edit_captcha);
        mLoginFormView=findViewById(R.id.login_container);
        mProgressBar=findViewById(R.id.login_progress);
        mContext=this;

        Glide.with(this)
                .load(HttpRequest.getAbsoluteApiUrl("captcha?uuid="+HttpRequest.SERVER_UUID))
                .placeholder(R.drawable.ic_captcha)//图片加载出来前，显示的图片
                .into(mImageCaptcha);

        final ImageView btnPwdEye=findViewById(R.id.btn_pwd_eye);

        btnPwdEye.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                int inputType=mEditPWDView.getInputType();
                if(inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    mEditPWDView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnPwdEye.setImageResource(R.drawable.ic_eye_black_24dp);
                }else{
                    mEditPWDView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnPwdEye.setImageResource(R.drawable.ic_eye_off_black_24dp);
                }
                mEditPWDView.setSelection(mEditPWDView.getText().toString().length());
            }
        });

        mImageCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(mContext)
                        .load(HttpRequest.getAbsoluteApiUrl("captcha?uuid="+HttpRequest.SERVER_UUID))
                        .placeholder(R.drawable.ic_captcha)//图片加载出来前，显示的图片
                        .into(mImageCaptcha);
            }
        });
        mLoginButton = findViewById(R.id.button_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                attemptLogin();
//                Intent replyIntent = new Intent();
//                if (TextUtils.isEmpty(mEditUIDView.getText())) {
//                   // setResult(RESULT_CANCELED, replyIntent);
//                    testReq();
//                } else {
//                    loginReq();
//                    //setResult(RESULT_OK, replyIntent);
//                }
              //  finish();
            }
        });

        if(savedInstanceState==null)
        {
            mUserProfileViewModel=new ViewModelProvider(this).get(UserProfileViewModel.class);
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void hideShowKeyboard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
//        if (imm.isActive()) {//如果开启
//            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
////            imm.showSoftInput(mLoginButton,0);
//        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(mLoginButton.getWindowToken(),0);
        }
    }

    private void attemptLogin()
    {
        showProgress(true);
        try {
            hideShowKeyboard();

            String uid = mEditUIDView.getText().toString();
            if(uid==null || uid.trim().length()<1)
            {
                showProgress(false);
                showTipsMessage("登录账号不能为空");
                return ;
            }
            String pwd = mEditPWDView.getText().toString();
            if(pwd==null || pwd.trim().length()<1)
            {
                showProgress(false);
                showTipsMessage("登录密码不能为空");
                return ;
            }
            String captcha = mEditCaptchaView.getText().toString();
            if(captcha==null || captcha.trim().length()<1)
            {
                showProgress(false);
                showTipsMessage("登录验证码不能为空");
                return ;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", HttpRequest.SERVER_UUID);
            jsonObject.put("captcha",captcha.trim());
            jsonObject.put("userName",uid.trim());
            jsonObject.put("password",pwd.trim());
            HttpRequest.post("login", jsonObject.toString(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        mAuthTask = new UserLoginTask(true,statusCode,headers, responseBody);
                        mAuthTask.execute((Void) null);
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    try {
                        mAuthTask = new UserLoginTask(false,statusCode,headers, responseBody);
                        mAuthTask.execute((Void) null);
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
        }catch (Exception ex)
        {
            ex.printStackTrace();
            showTipsMessage(ex.getMessage());
            showProgress(false);
        }

    }

    private void showTipsMessage(String msg)
    {
        if(msg!=null) {
            Snackbar.make(findViewById(R.id.login_container), msg, 3000).show();
        }
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        int statusCode;
        Header[] headers;
        byte[] responseBody;
        boolean isSuccess;

        public UserLoginTask(boolean isSuccess,int statusCode, Header[] headers, byte[] responseBody)
        {
            this.isSuccess=isSuccess;
            this.statusCode=statusCode;
             this.headers=headers;
             this.responseBody=responseBody;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {

                if(!isSuccess)
                {
                    switch (statusCode)
                    {
                        case 408:
                        case 504:{
                            errorMsg="网络请求超时("+statusCode+")";
                            return false;
                        }
                        case 404:{
                            errorMsg="网络服务错误("+statusCode+")";
                            return false;
                        }
                    }

                    if(responseBody!=null && responseBody.length>0)
                    {
                        String errorDetail=new String(responseBody);
                        errorMsg=errorDetail;
                        return false;
                    }
                    errorMsg="登录失败("+statusCode+")";
                    return false;
                }
                HttpJsonResult jsonResult=HttpJsonResult.getInstance(statusCode, headers, responseBody);
                if(jsonResult.okResult())
                {
                    JSONObject data=jsonResult.getData();
                    if(data!=null)
                    {
                        JSONObject ssoToken=  data.getJSONObject("ssoToken");
                        if(ssoToken!=null)
                        {
                            String token= ssoToken.getString("token");
                            if(token!=null && !token.isEmpty())
                            {
                                String userId=data.getString("userId");
                                String userName=data.getString("userName");
                                if(userId==null || userId.length()<1 || userName==null || userName.length()<1 || token==null || token.length()<1)
                                {
                                    errorMsg="登录失败";
                                }
                                User user=new User();
                                user.setUserName(userName);
                                user.setToken(token);
                                user.setId(userId);
                                UserRepository userRepository= mUserProfileViewModel.userRepository();
                                userRepository.saveUser(user,true);
                                User dbUser= userRepository.findUserById(user.getId());
                                User topUser=userRepository.topUser();
                                if(dbUser!=null && topUser!=null && dbUser.getId()!=null && dbUser.getId().equals(topUser.getId())){//登录成功情况下
                                    myApplication.setToken(token);
                                    return true;
                                }else{//登录保存失败
                                    errorMsg="登录失败(ERROR:002)";
                                }
                            }
                        }
                    }
                }else{
                    errorMsg=jsonResult.getMsg();
                }

            }catch (Exception ex)
            {
                ex.printStackTrace();
                errorMsg=ex.getMessage();
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Intent replyIntent = new Intent();
                setResult(RESULT_OK, replyIntent);
                finish();
            } else {
                showTipsMessage(errorMsg);

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}