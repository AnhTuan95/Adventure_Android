package studio.crazybt.adventure.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import studio.crazybt.adventure.R;
import studio.crazybt.adventure.activities.HomePageActivity;
import studio.crazybt.adventure.libs.ApiConstants;
import studio.crazybt.adventure.models.User;
import studio.crazybt.adventure.services.MySingleton;
import studio.crazybt.adventure.utils.JsonUtil;
import studio.crazybt.adventure.utils.RLog;
import studio.crazybt.adventure.utils.SharedPref;
import studio.crazybt.adventure.utils.ToastUtil;

/**
 * Created by Brucelee Thanh on 26/08/2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    @BindView(R.id.etPhoneEmailLogin)
    EditText etPhoneEmailLogin;
    @BindView(R.id.etPasswordLogin)
    TextInputEditText etPasswordLogin;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.tilPasswordLayoutLogin)
    TextInputLayout tilPasswordLayoutLogin;
    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_login, container, false);
        }
        ButterKnife.bind(this, rootView);
        realm = Realm.getDefaultInstance();
        btnLogin.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        Bundle bundle = this.getArguments();
        String memory;
        if(bundle != null){
            memory = bundle.getString("phoneNumber");
        }else{
            memory = SharedPref.getInstance(getContext()).getString(ApiConstants.KEY_PHONE_NUMBER_EMAIL, "");
        }
        if(memory != ""){
            etPhoneEmailLogin.setText(memory);
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                final String token= FirebaseInstanceId.getInstance().getToken();
                btnLogin.setClickable(false);
                final ApiConstants apiConstants = new ApiConstants();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConstants.getUrl(ApiConstants.API_NORMAL_LOGIN),
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonUtil jsonUtil = new JsonUtil();
                        JSONObject jsonObject = jsonUtil.createJSONObject(response);
                        int code = jsonUtil.getInt(jsonObject, apiConstants.DEF_CODE, -4);
                        RLog.i(code);
                        if(code == 1){
                            String id;
                            String email;
                            String phoneNumber;
                            String firstName;
                            String lastName;
                            int gender;
                            String address;
                            String religion;
                            String intro;
                            String idFacebook;
                            String avatar;
                            String cover;
                            String createdAt;
                            String latestActive;
                            jsonObject = jsonUtil.getJSONObject(jsonObject, apiConstants.DEF_DATA);
                            SharedPref.getInstance(getContext()).putString(apiConstants.KEY_ID, jsonUtil.getString(jsonObject, apiConstants.KEY_ID, ""));
                            SharedPref.getInstance(getContext()).putString(apiConstants.KEY_TOKEN, jsonUtil.getString(jsonObject, apiConstants.KEY_TOKEN, ""));
                            SharedPref.getInstance(getContext()).putString(apiConstants.KEY_PHONE_NUMBER_EMAIL, etPhoneEmailLogin.getText().toString());
                            SharedPref.getInstance(getContext()).putString(apiConstants.KEY_FIRST_NAME, jsonUtil.getString(jsonObject, apiConstants.KEY_FIRST_NAME, ""));
                            SharedPref.getInstance(getContext()).putString(apiConstants.KEY_LAST_NAME, jsonUtil.getString(jsonObject, apiConstants.KEY_LAST_NAME, ""));

                            id = JsonUtil.getString(jsonObject, ApiConstants.KEY_ID, "");
                            email = JsonUtil.getString(jsonObject, ApiConstants.KEY_EMAIL, "");
                            phoneNumber = JsonUtil.getString(jsonObject, ApiConstants.KEY_PHONE_NUMBER, "");
                            firstName = JsonUtil.getString(jsonObject, ApiConstants.KEY_FIRST_NAME, "");
                            lastName = JsonUtil.getString(jsonObject, ApiConstants.KEY_LAST_NAME,"");
                            gender=JsonUtil.getInt(jsonObject,ApiConstants.KEY_GENDER, 0);
                            address = JsonUtil.getString(jsonObject,ApiConstants.KEY_ADDRESS, "");
                            religion=JsonUtil.getString(jsonObject,ApiConstants.KEY_RELIGION, "");
                            intro = JsonUtil.getString(jsonObject,ApiConstants.KEY_INTRO, "");
                            idFacebook = JsonUtil.getString(jsonObject, ApiConstants.KEY_ID_FACEBOOK, "");
                            avatar = JsonUtil.getString(jsonObject, ApiConstants.KEY_AVATAR, "");
                            cover = JsonUtil.getString(jsonObject, ApiConstants.KEY_COVER, "");
                            createdAt=JsonUtil.getString(jsonObject, ApiConstants.KEY_CREATED_AT, "");
                            latestActive=JsonUtil.getString(jsonObject, ApiConstants.KEY_LATEST_ACTIVE, "");

                            realm.beginTransaction();
                            RealmResults<User> userRealmResults = realm.where(User.class).findAll();
                            userRealmResults.deleteAllFromRealm();
                            User user = new User(id, firstName, lastName, "", email, phoneNumber, gender, "birthday", address, religion,
                                    intro, idFacebook, avatar, cover, createdAt, latestActive);
                            User userRealm = realm.copyToRealmOrUpdate(user);
                            realm.insertOrUpdate(userRealm);
                            realm.commitTransaction();

                            Toast.makeText(getContext(), getResources().getString(R.string.login_success_loginviaemail), Toast.LENGTH_LONG).show();
                            Intent homePageIntent = new Intent(getActivity(), HomePageActivity.class);
                            startActivity(homePageIntent);
                            getActivity().finish();
                        }else{
                            if(code == -1 || code == -3){
                                etPhoneEmailLogin.setError(getResources().getString(R.string.error_phonenumber_email_et_loginviaemail));
                                btnLogin.setClickable(true);
                            }else{
                                tilPasswordLayoutLogin.setError(getResources().getString(R.string.error_password_et_loginviaemail));
                                btnLogin.setClickable(true);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put(apiConstants.KEY_PHONE_NUMBER_EMAIL, etPhoneEmailLogin.getText().toString());
                        params.put(apiConstants.KEY_PASSWORD, etPasswordLogin.getText().toString());
                        params.put(apiConstants.KEY_FCM_TOKEN, token);
                        return params;
                    }
                };
                MySingleton.getInstance(this.getContext()).addToRequestQueue(stringRequest, false);
                break;
            case R.id.tvForgetPassword:
                break;
        }
    }
}
