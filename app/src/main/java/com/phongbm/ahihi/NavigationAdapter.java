package com.phongbm.ahihi;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private LayoutInflater layoutInflater;
    private ArrayList<NavigationItem> navigationItems;
    private Handler handler;

    private int userAvatar = R.drawable.avatar_default;
    private String userName = "Phong Minh Bui", userEmail = "phongbm.it@gmail.com";

    public NavigationAdapter(Context context, Handler handler) {
        this.handler = handler;
        this.layoutInflater = LayoutInflater.from(context);
        this.initializeArrayListItemNavigation();
    }

    private void initializeArrayListItemNavigation() {
        String[] menuNames = new String[]{"Notifications", "Privacy Settings", "Settings", "About",
                "Account Management"};
        int[] menuIcons = new int[]{R.drawable.ic_menu_news, R.drawable.ic_menu_privacy_settings,
                R.drawable.ic_menu_settings, R.drawable.ic_menu_about,
                R.drawable.ic_menu_user,};
        navigationItems = new ArrayList<NavigationItem>();
        for (int i = 0; i < menuNames.length; i++) {
            navigationItems.add(new NavigationItem(menuIcons[i], menuNames[i]));
        }
    }

    @Override
    public int getItemCount() {
        return navigationItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    @Override
    public NavigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = layoutInflater.inflate(R.layout.item_navigation, parent, false);
            NavigationViewHolder navigationViewHolder = new NavigationViewHolder(view, viewType);
            return navigationViewHolder;
        } else {
            if (viewType == TYPE_HEADER) {
                View view = layoutInflater.inflate(R.layout.header_navigation, parent, false);
                NavigationViewHolder navigationViewHolder = new NavigationViewHolder(view, viewType);
                return navigationViewHolder;
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(NavigationViewHolder navigationViewHolder, int position) {
        if (navigationViewHolder.idHolder == TYPE_ITEM) {
            navigationViewHolder.imgIconMenu.setImageResource(navigationItems.get(position - 1).getIdIconMenu());
            navigationViewHolder.txtNameMenu.setText(navigationItems.get(position - 1).getNameMenu());
        } else {
            if (navigationViewHolder.idHolder == TYPE_HEADER) {
                navigationViewHolder.imgAvatar.setImageResource(userAvatar);
                navigationViewHolder.txtName.setText(userName);
                navigationViewHolder.txtEmail.setText(userEmail);
            }
        }
        return;
    }

    public class NavigationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int idHolder;
        private ImageView imgAvatar;
        private TextView txtName, txtEmail;
        private ImageView imgIconMenu;
        private TextView txtNameMenu;

        public NavigationViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == TYPE_ITEM) {
                imgIconMenu = (ImageView) itemView.findViewById(R.id.imgIconMenu);
                txtNameMenu = (TextView) itemView.findViewById(R.id.txtNameMenu);
                idHolder = TYPE_ITEM;
                itemView.setOnClickListener(this);
            } else {
                if (viewType == TYPE_HEADER) {
                    imgAvatar = (ImageView) itemView.findViewById(R.id.circleImageViewAvatar);
                    txtName = (TextView) itemView.findViewById(R.id.txtName);
                    txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
                    idHolder = TYPE_HEADER;
                }
            }
        }

        @Override
        public void onClick(View view) {
            Message message = new Message();
            message.what = NavigationViewHolder.this.getPosition() - 1;
            message.setTarget(handler);
            message.sendToTarget();
        }
    }

}