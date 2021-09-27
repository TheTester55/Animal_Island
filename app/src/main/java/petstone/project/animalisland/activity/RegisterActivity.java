package petstone.project.animalisland.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import petstone.project.animalisland.R;

//회원가입
public class RegisterActivity extends AppCompatActivity {
    //선언부
    FirebaseAuth auth;

    //회원가입 버튼
    Button btn_register;
    //아이디
    EditText et_id;
    String id = "";
    //아이디 중복확인
    Button validateButton;

    //패스워드
    EditText et_pw;
    String password = "";
    //패스워드 중복확인 입력
    EditText et_pwck;
    String password_check = "";
    //닉네임
    EditText et_name;
    String nickname = "";
    //닉네임 중복확인
    Button validateButton2;

    //성별
    EditText et_sex;
    Sex sex = Sex.male;
    enum Sex {
        male, female
    }

    //나이
    EditText et_age;
    int age = 0;

    //이메일
    EditText et_mail;
    String email = "";

    //화면 초기화
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create);

        auth = FirebaseAuth.getInstance();

        btn_register = findViewById(R.id.btn_register);
        et_id = findViewById(R.id.et_id);
        validateButton = findViewById(R.id.validateButton);
        et_pw = findViewById(R.id.et_pw);
        et_pwck = findViewById(R.id.et_pwck);
        et_name = findViewById(R.id.et_name);
        validateButton2 = findViewById(R.id.validateButton2);
        et_sex = findViewById(R.id.et_sex);
        et_age = findViewById(R.id.et_age);
        et_mail = findViewById(R.id.et_mail);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 가져오기
                try {
                    id = et_id.getText().toString();
                    password = et_pw.getText().toString();
                    password_check = et_pwck.getText().toString();
                    nickname = et_name.getText().toString();
                    //TODO: 성별 아직 안함
                    //sex = ;
                    age = Integer.parseInt(et_age.getText().toString());
                    email = et_mail.getText().toString();
                    //로그인 정보 일치
                    if (password.equals(password_check) && email.contains("@")) {
                        //로그인 정보 일치 O
                        //회원 생성
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            //회원가입 성공
                                            Log.d("Success", "createUserWithEmail:success");
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            //회원가입 실패
                                            Log.w("Fail", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(RegisterActivity.this, "내용을 정확히 입력해주세요.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        //로그인 정보 일치 X
                    }
                } catch () {
                    //무언가 하나라도 빈칸이면 회원가입 실패
                    Toast.makeText(RegisterActivity.this, "내용을 정확히 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
