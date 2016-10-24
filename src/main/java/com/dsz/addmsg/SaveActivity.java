package com.dsz.addmsg;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dsz.db.RestaurantDataSource;
import com.dsz.db.RestaurantModel;
import com.dsz.db2.AddNameBean;
import com.dsz.db2.AddNameSql;

import java.util.List;


public class SaveActivity extends Activity {
    private RestaurantDataSource datasource;
    private AddNameSql addNameSql;
    ArrayAdapter<RestaurantModel> arrayAdapter;
    ArrayAdapter<AddNameBean> addArray;
    ListView listView;
    TextView hidden_id;
    EditText edtName, edtAddress;
    RadioGroup typeGroup;
    Button btnSave;
    String address;

    public String names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        datasource = new RestaurantDataSource(this);
        datasource.open();


        addNameSql = new AddNameSql(SaveActivity.this);
        addNameSql.open();


        List<RestaurantModel> listRestaurant = datasource.getAllRestaurants();
        List<AddNameBean> listBean = addNameSql.getAllRestaurants();


        listView = (ListView) findViewById(R.id.listView1);
        hidden_id = (TextView) findViewById(R.id.hidden_id);
        edtName = (EditText) findViewById(R.id.txtName);
        edtAddress = (EditText) findViewById(R.id.txtAddress);
        typeGroup = (RadioGroup)findViewById(R.id.typeGroup);
        btnSave = (Button)findViewById(R.id.btnSave);

        arrayAdapter = new ArrayAdapter<RestaurantModel>(this, android.R.layout.simple_list_item_1, listRestaurant);




        addArray = new ArrayAdapter<AddNameBean>(this, android.R.layout.simple_list_item_1, listBean);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestaurantModel objModel = (RestaurantModel)parent.getAdapter().getItem(position);
                edtName.setText(objModel.getName());
                hidden_id.setText(Long.toString(objModel.getId()));
                edtAddress.setText(objModel.getAddress());
                typeGroup.check(objModel.getType());
                btnSave.setText(R.string.update);
                /*Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                intent.putExtra("Name", objModel.getName());
                intent.putExtra("Address", objModel.getAddress());
                startActivity(intent);*/
            }
        });
    }

    /**
     * 点击保存或更新
     * @param v
     */
    RestaurantModel obj = new RestaurantModel();
    AddNameBean addNameBean = new AddNameBean();
    public void onClickSave(View v) {
//        final String[] user = new String[1];
        if(!edtName.getText().toString().isEmpty() && !edtAddress.getText().toString().isEmpty()) {

            address = edtAddress.getText().toString();
            Context context = getApplicationContext();
            Toast toast;
            if(getString(R.string.save).equals(btnSave.getText().toString())){

                {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
                    final EditText etUsername = (EditText) alertLayout.findViewById(R.id.et_username);
                    final EditText etPassword = (EditText) alertLayout.findViewById(R.id.et_password);
                    final CheckBox cbShowPassword = (CheckBox) alertLayout.findViewById(R.id.cb_show_password);

                    cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                // to encode password in dots
                                etPassword.setTransformationMethod(null);
                            } else {
                                // to display the password in normal text
                                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            }
                        }
                    });

                    android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this);
                    alert.setTitle("Login");
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setCancelable(false);
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alert.setPositiveButton("Login", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String user = etUsername.getText().toString();
                            String pass = etPassword.getText().toString();
                            Toast.makeText(getBaseContext(), "Username: " + user + " Password: " + pass, Toast.LENGTH_SHORT).show();
//                            Intent in = new Intent();
//                            in.putExtra("user",user);
//                            addNameSql = new AddNameSql(SaveActivity.this);
//                            addNameSql.open();

                            addNameBean = addNameSql.createRestaurant(user);


//                            obj = datasource.createRestaurant(user,address , typeGroup.getCheckedRadioButtonId());
//                    toast = Toast.makeText(context, edtName.getText().toString() + " has been inserted", Toast.LENGTH_SHORT);
//                    toast.show();
//                            Toast.makeText(SaveActivity.this, "nahoa" +address, Toast.LENGTH_SHORT).show();
                            addArray.add(addNameBean);


                        }
                    });
                    android.support.v7.app.AlertDialog dialog = alert.create();
                    dialog.show();


                }



//                EditDialog editDialog = new EditDialog(this);
//                editDialog.show();
//                editDialog.setOnPosNegClickListener(new EditDialog.OnPosNegClickListener() {
//                    @Override
//                    public void posClickListener(String value) {
//                        Toast.makeText(SaveActivity.this, "确定", Toast.LENGTH_SHORT).show();
////                        mTv.setText(value);
//                        names = value;
//                    }
//
//                    @Override
//                    public void negCliclListener(String value) {
//                        Toast.makeText(SaveActivity.this, "取消", Toast.LENGTH_SHORT).show();
////                        mTv.setText(value);
//                        names = value;
//                    }
//                });



//                Intent is = getIntent();
//                String str = is.getStringExtra("user");
//                Log.d("数据",str);
                obj = datasource.createRestaurant(edtName.getText().toString(), edtAddress.getText().toString(), typeGroup.getCheckedRadioButtonId());
////                    toast = Toast.makeText(context, edtName.getText().toString() + " has been inserted", Toast.LENGTH_SHORT);
////                    toast.show();
                arrayAdapter.add(obj);


            }
            else if (getString(R.string.update).equals(btnSave.getText().toString())){
                obj.setId(Long.parseLong(hidden_id.getText().toString()));
                obj.setName(edtName.getText().toString());
                obj.setAddress(edtAddress.getText().toString());
                obj.setType(typeGroup.getCheckedRadioButtonId());
                datasource.updateRestaurant(obj);
                toast = Toast.makeText(context, "Record has been updated", Toast.LENGTH_SHORT);
                toast.show();
                List<RestaurantModel> listRestaurant = datasource.getAllRestaurants();
                arrayAdapter = new ArrayAdapter<RestaurantModel>(this, android.R.layout.simple_list_item_1, listRestaurant);
                listView.setAdapter(arrayAdapter);
            }
            arrayAdapter.notifyDataSetChanged();

            onClickReset(v);
        }
        else {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Both name and address are required.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * 点击重置
     * @param v
     */
    public void onClickReset(View v) {
        edtName.setText("");
        edtAddress.setText("");
        typeGroup.check(R.id.takeout);
        btnSave.setText(R.string.save);
        hidden_id.setText("");
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
