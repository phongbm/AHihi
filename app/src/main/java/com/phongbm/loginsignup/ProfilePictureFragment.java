package com.phongbm.loginsignup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.phongbm.ahihi.MainActivity;
import com.phongbm.ahihi.R;
<<<<<<< HEAD
import com.phongbm.common.CommonMethod;
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
import com.phongbm.libs.SquareImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePictureFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    private static final String TAG = "ProfilePictureFragment";

    private View view;
    private GridView gridViewAvatarDefault;
    private CircleImageView imgAvatar;
    private int[] avatarDefaultIDs = new int[]{R.drawable.ic_ava_1, R.drawable.ic_ava_2,
            R.drawable.ic_ava_3, R.drawable.ic_ava_4, R.drawable.ic_ava_5, R.drawable.ic_ava_6,
            R.drawable.ic_ava_7, R.drawable.ic_ava_8, R.drawable.ic_ava_9, R.drawable.ic_ava_10,
            R.drawable.ic_ava_11, R.drawable.ic_ava_12, R.drawable.ic_avatar_default};
    private LayoutInflater layoutInflater;
    private AppCompatButton btnOK;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        layoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_picture, null);
        initializeComponent();
        return view;
    }

    private void initializeComponent() {
        btnOK = (AppCompatButton) view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);
        imgAvatar = (CircleImageView) view.findViewById(R.id.imgAvatar);
        gridViewAvatarDefault = (GridView) view.findViewById(R.id.gridViewAvatarDefault);
        gridViewAvatarDefault.setOnItemClickListener(this);
        gridViewAvatarDefault.setAdapter(new AvatarDefaultAdapter());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOK:
                final String fullName = ((MainFragment) getActivity()).getProfileInfomationFragment().getFullName();
                final String email = ((MainFragment) getActivity()).getProfileInfomationFragment().getEmail();
                final String birthday = ((MainFragment) getActivity()).getProfileInfomationFragment().getBirthday();
                final boolean sex = ((MainFragment) getActivity()).getProfileInfomationFragment().getSex();

                ParseUser newUser = ParseUser.getCurrentUser();

                newUser.put("fullName", fullName);
                newUser.setEmail(email);
                newUser.put("birthday", birthday);
                newUser.put("sex", sex);
                newUser.saveInBackground();

                imgAvatar.buildDrawingCache();
                Bitmap avatar = imgAvatar.getDrawingCache();
<<<<<<< HEAD
                CommonMethod.uploadAvatar(newUser, avatar);
=======
                uploadAvatar(newUser, avatar);
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5

                Intent intent = new Intent(this.getActivity(), MainActivity.class);
                this.getActivity().startActivity(intent);
                this.getActivity().finish();
                break;
        }
    }

<<<<<<< HEAD

=======
    private void uploadAvatar(ParseUser parseUser, Bitmap avatar) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (bytes != null) {
            ParseFile parseFile = new ParseFile(bytes);
            parseUser.put("avatar", parseFile);
            parseUser.saveInBackground();
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        imgAvatar.setImageResource(avatarDefaultIDs[position]);
    }

    private class AvatarDefaultAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return avatarDefaultIDs.length;
        }

        @Override
        public Integer getItem(int position) {
            return avatarDefaultIDs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_image, parent, false);
            }
            SquareImageView imgImage = (SquareImageView) convertView.findViewById(R.id.imgImage);
            imgImage.setImageResource(avatarDefaultIDs[position]);
            return convertView;
        }
    }

}